package com.excilys.formation.battleships.ship;

import java.io.Serializable;

public class Destroyer extends AbstractShip implements Serializable {
	
	public Destroyer() {
		this(Orientation.NORTH);
	}

	public Destroyer(Orientation o) {
		super('D', "Frégate", 2, o);
	}
}
