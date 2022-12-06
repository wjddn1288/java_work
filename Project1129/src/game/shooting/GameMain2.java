package game.shooting;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class GameMain2 extends JFrame {
	GamePanel gamePanel;

	public GameMain2() {
		gamePanel =new GamePanel();
		add(gamePanel);
		
		pack();
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key =e.getKeyCode();
				
				switch(key) {
				case KeyEvent.VK_LEFT:gamePanel.moveX(-5);break;
				case KeyEvent.VK_RIGHT:gamePanel.moveX(5);break;
				case KeyEvent.VK_UP:gamePanel.moveX(-5);break;
				case KeyEvent.VK_DOWN:gamePanel.moveX(5);break;
				case KeyEvent.VK_SPACE:gamePanel.moveX(0);break;
				}
			}
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				switch(key) {
				case KeyEvent.VK_LEFT:gamePanel.moveX(0);break;
				case KeyEvent.VK_RIGHT:gamePanel.moveX(0);break;
				case KeyEvent.VK_UP:gamePanel.moveX(0);break;
				case KeyEvent.VK_DOWN:gamePanel.moveX(0);break;
				case KeyEvent.VK_SPACE:gamePanel.moveX(0);break;
				}
			}
		});

	}

	public static void main(String[] args) {
		new GameMain2();
	}
}
