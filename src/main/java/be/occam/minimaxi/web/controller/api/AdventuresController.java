package be.occam.minimaxi.web.controller.api;

import static be.occam.utils.spring.web.Controller.response;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import be.occam.minimaxi.domain.service.AdventureService;
import be.occam.minimaxi.web.dto.AdventureDTO;
import be.occam.minimaxi.web.dto.EntryDTO;
import be.occam.utils.spring.web.Result;

@Controller
@RequestMapping(value="/adventures")
public class AdventuresController {
	
	private final Logger logger 
		= LoggerFactory.getLogger( AdventuresController.class );
	
	@Resource
	AdventureService adventureService;
	
	@RequestMapping( method = { RequestMethod.GET } )
	@ResponseBody
	public ResponseEntity<Result<List<Result<AdventureDTO>>>> query( @RequestParam( required=false ) String recipient, WebRequest request ) {
		
		logger.info( "GET reveived" );
		
		Result<List<Result<AdventureDTO>>> adventures
			= adventureService.guard().query( recipient );
		
		return response(adventures, HttpStatus.OK );
			
	}
	
}
