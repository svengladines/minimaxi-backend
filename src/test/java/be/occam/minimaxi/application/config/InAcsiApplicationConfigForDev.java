package be.occam.minimaxi.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import be.occam.minimaxi.web.util.DataGuard;
import be.occam.minimaxi.web.util.DevGuard;
import be.occam.utils.spring.configuration.ConfigurationProfiles;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.apphosting.api.ApiProxy;

@Configuration
public class InAcsiApplicationConfigForDev {
	
	@Profile( { ConfigurationProfiles.DEV } )
	public static class DbConfigForDev {
		
		@Bean
		String acsiEmailAddress() {
			
			return "sven.gladines@gmail.com"; 
			
		}
		
	}
	
	@Configuration
	@Profile( { ConfigurationProfiles.DEV } ) 
	public static class ConfigForDevelopment {
		
		@Bean
		DataGuard dataGuard( LocalServiceTestHelper helper ) {
			
			return new DevGuard( ApiProxy.getCurrentEnvironment() );
			
		}
		
	}
	
}