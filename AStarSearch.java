import java.util.*;

public class AStarSearch {
    public static void aStarSearch(BoardState current) {
        if (current.wrong.size() <=1) {
        	return;
        }
       
        List<BoardState> neighbors = current.generateNeighbors();
        float min = Integer.MAX_VALUE;
        BoardState next = null;
        for (BoardState neighbor : neighbors) {
            if (neighbor.getFValue() < min) { //FIFO
            	next = neighbor;
            	min = neighbor.getFValue();
            }
        }
        current.child = next;
        aStarSearch(next);
        return;
    }

public static void main(String[] args) {
        // Define your initial 3x3 board state here.
        int[][] initialBoard = {
            {0, 1, 3},
            {8, 2, 6},
            {7, 4, 5}
        };

        BoardState initialState = new BoardState(initialBoard);
        aStarSearch(initialState);

        System.out.println("TRACE RESULTS=========================================");
        result(initialState);
    }

	public static void result(BoardState board) {
		board.toString();
		int i = 0;
		while(board.child != null) {
			board.child.toString();
			board = board.child;
		}
	}

}
