package game.shooting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

//총알을 정의한다!!
public class Bullet extends GameObject {

	public Bullet(GamePanel gamePanel, int x, int y, int width, int height, int velX, int velY) { // 자동생성을 이용하자!!
		super(gamePanel, x, y, width, height, velX, velY);
	}

	// 총알에 맞는 물리량 변화코드(부모의 메서드를 재정의함)
	public void tick() {
		this.x += this.velX;

		// 총알과 적군이 서로 교차하는지 조사해보자!!
		collsionCheck();
	}

	public void collsionCheck() {
		// 나와 적군들 교차여부 판단하기 !!
		for (int i = 0; i < gamePanel.enemyList.size(); i++) {
			Enemy enemy = gamePanel.enemyList.get(i);
			boolean result = this.rect.intersects(enemy.rect);
			// 컬렉션에서 제거하기
			if(result) {
				
				//나죽고
				int myIndex=gamePanel.bulletList.indexOf(this);
				gamePanel.bulletList.remove(myIndex);
			
				//너죽고
				int youIndex=gamePanel.enemyList.indexOf(enemy);
				gamePanel.enemyList.remove(youIndex);
			}                                                                                                                                                                              
			
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, width, height);
	}

}
