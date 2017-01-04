package be.occam.minimaxi.domain.service;

import static be.occam.utils.javax.Utils.list;
import static be.occam.utils.javax.Utils.map;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import be.occam.minimaxi.domain.human.Adventurer;
import be.occam.minimaxi.repository.Story;
import be.occam.minimaxi.web.dto.AdventureDTO;
import be.occam.minimaxi.web.util.DataGuard;
import be.occam.minimaxi.web.util.MiniMaxi;
import be.occam.utils.spring.web.Result;
import be.occam.utils.spring.web.Result.Value;

public class AdventureService {
	
	protected final Logger logger
		= LoggerFactory.getLogger( this.getClass() );
	
	@Resource
	Adventurer adventurer;
	
	@Resource
	DataGuard dataGuard;
	
	public AdventureService( ) {
		logger.info( "adventure service started" );
	}
	
	@Transactional( readOnly=true )
	public Result<List<Result<AdventureDTO>>> query( String q ) {
		
		logger.info( "query, q is [{}]", q );
		
		Result<List<Result<AdventureDTO>>> result
			= new Result<List<Result<AdventureDTO>>>();
		
		List<Result<AdventureDTO>> individualResults
			= list();
		
		Map<String, List<AdventureDTO>> map
			= map();
		
		for ( String recipient : MiniMaxi.RECIPIENTS ) {
			
			List<AdventureDTO> adventures = this.adventurer.read( recipient );
			
			map.put( recipient, adventures );
			
		}
		
		for ( String recipient : map.keySet() ) {
			
			if ( q != null ) {
				
				if ( ! q.equals( recipient ) ) {
					continue;
				}
				
			}
			
			List<AdventureDTO> adventures
				= map.get( recipient );
			
			for ( AdventureDTO adventure: adventures ) {
				
				Result<AdventureDTO> individualResult
					= new Result<AdventureDTO>();
				
				individualResult.setValue( Value.OK );
				individualResult.setObject( adventure );
				
				individualResults.add( individualResult );
				
			}
			
		}
		
		result.setValue( Value.OK );
		result.setObject( individualResults );
		
		return result;
			
	}
	
	@Transactional( readOnly=false )
	public Result<AdventureDTO> update( String recipient, AdventureDTO adventure ) {
		
		logger.info( "update, set visited to one" );
		
		Result<AdventureDTO> result
			= new Result<AdventureDTO>();
		
		
		Story story 
			= this.adventurer.readStory( recipient );
		
		this.adventurer.writeStory( recipient, story, adventure );
			
		result.setValue( Value.OK );
		result.setObject( adventure );

		
		return result;
			
	}

	
	public AdventureService guard() {
		this.dataGuard.guard();
		return this;
	}

}
