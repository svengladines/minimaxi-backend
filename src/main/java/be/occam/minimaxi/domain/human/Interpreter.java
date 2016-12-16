package be.occam.minimaxi.domain.human;

import java.util.HashMap;
import java.util.Map;

import be.occam.minimaxi.web.dto.AdventureDTO;
import be.occam.minimaxi.web.dto.EntryDTO;

public class Interpreter {
	
	public Map<String, AdventureDTO> translate( EntryDTO entry ) {
		
		Map<String,AdventureDTO> map
			= new HashMap<String,AdventureDTO>();
		
		for ( String recipient : entry.getRecipients() ) {
		
			AdventureDTO adventure
				= new AdventureDTO();
			
			adventure.setTitle( entry.getTitle() );
			adventure.setDescription( entry.getDescription() );
			adventure.setType( "pr" );
			
			String date 
				= String.format( "%s/%s/%s", entry.getMomentDay(), entry.getMomentMonth(), entry.getMomentYear() );
			
			adventure.setDate( date );
			
			if ( "Image".equals( entry.getMediaType() ) ) {
				adventure.setMedia( "img" );
			}
			
			map.put( recipient, adventure );
			
		}
		
		return map;
		
	}

}
