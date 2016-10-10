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
	public void addMove (int type, long startT, long endT, int distance, boolean isFullScreenBG){
		// max change in x, y
		int dXmax = 0;
		int dYmax = 0;
		
		// line
		int dX = (int) (distance * Math.cos(Math.PI/4));
		int dY = (int) (distance * Math.sin(Math.PI/4));
		long dt = (endT-startT)/2;
		// v shape
		int dX2 = (int) (distance/Math.sqrt(20));
		int dY2 = (int) (2*distance/Math.sqrt(20));
		
		switch(type){
		case CameraMove.Upward:
			add(new Move(startT,endT,x,y-distance/2,x,y+distance/2));
			dYmax = distance;
			break;
		case CameraMove.Downward:
			add(new Move(startT,endT,x,y+distance/2,x,y-distance/2));
			dYmax = distance;
			break;
		case CameraMove.toLeft:
			add(new Move(startT,endT,x-distance/2,y,x+distance/2,y));
			dXmax = distance;
			break;
		case CameraMove.toRight:
			add(new Move(startT,endT,x+distance/2,y,x-distance/2,y));
			dXmax = distance;
			break;
		case CameraMove.toTopRight:
			add(new Move(startT,endT,x+dX,y-dY,x-dX,y+dY));
			dXmax = 2*dX;
			dYmax = 2*dY;
			break;
		case CameraMove.toTopLeft:
			add(new Move(startT,endT,x-dX,y-dY,x+dX,y+dY));
			dXmax = 2*dX;
			dYmax = 2*dY;
			break;
		case CameraMove.toBottomLeft:
			add(new Move(startT,endT,x-dX,y+dY,x+dX,y-dY));
			dXmax = 2*dX;
			dYmax = 2*dY;
			break;
		case CameraMove.toBottomRight:
			add(new Move(startT,endT,x+dX,y+dY,x-dX,y-dY));
			dXmax = 2*dX;
			dYmax = 2*dY;
			break;
		case CameraMove.v_r:
			add(new Move(startT,startT+dt,x-dX2,y-dY2/2,x,y+dY2/2));
			add(new Move(startT+dt,endT,x,y+dY2/2,x+dX,y-dY2/2));
			reflectMovementsToOrigin();
			if (dX2>dX){
				dXmax = 2* dX2;
			} else {
				dXmax = 2* dX;
			}
			dYmax = 2*dY2;
			break;
			
		case CameraMove.v_reversed_r:
			add(new Move(startT,startT+dt,x-dX2,y-dY2/2,x,y+dY2/2));
			add(new Move(startT+dt,endT,x,y+dY2/2,x+dX,y-dY2/2));
			reflectMovementsToYAxis();
			if (dX2>dX){
				dXmax = 2* dX2;
			} else {
				dXmax = 2* dX;
			}
			dYmax = 2*dY2;
			break;
			
		case CameraMove.v_l:
			add(new Move(startT,startT+dt,x+dX,y-dY2,x,y+dY2/2));
			add(new Move(startT+dt,endT,x,y+dY2/2,x-dX2,y-dY2/2));
			if (dX2>dX){
				dXmax = 2* dX2;
			} else {
				dXmax = 2* dX;
			}
			dYmax = 2*dY2;
			reflectMovementsToXAxis();
			break;
			
		case CameraMove.v_reversed_l:
			add(new Move(startT,startT+dt,x-dX2,y-dY2/2,x,y+dY2/2));
			add(new Move(startT+dt,endT,x,y+dY2/2,x+dX,y-dY2/2));
			if (dX2>dX){
				dXmax = 2* dX2;
			} else {
				dXmax = 2* dX;
			}
			dYmax = 2*dY2;
			break;
			
		case CameraMove.halfCircle_top_ccw://give up
			addHalfTopCircleCCW(startT,endT,distance);
			break;
		case CameraMove.halfCircle_top_cw: //give up
			addHalfTopCircleCCW(startT,endT,distance);
			//reflectMovementToYAxis();
			break;

		default:
			System.out.println("undefined movement!");
			System.exit(-1);	
		}
		double scale = 0;
		if (isFullScreenBG){
			if (dYmax > dXmax){
				if (dYmax > 0){
					scale =  (1.0*Yc*2 + dYmax)/(Yc*2);
				}
			} else {
				if (dXmax > 0){
					scale = (1.0*Xc*2 + dXmax)/(Xc*2);
				}
			}
		VectorScale s1 = (VectorScale) getCommand(CommandName.VectorScale);
		s1.setSize(scale*s1.getEndScaleX());
		}
	}
	
	public Command getCommand(CommandName name){
		for (Command c : commands){
			if (c.getName().equals(name)){
				return c;
			}
		}
		return null;
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

	public void reflectMovementsToYAxis(){
		ArrayList<Command> newCommands = new ArrayList<>();
		for (Command c : commands){
			if (!(c instanceof Move)){
				// backup other commands
				newCommands.add(c.clone());
			} else{
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
	
	public void reflectMovementsToXAxis(){
		ArrayList<Command> newCommands = new ArrayList<>();
		for (Command c : commands){
			if (!(c instanceof Move)){
				// backup other commands
				newCommands.add(c.clone());
			} else{
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
	
	public void reflectMovementsToOrigin(){
		reflectMovementsToXAxis();
		reflectMovementsToYAxis();
	}
	
	public void mirrorAllMovementsFrom(VisualObject source){
		removeAll(CommandName.Move);
		for (Command c : source.commands){
			if (c instanceof Move){
				add( c.clone());
			}
		}
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

