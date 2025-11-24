package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BeerControllerIT extends BaseIT{

    @Test
    void findBeersUNAUTHORIZED() throws Exception {
        mockMvc.perform(get("/beers/find").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeersADMIN() throws Exception {
        mockMvc.perform(get("/beers/find")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void findBeersUSER() throws Exception {
        mockMvc.perform(get("/beers/find")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void findBeersCUSTOMER() throws Exception {
        mockMvc.perform(get("/beers/find")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    //creating "some_user" for our test
    @WithMockUser("some_user")
    @Test
    @Disabled
    void findBeers() throws Exception {
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void findBeersWithAnonymous() throws Exception {
        mockMvc.perform(get("/beers/find").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeersWithHttpBadCredentials() throws Exception {
        mockMvc.perform(get("/beers/find").with(httpBasic("foo", "bar")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void initCreationFormUNAUTHORIZED() throws Exception {
        mockMvc.perform(get("/beers/new"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void initCreationFormADMIN() throws Exception {
        mockMvc.perform(get("/beers/new").with(httpBasic("spring", "guru")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void initCreationFormUSER() throws Exception {
        mockMvc.perform(get("/beers/new").with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void initCreationFormCUSTOMER() throws Exception {
        mockMvc.perform(get("/beers/new")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isForbidden());
    }

    @Test
    void initCreationFormWithAuth2() throws Exception {
        mockMvc.perform(get("/beers/new")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void initCreationFormWithAuth3() throws Exception {
        mockMvc.perform(get("/beers/new")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }
}