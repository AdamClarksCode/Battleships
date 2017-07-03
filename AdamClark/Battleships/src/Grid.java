
public class Grid {
	String[][] newGrid = new String[26][26];
	int hits = 0;
	int aircraft = 0;
	int destroyer = 0;
	int battleship1 = 0;
	int battleship2 = 0;
	int submarine = 0;
	int patrol1 = 0;
	int patrol2 = 0;
	
	// Co-ordinates limited to 26 as their are only that many letters in english
	public String[][] Board(int i, int j) {
		for (int y = 0; y < i; y++) {
			for (int x = 0; x < j; x++) {
				newGrid[y][x] = "#";
			}
		}
		return newGrid;
	}

}
