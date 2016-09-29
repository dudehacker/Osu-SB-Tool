package Commands;

public enum MoveTypes {
	Upward(1),
	Downward(2),
	toLeft(3),
	toRight(4),
	toTopLeft(5),
	toTopRight(6),
	toBottomLeft(7),
	toBottomRight(8)
	;
	
	private int type;
	
	MoveTypes(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
}
