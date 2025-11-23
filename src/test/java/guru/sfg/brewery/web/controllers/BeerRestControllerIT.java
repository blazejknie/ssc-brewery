package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT{

    @Autowired
    BeerRepository beerRepository;

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + beer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUPC() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }
    @Nested
    @DisplayName("Delete Tests")
    public class DeleteTests {
        public Beer beerToDelete() {
            Random random = new Random();
            return beerRepository.saveAndFlush(Beer.builder()
                            .beerName("Delete Me Beer")
                            .beerStyle(BeerStyleEnum.PORTER)
                            .minOnHand(12)
                            .quantityToBrew(123)
                            .upc(String.valueOf(random.nextInt(999999999)))
                    .build());
        }

        @Test
        void deleteBeerByIdBadCreds() throws Exception {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Api-Key", "spring");
            httpHeaders.add("Api-Secret", "guruXXX");
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()).headers(httpHeaders))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void deleteBeerHttpBasicAdmin() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("spring", "guru")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void deleteBeerHttpUser() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicCustomer() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerByIdWithNoAuth() throws Exception {
            mockMvc.perform(delete("/api/v1/beer" + beerToDelete().getId()))
                    .andExpect(status().isUnauthorized());
        }
    }

}
