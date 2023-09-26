import java.util.*;

public class AStarSearch {

	/**
	 * @param current The result of this method is the creation and assignment of
	 *                child nodes that form the minimized path to the goal state.
	 */
	public static void aStarSearch(BoardState current) {

		// Base case
		if (current.wrong.size() <= 1) {
			return;
		}

		// Subproblem
		List<BoardState> searchSpace = current.generateSearchSpace();
		float min = Integer.MAX_VALUE;
		BoardState next = null;
		for (BoardState candidate : searchSpace) {
			if (candidate.getFValue() < min) { // FIFO
				next = candidate;
				min = candidate.getFValue();
			}
		}

		// Solution
		current.child = next;
		aStarSearch(next);
		return;
	}

	public static void main(String[] args) {
		int[][] initialBoard = { { 0, 1, 3 }, 
					 { 8, 2, 6 }, 
					 { 7, 4, 5 } };

		BoardState initialState = new BoardState(initialBoard);
		aStarSearch(initialState);

		System.out.println("TRACE RESULTS=========================");
		result(initialState);
	}

	public static void result(BoardState board) {
		board.toString();
		int i = 0;
		while (board.child != null) {
			board.child.toString();
			board = board.child;
		}
	}
}
