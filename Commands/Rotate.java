package Commands;

import Utils.Easing;
import Utils.OsuUtils;

public class Rotate extends Command{
	// Radian
	// negative is anti-clockwise/counterclockwise rotation, positive is clockwise.
	private double startR; 
	private double endR;
	
	public Rotate(long startT, long endT){
		super(CommandName.Rotate, 0, startT, endT);
		setStartR(0);
		setEndR(0);
	}
	
	public Rotate(int easing, long startT, long endT, double startRadian, double endRadian) {
		super(CommandName.Rotate, easing, startT, endT);
		setStartR(startRadian);
		setEndR(endRadian);
	}

	public Rotate(int easing, long startT, long endT, double startRadian) {
		super(CommandName.Rotate, easing, startT, endT);
		setStartR(startRadian);
		setEndR(startRadian);
	}
	
	public Rotate(long startT, long endT, double startRadian, double endRadian) {
		super(CommandName.Rotate, 0, startT, endT);
		setStartR(startRadian);
		setEndR(endRadian);
	}
	
	public Rotate(long startT, long endT, double startRadian) {
		super(CommandName.Rotate, Easing.Linear, startT, endT);
		setStartR(startRadian);
		setEndR(startRadian);
	}
	
	@Override
	public Command clone() {
		return new Rotate(easing,startTime,endTime,startR,endR);
	}

	
	/**
	 * using 4 decimals to compare/optimise
	 */
	@Override
	public String toString() {
		String f = OsuUtils.format;
		String s = String.format(f,startR);
		String e = String.format(f,endR);
		if (s.equals(e)){
			return " R,"+easing+","+startTime+","+endTime+","+s;
		}
		return " R,"+easing+","+startTime+","+endTime+","+s + ","+e;
	}

	public void setCCWSpin(double cycles){
		endR = (Math.PI * -2 * cycles) + startR;
	}
	
	public void setPercentSpeed(double percent){
		double speed = (endR - startR)/(endTime-startTime); 
		speed = speed * percent;
		endR = speed*(endTime-startTime) + startR;
	}
	
	public double getStartR() {
		return startR;
	}


	public void setStartR(double startR) {
		this.startR = startR;
	}


	public double getEndR() {
		return endR;
	}


	public void setEndR(double endR) {
		this.endR = endR;
	}

}
