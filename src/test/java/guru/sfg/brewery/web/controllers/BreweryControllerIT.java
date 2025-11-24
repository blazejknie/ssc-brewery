package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BreweryControllerIT extends BaseIT {

    @Test
    void testListBreweriesCustomer() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk())
                .andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
    }

    @Test
    void testListBreweriesAdmin() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().isOk())
                .andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
    }

    @Test
    void testListBreweriesUser() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testListBreweriesUnauthenticated() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void testApiListBreweriesCustomer() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void testApiListBreweriesAdmin() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testApiListBreweriesUser() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testApiListBreweriesUnauthenticated() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());
    }
}
