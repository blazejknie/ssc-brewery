package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT{

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/36ebf408-4226-4378-94bf-fa89a53033a4"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUPC() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/012345678905"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerByIdWithAuth() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Api-Key", "spring");
        httpHeaders.add("Api-Secret", "guru");
        mockMvc.perform(delete("/api/v1/beer/36ebf408-4226-4378-94bf-fa89a53033a4").headers(httpHeaders))
                .andExpect(status().isNoContent());

        verify(beerRepository).deleteById(UUID.fromString("36ebf408-4226-4378-94bf-fa89a53033a4"));
    }
}
