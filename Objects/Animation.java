package Objects;

import java.util.ArrayList;

import Utils.OsuUtils;
import Commands.Command;


public class Animation extends VisualObject{
	private LoopType loopType;
	private int frameCount;
	private int frameDelay;
	
	public Animation(CoordinateType c, Layer layer, String filePath, int x, int y, int frameCount, int frameDelay, LoopType loopType) {
		super(c, ObjectType.Animation,layer, filePath, x, y);
		this.loopType = loopType;
		this.frameCount = frameCount;
		this.frameDelay = frameDelay;
	}

	public Animation(CoordinateType c, Layer layer, Origin origin, String filePath, int x, int y, int frameCount, int frameDelay, LoopType loopType) {
		super(c, ObjectType.Animation,layer,origin, filePath, x, y);
		this.loopType = loopType;
		this.frameCount = frameCount;
		this.frameDelay = frameDelay;
	}

	@Override
	public String getHeader() {
		addQuotesToFilePath();
		if (isSBCoordinate()){
			return type + "," + getLayer() + "," + origin  +"," + filepath + "," +  x + "," + y + "," + frameCount + "," + frameDelay + "," + loopType;
		}
		return type + "," + getLayer() + "," + origin  +"," + filepath + "," +  OsuUtils.hitObjectXToStoryboardX(getX()) + "," + OsuUtils.hitObjectYToStoryboardY(getY()) + "," + frameCount + "," + frameDelay + "," + loopType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public VisualObject Clone() {
		Animation a = new Animation(coordinate,getLayer(),origin,filepath,x,y,frameCount, frameDelay, loopType);
		a.commands = (ArrayList<Command>) commands.clone();
		return a;
	}

}
