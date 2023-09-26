import java.util.*;

class BoardState {
	int[][] board;
	float gValue; // Cost to reach this state from the previous state
	float hValue; // Heuristic value: Cost to reach goal state from this state
	List<int[]> wrong; // A list of indices to track which numbers are in the wrong place.
	BoardState child;

	// Stores the correct index position of each number
	public static HashMap<Integer, int[]> goalMap = new HashMap<>();
	static {
		goalMap.put(1, new int[] { 0, 0 });
		goalMap.put(2, new int[] { 0, 1 });
		goalMap.put(3, new int[] { 0, 2 });
		goalMap.put(4, new int[] { 1, 2 });
		goalMap.put(5, new int[] { 2, 2 });
		goalMap.put(6, new int[] { 2, 1 });
		goalMap.put(7, new int[] { 2, 0 });
		goalMap.put(8, new int[] { 1, 0 });
	}

	public static int[][] goalState = { { 1, 2, 3 }, 
					    { 8, 0, 4 }, 
					    { 7, 6, 5 } };

	// Constructor method
	public BoardState(int[][] board) {
		this.board = board;
		this.wrong = misplacedValues(); // first element is empty tile position
		this.gValue = 0;
		this.hValue = calculateHeuristic();
		this.child = null;
	}

	private float calculateHeuristic() {
		float distance = 0;
		for (int[] el : wrong) {
			int i = el[0];
			int j = el[1];
			int value = board[i][j];
			if (value != 0) {
				int[] idx = goalMap.get(value);
				distance += Math.sqrt(Math.pow(Math.abs(i - idx[0]), 2) + Math.pow(Math.abs(j - idx[1]), 2));
			}
		}
		return distance;
	}

	public float getFValue() {
		return gValue + hValue;
	}

	public List<int[]> misplacedValues() {
		List<int[]> indexPairs = new ArrayList<>();
		int n = board.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 0) {
					// first element of wrong is always the position of the empty tile
					indexPairs.add(0, new int[] { i, j });
				} else if (board[i][j] != goalState[i][j]) {
					indexPairs.add(new int[] { i, j });
				}
			}
		}
		return indexPairs;
	}

	public List<BoardState> generateSearchSpace() {

		List<BoardState> searchSpace = new ArrayList<>();

		// find empty tile
		int[] emptyTile = wrong.get(0);
		int r = emptyTile[0];
		int c = emptyTile[1];

		// check if el in wrong is adjacent to the empty tile
		for (int[] el : wrong.subList(1, wrong.size())) {
			if (Math.abs(r - el[0]) <= 1 && Math.abs(c - el[1]) <= 1) {
				int[][] swapped = new int[board.length][board.length];

				// duplicate board
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board.length; j++) {
						swapped[i][j] = board[i][j];
					}
				}
				// swap elements
				swapped[r][c] = board[el[0]][el[1]];
				swapped[el[0]][el[1]] = 0;
				// add to searchSpace
				BoardState newBoard = new BoardState(swapped);
				newBoard.gValue = (float) Math.sqrt(Math.abs(r - el[0]) + Math.abs(c - el[1]));
				searchSpace.add(newBoard);

			}
		}

		return searchSpace;
	}

	@Override
	public String toString() {

		System.out.println("BoardState------------------");
		for (int[] el1 : board) {
			System.out.println(Arrays.toString(el1));
		}

		System.out.println("Misplaced Values-------------");
		for (int[] el2 : wrong) {
			System.out.print(Arrays.toString(el2));
		}
		System.out.println();

		System.out.println("gValue=" + gValue + ", hValue=" + hValue + ", fValue=" + this.getFValue());
		return "";
	}

}
