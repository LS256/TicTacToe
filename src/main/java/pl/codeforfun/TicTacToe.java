package pl.codeforfun;

import java.awt.EventQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

public class TicTacToe {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			JFrame gameFrame = new GameFrame();
			}
		});

	}
}
