package image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ImageManager;

public class HeroControl2 extends JFrame{
	JPanel p;
	Image image;
	ImageManager imageManager;
	
	String[] imgName = {
			"res/hero/image1.png","res/hero/image2.png", "res/hero/image3.png", "res/hero/image4.png",
			"res/hero/image5.png", "res/hero/image6.png", "res/hero/image7.png", "res/hero/image8.png",
			"res/hero/image9.png", "res/hero/image10.png", "res/hero/image11.png", "res/hero/image12.png",
			"res/hero/image13.png", "res/hero/image14.png", "res/hero/image15.png", "res/hero/image16.png",
			"res/hero/image17.png", "res/hero/image18.png"};
	Image[] images;
	int index;
	Thread thread;
	
	public HeroControl2() {
		imageManager= new ImageManager();
		images =  imageManager.createImages(imgName);
		
		p=new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2= (Graphics2D) g;
				g2.clearRect(0,0,800,600);
				
				g2.drawImage(images[index],50,5,200,200,HeroControl2.this);
				if(index>=images.length-1) {
					index=0;
				}
			}
		};
		
		thread = new Thread() {
			public void run() {
				while(true) {
					gameLoop();
					try {
						thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		add(p);
		
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	public void tick() {
		index++;
	}
	public void render() {
		p.repaint();
	}
	public void gameLoop() {
		tick();
		render();
	}
	
	public static void main(String[]args) {
		new HeroControl2();
	}
}
