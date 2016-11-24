package com.terena.shoot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;//鐎涙ぞ缍�

import sun.audio.*;

public class ShootGame extends JPanel
{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 654;
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage airplane;
	public static BufferedImage gameover;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	
	
	private static File wavfile;
	private int state = START;

	static
	{
		try
		{
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			
			
			wavfile = new File(ShootGame.class.getResource("shoot.wav").toURI());
			
		
		} catch (Exception e)
		{//
			e.printStackTrace();// stack閸棴绱漷race鐡掑疇鎶楅妴锟�
		}
	}
	/*
	 * String str=null; String str1="";
	 */
	private Hero hero = new Hero();
	private FlyingObject[] flying = {};// 閺勵垳鈹栭惃鍕剁礉閸掑棝鍘ら崘鍛摠娴滐拷
	private Bullet[] bullets = {};// 閺勵垳鈹栭惃鍕剁礉閸掑棝鍘ら崘鍛摠娴滐拷
	// ShootGame(){
	// flying= new FlyingObject[2];
	// flying[0]=new Airplane();
	// flying[1]=new Bee();
	// bullets=new Bullet[1];
	// bullets[0]=new Bullet(100,200);
	// }

	public FlyingObject nextone()
	{
		Random rand = new Random();
		int type = rand.nextInt(20);
		if (type < 4)
		{
			return new Bee();
		} else
		{
			return new Airplane();
		}
	}

	int flyEnterIndex = 0;

	public void enterAction()
	{
		flyEnterIndex++;
		if (flyEnterIndex % 40 == 0)
		{// 濮ｏ拷400濮ｎ偆顫楃挧棰佺濞嗭拷
			FlyingObject one = nextone();
			flying = Arrays.copyOf(flying, flying.length + 1);
			flying[flying.length - 1] = one;
		}
	}

	public void stepAction()
	{
		hero.step();
		for (int i = 0; i < flying.length; i++)
		{
			flying[i].step();
		}
		for (int i = 0; i < bullets.length; i++)
		{
			bullets[i].step();
		}
	}

	int shootIndex = 0;

	public void shootAction()
	{
		shootIndex++;
		if (shootIndex % 30 == 0)
		{
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// 閺佹壆绮嶉惃鍕嫹閸旓拷
		}
	}

	public void outOfBoundsAction()
	{
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flying.length];
		for (int i = 0; i < flying.length; i++)
		{
			FlyingObject f = flying[i];
			if (!f.outOfBounds())
			{
				flyingLives[index] = f;
				index++;
			}
		}

		flying = Arrays.copyOf(flyingLives, index);
		Bullet[] bullives = new Bullet[bullets.length];
		index = 0;
		for (int i = 0; i < bullets.length; i++)
		{
			Bullet b = bullets[i];
			if (!b.outOfBounds())
			{
				bullives[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bullives, index);
	}

	public void bangAction()
	{
		for (int i = 0; i < bullets.length; i++)
		{
			Bullet b = bullets[i];
			bang(b);
		}
	}

	int score = 0;

	public void bang(Bullet bullet)
	{
		int index = -1;
		for (int i = 0; i < flying.length; i++)
		{
			FlyingObject f = flying[i];
			if (f.shootBy(bullet))
			{
				index = i;
				break;
			}
		}
		if (index != -1)
		{
			FlyingObject one = flying[index];
			if (one instanceof Enemy)
			{
				Enemy ones = (Enemy) one;
				Runnable r = new Sound(wavfile);
				Thread t = new Thread(r);
				t.start();
				score += ones.getScore();
			}
			if (one instanceof Award)
			{
				Award ones = (Award) one;
				int type = ones.getType();
				switch (type)
				{
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire();
					break;
				case Award.LIFE:
					hero.addLife();
					break;
				}
			}
			FlyingObject e = flying[index];
			flying[index] = flying[flying.length - 1];
			flying[flying.length - 1] = e;
			flying = Arrays.copyOf(flying, flying.length - 1);
		}
	}

	public void checkGameOverAction()
	{
		if (isGameOver())
		{
			state = GAME_OVER;
		}
	}

	public boolean isGameOver()
	{
		for (int i = 0; i < flying.length; i++)
		{
			FlyingObject b = flying[i];
			if (hero.hit(b))
			{
				hero.subtractLife();
				hero.clearDoubleFire();
				FlyingObject f = flying[i];
				flying[i] = flying[flying.length - 1];
				flying[flying.length - 1] = f;
				flying = Arrays.copyOf(flying, flying.length - 1);

			}
		}
		return hero.getLife() <= 0;
	}

	public void action()
	{
		MouseAdapter l = new MouseAdapter()
		{
			public void mouseMoved(MouseEvent e)
			{
				if (state == RUNNING)
				{
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}

			public void mouseExited(MouseEvent e)
			{
				if (state == RUNNING)
				{
					state = PAUSE;
				}
			}

			public void mouseEntered(MouseEvent e)
			{
				if (state == PAUSE)
				{
					state = RUNNING;
				}
			}

			public void mouseClicked(MouseEvent e)
			{
				switch (state)
				{
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					hero = new Hero();
					flying = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;

					break;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		Timer timer = new Timer();
		int intervel = 10;// 娴犮儲顕犵粔鎺嶈礋閸楁洑缍卼
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				if (state == RUNNING)
				{
					enterAction();
					stepAction();
					shootAction();
					outOfBoundsAction();
					bangAction();
					checkGameOverAction();
				}

				repaint();// 闁插秶鏁�
			}
		}, intervel, intervel);// 鐠佲�冲灊

	}

	public void paint(Graphics g)
	{
		// 閼冲本娅� 閼婚亶娉熼張锟� 閻㈢粯鏅張锟�
		g.drawImage(background, 0, 0, null);
		paintHero(g);// 閻㈡槒瀚抽梿鍕簚鐎电钖�
		paintFlyingObjects(g);// 閻㈢粯鏅張锟�
		paintBullets(g);// 閻㈣鐡欏鐟邦嚠鐠烇拷
		paintScoreAndLife(g);// 閻㈣鍨庨崪宀�鏁鹃崨锟�
		paintState(g);
	}

	public void paintHero(Graphics g)
	{
		g.drawImage(hero.image, hero.x, hero.y, null);
	}

	public void paintFlyingObjects(Graphics g)
	{
		for (int i = 0; i < flying.length; i++)
		{
			FlyingObject f = flying[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}

	public void paintBullets(Graphics g)
	{
		for (int i = 0; i < bullets.length; i++)
		{
			Bullet f = bullets[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}

	public void paintScoreAndLife(Graphics g)
	{
		g.setColor(new Color(123456));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD/* 閸旂姷鐭� */, 24));
		g.drawString("SCORE:" + score, 10, 25);// 閻㈣鐡х粭锔胯閸欏﹤鍙鹃崸鎰垼
		g.drawString("LIFE:" + hero.getLife(), 10, 35);
	}

	public void paintState(Graphics g)
	{
		switch (state)
		{
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:

			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}

	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						JFrame frame = new JFrame("Fly");// 閻╁憡顢�
		ShootGame game = new ShootGame();// 闂堛垺婢�
		frame.add(game);// 鐏忓棝娼伴弶鎸庡潑閸旂姴鍩岄惄鍛婎攱娑擄拷
		frame.setSize(WIDTH, HEIGHT);// 鐠佸墽鐤嗙粣妤�褰涙径褍鐨�
		frame.setAlwaysOnTop(true);// 鐠佸墽鐤嗘稉锟介惄鏉戠湷娑擄拷
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 鐠佸墽鐤嗘妯款吇閸忔娊妫撮幙宥勭稊閿涘牏鐛ラ崣锝夛拷锟介崙鐑樻閸忔娊妫寸粙瀣碍閿涳拷
		frame.setLocationRelativeTo(null);// 鐠佸墽鐤嗙仦鍛厬
		frame.setVisible(true);// 1.鐠佸墽鐤嗛崣顖濐潌缁愭褰�2.鐏忚棄鎻╃拫鍐暏paint();
		game.action();// 閸氼垰濮╃粙瀣碍閹笛嗩攽
					}
			
				});
		
	}
}
