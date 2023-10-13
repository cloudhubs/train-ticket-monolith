
package edu.fudanselab.trainticket.config;

import edu.fudanselab.trainticket.config.jwt.JWTFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springframework.web.cors.CorsConfiguration.ALL;

/**
 * @author fdse
 */

@Configuration
@EnableWebSecurity
// @Order(1000)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    String orderOther = "/api/v1/orderOtherService/orderOther";
    String order = "/api/v1/orderservice/order";
    String prices = "/api/v1/priceservice/prices";
    String stations = "/api/v1/stationservice/stations";
    String trips = "/api/v1/travel2service/trips";


    @Autowired
    @Qualifier("userDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * load password encoder
     *
     * @return PasswordEncoder
     */

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth", "/api/v1/auth/hello", "/api/v1/users/hello", "/api/v1/verifycode/**").permitAll()
                .antMatchers("/api/v1/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/*").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/v1/adminbasicservice/adminbasic/stations").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/adminbasicservice/adminbasic/trains").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/adminbasicservice/adminbasic/prices").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/adminbasicservice/adminbasic/configs").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/adminbasicservice/adminbasic/contacts").permitAll()
                .antMatchers("/api/v1/adminbasicservice/**").hasRole("ADMIN")
                .antMatchers("/api/v1/adminorderservice/**").hasRole("ADMIN")
                .antMatchers("/api/v1/adminrouteservice/**").hasRole("ADMIN")
                .antMatchers("/api/v1/admintravelservice/**").hasRole("ADMIN")
                .antMatchers("/api/v1/adminuserservice/users/**").hasRole("ADMIN")
                .antMatchers("/api/v1/assuranceservice/**").hasRole("USER")
                .antMatchers("/api/v1/basicservice/**").permitAll()
                .antMatchers("/api/v1/cancelservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/configservice/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/configservice/configs").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/configservice/configs").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/configservice/configs/*").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/consignpriceservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/consignservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/contactservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/executeservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/fooddeliveryservice/**").permitAll()
                .antMatchers("/api/v1/foodservice/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/foodservice/orders/*").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/foodservice/orders").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/foodservice/orders").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/inside_pay_service/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/notifyservice/**").permitAll()
                .antMatchers("/api/v1/notifyservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/orderOtherService/orderOther/**").permitAll()
                .antMatchers(HttpMethod.POST, orderOther).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, orderOther).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, orderOther).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/v1/orderOtherService/orderOther/admin").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/orderOtherService/orderOther/admin").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, order).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, order).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, order).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/v1/orderservice/order/admin").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/orderservice/order/admin").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/orderservice/order/**").permitAll()
                .antMatchers("/api/v1/paymentservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/preserveotherservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/preserveservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/priceservice/**").permitAll()
                .antMatchers(HttpMethod.POST, prices).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, prices).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, prices).hasAnyRole("ADMIN")
                .antMatchers("/api/v1/rebookservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/routeplanservice/**").permitAll()
                .antMatchers("/api/v1/routeservice/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/routeservice/routes").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/routeservice/routes/*").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/seatservice/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/seatservice/seats").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/securityservice/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/v1/stationfoodservice/**").permitAll()
                .antMatchers(HttpMethod.POST, stations).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, stations).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, stations).hasAnyRole("ADMIN")
                .antMatchers("/api/v1/stationservice/**").permitAll()
                .antMatchers("/api/v1/trainfoodservice/**").permitAll()
                .antMatchers("/api/v1/trainservice/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/trainservice/trains").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/trainservice/trains").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/trainservice/trains/*").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/travelplanservice/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/travelservice/trips").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/travelservice/trips/*").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/travelservice/**").permitAll()
                .antMatchers("/api/v1/travel2service/**").permitAll()
                .antMatchers(HttpMethod.PUT, trips).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, trips).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, trips).hasAnyRole("ADMIN")
                .antMatchers("/api/v1/userservice/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/userservice/users/*").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, order).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, order).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, order).hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/v1/orderservice/order/admin").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/orderservice/order/admin").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/orderservice/order/**").permitAll()
                // create user and role while user register
                .antMatchers("/user/**").permitAll()
                .antMatchers("/swagger-ui.html", "/webjars/**", "/images/**",
                        "/configuration/**", "/swagger-resources/**", "/v2/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers().cacheControl();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALL)
                        .allowedMethods(ALL)
                        .allowedHeaders(ALL)
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }

    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
