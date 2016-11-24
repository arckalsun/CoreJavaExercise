package test;



//´æ´¢Á½¸öBallDemo
class BallPairs
{
	public BallPoint b1;
	public BallPoint b2;
	
	public BallPairs(BallPoint b1, BallPoint b2)
	{
		this.b1 = b1;
		this.b2 = b2;
	}
	
}

class BallPoint
{
	public double x;
	public double y;
	public double ux;
	public double uy;
	
	public BallPoint(double px, double py, double pux, double puy)
	{
		this.x = px;
		this.y = py;
		this.ux = pux;
		this.uy = puy;
	}
}