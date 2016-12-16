package be.occam.minimaxi.domain.service;

import static be.occam.utils.spring.web.Controller.response;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import be.occam.minimaxi.domain.human.Adventurer;
import be.occam.minimaxi.domain.human.Interpreter;
import be.occam.minimaxi.domain.human.MailMan;
import be.occam.minimaxi.domain.object.Entry;
import be.occam.minimaxi.web.dto.AdventureDTO;
import be.occam.minimaxi.web.dto.EntryDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class EntryService {
	
	protected final Logger logger
		= LoggerFactory.getLogger( this.getClass() );
	
	@Resource
	protected JavaMailSender javaMailSender;
	
	@Resource
	protected MailMan mailMan;
	
	@Resource
	Adventurer adventurer;
	
	@Resource
	Interpreter interpreter;
	
	protected final String fromEmailAddress;
	protected final String toEmailAddress;
	
	public EntryService( String fromEmailAddress, String toEmailAddress ) {
		this.fromEmailAddress = fromEmailAddress;
		this.toEmailAddress = toEmailAddress;
		
		logger.info( "entry service started, from-email address is [{}], to-email address is [{}]", fromEmailAddress, toEmailAddress );
	}
	
	public ResponseEntity<EntryDTO> accept( EntryDTO entryDTO) {
		
		logger.info( "accept [{}]", entryDTO );
		
		Entry entry
			= Entry.from( entryDTO );
		
		/*
		MimeMessage message
			= this.formatEntryReceivedMessage( entry, this.toEmailAddress );
		
		this.mailMan.deliver( message );
		
		MimeMessage messageForSven
			= this.formatEntryReceivedForSvenMessage( entry, "sven.gladines@gmail.com" );
		
		this.mailMan.deliver( messageForSven );
		*/
		
		Map<String,AdventureDTO> newAdventures
			= this.interpreter.translate( entryDTO );
		
		for ( String recipient : newAdventures.keySet() ) {
			
			List<AdventureDTO> adventures
				= this.adventurer.read( recipient );
			
			adventures.add( newAdventures.get( recipient ) );
			
			this.adventurer.write( recipient, adventures );
			
		}
		
		return response( entryDTO, HttpStatus.CREATED );
			
	}
	
	protected MimeMessage formatEntryReceivedMessage( Entry entry, String... recipients ) {
		
		MimeMessage message
			= null;
			
		Configuration cfg 
			= new Configuration();
		
		String templateID
			= "/templates/to-acsi/entry-received.tmpl";
		
		try {
			
			InputStream tis
				= this.getClass().getResourceAsStream( templateID );
			
			Template template 
				= new Template( templateID, new InputStreamReader( tis ), cfg );
			
			Map<String, Object> model = new HashMap<String, Object>();
					
			model.put( "entry", entry );
			
			StringWriter bodyWriter 
				= new StringWriter();
			
			template.process( model , bodyWriter );
			
			bodyWriter.flush();
				
			message = this.javaMailSender.createMimeMessage( );
			// SGL| GAE does not support multipart_mode_mixed_related (default, when flag true is set)
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_NO, "utf-8");
				
			helper.setFrom( this.fromEmailAddress );
			helper.setTo( recipients );
			helper.setSubject( String.format( "Nieuwe aanmelding: %s", entry.getTitle() ) );
				
			String text
				= bodyWriter.toString();
				
			logger.info( "email text is [{}]", text );
				
			helper.setText(text, true);
			
		}
		catch( Exception e ) {
			logger.warn( "could not create e-mail", e );
			throw new RuntimeException( e );
		}
		
		return message;
    	
    }
	
	protected MimeMessage formatEntryReceivedForSvenMessage( Entry entry, String... recipients ) {
		
		MimeMessage message
			= null;
			
		Configuration cfg 
			= new Configuration();
		
		String templateID
			= "/templates/to-sven/entry-received.tmpl";
		
		try {
			
			InputStream tis
				= this.getClass().getResourceAsStream( templateID );
			
			Template template 
				= new Template( templateID, new InputStreamReader( tis ), cfg );
			
			Map<String, Object> model = new HashMap<String, Object>();
					
			model.put( "entry", entry );
			
			StringWriter bodyWriter 
				= new StringWriter();
			
			template.process( model , bodyWriter );
			
			bodyWriter.flush();
				
			message = this.javaMailSender.createMimeMessage( );
			// SGL| GAE does not support multipart_mode_mixed_related (default, when flag true is set)
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_NO, "utf-8");
				
			helper.setFrom( this.fromEmailAddress );
			helper.setTo( recipients );
			helper.setSubject( String.format( "Nieuwe aanmelding: %s ...", entry.getTitle().substring( 0, 3 ) ) );
				
			String text
				= bodyWriter.toString();
				
			logger.info( "email text is [{}]", text );
				
			helper.setText(text, true);
			
		}
		catch( Exception e ) {
			logger.warn( "could not create e-mail", e );
			throw new RuntimeException( e );
		}
		
		return message;
    	
    }

}
