package com.example.demoAuthentication.sercurity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demoAuthentication.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtFilter jwtfilter;
	 @Bean
	    public SecurityFilterChain sercurityFilter(HttpSecurity http) throws Exception {

	        http.csrf(csrf -> csrf.disable())
	            .sessionManagement(sess -> 
	                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/auth/login","/api/auth/register").permitAll()
	                .anyRequest().authenticated()
	            )
	            .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
	@Bean
	public UserDetailsService userDetailsservice()
	{
		return customUserDetailsService;
	}
	@Bean
	public DaoAuthenticationProvider authprovider()
	{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsservice());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
			
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authManager()
	{
		return new ProviderManager(List.of(authprovider()));
	}
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter filter) {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
	

}
