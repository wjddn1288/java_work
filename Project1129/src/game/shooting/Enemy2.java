package game.shooting;

import java.awt.Graphics2D;

public class Enemy2 extends GameObject {
	
	public Enemy2(GamePanel gamePanel, int x, int y,int width,int height,
			int velX,int velY, String path) {
		super(gamePanel,x,y,width,height,velX,velY);
		image=imageManager.getImage(path, width, height);
	}
	public void tick() {
	x+=this.velX;
	}
	public void render(Graphics2D g) {
		g.drawImage(image, x, y, width, height, null);
	}
}
