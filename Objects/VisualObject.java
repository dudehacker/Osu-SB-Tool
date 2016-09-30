package Objects;

import java.util.*;

import Commands.*;
import Utils.OsuUtils;
import effects.EffectsConstants;



// A visual object contains multiple commands
public abstract class VisualObject {
	private final String nl = OsuUtils.nl;
	
	protected Object type;
	private Layer layer; 
	protected Origin origin; //  where on the image should osu! consider that image's origin (coordinate) to be
	protected String filepath;
	// x and y are in terms of hit object and not the SB coordinate
	// auto convert to SB coordinate in constructor
	protected int x; 
	protected int y;
	protected ArrayList<Command> commands;
	protected CoordinateType coordinate;
	private int Xc = EffectsConstants.x;
	private int Yc = EffectsConstants.y;
	public VisualObject(ObjectType type, Layer layer, String filePath){
		this.type = type;
		this.layer = layer;
		this.filepath = filePath;
		commands = null;
	}
	
	public VisualObject(CoordinateType c, ObjectType type, Layer layer, String filePath , int x , int y){
		initializeParameters(c, type, layer,Origin.Centre, filePath , x ,y);
	}
	
	public VisualObject(CoordinateType c, ObjectType type, Layer layer,Origin origin, String filePath , int x , int y){
		initializeParameters(c, type, layer,origin, filePath , x ,y);
	}
	
	private void initializeParameters(CoordinateType c, ObjectType type, Layer layer,Origin origin, String filePath , int x , int y){
		this.type = type;
		this.setLayer(layer);
		this.origin = origin;
		this.filepath = filePath;
		coordinate = c;
		if (isSBCoordinate()){
			this.x = x;
			this.y = y;
		} else {
			this.x = OsuUtils.hitObjectXToStoryboardX(x);
			this.y = OsuUtils.hitObjectYToStoryboardY(y);
		}
		commands = new ArrayList<>();
	}
	
	public boolean isSBCoordinate(){
		return coordinate == CoordinateType.Storyboard;
	}
	
	public void add(Command c){
		commands.add(c);
	}
	
	public void remove(Command c){
		commands.remove(c);
	}
	
	public long getStartTime(){
		long startTime=Long.MAX_VALUE;
		if (commands.size()>0){
			for (Command c : commands){
				if (c.getStartTime()<startTime){
					startTime = c.getStartTime();
				}
			}
		}
		return startTime;
	}
	
	public long getEndTime(){
		long endTime=Long.MIN_VALUE;
		if (commands.size()>0){
			for (Command c : commands){
				if (c.getEndTime()>endTime){
					endTime = c.getEndTime();
				}
			}
		}
		return endTime;
	}
	
	public abstract String getHeader();
	public abstract VisualObject Clone();
		
	protected void addQuotesToFilePath(){
		filepath =  "\"" + filepath + "\"";
	}
	
	public void sortByStartTime(){
		commands.sort(Command.StartTimeComparator);
	}
	
	public void sortByEndTime(){
		commands.sort(Command.EndTimeComparator);
	}
	
	public boolean isSprite(){
		return type.equals(ObjectType.Sprite);
	}
	
	/**8 Basic linear movements
	 * 
	 * @param type Direction of movement
	 * @param startT End Time
	 * @param endT Start Time
	 * @param distance in SB coordinate
	 */
	public void addMove (int type, long startT, long endT, int distance){
		int dX = (int) (distance * Math.cos(Math.PI/4));
		int dY = (int) (distance * Math.sin(Math.PI/4));
		long dt = (endT-startT)/2;
		// v shape
		int dX2 = (int) (distance/Math.sqrt(20));
		int dY2 = (int) (2*distance/Math.sqrt(20));

		switch(type){
		case MoveTypes.Upward:
			add(new Move(startT,endT,x,y-distance/2,x,y+distance/2));
			break;
		case MoveTypes.Downward:
			add(new Move(startT,endT,x,y+distance/2,x,y-distance/2));
			break;
		case MoveTypes.toLeft:
			add(new Move(startT,endT,x-distance/2,y,x+distance/2,y));
			break;
		case MoveTypes.toRight:
			add(new Move(startT,endT,x+distance/2,y,x-distance/2,y));
			break;
		case MoveTypes.toTopRight:
			add(new Move(startT,endT,x+dX,y-dY,x-dX,y+dY));
			break;
		case MoveTypes.toTopLeft:
			add(new Move(startT,endT,x-dX,y-dY,x+dX,y+dY));
			break;
		case MoveTypes.toBottomLeft:
			add(new Move(startT,endT,x-dX,y+dY,x+dX,y-dY));
			break;
		case MoveTypes.toBottomRight:
			add(new Move(startT,endT,x+dX,y+dY,x-dX,y-dY));
			break;
		case MoveTypes.v_r:
			add(new Move(startT,startT+dt,x-dX2,y-dY2/2,x,y+dY2/2));
			add(new Move(startT+dt,endT,x,y+dY2/2,x+dX,y-dY2));
			reflectMovementToOrigin();
			break;
		case MoveTypes.v_l:
			add(new Move(startT,startT+dt,x+dX,y-dY2,x,y+dY2/2));
			add(new Move(startT+dt,endT,x,y+dY2/2,x-dX2,y-dY2/2));
			reflectMovementToXAxis();
			break;
		case MoveTypes.halfCircle_top_ccw:
			addHalfTopCircleCCW(startT,endT,distance);
			break;
		case MoveTypes.halfCircle_top_cw:
			addHalfTopCircleCCW(startT,endT,distance);
			//reflectMovementToYAxis();
			break;
		default:
			System.out.println("undefined movement!");
			System.exit(-1);
			
		}

	}
	
	private void addHalfTopCircleCCW(long startT, long endT, int distance){
		// half circle
		int n = 10;
		long dt2 = (endT-startT)/n;
		int r = (int) (distance * n / Math.PI);
		
		for (int i=0; i<n;i++){
			add( new Move(startT+dt2*i,startT+(i+1)*dt2,
					(int) (r*Math.cos(i*Math.PI/n))+x+r,(int) (-r*Math.sin(i*Math.PI/n)+y),
					(int) (r*Math.cos(i+1*Math.PI/n))+x+r,(int) (-r*Math.sin(i+1*Math.PI/n+y))   
				)     
			);
		}
	}

	public void reflectMovementToYAxis(){
		ArrayList<Command> newCommands = new ArrayList<>();
		for (Command c : commands){
			if (!(c instanceof Move)){
				// backup other commands
				newCommands.add(c.clone());
			} else{
				System.out.println("flipped to Y axis");
				Move m = ((Move) c);
				// create new move commands
				newCommands.add(new Move(c.getEasing(),c.getStartTime(),c.getEndTime(),
						2*Xc - m.getStartX(), m.getStartY(),
						2*Xc - m.getEndX(), m.getEndY()
						));
			}
		}
		commands = newCommands;
	}
	
	public void reflectMovementToXAxis(){
		ArrayList<Command> newCommands = new ArrayList<>();
		for (Command c : commands){
			if (!(c instanceof Move)){
				// backup other commands
				newCommands.add(c.clone());
			} else{
				System.out.println("flipped to X axis");
				Move m = ((Move) c);
				// create new move commands
				newCommands.add(new Move(c.getEasing(),c.getStartTime(),c.getEndTime(),
						m.getStartX(), Yc*2 - m.getStartY(),
						m.getEndX(), Yc*2 - m.getEndY()
						));
			}
		}
		commands = newCommands;
	}
	
	public void reflectMovementToOrigin(){
		reflectMovementToXAxis();
		reflectMovementToYAxis();
	}
	
	public String toString(){
		sortByStartTime();
		String output = getHeader() + nl;
		for (Command c: commands){
			if (!c.toString().equals(""))
			output += c.toString() + nl;
		}
		return output;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public void removeAll(CommandName name) {
		ArrayList<Command> newCommands = new ArrayList<>();
		for (Command c : commands){
			if (!c.getName().equals(name)){
				newCommands.add(c.clone());
			}
		}
		commands = newCommands;
		
	}
}

