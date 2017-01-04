package be.occam.minimaxi.web.controller.api;

import static be.occam.utils.javax.Utils.list;
import static be.occam.utils.spring.web.Controller.response;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import be.occam.minimaxi.domain.service.AdventureService;
import be.occam.minimaxi.web.dto.AdventureDTO;
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
	public ResponseEntity<List<AdventureDTO>> query( @RequestParam( required=true ) String recipient, WebRequest request ) {
		
		logger.info( "GET reveived" );
		
		Result<List<Result<AdventureDTO>>> adventuresResult
			= adventureService.guard().query( recipient );
		
		List<AdventureDTO> adventures
			= list();
		
		for ( Result<AdventureDTO> result : adventuresResult.getObject() ) {
			
			adventures.add( result.getObject() );
			
		}
		
		return response(adventures, HttpStatus.OK );
			
	}
	
}
