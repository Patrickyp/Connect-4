/*
 * 
 */
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;


public class c4tool {
	/**
	 * Makes a copy of the given array.
	 * 0 = yellow, 1 = red, 2 = black
	 * @param ori
	 * @return The copy of the array.
	 */
	public static int[] copy(JButton[] ori){
		int len = ori.length;
		int[] copy = new int[len];
		for(int x = 0; x < len; x++){
			if(ori[x].getBackground() == Color.YELLOW){
				copy[x] = 0;
			}
			if(ori[x].getBackground() == Color.RED){
				copy[x] = 1;
			}
			if(ori[x].getBackground() == Color.BLACK){
				copy[x] = 2;
			}
		}
		return copy;
	}
	
	public static int[] copy(int[] ori){
		
		int[] copy = new int[ori.length];
		for(int x = 0; x < ori.length; x++){
			if(ori[x] == 0){
				copy[x] = 0;
			}
			if(ori[x] == 1){
				copy[x] = 1;
			}
			if(ori[x] == 2){
				copy[x] = 2;
			}
		}
		return copy;
	}
	/**
	 * Update the board based on move to given col.
	 * @param col The col where move is made.
	 * @param board The board to be updated.
	 * @param color The color of the player making this move.
	 * @return
	 */
	public static JButton[] move(int col, JButton[] board, int color){
		JButton[] temp = board.clone();
		if(c4tool.isFull(temp)){
			System.err.println("Invalid parameter: c4ai.move().");
			return null;
		}
		//loop starting from bottom square of the selected col
		for(int start = col + 35; start >= 0; start -= 7){
			//empty square found
			if(temp[start].getBackground() == Color.YELLOW){
				//if positive color, player move
				if(color > 0){
					temp[start].setBackground(Color.RED);
					temp[start].setIcon(c4gui.red);
					break;
				}
				//if negative color, ai move
				else{
					temp[start].setBackground(Color.BLACK);
					temp[start].setIcon(c4gui.black);
					break;
				}
			}
		}
		return temp;
	}
	public static int[] move(int col, int[] board, int color){
		int[] temp = copy(board);
		if(c4tool.isFull(board)){
			System.err.println("Invalid parameter: c4ai.move().");
			return null;
		}
		//loop starting from bottom square of the selected col
		for(int start = col + 35; start >= 0; start -= 7){
			//empty square found
			if(temp[start] == 0){
				//if positive color, player move
				if(color > 0){
					temp[start] = 1;
					break;
				}
				//if negative color, ai move
				else{
					temp[start] = 2;
					break;
				}
			}
		}
		return temp;
	}
	
	public static int[] undoMove(int col, int[] board){
		
		return board;
	}
	/**
	 * Check to see if there are any valid moves left.
	 * @return true if no valid moves left.
	 */
	public static boolean isFull(JButton[] b) {
		JButton[] board = b;
		for (int x = 0; x < board.length; x += 1) {
			if (board[x].getBackground() == Color.YELLOW) {
				return false;
			}
		}
		return true;
	}
	public static boolean isFull(int[] board) {
		for (int x = 0; x < board.length; x += 1) {
			if (board[x] == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method to check if there exist a win state on the board, loop through all
	 * squares to check for win
	 * @param board The board to be checked.
	 * @return false if no win state, true if there is a win state.
	 */
	public static boolean checkWin(int[] board){
		for(int x = 0; x < board.length; x++){
			if(board[x] != 0){
				if(c4tool.checkWin(board,x)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if there is a win for the player that made the last move This is
	 * an overview of the board[p] button positions relative to the game board 
	 *  0  1  2  3  4  5  6
	 *  7  8  9 10 11 12 13 
	 * 14 15 16 17 18 19 20
	 * 21 22 23 24 25 26 27
	 * 28 29 30 31 32 33 34 
	 * 35 36 37 38 39 40 41 
	 * 
	 * methods checks for wins in the vertical, horizontal and both diagonal directions based 
	 * on the last move's position i.e. 26
	 * 
	 * @param move
	 *            The number representation of the last move made.
	 * @param b
	 *            The array holding the board buttons
	 * @return true is there is a win state
	 */
	public static boolean checkWin(int[] b, int move) {

		// Counter to record number consecutive block, 4+ is win
		int h = 1;// "-" counter
		int v = 1;// "|" counter
		int d1 = 1;// "\" counter
		int d2 = 1;// "/" counter
		int color;
		if (b[move] == 2) {
			color = 2;
		} else {
			color = 1;
		}
		// ArrayLists holding the numbers of the outer edges of the game board,
		// used to tell the computer
		// to stop checking for consecutive colors when arrive at the edge
		// row/column.
		ArrayList<Integer> tBound = new ArrayList<Integer>();// 0,1,2,3,4,5,6
		for (int x = 0; x <= 6; x += 1) {
			tBound.add(x);
		}
		ArrayList<Integer> bBound = new ArrayList<Integer>();// 35,36,37,38,39,40,41
		for (int x = 35; x <= 41; x += 1) {
			bBound.add(x);
		}
		ArrayList<Integer> lBound = new ArrayList<Integer>();// 0,7,14,21,28,35
		for (int x = 0; x <= 35; x += 7) {
			lBound.add(x);
		}
		ArrayList<Integer> rBound = new ArrayList<Integer>();// 6,13,20,27,34,41
		for (int x = 6; x <= 41; x += 7) {
			rBound.add(x);
		}
		int[] board = b;
		// check horizontally (check in -> direction)
		int p = move; // reference the current square being examined, initially
						// the latest move
		int count = 0;
		// if reach blank square or board edge, stop loop
		while (board[p] != 0) {
			// if last move was made by p1
			if (color == 1) {
				// incriment appropriate counter for each square of the same
				// color, break otherwise
				if (board[p] == 1) {
					if (count > 0) {// does not count the first square being
									// examined as it's already known what color
									// it is
						h += 1;// increment the horizontal counter
					}
					if (rBound.contains(p)) {
						break;
					}
					p += 1;// move to the next square to examine in this case in
							// the -> direction
				} 
				else {
					break;
				}
			}
			// if last move was made by p2
			if (color == 2) {
				if (board[p] == 2) {
					if (count > 0) {
						h += 1;
					}
					if (rBound.contains(p)) {
						break;
					}
					p += 1;
				} 
				else {
					break;
				}
			}
			count += 1;
		}
		// check horizontal (check in <- direction)
		p = move;
		count = 0;
		while (board[p] != 0) {
			if (color == 1) {
				if (board[p] == 1) {
					if (count > 0) {
						h += 1;
					}
					if (lBound.contains(p)) {
						break;
					}

					p -= 1;
				} 
				else {
					break;
				}
			}
			if (color == 2) {
				if (board[p] == 2) {
					if (count > 0) {
						h += 1;
					}
					if (lBound.contains(p)) {
						break;
					}
					p -= 1;
				} 
				else {
					break;
				}
			}
			count += 1;
		}

		// check vertical (check for wins in this | direction)
		p = move;
		count = 0;
		while (board[p] != 0) {
			if (color == 1) {
				if (board[p] == 1) {
					if (count > 0) {
						v += 1;
					}
					if (bBound.contains(p)) {
						break;
					}
					p += 7;
				} 
				else {
					break;
				}
			}
			if (color == 2) {
				if (board[p] == 2) {
					if (count > 0) {
						v += 1;
					}
					if (bBound.contains(p)) {
						break;
					}
					p += 7;
				} 
				else {
					break;
				}
			}
			count += 1;
		}

		// check diagonal 1(check for wins in this \ direction)
		p = move;
		count = 0;
		while (board[p] != 0) {
			if (color == 1) {
				if (board[p] == 1) {
					if (count > 0) {
						d1 += 1;
					}
					if ((bBound.contains(p)) || (rBound.contains(p))) {
						break;
					}
					p += 8;
				} 
				else {
					break;
				}
			}
			if (color == 2) {
				if (board[p] == 2) {
					if (count > 0) {
						d1 += 1;
					}
					if ((bBound.contains(p)) || (rBound.contains(p))) {
						break;
					}
					p += 8;
				} 
				else {
					break;
				}
			}
			count += 1;
		}

		p = move;
		count = 0;
		while (board[p] != 0) {
			if (color == 1) {
				if (board[p] == 1) {
					if (count > 0) {
						d1 += 1;
					}
					if ((tBound.contains(p)) || (lBound.contains(p))) {
						break;
					}
					p -= 8;
				} 
				else {
					break;
				}
			}
			if (color == 2) {
				if (board[p] == 2) {
					if (count > 0) {
						d1 += 1;
					}
					if ((tBound.contains(p)) || (lBound.contains(p))) {
						break;
					}
					p -= 8;
				} 
				else {
					break;
				}
			}
			count += 1;
		}

		// check diagonal 2 (check for wins in this / direction)
		p = move;
		count = 0;
		while (board[p] != 0) {
			if (color == 1) {
				if (board[p] == 1) {
					if (count > 0) {
						d2 += 1;
					}
					if ((bBound.contains(p)) || (lBound.contains(p))) {
						break;
					}
					p += 6;
				} 
				else {
					break;
				}
			}
			if (color == 2) {
				if (board[p] == 2) {
					if (count > 0) {
						d2 += 1;
					}
					if ((bBound.contains(p)) || (lBound.contains(p))) {
						break;
					}
					p += 6;
				} 
				else {
					break;
				}
			}
			count += 1;
		}

		p = move;
		count = 0;
		while (board[p] != 0) {
			if (color == 1) {
				if (board[p] == 1) {
					if (count > 0) {
						d2 += 1;
					}
					if ((tBound.contains(p)) || (rBound.contains(p))) {
						break;
					}
					p -= 6;
				} 
				else {
					break;
				}
			}
			if (color == 2) {
				if (board[p] == 2) {
					if (count > 0) {
						d2 += 1;
					}
					if ((tBound.contains(p)) || (rBound.contains(p))) {
						break;
					}
					p -= 6;
				} 
				else {
					break;
				}
			}
			count += 1;
		}
		// if there are 4 or more same colors in a row return true otherwise
		// false
		return((h >= 4) || (v >= 4) || (d1 >= 4) || (d2 >= 4));
		
	}
}
