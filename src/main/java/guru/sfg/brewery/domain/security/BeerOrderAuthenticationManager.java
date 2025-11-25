package guru.sfg.brewery.domain.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BeerOrderAuthenticationManager {

    public boolean customerIdMatches(Authentication authentication, UUID customerId) {

        User authenticatedUser = (User) authentication.getPrincipal();

        if (authenticatedUser.getCustomer() == null) {
            return false;
        }
        log.debug("Auth User CustomerId: " + authenticatedUser.getCustomer().getId() + " Customer Id: " + customerId);

        return authenticatedUser.getCustomer().getId().equals(customerId);
    }
}
