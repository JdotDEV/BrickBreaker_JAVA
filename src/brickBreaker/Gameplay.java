package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0;
	
	private int totalbricks = 36;
	
	private Timer timer;
	private int delay = 1;
	
	private int playerX = (int) (Math.random() * 800) + 100;
	
	private int ballposX = (int) (Math.random() * 600) + 300;
	private int ballposY = (int) (Math.random() * 300) + 200;
	private int ballXdir = -2;
	private int ballYdir = -4;
	
	private MapGenerator map; 
	
	public Gameplay() {
		map = new MapGenerator(3,12);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		ballposX = (int) (Math.random() * 600) + 300;
	    ballposY = (int) (Math.random() * 300) + 200;
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		// BG
		g.setColor(Color.decode("#023047"));
		g.fillRect(1,1, 1192, 712);
		
		// Bricks drawing
		map.draw((Graphics2D)g);
		
		// Borders
		g.setColor(Color.decode("#ef476f"));
		g.fillRect(0, 0, 3, 712);
		g.fillRect(0, 0, 1192, 3);
		g.fillRect(1184, 0, 3, 712);
		// No border for bottom because ball should go down to end game
		
		// Scores
		g.setColor(Color.decode("#219EBC"));
		g.setFont(new Font("sans serif", Font.BOLD, 25));
		g.drawString(""+score, 1090, 30);
		
		// Paddle
		g.setColor(Color.decode("#8ECAE6"));
		g.fillRect(playerX, 650, 120, 8);
		
		// Ball
		g.setColor(Color.decode("#FFB703"));
		g.fillOval(ballposX, ballposY, 30, 30);
		
		if(totalbricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.decode("#ff6b35"));
			g.setFont(new Font("sans serif", Font.BOLD, 25));
			g.drawString("🔥 Ayy Hyy 🔥", 490, 350);
			g.drawString("You Won, Score: "+score, 460, 380);
			
			g.setFont(new Font("sans serif", Font.PLAIN, 20));
			g.drawString("Press ENTER to play again", 455, 450); 
			
		}
		
		if(ballposY > 700) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.decode("#ff6b35"));
			g.setFont(new Font("sans serif", Font.BOLD, 25));
			g.drawString("Game Over, Score: "+score, 490, 350);
			//g.drawString("Koi na bhai phirse try karle", 460, 380);
			
			g.setFont(new Font("sans serif", Font.PLAIN, 20));
			g.drawString("Press ENTER to restart", 510, 390); 
			
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX, ballposY, 30, 30).intersects(new Rectangle(playerX, 650, 120, 8))) {
				ballYdir = -ballYdir;
			}
			A: for(int i = 0; i < map.map.length; i++) {
				for(int j = 0; j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 30, 30);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalbricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							
							break A;
						}
					}
				}
			}
			
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 1150) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 1060) {
				playerX = 1060;
			} else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = (int) (Math.random() * 600) + 300;
			    ballposY = (int) (Math.random() * 300) + 200;
				ballXdir = -2;
				ballYdir = -4;
				playerX = (int) (Math.random() * 800) + 100;
				score = 0;
				totalbricks = 36;
				map = new MapGenerator(3, 12);
				
				repaint();
			}
		}
		
	}
	public void moveRight() {
		play = true;
		playerX += 40;
	}
	public void moveLeft() {
		play = true;
		playerX -= 40;
	}

	

}
