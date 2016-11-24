package com.terena.shoot;

import java.util.Random;

public class Bee extends FlyingObject implements Award {
	private int xSpeed=1;
	private int ySpeed=2;
	private int awardType;
	public Bee(){
		image=ShootGame.bee;
		width=image.getWidth();
		height=image.getHeight();
		Random rand=new Random();
		x=rand.nextInt(ShootGame.WIDTH-this.width);
		y=-this.height;
		awardType=rand.nextInt(2);
	}
	public boolean outOfBounds(){
		return this.y>=ShootGame.HEIGHT;
	}
	public int getType(){
		return awardType;
	}
	public void step(){
		x+=xSpeed;
		y+=ySpeed;
		if(x>=ShootGame.WIDTH-this.width){
			xSpeed=-1;
			if(x<=0){
				xSpeed=1;
			}
		}
	}
}
