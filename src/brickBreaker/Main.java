package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		obj.setBounds(10, 10, 1200, 720);
		Gameplay gamePlay = new Gameplay();
		obj.setTitle("Brick Breaker (IU2141230022)");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);

	}

}