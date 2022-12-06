package game.shooting;

import java.awt.Graphics2D;

public class Hero2 extends GameObject {

	public Hero2(GamePanel gamePanel, int x, int y, int width, int height, int velX, int velY) {
		super(gamePanel, x, y, width, height, velX, velY);
		image = imageManager.getImage("res/plane.png", width, height);
	}

	public void tick() {
		this.x += this.velX;
		this.y += this.velY;
	}
	public void render(Graphics2D g) {
		g.drawImage(image, x, y, width, height, null);
	}
}
