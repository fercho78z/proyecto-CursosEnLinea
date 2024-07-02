package com.app.cursos.security;

import java.beans.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Bean
	protected InMemoryUserDetailsManager userDetailsManager() {
		UserDetails user1 = User.builder().username("user1")
				.password("{bcrypt}$2a$10$6zFeVKeUn2glTJV/gL78guCvuIyKTnvcIdvShPhNzBHZXEaTan8..").roles("USER").build();
		UserDetails user2 = User.builder().username("admin2")
				.password("{bcrypt}$2a$10$6zFeVKeUn2glTJV/gL78guCvuIyKTnvcIdvShPhNzBHZXEaTan8..").roles("ADMIN").build();
				

		return new InMemoryUserDetailsManager(user1, user2);
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.COOKIES));
		CookieClearingLogoutHandler cookies = new CookieClearingLogoutHandler("JSESSIONID");
		httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/").hasAnyRole("ADMIN","USER")
						.requestMatchers("/cursos").hasAnyRole("ADMIN","USER")
						.requestMatchers("/cursos/nuevo").hasAnyRole("ADMIN")
						.requestMatchers("/cursos/editar","/cursos/editar/*","/cursos/eliminar", "/cursos/eliminar/*").hasRole("ADMIN")
						.anyRequest().authenticated())
				//.formLogin(org.springframework.security.config.Customizer.withDefaults())
				.formLogin(form->form.loginPage("/login").permitAll())
				//.httpBasic(org.springframework.security.config.Customizer.withDefaults())
				/*.logout((logout) -> logout.logoutUrl("/src/main/resources/templates/logout")
											.logoutSuccessUrl("/index")
											//.addLogoutHandler(cookies)
											.invalidateHttpSession(true)
											.deleteCookies("remove")
											.clearAuthentication(true)
											.addLogoutHandler(clearSiteData))*/
				//.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
				//.logout(org.springframework.security.config.Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.exceptionHandling(e -> e.accessDeniedHandler(customAccessDeniedHandler()));

		return httpSecurity.build();

	}
	
	@Bean
	AccessDeniedHandler customAccessDeniedHandler() {
	    return (request, response, accessDeniedException) -> {
	    	response.sendRedirect("/403");
	    	//.exceptionHandling(e -> e.accessDeniedPage("/403"));
	    };
	}
}
