package com.excilys.formation.battleships.ship;

import java.io.Serializable;

public class Submarine extends AbstractShip implements Serializable {
	
	public Submarine() {
		this(Orientation.NORTH);
	}
	
	public Submarine(Orientation o) {
		super('S', "Sous-marin", 3, o);
	}
}
