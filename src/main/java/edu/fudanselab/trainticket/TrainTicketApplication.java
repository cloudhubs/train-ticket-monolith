package edu.fudanselab.trainticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.web.cors.CorsConfiguration.ALL;

/**
 * @author Arju
 */

@SpringBootApplication
@EnableSwagger2
@EnableAsync
//@IntegrationComponentScan
@EnableJpaRepositories("edu.fudanselab.trainticket.repository")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TrainTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTicketApplication.class, args);

	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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

}
