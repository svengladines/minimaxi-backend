package be.occam.minimaxi.web.controller.api;

import static be.occam.utils.spring.web.Controller.response;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import be.occam.minimaxi.domain.service.AdventureService;
import be.occam.minimaxi.web.dto.AdventureDTO;
import be.occam.utils.spring.web.Result;

@Controller
@RequestMapping(value="/adventures/{recipient}/{uuid}")
public class AdventureController {
	
	private final Logger logger 
		= LoggerFactory.getLogger( AdventureController.class );
	
	@Resource
	AdventureService adventureService;
	
	@RequestMapping( method = { RequestMethod.GET } )
	@ResponseBody
	public ResponseEntity<Result<AdventureDTO>> update( @PathVariable String recipient, @RequestBody AdventureDTO adventure, WebRequest request ) {
		
		logger.info( "GET reveived" );
		
		Result<AdventureDTO> updated
			= adventureService.guard().update( recipient, adventure );
		
		return response( updated, HttpStatus.OK );
			
	}
	
}
