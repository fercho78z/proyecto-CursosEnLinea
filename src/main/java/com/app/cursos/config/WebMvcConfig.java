package com.app.cursos.config;


import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/403").setViewName("403");
		registry.addViewController("/login");
	}
	
	
@Bean
public LocaleResolver localResolver() {
	
	SessionLocaleResolver slr=new SessionLocaleResolver();
	slr.setDefaultLocale(new Locale("es"));
	return slr;
	
}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		
		LocaleChangeInterceptor lci= new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
		
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry interceptor) {
		interceptor.addInterceptor(localeChangeInterceptor());
		
	}
	





}
