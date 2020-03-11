package it.intesys.academy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService customUserDetailsService() {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user")
            .password("user")
            .roles("USER")
            .build());
        manager.createUser(users.username("admin")
            .password("admin")
            .roles("USER", "ADMIN")
            .build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http)
        throws Exception {

        http
            .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**" , "/login", "/logout").permitAll()
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
            .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }
}
