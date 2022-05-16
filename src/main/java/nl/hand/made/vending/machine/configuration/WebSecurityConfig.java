package nl.hand.made.vending.machine.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                // USER: Only creating a new user is open to everyone
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers("/user").hasRole("ADMIN")
                // PRODUCT: Only fetching product information is open to everyone
                .antMatchers(HttpMethod.GET, "/product*/**").permitAll()
                .antMatchers("/product/**").hasAnyRole("ADMIN", "BUYER")
                // VENDING: Only allowed for buyers
                .antMatchers("/deposit", "/buy", "/reset").hasRole("BUYER")
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/logout/all")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    private AntPathRequestMatcher requestMatcher(String path, HttpMethod method) {
        return new AntPathRequestMatcher(path, method.toString());
    }
}