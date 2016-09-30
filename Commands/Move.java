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
		this.setStartX(startX);
		this.setStartY(startY);
		this.setEndX(endX);
		this.setEndY(endY);
	}
	
	@Override
	public Move clone() {
		return new Move(easing,startTime,endTime,getStartX(),getStartY(),getEndX(),getEndY());
	}

	@Override
	public String toString() {
		if (getStartY() == getEndY()){
			return " MX," + easing + "," +startTime + "," +endTime + ","+getStartX()+","+getEndX();
		} else if(getStartX() == getEndX()){
			return " MY," + easing + "," +startTime + "," +endTime + ","+getStartY()+","+getEndY();
		}
		return " M," + easing + "," +startTime + "," +endTime + ","+getStartX()+","+getStartY()+","+getEndX()+","+getEndY();
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}
}
