package game.shooting;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet2 extends GameObject {

	public Bullet2(GamePanel gamePanel, int x, int y ,int width, int height,
			int velX,int velY) {
		super(gamePanel,x,y,width,height,velX,velY);
	}
	public void tick() {
		this.x += this.velX;

		collsionCheck();
	}

	public void collsionCheck() {
		for (int i = 0; i < gamePanel.enemyList.size(); i++) {
			Enemy enemy = gamePanel.enemyList.get(i);
			boolean result=this.rect.intersects(enemy.rect);
			
			if(result) {
				int myIndex =gamePanel.bulletList.indexOf(this);
				gamePanel.bulletList.remove(myIndex);
				
				int youIndex=gamePanel.bulletList.indexOf(enemy);
				gamePanel.enemyList.remove(youIndex);
			}
		}
	}
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, width, height);
	}
}
