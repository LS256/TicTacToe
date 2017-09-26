package pl.codeforfun;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * 
 * @author LS
 * class where is created panel with all components. This panel will be added to GameFrame
 */

//	ToDo lines 244, 245 -to get game more dificult exchange i and j with on position otherwise there is simple algoritm to win the game

public class GamePanel extends JPanel {
	private static final int DEFAULT_WIDTH = 200;
	private static final int DEFAULT_HEIGHT = 200;
	private static final int BOARD_SIZE = 3;
	private static final int BOARD_DISTANCE  = 3;
	static String[][] results = new String[BOARD_SIZE][BOARD_SIZE];
	
	int counter = 0; 
	int xWins = 0;
	int yWins = 0;
	
	JButton[] gameButtons = new JButton[10];

	static final Logger logger = LogManager.getLogger(GamePanel.class.getName());
	
	JLabel player1score;
	JLabel player2score;
	
	GridBagConstraints gbc;
	Board board;
	
	/**
	 * Game Panel prepare layout of TicTacToe game
	 */
	public GamePanel(){

		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		for(int i = 1 ; i < 10 ; i++){
			gameButtons[i] = new JButton(" ");
			gameButtons[i].setActionCommand(Integer.toString(i));
			gameButtons[i].setPreferredSize(new Dimension(40,40));
			gameButtons[i].addActionListener((ActionEvent e) -> {
				JButton button = (JButton) e.getSource();

				if(counter % 2 == 0){
					button.setText("X");
					logger.info("Symbol x was inserted");
				} 
				counter++;
				button.setEnabled(false);
				move(button);	
			});
			add(gameButtons[i], setElementPosition(((i-1) % 3),((i-1) / 3),1,30,30));
		}	
		
		JLabel player1name = new JLabel("Player X : ");
		add(player1name, setElementPosition(0,5,2,10,10));
		
		player1score = new JLabel("0");
		add(player1score, setElementPosition(2,5,1,10,10));
		
		JLabel player2name = new JLabel("Player 0 : ");
		add(player2name, setElementPosition(0,6,2,10,10));
		
		player2score = new JLabel("0");
		add(player2score, setElementPosition(2,6,1,10,10));
			
	}
	
	/**
	 * Method which helps to fix position of JLabel and JButton
	 * @return gbc - configuration of exact element position
	 */
	public GridBagConstraints setElementPosition(int gridx, int gridy, int gridWidth, int ipadx, int ipady) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridWidth;
		gbc.ipadx = ipadx;
		gbc.ipady = ipady;
		gbc.fill = GridBagConstraints.BOTH;
		return gbc;
	}
	
	/**
	 * method responsible for creating move of opponent.
	 * @param button - allows to find out where playerX clicked and base on that we check if he won.
	 * if playerX haven't won then position for opponent move is searched.
	 */
	public void move(JButton button){
		int position = Integer.parseInt(button.getActionCommand());
		//	change number of button to it's coordinates in array
		int row = (position-1) / 3;
		int col = (position-1) % 3;
		String state = button.getText();
		results[row][col] = state;
		if(checkIfWin(row, col, state)){
			int xWins = Integer.valueOf(player1score.getText()) + 1;
			player1score.setText(Integer.toString(xWins));
			counter = 9;
		} else {
			whereToMove(row, col, "O");
		}
		//	check if any moves are possible
		if(counter >=9){
			for(int i = 1 ; i < 10 ; i++) {
				if(!gameButtons[i].getText().equals("X") || !gameButtons[i].getText().equals("O")){
					gameButtons[i].setEnabled(false);
					}
				}
		}
		
	}
	
	/**
	 * Check if current player won the game
	 * game board has 9 fields with numbers from 1 to 9. 
	 * @param row - represent a row in game board. Range between 0 and 2
	 * @param col - represent a col in game board. Range between 0 and 2
	 * @param state - represent symbol of current player
	 * @return true in case if checked player won, otherwise return false
	 */
	public boolean checkIfWin(int row, int col, String state){
		
		//	check if in horizontal position are three the same symbols
		for(int i = 0; i < BOARD_SIZE ; i++) {
			if(results[row][i] != state) {
				break;
			}
			if( i == (BOARD_SIZE - 1)) {
				for(int j = 0; j<BOARD_SIZE; j++){
					gameButtons[row*3+1+j].setBackground(Color.GREEN);
				}
				return true;
			}
		}
		
		// check if in vertical position are three the same symbols
		for(int i = 0; i < BOARD_SIZE ; i++) {
			if(results[i][col] != state) {
				break;
			}
			if( i == (BOARD_SIZE - 1)) {
				for(int j = 0; j<BOARD_SIZE; j++){
					gameButtons[j*3+1+col].setBackground(Color.GREEN);
				}
				return true;
			}
		}
		
		//	check if on diagonal are three the same symbols
		if(row == col) {
			for(int i = 0; i < BOARD_SIZE ; i++) {
				
				if(results[i][i] != state) {
					break;
				}
				if( i == (BOARD_SIZE - 1)) {
					for(int j = 0 ; j<BOARD_SIZE; j++) {
						gameButtons[j*3+j+1].setBackground(Color.GREEN);
					}
					return true;
				}
			}
		}
		
		//	check if on hypotenuse are three the same symbols
		for(int i = 0 ; i < BOARD_SIZE ; i++) {
			if(results[i][BOARD_SIZE-1-i] != state){
				break;
			}
			if(i == BOARD_SIZE - 1){
				for(int j = 0 ; j < BOARD_SIZE ; j++ ) {
					gameButtons[j*3+BOARD_SIZE-j].setBackground(Color.GREEN);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method where is checked necessity of opponent blocking
	 * it is using the sub methods for single element checking for example row check, col check and diagonal check
	 */
	public void whereToMove(int row, int col, String opponentSymbol){

		if(!checkIfBlockDiagonal().getState().equals("Z")){
			blockingFunction(checkIfBlockDiagonal().getRow(),checkIfBlockDiagonal().getColumn(), opponentSymbol);	
		}
		else if(!checkIfBlockAntiDiagonal().getState().equals("Z")){
			blockingFunction(checkIfBlockAntiDiagonal().getRow(),checkIfBlockAntiDiagonal().getColumn(), opponentSymbol);	
		}  
		else if(!checkIfBlockRow().getState().equals("Z")){
			blockingFunction(checkIfBlockRow().getRow(),checkIfBlockRow().getColumn(), opponentSymbol);				
		} 
		else if (!checkIfBlockCol().getState().equals("Z")){
			blockingFunction(checkIfBlockCol().getRow(),checkIfBlockCol().getColumn(), opponentSymbol);
		}
		else {
			firstOpponentMove(row, col, opponentSymbol);
		}
	}

	
	/*
	 * Method where we check where to move if there is no necessity of blocking
	 */
	public void firstOpponentMove(int row, int col, String opponentSymbol) {
		boolean isEmptyField = false;
		while(!isEmptyField & counter<9){
			if(results[col][row] == null) {
				blockingFunction(col, row, opponentSymbol);
				isEmptyField = true;
			} else {
				for(int i=0; i<BOARD_SIZE; i++){
					for(int j=0; j<BOARD_SIZE; j++){
						if(results[i][j] == null){
							blockingFunction(i, j, opponentSymbol);
							isEmptyField = true;	
							i=BOARD_SIZE;
							j=BOARD_SIZE;
						}
					}
				}
			}
		}
	}
	
	
	
	/**
	 * to short blocking process in method whereToMove.
	 */
	public void blockingFunction(int row, int col, String opponentSymbol){
		int tempVar = row*3+col+1;
		gameButtons[tempVar].setText(opponentSymbol) ;
		gameButtons[tempVar].setEnabled(false);
		results[row][col] = opponentSymbol;
		counter++;	
		if(counter>=9){
			JOptionPane.showMessageDialog(this, "Dead-heat - press Game reset button to play once again.");
		}
		if(checkIfWin(row, col, opponentSymbol)){
			JOptionPane.showMessageDialog(this, "Player X won - press Game reset button to play once again.");
			int oWins = Integer.valueOf(player2score.getText()) + 1;
			player2score.setText(Integer.toString(oWins));
			counter = 9;
		}
		logger.info("Block on: [" + row + ", " + col + "]");
	}
	
	/**
	 * check if opponent should be blocked in row
	 * @return board - if there is necessity of blocking the certain row then it include number of row and column where 'O' symbol should be inserted. 
	 * it also include state symbol. if it's 'O' then it means that this point should be block otherwise 'Z' = no necessity to block. 
	 */
	public Board checkIfBlockRow(){
		int xCounter = 0;
		int blankCounter = 0;
		int tempRow = 0;
		int tempCol = 0;	
		
		for(int i = 0 ; i < BOARD_SIZE ; i++){
			for(int j = 0 ; j < BOARD_SIZE ; j++){
				if(results[i][j] != null){
					if(results[i][j].equals("X")){
						xCounter++;
					}
				} else {
					blankCounter++;
					tempRow = i;
					tempCol = j;
				}					
				if(xCounter>1 && blankCounter==1){
					return new Board(tempRow, tempCol, "O");
				}
			}
			xCounter=0;
			blankCounter=0;
		}
		return new Board(3, 3, "Z");
	}
	
	 /** check if opponent should be blocked in column
	 * @return board - if there is necessity of blocking the certain column then it include number of row and column where 'O' symbol should be inserted. 
	 * it also include state symbol. if it's 'O' then it means that this point should be block otherwise 'Z' = no necessity to block. 
	 */
	public Board checkIfBlockCol(){
		int xCounter = 0;
		int blankCounter = 0;
		int tempRow = 0;
		int tempCol = 8;	
		
		for(int i = 0 ; i < BOARD_SIZE ; i++){
			for(int j = 0 ; j < BOARD_SIZE ; j++){
				if(results[j][i] != null){
					if(results[j][i].equals("X")){
						xCounter++;
					}
				} else {
					blankCounter++;
					tempRow = j;
					tempCol = i;
				}					
				if(xCounter>1 && blankCounter==1){
					return new Board(tempRow, tempCol, "O");
				}
			}
			xCounter=0;
			blankCounter=0;
		}
		return new Board(3, 3, "Z");
	}
			
	
	 /** check if opponent should be blocked in diagonal
	 * @return board - if there is necessity of blocking the diagonal then it include number of row and column where 'O' symbol should be inserted. 
	 * it also include state symbol. if it's 'O' then it means that this point should be block otherwise 'Z' = no necessity to block. 
	 */
	public Board checkIfBlockDiagonal(){
		int xCounter = 0;
		int blankCounter = 0;
		int tempRow = 0;
		int tempCol = 0;
		
		for(int i = 0 ; i < BOARD_SIZE ; i++){
				if(results[i][i]!= null){
					if(results[i][i].equals("X")){
						xCounter++;
					}
				} else {
					blankCounter++;
					tempRow = i;
					tempCol = i;
				}
				if(xCounter>1 && blankCounter==1){
					return new Board(tempRow, tempCol, "O");
				}
		}
		return new Board(3,3,"Z");
	}
	
	 /** check if opponent should be blocked in anti-diagonal
	 * @return board - if there is necessity of blocking the anti diagonal then it include number of row and column where 'O' symbol should be inserted. 
	 * it also include state symbol. if it's 'O' then it means that this point should be block otherwise 'Z' = no necessity to block. 
	 */
	public Board checkIfBlockAntiDiagonal(){
		int xCounter = 0;
		int blankCounter = 0;
		int tempRow = 0;
		int tempCol = 0;
		
		for(int i = 0 ; i < BOARD_SIZE ; i++){
				if(results[i][BOARD_SIZE-1-i] != null){
					
					if(results[i][BOARD_SIZE-1-i].equals("X")){
						xCounter++;
					}
				} else {
					blankCounter++;
					tempRow = i;
					tempCol = BOARD_SIZE-1-i;
				}
				if(xCounter>1 && blankCounter==1){
					return new Board(tempRow, tempCol, "O");
				}
		}
		return new Board(3,3,"Z");
	}
	
	public void newGame(){
		for(int i = 1; i<10 ; i++){
			gameButtons[i].setEnabled(true);
			gameButtons[i].setBackground(new Color(238,238,238));
			gameButtons[i].setText("");
		}
	
		for(int i = 0 ; i < BOARD_SIZE ; i++){
			for(int j = 0 ; j < BOARD_SIZE ; j++){
				results[i][j] = null;
			}
		}	
		counter = 0;
	}
	
}
