package com.excilys.formation.battleships;

import com.excilys.formation.battleships.ship.AbstractShip.Orientation;
import com.excilys.formation.battleships.ship.BattleShip;
import com.excilys.formation.battleships.InputHelper.ShipInput;
import com.excilys.formation.battleships.ship.AbstractShip;
import com.excilys.formation.battleships.ship.Carrier;
import com.excilys.formation.battleships.ship.Destroyer;
import com.excilys.formation.battleships.ship.Submarine;

public class TestBoard {

	public static Board b1 = new Board("player1");

	public static void main(String args[]) {
		b1.print();

//        Destroyer s = new Destroyer(Orientation.SOUTH);
//        b1.putShip(s, 0, 0);
//        b1.sendHit(0, 0);
//		IBoard.Hit res = b1.sendHit(0, 1);
//		if (res == IBoard.Hit.DESTROYER) {
//			System.out.println("destroyer coulé");
//		}

//		putShips(b1);

		b1.print();

	}

	private static void putShips(Board board) {

		boolean done = false;
		int i = 0;
		AbstractShip[] ships = {
				new Destroyer(), new Submarine(), new Submarine(), new BattleShip(), new Carrier()
		};

		do {
			AbstractShip s = ships[i];
			String msg = String.format("navire %d : %s(%d)", i + 1, s.getName(), s.getLength());
			System.out.println(msg);
			ShipInput res = InputHelper.readShipInput();
			Orientation orientation = null;
			if (res.orientation.equals("n")) {
				orientation = Orientation.NORTH;
			} else if (res.orientation.equals("s")) {
				orientation = Orientation.SOUTH;
			} else if (res.orientation.equals("e")) {
				orientation = Orientation.EAST;
			} else if (res.orientation.equals("o")) {
				orientation = Orientation.WEST;
			}

			s.setOrientation(orientation);
			try {
				board.putShip(s, res.x, res.y);
				++i;
				done = i == 5;
			} catch(IllegalArgumentException e) {
				System.err.println("Impossible de placer le navire a cette position");
			}
			board.print();

		} while (!done);
	}

}
