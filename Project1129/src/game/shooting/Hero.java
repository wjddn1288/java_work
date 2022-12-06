package game.shooting;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

import util.ImageManager;

public class Hero extends GameObject {

	public Hero(GamePanel gamePanel,int x, int y, int width, int height, int velX, int velY) {
		super(gamePanel, x, y, width, height, velX, velY);
		image = imageManager.getImage("res/plane.png", width, height);

	}

	// Hero의 물리량 변화
	public void tick() {
		this.x += this.velX;
		this.y += this.velY;
	}

	// Hero 는 컴포넌트가 아니므로, 그려질 수 없지만 JPanel인 GamePanel의 Graphics2D를 넘겨받아 왔으므로,
	// Hero에서 그려지는 모든 그래픽처리는 모두 GamePanel에 표현된다... 또한 java의 모든 게임 오브젝트들은
	// GamePanel 위에서 그려질 예정...
	public void render(Graphics2D g) {
		// g.setColor(Color.RED);
		// g.fillRect(x, y, width, height);
		g.drawImage(image, x, y, width, height, null);
	}

}
