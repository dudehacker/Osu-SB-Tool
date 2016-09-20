package Objects;

import java.util.ArrayList;

import Utils.OsuUtils;
import Commands.Command;


public class Animation extends VisualObject{
	private Loop loopType;
	private int frameCount;
	private int frameDelay;
	
	public Animation(Layer layer, String filePath, int x, int y, int frameCount, int frameDelay, Loop loopType) {
		super(ObjectType.Animation,layer, filePath, x, y);
		this.loopType = loopType;
		this.frameCount = frameCount;
		this.frameDelay = frameDelay;
	}

	public Animation(Layer layer, Origin origin, String filePath, int x, int y, int frameCount, int frameDelay, Loop loopType) {
		super(ObjectType.Animation,layer,origin, filePath, x, y);
		this.loopType = loopType;
		this.frameCount = frameCount;
		this.frameDelay = frameDelay;
	}

	@Override
	public String getHeader() {
		addQuotesToFilePath();
		return type + "," + getLayer() + "," + origin  +"," + filepath + "," + x + "," + y + "," + frameCount + "," + frameDelay + "," + loopType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public VisualObject Clone() {
		Animation a = new Animation(getLayer(),origin,filepath,OsuUtils.storyboardXToHitObjectX(x),OsuUtils.storyboardYToHitObjectY(y),frameCount, frameDelay, loopType);
		a.commands = (ArrayList<Command>) commands.clone();
		return a;
	}

}
