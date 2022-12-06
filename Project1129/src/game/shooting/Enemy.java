package game.shooting;

import java.awt.Graphics2D;

public class Enemy extends GameObject {

	public Enemy(GamePanel gamePanel, int x, int y, int width, int height, int velX, int velY, String path) {
		super(gamePanel, x, y, width, height, velX, velY);
		image = imageManager.getImage(path, width, height);
	}

	@Override
	public void tick() {
		x += this.velX;
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(image, x, y, width, height, null);
	}

}
