package Objects;

import java.util.ArrayList;

import Utils.OsuUtils;
import Commands.Command;


public class Sprite extends VisualObject{

	public Sprite(CoordinateType c, Layer layer, String filePath, int x, int y) {
		super(c, ObjectType.Sprite,layer, filePath, x, y);

	}
	
	public Sprite(CoordinateType c, Layer layer, Origin origin, String filePath, int x, int y) {
		super(c,ObjectType.Sprite,layer,origin,filePath, x, y);

	}

	
	@Override
	public String getHeader() {
		addQuotesToFilePath();
		if (isSBCoordinate()){
			return type + "," + getLayer() + "," + origin  +"," + filepath + "," + x + "," + y;
		}
		return type + "," + getLayer() + "," + origin  +"," + filepath + "," 
		+ OsuUtils.hitObjectXToStoryboardX(x) + "," + OsuUtils.hitObjectYToStoryboardY(y);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Sprite Clone() {
		Sprite s = new Sprite(coordinate,getLayer(),origin,filepath, x,y);
		s.commands = (ArrayList<Command>) commands.clone();
		return s;
	}

}
