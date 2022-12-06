package game.shooting;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import util.ImageManager;

public abstract class GameObject2 {
	int x, y, width, height, velX, velY;
	// ImageManager imageManager;
	Rectangle rect;
	GamePanel gamePanel;
	ImageManager imageManager;
	Image image;

	public GameObject2(GamePanel gamePanle, int x, int y, int width, int height, int velX, int velY) {
		this.gamePanel = gamePanel;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.velX = velX;
		this.velY = velY;

		rect = new Rectangle(x, y, width, height);
		imageManager = new ImageManager();
	}

	public abstract void tick();

	public abstract void render(Graphics2D g);
}
