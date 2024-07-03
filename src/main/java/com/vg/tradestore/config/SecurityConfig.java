package com.vg.tradestore.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
				return http.authorizeHttpRequests(
						requests -> requests.requestMatchers("/v1/tradestore/trade", "/v1/tradestore/**").permitAll().anyRequest().authenticated())
				.oauth2Login(withDefaults()).formLogin(withDefaults()).build();

	}

}
