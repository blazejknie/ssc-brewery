package guru.sfg.brewery.config;

import guru.sfg.brewery.security.CustomPasswordEncodeFactory;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestURLParamsAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestURLParamsAuthFilter restURLParamsAuthFilter(AuthenticationManager authenticationManager) {
        RestURLParamsAuthFilter filter = new RestURLParamsAuthFilter(new AntPathRequestMatcher("api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//        return new LdapShaPasswordEncoder();
//        return new StandardPasswordEncoder();
//        return new BCryptPasswordEncoder();
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return CustomPasswordEncodeFactory.createDelegatingPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

        http.addFilterBefore(restURLParamsAuthFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**").permitAll() //do not use in production!
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                            .antMatchers("/beers/find", "/beers*").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin().defaultSuccessUrl("/");

        http.headers().frameOptions().sameOrigin();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

// {noop} is set for no encryption
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("spring")
//                .password("{ldap}{SSHA}f/FvltG1KkJAGbFINvXvzyq/3UoguOb7oox5QA==")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{bcrypt10}$2a$10$HuROHgb7yjszurF8mcTwUOlykMDoRVyGNwW2DNtS3v4pixqmZwvri")
////                .password("{SSHA}ycF5qsioBB5JqiWUzreuNYpLdpEHUI8Qv0cWJg==")
//                .roles("USER")
//                .and()
//                .withUser("scott")
//                .password("{sha256}e36f9e44f3acbe164886bc4ca6e549ad7edea83d6fdff25b3be95b9efffd053a43299d331c04fa81")
//                .roles("CUSTOMER");
//    }
}
