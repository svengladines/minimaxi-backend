package be.occam.minimaxi.domain.human;

import static be.occam.utils.javax.Utils.list;
import static be.occam.utils.spring.web.Client.getJSON;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.google.appengine.api.datastore.KeyFactory;

import be.occam.minimaxi.repository.Story;
import be.occam.minimaxi.repository.StoryRepository;
import be.occam.minimaxi.web.dto.AdventureDTO;
import be.occam.utils.ftp.FTPClient;

public class Adventurer {
	
	protected final Logger logger
		= LoggerFactory.getLogger( this.getClass() );
	
	protected final String baseAdventureURL
		= "/api/adventures";
	
	protected final ObjectMapper objectMapper;
	
	// @Resource
	// protected FTPClient ftpClient;
	@Resource
	protected StoryRepository storyRepository;
	
	public Adventurer() {
		
		this.objectMapper = new ObjectMapper();
		this.objectMapper.configure( org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
		
	} 
	
	/*
	public List<AdventureDTO> read( String recipient ) {
		
		List<AdventureDTO> adventures
			= list();
		
		String url
			= new StringBuilder( this.baseAdventureURL ).append("/").append( recipient ).append("/adventures.json").toString();
		
		ResponseEntity<String> getResponse
			= getJSON( url, String.class, this.headers() );
	
		logger.info( "GET response code: {} ", getResponse.getStatusCode() );
		logger.info( "GET response body: {} ", getResponse.getBody() );
	
		String responseJSON
			= getResponse.getBody();
		
		logger.info( "json = [{}]", responseJSON );
	
		try {
			AdventureDTO[] reads 
				= this.objectMapper.reader( AdventureDTO[].class ).readValue( responseJSON );
			adventures.addAll( Arrays.asList( reads ) );
		} catch (Exception e) {
			logger.warn( "d'oh", e );
		}
		
		return adventures;
		
	}
	*/
	public List<AdventureDTO> read( String recipient ) {
		
		List<AdventureDTO> adventures
			= list();
		
		Story story
			= this.storyRepository.findByRecipient( recipient );
		
		if ( story != null ) {
			
			String json
				= story.getJson();
			
			logger.info( "read json = [{}]", json );
			
			try {
			
			AdventureDTO[] reads 
				= this.objectMapper.reader( AdventureDTO[].class ).readValue( json );
			adventures.addAll( Arrays.asList( reads ) );
			
			} catch (Exception e) {
				logger.warn( "d'oh", e );
			}
				
		}
		
		return adventures;
		
	}
	
	/*
	public void write( String recipient, List<AdventureDTO> adventures ) {
		
		String path
			= new StringBuilder( this.baseFTPPath ).append("/").append( recipient ).toString();
		
		String fileName
			= "adventures.json";
		
		Map<String, String> headers
			= this.headers();
		
		headers.put( "Content-Type", MediaType.APPLICATION_JSON_VALUE );
		
		AdventureDTO[] toWrite
			= adventures.toArray( new AdventureDTO[] {} );
		
		try {
		
			/*
				// = putJSON( url, toWrite  );
	
			// logger.info( "PUT response code: {} ", putResponse.getStatusCode() );
			// logger.info( "PUT response body: {} ", putResponse.getBody() );
			 String json 
				= this.objectMapper.writeValueAsString( adventures );
			 
			 this.ftpClient.putTextFile(path, fileName, json );
	
			 logger.info( "written adventures [{}] to [{}]", json, path );
			
		} catch (Exception e) {
			logger.warn( "d'oh", e );
		}
		
	}
	*/
	
	public void write( String recipient, List<AdventureDTO> adventures ) {
		
		Story story
			= this.storyRepository.findByRecipient( recipient );
		
		if ( story == null ) {
			
			story = new Story();
			story.setRecipient( recipient );
			story = this.storyRepository.saveAndFlush( story );
			story.setUuid( KeyFactory.keyToString( story.getKey() ) );
			story = this.storyRepository.saveAndFlush( story );
			
		}
		
		try {
			
			String json
				= this.objectMapper.writeValueAsString( adventures );
			
			story.setJson( json );
			
			story = this.storyRepository.saveAndFlush( story );
			
			
		} catch (Exception e) {
			
			logger.warn( "failed to write story", e );
			
		}
		
	}
	
	protected Map<String,String> headers() {
		
		Map<String,String> headers
			= new HashMap<String,String>();
		
	    headers.put("User-Agent", "RestSharp 104.4.0.0");
	    headers.put("Accept", "application/json");
	    
		return headers;
		
	}
	
	/*
	public void setFTPClient( FTPClient ftpClient ) {
		
		this.ftpClient = ftpClient;
		
	}
	*/

}
