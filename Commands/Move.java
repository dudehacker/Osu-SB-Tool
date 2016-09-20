package Commands;

public class Move extends Command{
	private int startX;
	private int startY;
	private int endX;
	private int endY;

	public Move(int easing, long startT, long endT, int startX, int startY, int endX, int endY) {
		super(CommandName.Move, easing, startT, endT);
		
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
