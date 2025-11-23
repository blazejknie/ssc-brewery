package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
    void deleteBeerByIdWithCustomAuth() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Api-Key", "spring");
        httpHeaders.add("Api-Secret", "guru");
        mockMvc.perform(delete("/api/v1/beer/36ebf408-4226-4378-94bf-fa89a53033a4").headers(httpHeaders))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerByIdBadCreds() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Api-Key", "spring");
        httpHeaders.add("Api-Secret", "guruXXX");
        mockMvc.perform(delete("/api/v1/beer/36ebf408-4226-4378-94bf-fa89a53033a4").headers(httpHeaders))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerByIdWithCustomParamAuth() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("username", "spring");
        httpHeaders.add("password", "guru");
        mockMvc.perform(delete("/api/v1/beer/36ebf408-4226-4378-94bf-fa89a53033a4").params(httpHeaders))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerByIdBadParamCreds() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("username", "spring");
        httpHeaders.add("params", "guruXXX");
        mockMvc.perform(delete("/api/v1/beer/36ebf408-4226-4378-94bf-fa89a53033a4").params(httpHeaders))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerByIdWithHttpBasic() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/36ebf408-4226-4378-94bf-fa89a53033a4")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void deleteBeerByIdWithNoAuth() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/36ebf408-4226-4378-94bf-fa89a53033a4"))
                .andExpect(status().isUnauthorized());
    }
}
