package be.occam.minimaxi.domain.human;

import static be.occam.utils.javax.Utils.list;
import static be.occam.utils.spring.web.Client.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import be.occam.minimaxi.web.dto.AdventureDTO;

public class Adventurer {
	
	protected final Logger logger
		= LoggerFactory.getLogger( this.getClass() );
	
	protected final String baseAdventureURL
		= "http://www.debrodders.be/svekke/minimaxi/adventures";
	
	protected final ObjectMapper objectMapper;
	
	public Adventurer() {
		
		this.objectMapper = new ObjectMapper();
		this.objectMapper.configure( org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
		
	} 
	
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
	
	public void write( String recipient, List<AdventureDTO> adventures ) {
		
		String url
			= new StringBuilder( this.baseAdventureURL ).append("/").append( recipient ).append("/adventures.json").toString();
		
		Map<String, String> headers
			= this.headers();
		
		headers.put( "Content-Type", MediaType.APPLICATION_JSON_VALUE );
		
		AdventureDTO[] toWrite
			= adventures.toArray( new AdventureDTO[] {} );
		
		try {
		
			ResponseEntity<AdventureDTO[] > putResponse
				= putJSON( url, toWrite  );
	
			logger.info( "PUT response code: {} ", putResponse.getStatusCode() );
			logger.info( "PUT response body: {} ", putResponse.getBody() );
	
			// String json 
				// = this.objectMapper.writeValueAsString( adventures );
			
			// logger.info( "written adventures as {}", json );
			
		} catch (Exception e) {
			logger.warn( "d'oh", e );
		}
		
	}
	
	protected Map<String,String> headers() {
		
		Map<String,String> headers
			= new HashMap<String,String>();
		
	    headers.put("User-Agent", "RestSharp 104.4.0.0");
	    headers.put("Accept", "application/json");
	    
		return headers;
		
	}

}
