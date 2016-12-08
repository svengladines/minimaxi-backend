package be.occam.minimaxi.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import be.occam.minimaxi.web.controller.EntriesController;

@Configuration
@EnableWebMvc
public class MvcConfig {
	
	@Configuration
	public static class DispatcherConfig {
		
		@Bean
		public InternalResourceViewResolver internalResourceViewResolver() {
			InternalResourceViewResolver resolver
				= new InternalResourceViewResolver();
			resolver.setPrefix( "/WEB-INF/jsp/" );
			resolver.setSuffix( ".jsp" );
			return resolver;
		}
		
	}
	
	/*
	@Bean
	MultipartResolver multipartResolver() {
		
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		
		resolver.setMaxInMemorySize( 1024000 );
		
		return resolver;
		
	}
	*/
	
	@Configuration
	public static class ControllerConfig {
		
		@Bean
		public EntriesController entriesController() {
			
			return new EntriesController();
			
		}
		
	}
	
	@Configuration
	public static class FormatConfig {
		
		@Bean
		DateFormatter dateFormatter() {
			
			DateFormatter dateFormatter
				= new DateFormatter();
			
			dateFormatter.setPattern("dd/MM/yyyy");
			
			return dateFormatter;
			
		}
	}
		

}
