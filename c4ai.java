import java.awt.Color;
import java.util.*;

import javax.swing.JButton;

public class c4ai {
	JButton[] board;

	public c4ai(JButton[] board, int depth) {
		this.board = board;
	}

	/**
	 * Minmax implementation, return value of a particular move, higher = better
	 * 
	 * @param col Column 
	 * @param board the int[] representation of the gameboard
	 * @param color positive = player, negative = ai
	 * @param depth tree search depth
	 * @return
	 */
	public static int minmax(int col, int[] board, int color, int depth) {
		// temp is board after move
		int[] temp = c4tool.copy(board);
		temp = c4tool.move(col, temp, color);
		// if there is win state, return max value (no need to check further)
		if (c4tool.checkWin(temp)) {
			return Integer.MAX_VALUE;
		}

		// heuristic value
		if (depth == 0) {
			return 0;
		}
		
		int alpha = Integer.MAX_VALUE;

		for (int x = 0; x <= 6; x++) {
			// check for full col
			if (temp[x] == 0) {
				alpha = min(alpha, -minmax(x, temp, -color, depth-1));
			}
		}
		return alpha;
	}

	public static int min(int a, int b) {
		if (a <= b) {
			return a;
		}
		return b;
	}

	public static int max(int a, int b) {
		if (a < b) {
			return b;
		}
		return a;
	}
}
