package Commands;

import Utils.Easing;

public class Move extends Command{
	private int startX;
	private int startY;
	private int endX;
	private int endY;

	public Move(int easing, long startT, long endT, int startX, int startY, int endX, int endY) {
		super(CommandName.Move, easing, startT, endT);
		assignParemeters(startX,startY,endX,endY);
		
	}

	public Move(long startT, long endT, int startX, int startY, int endX, int endY) {
		super(CommandName.Move, Easing.Linear, startT, endT);
		assignParemeters(startX,startY,endX,endY);
	}
	
	
	private void assignParemeters(int startX, int startY, int endX, int endY){
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	@Override
	public Move clone() {
		return new Move(easing,startTime,endTime,startX,startY,endX,endY);
	}

	@Override
	public String toString() {
		if (startY == endY){
			return " MX," + easing + "," +startTime + "," +endTime + ","+startX+","+endX;
		} else if(startX == endX){
			return " MY," + easing + "," +startTime + "," +endTime + ","+startY+","+endY;
		}
		return " M," + easing + "," +startTime + "," +endTime + ","+startX+","+startY+","+endX+","+endY;
	}
}
