package effects.customEffects;

import java.io.File;

import Commands.*;
import Objects.*;
import Utils.Easing;
import Utils.OsuUtils;
import effects.*;

/**
 * Solid color BG to fade in and fade out, default white color, sorta looks like a FlashBang grenade
 * @author DH
 *
 */
public class FlashBang extends Effects{

	private long duration = 1000;
	private double maxFade = 1;
	private File osuFile;
	
	
	public FlashBang(File osuFile, String fileName,long startTime){
		this.osuFile = osuFile;
		makeFlash(fileName, startTime,  duration,  maxFade);
	}
	
	public FlashBang(File osuFile, String fileName,long startTime, long duration){
		this.osuFile = osuFile;
		makeFlash(fileName, startTime,  duration,  maxFade);
	}
	
	public FlashBang(File osuFile, String fileName,long startTime, long duration, double maxFade){
		this.osuFile = osuFile;
		makeFlash(fileName, startTime,  duration,  maxFade);
	}
	
	
	private void makeFlash(String fileName, long startTime, long duration, double maxFade){
		Sprite flash = new Sprite(CoordinateType.Storyboard, Layer.Foreground, fileName, EffectsConstants.x, EffectsConstants.y);
		String fullPath = osuFile.getParent() + "\\" + fileName;
		long t1 = startTime-duration/4;
		long t3 = startTime+duration*3/4;
		double size = OsuUtils.getImageToSBSize(fullPath);
		flash.add(new VectorScale(t1,t3,size,size));
		flash.add(new Fade(Easing.ExpoOut,t1,startTime,0,maxFade));
		flash.add(new Fade(Easing.CircIn,startTime,t3,maxFade,0));
		add(flash);
	}
	
}
