package be.occam.minimaxi.web.controller;

import static be.occam.utils.spring.web.Controller.response;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import be.occam.minimaxi.domain.service.EntryService;
import be.occam.minimaxi.web.dto.EntryDTO;

@Controller
@RequestMapping(value="/entries")
public class EntriesController {
	
	private final Logger logger 
		= LoggerFactory.getLogger( EntriesController.class );
	
	@Resource
	EntryService entryService;
	
	@RequestMapping( method = { RequestMethod.GET } )
	@ResponseBody
	public ResponseEntity<EntryDTO> get( WebRequest request ) {
		
		logger.info( "GET reveived" );
		
		return response( new EntryDTO(), HttpStatus.CREATED );
			
	}
	
	@RequestMapping( method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_JSON_VALUE } )
	@ResponseBody
	public ResponseEntity<EntryDTO> post( @RequestBody EntryDTO entryDTO, WebRequest request ) {
		
		logger.info( "Entry received: [{}]", entryDTO );
		
		HttpHeaders httpHeaders
			= new HttpHeaders();

		httpHeaders.add("Access-Control-Allow-Origin", "*" ) ;
		httpHeaders.add("Access-Control-Allow-Methods", "GET,OPTIONS" );
		httpHeaders.add("Access-Control-Allow-Credentials","true");
		
		this.entryService.accept( entryDTO );

		ResponseEntity<EntryDTO> response
			= new ResponseEntity<EntryDTO>( entryDTO , httpHeaders, HttpStatus.OK );


		return response;

}

	@RequestMapping( value="/**", method = { RequestMethod.OPTIONS } )
	@ResponseBody
	public ResponseEntity<String> options() {

		logger.info( "options!" );
	
		HttpHeaders httpHeaders
			= new HttpHeaders();
		
		httpHeaders.add("Access-Control-Allow-Origin", "*" ) ;
		httpHeaders.add("Access-Control-Allow-Methods", "GET,OPTIONS,POST" );
		httpHeaders.add("Access-Control-Allow-Credentials","true");
		httpHeaders.add("Access-Control-Allow-Headers","Content-Type");
		
		ResponseEntity<String> response;
		
		response = new ResponseEntity<String>( "okelidokeli", httpHeaders, HttpStatus.OK );
		
		return response;
	}


	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleServiceException(IllegalArgumentException e, WebRequest webRequest ) {
		logger.warn("server error", e);
		return response( e.getMessage(), HttpStatus.BAD_REQUEST );
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleServiceException(Exception e, WebRequest webRequest ) {
		logger.warn("server error", e);
		return response( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
	}
	
}
