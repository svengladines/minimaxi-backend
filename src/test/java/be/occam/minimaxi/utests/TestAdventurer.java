package be.occam.minimaxi.utests;

import org.junit.Test;

import be.occam.minimaxi.domain.human.Adventurer;

public class TestAdventurer {
	
	@Test
	public void doesItSmoke() {
		
		Adventurer adventurer
			= new Adventurer();
		
		adventurer.write( "svekke", adventurer.read( "svekke" ) );
		
		
	}

}
