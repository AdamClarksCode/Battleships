import java.util.Scanner;

public class BattleshipsMain {
	public static void main(String[] args) {
		StartUp();
	}

	public static void StartUp() {
		Scanner s = new Scanner(System.in);
		GameState CurrentState = new GameState("Start");
		System.out.println("                                      |__");
		System.out.println("                                      |\\/");
		System.out.println("                                      ---");
		System.out.println("                                     / | [");
		System.out.println("                              !      | |||");
		System.out.println("                            _/|     _/|-++'");
		System.out.println("                        +  +--|    |--|--|_ |-");
		System.out.println("                     { /|__|  |/\\__|  |--- |||__/");
		System.out.println("                    +---------------___[}-_===_.'____                 /\\");
		System.out.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
		System.out.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
		System.out.println("|                                                                     BB-61/");
		System.out.println(" \\_________________________________________________________________________|");
		System.out.println("                            Welcome to Battleships!");
		s.hasNextLine();
		Menu(CurrentState);
	}

	public static void Menu(GameState CurrentState) {
		while (CurrentState.state != "Exit") {
			switch (CurrentState.state) {
			case "Start": {
				CurrentState.state = "Menu";
				break;
			}
			case "Menu": {
				while (CurrentState.state == "Menu") {
					System.out.println("Please choose an option to continue:");
					System.out.println("1: 1 Player Mode"); //not finished
					System.out.println("2: 2 Player Mode"); //finished
					System.out.println("3: Super Hard Mode"); //didn't even start
					System.out.println("4: Exit");
					Scanner s = new Scanner(System.in);
					int menuOption = s.nextInt();
					switch (menuOption) {
					case 1: {
						CurrentState.state = "1 Player";
						break;
					}
					case 2: {
						CurrentState.state = "2 Player";
						break;
					}
					case 3: {
						System.out.println("Not implemented yet, please try again");
						break;
					}
					case 4: {
						CurrentState.state = "Exit";
						break;
					}
					default: {
						System.out.println("Please try again");
						break;
					}
					}
				}
				break;
			}
			case "2 Player": {
				InitialiseTwoPlayerMode(CurrentState);
			}
			case "1 Player": {
				InitialiseOnePlayerMode(CurrentState);
			}
			}
		}
		System.out.println("Thank you for playing");
	}

	public static void InitialiseTwoPlayerMode(GameState CurrentState) {
		Scanner s = new Scanner(System.in);
		System.out.println("How large would you like the X axis to be?");
		int xAxis = s.nextInt();
		while (xAxis < 5 || xAxis > 26) {
			System.out.println("Please make sure the X axis is bigger than 4 and less than 27.");
			xAxis = s.nextInt();
		}
		System.out.println("How large would you like the Y axis to be?");
		int yAxis = s.nextInt();
		while (yAxis < 5 || yAxis > 26) {
			System.out.println("Please make sure the Y axis is bigger than 4 and less than 27.");
			yAxis = s.nextInt();
		}
		Grid player1 = new Grid();
		player1.Board(yAxis, xAxis);
		player1 = PlaceShips(yAxis, xAxis, player1);
		Grid player2 = new Grid();
		player2.Board(yAxis, xAxis);
		player2 = PlaceShips(yAxis, xAxis, player2);
		TwoPlayerMode(yAxis, xAxis, player1, player2, CurrentState);
	}

	public static void InitialiseOnePlayerMode(GameState CurrentState) {
		Scanner s = new Scanner(System.in);
		System.out.println("How large would you like the X axis to be?");
		int xAxis = s.nextInt();
		while (xAxis < 5 || xAxis > 26) {
			System.out.println("Please make sure the X axis is bigger than 4 and less than 27.");
			xAxis = s.nextInt();
		}
		System.out.println("How large would you like the Y axis to be?");
		int yAxis = s.nextInt();
		while (yAxis < 5 || yAxis > 26) {
			System.out.println("Please make sure the Y axis is bigger than 4 and less than 27.");
			yAxis = s.nextInt();
		}
		Grid player1 = new Grid();
		player1.Board(yAxis, xAxis);
		player1 = PlaceShips(yAxis, xAxis, player1);
		Grid gridAI = new Grid();
		gridAI.Board(yAxis, xAxis);
		gridAI = PlaceAIShips(yAxis, xAxis, gridAI);
		OnePlayerMode(yAxis, xAxis, player1, gridAI, CurrentState);
	}

	public static void DrawGrid(int i, int j, Grid toDraw) {
		for (int y = 0; y < i + 1; y++) {
			for (int x = 0; x < j + 1; x++) {
				if (y == 0 && x == 0) {
					System.out.print(" ");
				} else if (y == 0) {
					System.out.print(getCharForNumber(x));
				} else if (x == 0) {
					System.out.print(getCharForNumber(y));
				} else {
					System.out.print(toDraw.newGrid[y - 1][x - 1]);
				}
			}
			System.out.println();
		}
	}

	public static void DrawOtherPlayersGrid(int i, int j, Grid toDraw) {
		for (int y = 0; y < i + 1; y++) { //only need to store 2 grids if you draw what you can see of your opponents grid
			for (int x = 0; x < j + 1; x++) {
				if (y == 0 && x == 0) {
					System.out.print(" ");
				} else if (y == 0) {
					System.out.print(getCharForNumber(x));
				} else if (x == 0) {
					System.out.print(getCharForNumber(y));
				} else {
					if (toDraw.newGrid[y - 1][x - 1] == "H" || toDraw.newGrid[y - 1][x - 1] == "M")
						System.out.print(toDraw.newGrid[y - 1][x - 1]);
					else
						System.out.print('#');
				}
			}
			System.out.println();
		}
	}

	public static Grid PlaceShips(int i, int j, Grid toPlace) {
		Boat Aircraft = new Boat("Aircraft Carrier", 5);
		toPlace = PlaceAShip(i, j, toPlace, Aircraft);
		DrawGrid(i, j, toPlace);
		Boat Destroyer = new Boat("Destroyer", 4);
		toPlace = PlaceAShip(i, j, toPlace, Destroyer);
		DrawGrid(i, j, toPlace);
		Boat Submarine = new Boat("Submarine", 3);
		toPlace = PlaceAShip(i, j, toPlace, Submarine);
		DrawGrid(i, j, toPlace);
		Boat Battleship = new Boat("Battleship", 3);
		toPlace = PlaceAShip(i, j, toPlace, Battleship);
		DrawGrid(i, j, toPlace);
		toPlace = PlaceAShip(i, j, toPlace, Battleship);
		DrawGrid(i, j, toPlace);
		Boat PatrolBoat = new Boat("Patrol Boat", 2);
		toPlace = PlaceAShip(i, j, toPlace, PatrolBoat);
		DrawGrid(i, j, toPlace);
		toPlace = PlaceAShip(i, j, toPlace, PatrolBoat);
		DrawGrid(i, j, toPlace);
		return toPlace;
	}

	public static Grid PlaceAIShips(int i, int j, Grid aiShips) {

		Boat aiAircraft = new Boat("Aircraft Carrier", 5);
		aiShips = PlaceOneAIShip(i, j, aiShips, aiAircraft);
		Boat aiDestroyer = new Boat("Destroyer", 4);
		aiShips = PlaceOneAIShip(i, j, aiShips, aiDestroyer);
		Boat aiSubmarine = new Boat("Submarine", 3);
		aiShips = PlaceOneAIShip(i, j, aiShips, aiSubmarine);
		Boat aiBattleship1 = new Boat("Battleship", 3);
		aiShips = PlaceOneAIShip(i, j, aiShips, aiBattleship1);
		Boat aiBattleship2 = new Boat("Battleship", 3);
		aiShips = PlaceOneAIShip(i, j, aiShips, aiBattleship2);
		Boat aiPatrol1 = new Boat("Patrol1", 2);
		aiShips = PlaceOneAIShip(i, j, aiShips, aiPatrol1);
		Boat aiPatrol2 = new Boat("Patrol2", 2);
		aiShips = PlaceOneAIShip(i, j, aiShips, aiPatrol2);
		return aiShips;
	}

	public static Grid PlaceOneAIShip(int i, int j, Grid aiShips, Boat boatToPlace) { //this should place all ships 1 square apart, it does not.
		boolean notSet = true;														  //Also places ships inside each other occasionally
		while (notSet == true) {
			int xStart = (int) (Math.random() * (j-boatToPlace.length));
			int yStart = (int) (Math.random() * (i-boatToPlace.length));
			int direction = (int) (Math.random() * 4);
			boolean spaceTaken = false;
			if (boatToPlace.name != "Aircraft Carrier") {
				switch (direction) {
				case 0: { // placing north
					if (yStart < boatToPlace.length)
						yStart += boatToPlace.length;
					for (int c = yStart - boatToPlace.length - 1; c < boatToPlace.length + 1; c++) {
						if (c == -1)
							c++;
						else {
							for (int d = xStart - 1; d < xStart + 1; d++) {
								if (d == -1)
									d++;
								else {
									if (c <= i && d <= j && c >= 0 && d >= 0) {
										if (aiShips.newGrid[c][d] != "#") {
											spaceTaken = true;
											break;
										}
									} else if(c>i || d>j){
										spaceTaken = true;
										break;
									} 
								}
							}
							if (spaceTaken == true)
								break;
						}
					}
					if (spaceTaken == false) {
						for (int b = 0; b < boatToPlace.length; b++) {
							aiShips.newGrid[yStart + b][xStart] = boatToPlace.name.substring(0, 1);
						}
						notSet = false;
					}
					break;
				}
				case 1: { // placing east
					if (xStart > j - boatToPlace.length)
						xStart -= boatToPlace.length;
					for (int c = yStart - 1; c < yStart + 1; c++) {
						if (c == -1)
							c++;
						else {
							for (int d = xStart - 1; d < xStart + boatToPlace.length + 1; d++) {
								if (d == -1)
									d++;
								else {
									if (c <= i && d <= j && c >= 0 && d >= 0) {
										if (aiShips.newGrid[c][d] != "#") {
											spaceTaken = true;
											break;
										} 
									} else if(c>i || d>j){
										spaceTaken = true;
										break;
									} 
								}
							}
							if (spaceTaken == true)
								break;
						}
					}
					if (spaceTaken == false) {
						for (int b = 0; b < boatToPlace.length; b++) {
							aiShips.newGrid[yStart][xStart + b] = boatToPlace.name.substring(0, 1);
						}
						notSet = false;
					}
					break;
				}
				case 2: { // placing south
					if (yStart > i - boatToPlace.length)
						yStart -= boatToPlace.length;
					for (int c = yStart - 1; c <= yStart + boatToPlace.length + 1; c++) {
						for (int d = xStart - 1; d < xStart + 1; d++) {
							if (d == -1)
								d++;
							else {
								if (c <= i && d <= j && c >= 0 && d >= 0) {
									if (aiShips.newGrid[c][d] != "#") {
										spaceTaken = true;
										break;
									}
								} else if(c>i || d>j){
									spaceTaken = true;
									break;
								} 
							}
						}
						if (spaceTaken == true)
							break;
					}
					if (spaceTaken == false) {
						for (int b = 0; b < boatToPlace.length; b++) {
							aiShips.newGrid[yStart+b][xStart] = boatToPlace.name.substring(0, 1);
						}
						notSet = false;
					}
					break;
				}
				case 3: { // placing west
					if (xStart < boatToPlace.length)
						xStart += boatToPlace.length;
					for (int c = yStart - boatToPlace.length - 1; c < yStart + 1; c++) {
						for (int d = xStart - boatToPlace.length - 1; d < xStart + 1; d++) {
							if (d == -1)
								d++;
							else {
								if (c <= i && d <= j && c >= 0 && d >= 0) {
									if (aiShips.newGrid[c][d] != "#") {
										spaceTaken = true;
										break;
									}
								} else if(c>i || d>j){
									spaceTaken = true;
									break;
								} 
							}
						}
						if (spaceTaken == true)
							break;
					}
					if (spaceTaken == false) {
						for (int b = 0; b < boatToPlace.length; b++) {
							aiShips.newGrid[yStart][xStart + b] = boatToPlace.name.substring(0, 1);
						}
						notSet = false;
					}
					break;
				}
				}
			} else {
				switch (direction) {
				case 0: { // placing north
					if (yStart < boatToPlace.length)
						yStart += boatToPlace.length;
					for (int b = 0; b < boatToPlace.length; b++) {
						aiShips.newGrid[yStart - b][xStart] = boatToPlace.name.substring(0, 1);
					}
					notSet = false;
					break;
				}
				case 1: { // placing east
					if (xStart > j - boatToPlace.length)
						xStart -= boatToPlace.length;
					for (int b = 0; b < boatToPlace.length; b++) {
						aiShips.newGrid[yStart][xStart + b] = boatToPlace.name.substring(0, 1);
					}
					notSet = false;
					break;
				}
				case 2: { // placing south
					if (yStart > i - boatToPlace.length)
						yStart -= boatToPlace.length;
					for (int b = 0; b < boatToPlace.length; b++) {
						aiShips.newGrid[yStart + b][xStart] = boatToPlace.name.substring(0, 1);
					}
					notSet = false;
					break;
				}
				case 3: { // placing west
					if (xStart < boatToPlace.length)
						xStart += boatToPlace.length;
					for (int b = 0; b < boatToPlace.length; b++) {
						aiShips.newGrid[yStart + b][xStart] = boatToPlace.name.substring(0, 1);
					}
					notSet = false;
					break;
				}
				}
			}
		}
		return aiShips;
	}

	public static Grid PlaceAShip(int i, int j, Grid toPlace, Boat boatToPlace) {
		boolean placed = false; //found an error where some ships can be placed over others
		while (placed == false) {
			int xPlaceStart = getCoOrdinateForBoat(boatToPlace, "X", "start");
			while (xPlaceStart < 0 || xPlaceStart > j)
				xPlaceStart = getCoOrdinateOnGrid();
			int yPlaceStart = getCoOrdinateForBoat(boatToPlace, "Y", "start");
			while (yPlaceStart < 0 || yPlaceStart > i)
				yPlaceStart = getCoOrdinateOnGrid();
			int xPlaceEnd = getCoOrdinateForBoat(boatToPlace, "X", "end");
			while (xPlaceEnd < 0 || xPlaceEnd > j)
				xPlaceEnd = getCoOrdinateOnGrid();
			if (xPlaceStart - xPlaceEnd != boatToPlace.length - 1 && xPlaceEnd - xPlaceStart != boatToPlace.length - 1
					&& xPlaceStart - xPlaceEnd != 0 && xPlaceEnd - xPlaceStart != 0)
				xPlaceEnd = getLegalPlacement(boatToPlace, "X", xPlaceStart);
			int yPlaceEnd = getCoOrdinateForBoat(boatToPlace, "Y", "end");
			while (yPlaceEnd < 0 || yPlaceEnd > i)
				yPlaceEnd = getCoOrdinateOnGrid();
			if (yPlaceStart - yPlaceEnd != boatToPlace.length - 1 && yPlaceEnd - yPlaceStart != boatToPlace.length - 1
					&& yPlaceStart - yPlaceEnd != 0 && yPlaceEnd - yPlaceStart != 0)
				yPlaceEnd = getLegalPlacement(boatToPlace, "Y", yPlaceStart);
			if (yPlaceStart == xPlaceStart && yPlaceEnd == xPlaceEnd)
				yPlaceEnd = getNotDiagonalOrNotOne(yPlaceStart, yPlaceEnd, xPlaceStart, yPlaceEnd, i, boatToPlace);
			if (xPlaceEnd < xPlaceStart) {
				int xSwitch = xPlaceEnd;
				xPlaceEnd = xPlaceStart;
				xPlaceStart = xSwitch;
			}
			if (yPlaceEnd < yPlaceStart) {
				int ySwitch = yPlaceEnd;
				yPlaceEnd = yPlaceStart;
				yPlaceStart = ySwitch;
			}
			if (xPlaceStart == xPlaceEnd) {
				for (int y = yPlaceStart; y <= yPlaceEnd; y++) {
					if (toPlace.newGrid[y][xPlaceStart] != "#") {
						placed = false;
						System.out.println("You have placed a ship on top of a ship, please try again");
						break;
					} else if (y == yPlaceEnd)
						placed = true;
				}
			} else {
				for (int x = xPlaceStart; x <= xPlaceEnd; x++) {
					if (toPlace.newGrid[yPlaceStart][x] != "#") {
						placed = false;
						System.out
								.println("You have placed a ship on top of a ship or out of bounds, please try again");
						break;
					} else if (x == xPlaceEnd)
						placed = true;
				}
			}
			if (xPlaceStart == xPlaceEnd) {
				for (int y = yPlaceStart; y <= yPlaceEnd; y++) {
					toPlace.newGrid[y][xPlaceStart] = boatToPlace.name.substring(0, 1);
				}
			} else {
				for (int x = xPlaceStart; x <= xPlaceEnd; x++) {
					toPlace.newGrid[yPlaceStart][x] = boatToPlace.name.substring(0, 1);
				}
			}
		}
		return toPlace;
	}

	public static void TwoPlayerMode(int y, int x, Grid player1, Grid player2, GameState CurrentState) {
		String turn = "Player 1"; //bug where a player can shoot both battleships 3 times, not sink one but game thinks it did 
		while (turn != "Over") {
			switch (turn) {
			case "Player 1": {
				player2 = getShot(y, x, player1, player2);
				if (player2.hits == 22) {
					System.out.println("Congratulations Player 1, you have sunk all of their battleships!");
					turn = "Over";
				} else {
					turn = "Player 2";
				}
				break;
			}
			case "Player 2": {
				player1 = getShot(y, x, player2, player1);
				if (player1.hits == 22) {
					System.out.println("Congratulations Player 2, you have sunk all of their battleships!");
					turn = "Over";
				} else {
					turn = "Player 1";
				}
				break;
			}
			}
		}
		CurrentState.state = "Menu";
		Menu(CurrentState);
	}

	public static void OnePlayerMode(int y, int x, Grid player1, Grid aiShips, GameState CurrentState) {
		DrawGrid(y, x, aiShips);
		DrawGrid(y, x, player1); //AI and turn structure would go here
	}

	public static int getCoOrdinateForBoat(Boat askBoat, String axis, String startEnd) {
		Scanner s = new Scanner(System.in);
		System.out.println(
				"Where would you like to place the " + startEnd + " of the " + askBoat.name + ", " + axis + " first?");
		String convert = s.nextLine();
		int CoOrdinateForBoat = getNumberForCharacter(convert.charAt(0));
		return CoOrdinateForBoat;
	}

	public static int getCoOrdinateOnGrid() {
		Scanner s = new Scanner(System.in);
		System.out.println("Please pick a Co-ordinate on the grid.");
		String convert = s.nextLine();
		int CoOrdinate = getNumberForCharacter(convert.charAt(0));
		return CoOrdinate;
	}

	public static int getLegalPlacement(Boat legalBoat, String axis, int start) {
		Scanner s = new Scanner(System.in);
		int correct = 0;
		int end = 0;
		while (correct == 0) {
			System.out.println("Your " + legalBoat.name + " is not " + legalBoat.length
					+ " long, please change your end " + axis + " co-ordinate.");
			String convert = s.nextLine();
			end = getNumberForCharacter(convert.charAt(0));
			if (start - end == legalBoat.length - 1)
				correct++;
			else if (end - start == legalBoat.length - 1)
				correct++;
			else if (start - end == 0)
				correct++;
			else if (end - start == 0)
				correct++;
		}
		return end;
	}

	public static Grid getShot(int y, int x, Grid shooter, Grid target) {
		Scanner s = new Scanner(System.in);
		boolean shotFired = false;
		while (shotFired == false) {
			DrawOtherPlayersGrid(y, x, target);
			DrawGrid(y, x, shooter);
			System.out.println("Where would you like to shoot, X?");
			String convert = s.nextLine();
			int xShot = getNumberForCharacter(convert.charAt(0));
			if (xShot < 0 || xShot > x)
				xShot = getCoOrdinateOnGrid();
			System.out.println("Where would you like to shoot, Y?");
			convert = s.nextLine();
			int yShot = getNumberForCharacter(convert.charAt(0));
			if (yShot < 0 || yShot > y)
				yShot = getCoOrdinateOnGrid();
			String theShot = target.newGrid[yShot][xShot];
			switch (theShot) {
			case "#":
				System.out.println("Miss!");
				target.newGrid[yShot][xShot] = "M";
				shotFired = true;
				break;
			case "M":
				System.out.println("You have already shot at that square, you missed, please try again");
				break;
			case "H":
				System.out.println("You have already shot at that square, you hit, please try again");
				break;
			case "A":
				System.out.println("Hit!");
				target.newGrid[yShot][xShot] = "H";
				target.aircraft++;
				if (target.aircraft == 5)
					System.out.println("You sunk my Aircraft Carrier!");
				target.hits++;
				shotFired = true;
				break;
			case "D":
				System.out.println("Hit!");
				target.newGrid[yShot][xShot] = "H";
				target.destroyer++;
				if (target.destroyer == 4)
					System.out.println("You sunk my Destroyer!");
				target.hits++;
				shotFired = true;
				break;
			case "S":
				System.out.println("Hit!");
				target.newGrid[yShot][xShot] = "H";
				target.submarine++;
				if (target.submarine == 3)
					System.out.println("You sunk my Submarine!");
				target.hits++;
				shotFired = true;
				break;
			case "B":
				System.out.println("Hit!");
				target.newGrid[yShot][xShot] = "H";
				if (target.battleship1 < 3) {
					target.battleship1++;
					if (target.battleship1 == 3)
						System.out.println("You sunk my Battleship!");
				} else {
					target.battleship2++;
					if (target.battleship2 == 3)
						System.out.println("You sunk my Battleship!");
				}
				target.hits++;
				shotFired = true;
				break;
			case "P":
				System.out.println("Hit!");
				target.newGrid[yShot][xShot] = "H";
				if (target.patrol1 < 2) {
					target.patrol1++;
					if (target.patrol1 == 2)
						System.out.println("You sunk my Patrol Boat!");
				} else {
					target.patrol2++;
					if (target.patrol2 == 2)
						System.out.println("You sunk my Patrol Boat!");
				}
				target.hits++;
				shotFired = true;
				break;
			}
		}
		return target;
	}

	public static int getNotDiagonalOrNotOne(int yStart, int yEnd, int xStart, int xEnd, int gridEnd, Boat notBoat) {
		Scanner s = new Scanner(System.in);
		if (yStart - yEnd == 0) {
			while (yStart - yEnd == xStart - xEnd) {
				System.out
						.println("Your " + notBoat.name + " is not 1 unit long. Please put another end Y co-ordinate.");
				yEnd = getCoOrdinateForBoat(notBoat, "Y", "end");
				while (yEnd < 0 || yEnd > gridEnd)
					yEnd = getCoOrdinateOnGrid();
				if (yStart - yEnd != notBoat.length - 1 && yEnd - yStart != notBoat.length - 1)
					yEnd = getLegalPlacement(notBoat, "Y", yStart);
			}
		} else {
			while (yStart == xStart && yEnd == xEnd) {
				System.out.println("You cannot place a battleship diagonally. Please put another end Y co-ordinate.");
				yEnd = getCoOrdinateForBoat(notBoat, "Y", "end");
				while (yEnd < 0 || yEnd > gridEnd)
					yEnd = getCoOrdinateOnGrid();
				if (yStart - yEnd != notBoat.length - 1 && yEnd - yStart != notBoat.length - 1 && yEnd - yStart != 0)
					yEnd = getLegalPlacement(notBoat, "Y", yStart);
			}
		}
		return yEnd;
	}

	public static String getCharForNumber(int i) { //I chose to use letters for the grid co-ordinates as they are always 1 char long and don't ruin the draw grid
		return i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : null;
	}

	public static int getNumberForCharacter(char c) {
		return Character.getNumericValue(c) - 10;
	}

}
