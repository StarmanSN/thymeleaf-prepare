package ru.gb.thymeleafprepare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(
                (requests) -> {
                    requests.antMatchers("/product/all").permitAll();
                    requests.antMatchers("/").permitAll();
                    requests.antMatchers(HttpMethod.GET, "/product").hasAuthority("product.create");
                    requests.mvcMatchers(HttpMethod.GET, "/product/{productId}").permitAll();
                    requests.antMatchers(HttpMethod.POST, "/product").hasAuthority("product.create");
                }
        );
        http.authorizeRequests((requests) -> {
            ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) requests.anyRequest()).authenticated();
        });
        http.exceptionHandling().accessDeniedPage("/access-denied");
        http.formLogin();
        http.httpBasic();
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("$2a$10$tRPiN1GMOsjVwoeOB3hli.jbwmkaorBXTmgiIzvOvP4MSiUuSUni2")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("$2a$10$RZN5yXj8TO2/z5osR9xM7OpexZmUUHYHtFH7whFwo58sasnIGBf6e")
                .roles("ADMIN");
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }*/
}
