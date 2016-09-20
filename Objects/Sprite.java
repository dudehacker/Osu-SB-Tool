package Objects;

import java.util.ArrayList;

import Utils.OsuUtils;
import Commands.Command;


public class Sprite extends VisualObject{

	public Sprite(Layer layer, String filePath, int x, int y) {
		super(ObjectType.Sprite,layer, filePath, x, y);

	}
	
	public Sprite(Layer layer, Origin origin, String filePath, int x, int y) {
		super(ObjectType.Sprite,layer,origin,filePath, x, y);

	}

	
	@Override
	public String getHeader() {
		addQuotesToFilePath();
		return type + "," + getLayer() + "," + origin  +"," + filepath + "," + getX() + "," + getY();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Sprite Clone() {
		Sprite s = new Sprite(getLayer(),origin,filepath, OsuUtils.storyboardXToHitObjectX(x),OsuUtils.storyboardYToHitObjectY(y));
		s.commands = (ArrayList<Command>) commands.clone();
		return s;
	}

}
