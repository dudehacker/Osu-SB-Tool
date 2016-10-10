package Commands;

import java.util.Random;


public class SpriteMove {
	// Linear
	public static final int Upward = 1;
	public static final int Downward = 0;
	public static final int toLeft = 3;
	public static final int toRight=2;
	public static final int toTopLeft = 7;
	public static final int toTopRight = 6;
	public static final int toBottomLeft = 5;
	public static final int toBottomRight = 4;
	
	// V shape
	public static final int v_r = 11;
	public static final int v_l = 10;
	public static final int v_reversed_r = 9;
	public static final int v_reversed_l = 8;
	// Half Circle
	public static final int halfCircle_top_ccw = 12;
	public static final int halfCircle_top_cw = 13;
	
	
	public static int getRandom(){
		Random r = new Random();
		return r.nextInt(14);
	}
	
}
