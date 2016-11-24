package test;


public class TestTest
{

	public static void main(String[] args)
	{
		BallPoint bd1 = new BallPoint(758.0, 158.0,    -0.1, 0.1);
		BallPoint bd2 = new BallPoint(690.0, 204.0,     0.1, -0.1);
		BallPairs b = collide(bd1,bd2);
		
	}
	public static BallPairs collide(BallPoint bd1, BallPoint  bd2)
	{
		// b1
		double x1 = bd1.x;
		double y1 = bd1.y;
		double ux = bd1.ux;
		double uy = bd1.uy;
		// b2
		double x2 = bd2.x;
		double y2 = bd2.x;
		double vx = bd2.ux;
		double vy = bd2.uy;
		
		double d = 80;
		//中间量
		double px = ux + vx;
		double py = uy + vy;
		double cos2 = (x2-x1) / Math.sqrt((x2-x1) * (x2-x1) + (y2-y1)*(y2-y1));
		double sin2 = (y2-y1) / Math.sqrt((x2-x1) * (x2-x1) + (y2-y1)*(y2-y1));
		double u = Math.sqrt(ux*ux + uy*uy);
		
		double cos1 = ux/u * cos2 - uy/u * sin2;
		if (cos1 > 1 || cos1 < -1)
			cos1 = ux/u * cos2 + uy/u * sin2;
		double sin1 = Math.sqrt(1-cos1*cos1);
		
		
		
		
		double ux_ = u * cos2 * (cos1 + sin1);
		double uy_ = u * sin2 * (cos1 + sin1);
		double vx_ = px - ux_;
		double vy_ = py - uy_;
		
		if (ux ==0 && uy == 0)
		{
			ux_ = vx;
			uy_ = vy;
			vx_ = 0;
			vy_ = 0;
		}
		if (vx == 0 && vy == 0)
		{
			vx_ = ux;
			vy_ = uy;
			ux_ = 0;
			uy_ = 0;
		}
		System.out.println("前	1: "+ ux + ", " + uy + ";    2: "+ vx + ", " + vy);
		System.out.println("后	1: "+ ux_ + ", " + uy_ + ";    2: "+ vx_ + ", " + vy_);
		return new BallPairs(new BallPoint(x1,y1,ux_, uy_), new BallPoint(x2,y2,vx_,vy_));
		
	}
}
