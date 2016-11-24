package com.terena.shoot;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject
{
	public Hero()
	{
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
		life = 3;
		doubleFire = 0;
		images = new BufferedImage[]
		{ ShootGame.hero0, ShootGame.hero1 };
		index = 0;

	}

	public boolean outOfBounds()
	{
		return false;
	}

	public void step()
	{// 10姣璧颁竴娆�

		image = images[index++ / 10 % 2];

	}

	public Bullet[] shoot()
	{
		int xStep = this.width / 4;
		if (doubleFire > 0)
		{
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x + xStep, this.y);
			bs[1] = new Bullet(this.x + 3 * xStep, this.y);
			doubleFire -= 2;
			return bs;
		} else
		{
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x + 2 * xStep, this.y);
			return bs;
		}
	}

	public void moveTo(int x, int y)
	{
		this.y = y - this.height / 2;
		this.x = x - this.width / 2;
	}

	private int life;
	private int doubleFire;
	private BufferedImage[] images;
	private int index;

	public void addDoubleFire()
	{
		doubleFire += 40;
	}

	public boolean hit(FlyingObject other)
	{
		int x1 = other.x - this.width / 2;
		int x2 = other.x + other.width + this.width / 2;
		int y1 = other.y - this.height / 2;
		int y2 = other.y + other.height + this.height / 2;
		int x = this.x + this.width / 2;
		int y = this.y + this.height / 2;
		return x <= x2 && x >= x1 && y <= y2 && y >= y1;
	}

	public void addLife()
	{
		life++;
	}

	public void subtractLife()
	{
		life--;
	}

	public void clearDoubleFire()
	{
		doubleFire = 0;
	}

	public int getLife()
	{
		return life;
	}
}
