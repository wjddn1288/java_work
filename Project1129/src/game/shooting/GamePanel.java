package game.shooting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

//실제적으로 게임이 구현되는 영역
public class GamePanel extends JPanel {
	Thread loopThread; // 게임 엔진 역할을 수행할 루프쓰레드
	BgObject bgObject;
	Hero hero;
	ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	String[] enemyPath = { "res/e1.png", "res/e2.png", "res/e3.png", "res/e4.png" };

	public GamePanel() {
		setPreferredSize(new Dimension(900, 500));
		setBackground(Color.YELLOW);

		createBg();
		createHero();
		createEnemy();

		loopThread = new Thread() {
			public void run() {
				while (true) { // 항상 실행되야 하므로 죽지마!!
					gameLoop();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loopThread.start(); // 쓰레드 시작 (Runnable 진입)
	}

	// 패널의 그래픽 처리
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// 기존 그림 지우기
		g2.clearRect(0, 0, 900, 500);
		// 배경처리
		bgObject.render(g2);
		hero.render(g2);

		for (int i = 0; i < bulletList.size(); i++) {
			Bullet bullet = bulletList.get(i);
			bullet.render(g2);
		}
		for (int i = 0; i < enemyList.size(); i++) {
			Enemy enemy = enemyList.get(i);
			enemy.render(g2);
		}
	}

	// 배경 객체 생성
	public void createBg() {
		bgObject = new BgObject(this,0, 0, 900, 500, -1, 0);
	}

	// 주인공생성
	public void createHero() {
		hero = new Hero(this, 50, 100, 80, 60, 0, 0);
	}

	// 적군 생성
	public void createEnemy() {
		for (int i = 0; i < enemyPath.length; i++) {
			Enemy enemy = new Enemy(this, 850, 120 * i, 85, 70, -3, 0, enemyPath[i]);
			enemyList.add(enemy); // 컬렉션에 추가 !!!
		}
	}

	// 좌우 방향 처리
	public void moveX(int velX) {
		hero.velX = velX;
	}

	// 위아래 방향 처리
	public void moveY(int velY) {
		hero.velY = velY;
	}

	// 총알 발사
	public void fire() {
		Bullet bullet = new Bullet(this,hero.x + hero.width, hero.y + hero.height / 2, 20, 20, 10, 0);
		bulletList.add(bullet); // 컬렉션(리스트배열)에 총알 추가!!!
	}

	// 물리량 변화
	public void tick() {
		bgObject.tick();
		hero.tick();

		// 총알 개수만큼 tick()
		for (int i = 0; i < bulletList.size(); i++) {
			Bullet bullet = bulletList.get(i);
			bullet.tick();
		}

		// 적군 개수만큼 tick()
		for (int i = 0; i < enemyList.size(); i++) {
			Enemy enemy = enemyList.get(i);
			enemy.tick();
		}
	}

	// 렌더링 처리
	public void render() {
		repaint();
	}

	public void gameLoop() {
		// System.out.println("gameLoop() called...");
		tick();
		render();
	}
}
