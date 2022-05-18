package nl.hand.made.vending.machine.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val dataSource: DataSource
) : WebSecurityConfigurerAdapter() {

    @Autowired
    fun configAuthentication(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication().dataSource(dataSource)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .httpBasic()
            .and()
            .authorizeRequests() // USER: Only creating a new user is open to everyone
            .antMatchers(HttpMethod.POST, "/user").permitAll()
            .antMatchers("/user").hasRole("ADMIN") // PRODUCT: Only fetching product information is open to everyone
            .antMatchers(HttpMethod.GET, "/product*/**").permitAll()
            .antMatchers("/product/**").hasAnyRole("ADMIN", "BUYER") // VENDING: Only allowed for buyers
            .antMatchers("/deposit", "/buy", "/reset").hasRole("BUYER")
            .anyRequest()
            .authenticated()
            .and()
            .logout()
            .logoutUrl("/logout/all")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
    }
}