package com.terena.shoot;

import java.awt.image.BufferedImage;
import sun.audio.*;

public abstract class FlyingObject
{
	protected int width;// 瀹�
	protected BufferedImage image;// 鍥剧墖
	protected int height;// 楂�
	protected int x;
	protected int y;

	public abstract void step();

	public abstract boolean outOfBounds();

	public boolean shootBy(Bullet bullet)
	{
		int x1 = this.x;
		int x2 = this.x + this.width;
		int y1 = this.y;
		int y2 = this.y + this.height;
		int x = bullet.x;
		int y = bullet.y;
		return x >= x1 && x <= x2 && y >= y1 && y <= y2;
	}
}
