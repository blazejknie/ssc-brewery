package guru.sfg.brewery.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RestURLParamsAuthFilter extends CustomAbstractAuthenticationProcessingFilter {

    public RestURLParamsAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected String getUsername(HttpServletRequest request) {
        return Optional.ofNullable(request.getParameter("username")).orElse("");
    }

    protected String getPassword(HttpServletRequest request) {
        return Optional.ofNullable(request.getParameter("password")).orElse("");
    }
}
