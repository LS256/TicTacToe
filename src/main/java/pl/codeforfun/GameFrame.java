package pl.codeforfun;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author LS
 * Main class with TicTacToe game.
 */
public class GameFrame extends JFrame{
		static final int DEFAULT_HEIGHT = 320;
		static final int DEFAULT_WEIGHT = 250;
		static final int BOARD_SIZE = 3;
		static final int BOARD_DISTANCE  = 3;
		int counter = 0;

		static String[][] results = new String[BOARD_SIZE][BOARD_SIZE];
		Board board;
		JButton[] buttons = new JButton[10];

		public GameFrame() {
			super("Tic Tac Toe");	
			this.setSize(DEFAULT_WEIGHT, DEFAULT_HEIGHT);		
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLayout(new GridBagLayout());
			
			GamePanel gamePanel = new GamePanel();
			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridx = 0;
			gbc.gridy = 0;			
			this.add(gamePanel, gbc);
			
			JButton gameReset = new JButton("Game reset");
			gameReset.addActionListener(e->{
				gamePanel.newGame();
			});
			
			gbc.gridx=0;
			gbc.gridy=5;
			this.add(gameReset, gbc);
			
			pack();
			this.setVisible(true);	
			}
}
