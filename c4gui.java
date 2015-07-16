import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

/**
 * A 2 player connect4 game implemented with JFrame and java awt/swing.
 */
public class c4gui implements ActionListener {
	int score1 = 0; // int to hold player1 score
	int score2 = 0; // int to hold player2 score

	String p1name = "Player 1"; // String to hold player1 name
	String p2name = "Player 2"; // String to hold player2 name
	int turn = 0;
	int lastm;
	boolean vsai = false; // set to true if against ai
	boolean gamestarted = false;// used to determine if start was pressed
	boolean marker = false;// used for isFull method

	JPanel container, topArea, centerArea, bottomArea, inputArea, scoreArea;
	JLabel p1NameLabel, p2NameLabel, scoreTitle, p1score, p2score;
	JTextField p1Input, p2Input;
	JLabel gameMessage;// game message display
	JButton[] board;
	JButton start1, start2, reset;
	static ImageIcon empty = new ImageIcon("empty.jpg");
	static ImageIcon black = new ImageIcon("black.jpg");
	static ImageIcon red = new ImageIcon("red.jpg");

	/**
	 * Constructs the gui.
	 */
	public c4gui() {
		c4tool tool = new c4tool();
		container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(720, 760)); // 720,760

		topArea = new JPanel();
		topArea.setLayout(new GridLayout(1, 3));

		// input pane
		inputArea = new JPanel();
		inputArea.setLayout(new BoxLayout(inputArea, BoxLayout.Y_AXIS));
		p1NameLabel = new JLabel("Player 1 Name");
		p1NameLabel.setForeground(Color.RED);
		p1NameLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		p1Input = new JTextField();
		p2NameLabel = new JLabel("Player 2 Name");
		p2NameLabel.setForeground(Color.BLACK);
		p2NameLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		p2Input = new JTextField();
		inputArea.add(p1NameLabel);
		inputArea.add(p1Input);
		inputArea.add(p2NameLabel);
		inputArea.add(p2Input);
		topArea.add(inputArea);

		// score pane
		scoreArea = new JPanel();
		scoreArea.setLayout(new BoxLayout(scoreArea, BoxLayout.Y_AXIS));
		scoreTitle = new JLabel("\t \t \t CURRENT \t SCORE:");
		scoreTitle.setFont(new Font("Serif", Font.BOLD, 18));
		p1score = new JLabel("\t \t \t " + p1name + "  : " + score1);
		p1score.setForeground(Color.RED);
		p1score.setFont(new Font("Serif", Font.PLAIN, 18));
		p2score = new JLabel("\t \t \t " + p2name + "  : " + score2);
		p2score.setForeground(Color.BLACK);
		p2score.setFont(new Font("Serif", Font.PLAIN, 18));
		scoreArea.add(scoreTitle);
		scoreArea.add(p1score);
		scoreArea.add(p2score);
		topArea.add(scoreArea);

		// game message pane
		gameMessage = new JLabel();
		// gameMessage.setEditable(false);
		gameMessage.setText("Press Start for new game.");
		gameMessage.setFont(new Font("Serif", Font.PLAIN, 18));
		topArea.add(gameMessage);
		container.add(topArea, BorderLayout.PAGE_START);

		// center area that holds the gameboard
		board = new JButton[42];
		centerArea = new JPanel(new GridLayout(6, 7));
		// create all the buttons with a for loop
		for (int x = 0; x < board.length; x++) {
			board[x] = new JButton();
			// board[x].setEnabled(false);
			board[x].setIcon(empty);
			board[x].setBackground(Color.YELLOW);
			board[x].addActionListener(this);
			centerArea.add(board[x]);
		}

		container.add(centerArea, BorderLayout.CENTER);

		// bottom area that holds the start/reset button
		bottomArea = new JPanel();
		bottomArea.setLayout(new BoxLayout(bottomArea, BoxLayout.X_AXIS));
		start1 = new JButton();
		start1.addActionListener(this);
		start1.setText("Player vs Player");
		start1.setFont(new Font("Serif", Font.BOLD, 25));
		start1.setBackground(Color.lightGray);
		start2 = new JButton();
		start2.addActionListener(this);
		start2.setText("Player vs Comp");
		start2.setFont(new Font("Serif", Font.BOLD, 25));
		start2.setBackground(Color.lightGray);
		reset = new JButton();
		reset.setText("Reset");
		reset.addActionListener(this);
		reset.setFont(new Font("Serif", Font.BOLD, 25));
		reset.setBackground(Color.lightGray);
		bottomArea.add(start1);
		bottomArea.add(start2);
		bottomArea.add(reset);
		container.add(bottomArea, BorderLayout.PAGE_END);
	}

	/*
	 * uncomment if you want to turn into applet public void init() { c4gui pad
	 * = new c4gui(); this.setContentPane(pad.container); setSize(560, 700); }
	 */

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == start2) {
			reset(0);
			vsai = true;
			if (gamestarted == false) {
				gamestarted = true;
				start1.setEnabled(false);
				p1Input.setEditable(false);
				p2Input.setEditable(false);
				parseName();
				p2name = "Computer";
				p1score.setText("\t \t \t " + p1name + "  : " + score1);
				p2score.setText("\t \t \t " + p2name + "  : " + score2);

				// random number generator to give each player 50% chance to go
				// 1st
				Random numGen = new Random();
				turn = numGen.nextInt(2) + 1;
				// update game message
				if (turn == 1) {
					gameMessage.setText(p1name + " is going first.");
				} else {
					gameMessage.setText("Computer is going first.");
					this.board = c4tool.move(3, board, -5); // always move to
															// middle on first
					turn = 1; //player turn right after ai
				}
			}
		}
		//player vs player
		if (ae.getSource() == start1) {
			reset(0);
			if (gamestarted == false) {
				vsai = false;
				gamestarted = true;
				start2.setEnabled(false);
				// players can't change their names after pressing start
				p1Input.setEditable(false);
				p2Input.setEditable(false);
				parseName();
				p1score.setText("\t \t \t " + p1name + "  : " + score1);
				p2score.setText("\t \t \t " + p2name + "  : " + score2);

				// random number generator to give each player 50% chance to go
				// 1st
				Random numGen = new Random();
				turn = numGen.nextInt(2) + 1;
				// update game message
				if (turn == 1) {
					gameMessage.setText(p1name + "'s \nmove.");
				} else {
					gameMessage.setText(p2name + "'s \nmove.");
				}

			}
		}

		// action listener for move squares, loops through all the move buttons
		// to finds which column was selected
		for (int m = 0; m < board.length; m++) {
			if (ae.getSource() == board[m]) {
				int top = m;
				// finds the top for a selected square
				while (true) {
					if (top <= 6) {
						break;
					}
					top -= 7;
				}
				// only works after "Start" is clicked
				if (gamestarted == true) {
					// checks if the top square(m is the top) is already
					// colored, which means the column is full, then quits
					if (board[top].getBackground() != Color.YELLOW) {
						return;
					}
					// from the chosen column keep going up till an empty square
					// is found, x is initially the bottom square
					// of the selected row
					for (int x = top + 35; x >= top; x -= 7) {
						// if an empty square is found...
						if (board[x].getBackground() == Color.YELLOW) {
							if (turn == 1) {
								board[x].setIcon(red);
								board[x].setBackground(Color.RED);
								//if win/tie, exit method
								if(afterMove(1)){
									return;
								}
								/*
								 * AI MOVE (ONLY APPLIES TO PLAYER VS AI)
								 */
								if (vsai) {	
									int value = Integer.MIN_VALUE;
									int col = 4;
									for (int c = 0; c <= 6; c++) {
										//this col is full check next col
										if(board[c].getBackground() != Color.YELLOW){
											continue;
										}
										int[] temp = c4tool.copy(board);
										//get the heuristic value for moving in column c
										int current = c4ai.minmax(c, temp, -5, 4);
										//if its the best move so far then value is updated
										if(value < current){
											value = current;
											col = c;
										}
										System.out.println("Col " + c + ": " + current);
									}
									c4tool.move(col, board,-5);
									if(afterMove(2)){
										return;
									}
									break;
								}
							/*
							 * Not turn 1 so must be player 2's move
							 */
							} else {
								board[x].setIcon(black);
								board[x].setBackground(Color.BLACK);
								if(afterMove(2)){
									return;
								}

							}
							
							break;

						}

					}
					if(!vsai){
						// Update game message for the next move, if it's player1
						// turn now it's player2 vice versa
						if (turn == 1) {
							turn = 2;
							gameMessage.setText(p2name + "'s \nmove.");
						} else {
							turn = 1;
							gameMessage.setText(p1name + "'s \nmove.");
						}
					}

				}
			}
		}
		if (ae.getSource() == reset) {
			reset(1);
		}
	}
	/**
	 * 
	 * @param t
	 * @return true if game is done i.e someone won or no valid moves
	 */
	private boolean afterMove(int t){
		int[] temp = c4tool.copy(this.board);
		if (c4tool.checkWin(temp)) {
			// who?
			if (t == 1) {
				gameMessage.setText(p1name + " \nWINS!");
				score1 += 1;
				p1score.setText("\t \t \t " + p1name
						+ "  : " + score1);
				gamestarted = false;
				return true;
			} else {
				gameMessage.setText(p2name + " \nWINS!");
				score2 += 1;
				p2score.setText("\t \t \t " + p2name
						+ "  : " + score2);
				gamestarted = false;
				return true;
			}
		}
		if (c4tool.isFull(board)) {
			gameMessage.setText("It's a TIE!");
			gamestarted = false;
			return true;
		}
		return false;
	}
	/**
	 * Resets the gameboard
	 */
	private void reset(int option) {
		gamestarted = false;
		if(option == 1){
			score1 = 0;
			score2 = 0;
		}
		start1.setEnabled(true);
		start2.setEnabled(true);
		p1Input.setEditable(true);
		p1Input.setText("");
		p2Input.setText("");
		p2Input.setEditable(true);
		this.p1name = "Player 1";
		this.p2name = "Player 2";
		p1score.setText("\t \t \t " + p1name + "  : " + score1);
		p2score.setText("\t \t \t " + p2name + "  : " + score2);
		// revert all squares to colorless
		for (int x = 0; x < board.length; x++) {
			board[x].setBackground(Color.YELLOW);
			board[x].setIcon(empty);
		}
		gameMessage.setText("Press Start for new game.");
		turn = 0;
	}

	/**
	 * Parse player names from the input boxes, set names to the instance
	 * variables.
	 */
	private void parseName() {
		// deciding player names, default if nothing entered
		if (!p1Input.getText().equals("")) {
			// if name is too long trims it to 8 char
			if (p1Input.getText().length() >= 8) {
				p1Input.setText(p1Input.getText().substring(0, 8));
				p1name = p1Input.getText().substring(0, 8);
			} else {
				p1name = p1Input.getText();
			}
		} else {
			p1name.equals("Player 1");
		}
		if (!p2Input.getText().equals("")) {
			if (p2Input.getText().length() >= 8) {
				p2Input.setText(p2Input.getText().substring(0, 8));
				p2name = p2Input.getText().substring(0, 8);

			} else {
				p2name = p2Input.getText();
			}
		} else {
			p2name.equals("Player 2");
		}
	}

}