package Commands;

import java.awt.Color;

//The virtual light source color on the object. The colors of the pixels on the object are determined subtractively.
//A color triple, written in decimal. The first value is red (R), the second green (G), and the third blue (B). 
//Each can vary from 0 to 255. (0,0,0) indicates black, (255,255,255) indicates white (original image). 
//Transparency is not affected.
public class ColorCMD extends Command{
	private int startR;
	private int endR;
	private int startG;
	private int endG;
	private int startB;
	private int endB;
	
	public ColorCMD(int easing, long startT, long endT, int startR,int startG,int startB,int endR,int endG,int endB) {
		super(CommandName.Color, easing, startT, endT);
		checkInputParameter(startR,startG,startB,endR,endG,endB);
	}

	public ColorCMD(long startT, long endT, int startR,int startG,int startB,int endR,int endG,int endB) {
		super(CommandName.Color, startT, endT);
		checkInputParameter(startR,startG,startB,endR,endG,endB);
	}
	
	public ColorCMD(int easing, long startT, long endT, int startR, int startG, int startB){
		super(CommandName.Color, easing, startT, endT);
		checkInputParameter(startR,startG,startB,startR,startG,startB);
	}
	
	public ColorCMD( long startT, long endT, int startR, int startG, int startB){
		super(CommandName.Color, startT, endT);
		checkInputParameter(startR,startG,startB,startR,startG,startB);
	}
	
	public ColorCMD( long startT, long endT, Color c){
		super(CommandName.Color, startT, endT);
		checkInputParameter(c.getRed(),c.getGreen(),c.getBlue(),c.getRed(),c.getGreen(),c.getBlue());
	} 
	
	private void checkInputParameter(int startR,int startG,int startB,int endR,int endG,int endB){
		if (startR>255){
			throw new IllegalArgumentException("Start Red is greater than max value of 255");
		}
		else if (startR<0){
			throw new IllegalArgumentException("Start Red is smaller than min value of 0");
		}
		if (startG>255){
			throw new IllegalArgumentException("Start Green is greater than max value of 255");
		}
		else if (startG<0){
			throw new IllegalArgumentException("Start Green is smaller than min value of 0");
		}
		if (startB>255){
			throw new IllegalArgumentException("Start Blue is greater than max value of 255");
		}
		else if (startB<0){
			throw new IllegalArgumentException("Start Blue is smaller than min value of 0");
		}
		if (endR>255){
			throw new IllegalArgumentException("End Red is greater than max value of 255");
		}
		else if (endR<0){
			throw new IllegalArgumentException("End Red is smaller than min value of 0");
		}
		if (endG>255){
			throw new IllegalArgumentException("End Green is greater than max value of 255");
		}
		else if (endG<0){
			throw new IllegalArgumentException("End Green is smaller than min value of 0");
		}
		if (endB>255){
			throw new IllegalArgumentException("End Blue is greater than max value of 255");
		}
		else if (endB<0){
			throw new IllegalArgumentException("End Blue is smaller than min value of 0");
		}
		this.setStartB(startB);
		this.setEndB(endB);
		this.setStartR(startR);
		this.setEndR(endR);
		this.setStartG(startG);
		this.setEndG(endG);
	}
	
	@Override
	public Command clone() {
		return new ColorCMD(easing,startTime,endTime,startR,startG,startB,endR,endG,endB);
	}

	@Override
	public String toString() {
		if (startR == endR && startG==endG && startB==endB){
			return " C,"+easing+","+startTime+","+endTime+","+toHex(startR)+","+toHex(startG)+","+toHex(startB);
		}
		return " C,"+easing+","+startTime+","+endTime+","+
		toHex(startR)+","+toHex(startG)+","+toHex(startB)+
		toHex(endR)+","+toHex(endG)+","+toHex(endB);
	}

	private String toHex(int v){
		if (v < 256){
			return "" + v;
		}
		return Integer.toHexString(v).toUpperCase();
	}
	
	public int getStartR() {
		return startR;
	}

	public void setStartR(int startR) {
		this.startR = startR;
	}

	public int getEndR() {
		return endR;
	}

	public void setEndR(int endR) {
		this.endR = endR;
	}

	public int getStartG() {
		return startG;
	}

	public void setStartG(int startG) {
		this.startG = startG;
	}

	public int getEndG() {
		return endG;
	}

	public void setEndG(int endG) {
		this.endG = endG;
	}

	public int getStartB() {
		return startB;
	}

	public void setStartB(int startB) {
		this.startB = startB;
	}

	public int getEndB() {
		return endB;
	}

	public void setEndB(int endB) {
		this.endB = endB;
	}

}
