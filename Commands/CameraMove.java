package Commands;

import java.util.Random;


public class CameraMove {
	// Linear
	public static final int Upward = 0;
	public static final int Downward = 1;
	public static final int toLeft = 2;
	public static final int toRight=3;
	public static final int toTopLeft = 4;
	public static final int toTopRight = 5;
	public static final int toBottomLeft = 6;
	public static final int toBottomRight = 7;
	
	// V shape
	public static final int v_r = 8;
	public static final int v_l = 9;
	public static final int v_reversed_r = 10;
	public static final int v_reversed_l = 11;
	// Half Circle
	public static final int halfCircle_top_ccw = 12;
	public static final int halfCircle_top_cw = 13;
	
	
	public static int getRandom(){
		Random r = new Random();
		return r.nextInt(14);
	}
	
}
