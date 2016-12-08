package be.occam.minimaxi.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import be.occam.minimaxi.domain.human.MailMan;
import be.occam.minimaxi.domain.service.EntryService;
import be.occam.minimaxi.web.util.DataGuard;
import be.occam.minimaxi.web.util.NoopGuard;
import be.occam.utils.spring.configuration.ConfigurationProfiles;

@Configuration
public class InAcsiApplicationConfig {
	
	final static Logger logger
		= LoggerFactory.getLogger( InAcsiApplicationConfig.class );

	final static String BASE_PKG 
		= "be.occam.zoncolan";
	
	static class propertiesConfigurer {
		
		@Bean
		@Scope("singleton")
		public static PropertySourcesPlaceholderConfigurer propertiesConfig() {
			return new PropertySourcesPlaceholderConfigurer();
		}
		
	}
	
	@Configuration
	@Profile({ConfigurationProfiles.PRODUCTION})
	static class DomainConfigForProduction {
		
		@Bean
		DataGuard dataGuard() {
			
			return new NoopGuard();
			
		}
		
		@Bean
		String acsiEmailAddress() {
			
			return "info@inacsi.be"; 
			
		}
		
		@Bean
		String acsiDigitaalEmailAddress() {
			
			return "acsi.digitaal@gmail.com"; 
			
		}
		
	}
	
	@Configuration
	public static class DomainConfigShared {
		
		@Bean
		public MailMan mailMan() {
			return new MailMan();
		}
		
		@Bean
		public JavaMailSender javaMailSender () {
			
			JavaMailSenderImpl sender
				= new JavaMailSenderImpl();
			return sender;
			
		}
		
		@Bean
		public EntryService entryService( String acsiDigitaalEmailAddress, String acsiEmailAddress ) {
			return new EntryService( acsiDigitaalEmailAddress, acsiEmailAddress );
		}
		
	}
	
}