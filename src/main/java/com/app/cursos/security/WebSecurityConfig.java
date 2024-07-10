package com.app.cursos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.app.cursos.services.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Bean	
	public UserDetailsService userDetailsService() {
		
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//implementa el autenticacion de usarios de base de datos con el provedor Dao
	// y verifica y autentica las credenciales del usuario
	@Bean 
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	} 
	
	//usa el provide para responder a la peticion 
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
		
		AuthenticationManagerBuilder authenticationManager= httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManager.authenticationProvider(authenticationProvider());
		
		return authenticationManager.build();
	}
	
	//define reglas de auth a traves de http
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/").hasAnyAuthority("ADMIN","USER","EDITOR","CREATOR")
						.requestMatchers("/cursos").hasAnyAuthority("ADMIN","USER","EDITOR","CREATOR")
						.requestMatchers("/cursos/nuevo").hasAnyAuthority("ADMIN","EDITOR")
						.requestMatchers("/cursos/editar","/cursos/editar/*","/cursos/eliminar", "/cursos/eliminar/*").hasAnyAuthority("ADMIN","CREATOR")
						.anyRequest().authenticated())
				//.formLogin(org.springframework.security.config.Customizer.withDefaults())
				.formLogin(form->form.loginPage("/login").permitAll())
				//.httpBasic(org.springframework.security.config.Customizer.withDefaults())
				.logout((logout) -> logout.logoutUrl("/logout").deleteCookies("JSESSIONID").logoutSuccessUrl("/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
				
											/*.logoutSuccessUrl("/index")
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
