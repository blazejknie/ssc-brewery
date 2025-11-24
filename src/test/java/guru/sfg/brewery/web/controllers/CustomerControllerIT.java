package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Customer;
import guru.sfg.brewery.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CustomerControllerIT extends BaseIT {

    public static final String TESTING_ROOM = "test room";

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testFindCustomersAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/find")
                        .with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testFindCustomersAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/find")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/findCustomers"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    void testFindCustomersUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/find")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testFindCustomersCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/find")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/findCustomers"))
                .andExpect(model().attributeExists("customer"));
    }
    @Nested
    @DisplayName("SHOW_CUSTOMER")
    public class ShowCustomer {
        Customer testingRoom;


        @BeforeEach
        void setup() {
            testingRoom = Customer.builder()
                    .customerName(TESTING_ROOM)
                    .apiKey(UUID.randomUUID())
                    .build();

            customerRepository.saveAndFlush(testingRoom);
        }

        @Test
        void testShowCustomerAnonymous() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + testingRoom.getId())
                            .with(anonymous()))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void testShowCustomerAdmin() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + testingRoom.getId())
                            .with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("customers/customerDetails"))
                    .andExpect(model().attributeExists("customer"));
        }

        @Test
        void testShowCustomerUser() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + testingRoom.getId())
                            .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void testShowCustomerCustomer() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + testingRoom.getId())
                            .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("customers/customerDetails"))
                    .andExpect(model().attributeExists("customer"));
        }
    }

    @Test
    void testProcessFindFormReturnManyAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testProcessFindFormReturnManyAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testProcessFindFormReturnManyUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testProcessFindFormReturnManyCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testInitCreationFormAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/new")
                        .with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testInitCreationFormAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/new")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testInitCreationFormUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/new")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testInitCreationFormCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/new")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testProcessCreationFormAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/new")
                        .with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testProcessCreationFormAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/new")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testProcessCreationFormUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/new")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testProcessCreationFormCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/new")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isForbidden());
    }
}
