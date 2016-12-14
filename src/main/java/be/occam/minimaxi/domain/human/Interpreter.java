package be.occam.minimaxi.domain.human;

import be.occam.minimaxi.web.dto.AdventureDTO;
import be.occam.minimaxi.web.dto.EntryDTO;

public class Interpreter {
	
	public AdventureDTO translate( EntryDTO entry ) {
		
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
		
		return adventure;
		
	}

}
