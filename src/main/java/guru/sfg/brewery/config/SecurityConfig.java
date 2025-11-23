package guru.sfg.brewery.config;

import guru.sfg.brewery.security.CustomPasswordEncodeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
        http
                .authorizeRequests(authorized -> {
                    authorized
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**")
                            .permitAll();
                })
                .authorizeRequests().antMatchers("/beers/find", "/beers*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
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
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring")
                .password("{ldap}{SSHA}f/FvltG1KkJAGbFINvXvzyq/3UoguOb7oox5QA==")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{bcrypt15}$2a$15$vXrz0rAwocLHRf.eHahLrOonge5hz/DNhu.9rLc6MNkwD5rYj.tMq")
//                .password("{SSHA}ycF5qsioBB5JqiWUzreuNYpLdpEHUI8Qv0cWJg==")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{sha256}e36f9e44f3acbe164886bc4ca6e549ad7edea83d6fdff25b3be95b9efffd053a43299d331c04fa81")
                .roles("CUSTOMER");
    }
}
