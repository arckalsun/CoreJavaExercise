package com.terena.shoot;

public class Bullet extends FlyingObject
{
	private int speed = 15;

	public Bullet(int x, int y)
	{
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;

	}

	public void step()
	{
		y -= speed;
	}

	public boolean outOfBounds()
	{
		return this.y <= -this.height;
	}
}
