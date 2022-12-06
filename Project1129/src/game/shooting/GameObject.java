package game.shooting;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import util.ImageManager;

//게임에 등장할 모든~~~~~~~객체들의 최상위 클래스!!
public abstract class GameObject {
	int x;
	int y;
	int width;
	int height;
	int velX;
	int velY;
	ImageManager imageManager;
	Image image;
	Rectangle rect;
	GamePanel gamePanel; //게임관련된 객체들은 게임패널에 붙여야 하므로 게임오브젝트에 넣어두기!

	public GameObject(GamePanel gamePanel,int x, int y, int width, int height, int velX, int velY) {
		this.gamePanel=gamePanel;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.velX = velX;
		this.velY = velY;
		
		rect = new Rectangle(x, y, width, height); //충돌체크 매서드
		
		imageManager = new ImageManager();

	}

	public abstract void tick();

	public abstract void render(Graphics2D g);

}
