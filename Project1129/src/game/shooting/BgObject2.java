package game.shooting;

import java.awt.Graphics2D;

public class BgObject2 extends GameObject{
	
	public BgObject2(GamePanel gamePanel,int x, int y, int width, int height,
			int velX,int velY) {
		super(gamePanel,x,y,width,height,velX,velY);
		image=imageManager.getImage("res/game_bg.jpg", width, height);
	}

	@Override
	public void tick() {
		this.x+=this.velX;
		if(x<=-900) {
			x=0;
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(image, x, y, width, height, null);
		g.drawImage(image, x+900, y, width, height, null);
	}
}
