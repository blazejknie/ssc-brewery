package guru.sfg.brewery.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RestHeaderAuthFilter extends CustomAbstractAuthenticationProcessingFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected String getUsername(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Api-Key")).orElse("");
    }

    protected String getPassword(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader("Api-Secret")).orElse("");
    }
}

