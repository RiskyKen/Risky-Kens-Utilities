package riskyken.utilities.utils;

public class PointF {
	public float x;
	public float y;
	
	public PointF() {}
	
	public PointF(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public PointF(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
	}
	
}
