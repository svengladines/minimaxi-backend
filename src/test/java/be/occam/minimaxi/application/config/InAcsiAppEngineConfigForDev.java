package be.occam.minimaxi.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import be.occam.utils.spring.configuration.ConfigurationProfiles;

import com.google.appengine.tools.development.testing.LocalAppIdentityServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMailServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@Configuration
@Profile({ConfigurationProfiles.DEV})
public class InAcsiAppEngineConfigForDev {
	
	@Configuration
	@Profile( ConfigurationProfiles.DEV )
	public static class LocalServiceConfig {
		
		@Bean
		public LocalServiceTestHelper helper() {
			
			LocalServiceTestHelper helper
				= new LocalServiceTestHelper( new LocalAppIdentityServiceTestConfig(), new LocalMailServiceTestConfig() );
			helper.setUp();
			
			return helper;
			
		}
		
		
	}
	
}
