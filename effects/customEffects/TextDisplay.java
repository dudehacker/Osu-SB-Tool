package effects.customEffects;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Random;

import Commands.*;
import Objects.*;
import Utils.*;
import effects.*;

/**
 * Font name needs to be same as character name 
 * Except the following characters need to be named using unicode value:
 * 1. Capital letters
 * 2. Character not supported in filename like / \ * etc
 * 3. Character with unicode value > 266B
 * 
 * @author DH
 *
 */
public class TextDisplay extends Effects{
	private String path = EffectsConstants.textPath;
	private int space = EffectsConstants.spaceBetween2Image;
	private int spaceWidth = EffectsConstants.spaceCharacterWidth;
	private double startFade = EffectsConstants.fade;
	private double endFade = 0;
	private long fadeDuration = 1000;
	
	// need testing for size
	private double size = 0.2 ;
	private double ImagetoSBSize = 0.2;
	
/**
 * 
 * @param c Coordinate Type
 * @param osuFile Osu File
 * @param text Text to display
 * @param startT Start Time
 * @param endT End Time
 * @param x X position
 * @param y Y position
 * @param degrees Degrees of text with respect to X-axis
 * @param rotate Rotate the each character of the text
 */
	public TextDisplay(CoordinateType c , File osuFile, String text, long startT, long endT, int x, int y, double degrees, boolean rotate){
		if (c.equals(CoordinateType.HitObject)){
			x = OsuUtils.hitObjectXToStoryboardX(x);
			y = OsuUtils.hitObjectYToStoryboardY(y);
		}
		double radian = OsuUtils.degreesToRadian(degrees);
		String[] paths = getAllFilePath(text);
		Dimension[] allXY = calculateAllXY(osuFile.getParent(), text, x, y , ImagetoSBSize, radian);
		for (int i = 0; i < text.length(); i++){
			String filePath = paths[i];
			if (!filePath.equals("null")){
				VisualObject o = createCharacter(filePath,Easing.Linear,startT,endT,allXY[i].width,allXY[i].height,startFade,endFade,size);
				if (rotate){
					Command r1 = new Rotate(startT,endT,radian);
					o.add(r1);
				}
			}
		}
	}
	
	public TextDisplay(CoordinateType c , File osuFile, String text, long startT, long endT, int x, int y,double size, double degrees, boolean rotate){
		if (c.equals(CoordinateType.HitObject)){
			x = OsuUtils.hitObjectXToStoryboardX(x);
			y = OsuUtils.hitObjectYToStoryboardY(y);
		}
		double radian = OsuUtils.degreesToRadian(degrees);
		String[] paths = getAllFilePath(text);
		Dimension[] allXY = calculateAllXY(osuFile.getParent(), text, x, y , size, radian);
		for (int i = 0; i < text.length(); i++){
			String filePath = paths[i];
			if (!filePath.equals("null")){
				VisualObject o = createCharacter(filePath,Easing.Linear,startT,endT,allXY[i].width,allXY[i].height,startFade,endFade,size);
				if (rotate){
					Command r1 = new Rotate(startT,endT,radian);
					o.add(r1);
				}
			}
		}
	}
	

	public TextDisplay(CoordinateType c , File osuFile, String text, long startT, long endT, int x, int y,double size, double degrees,int dXY, double dr[], double direction){
		if (c.equals(CoordinateType.HitObject)){
			x = OsuUtils.hitObjectXToStoryboardX(x);
			y = OsuUtils.hitObjectYToStoryboardY(y);
		}
		double radian = OsuUtils.degreesToRadian(degrees);
		String[] paths = getAllFilePath(text);
		int middle = text.length()/2;
		Dimension[] allXY = calculateAllXY(osuFile.getParent(), text, x, y , size, radian);
		for (int i = 0; i < text.length(); i++){
			String filePath = paths[i];
			if (!filePath.equals("null")){
				int startX = allXY[i].width;
				int startY = allXY[i].height;
				VisualObject o = createCharacter(filePath,Easing.Linear,startT,endT,startX,startY,startFade,endFade,size);
				double xdirection = 1;
				if (i>middle){
					xdirection = 0;
				}
				int dx = (int) (dXY * Math.pow(-1, xdirection));
				int dy = (int) (dXY * Math.pow(-1, direction));
				o.add(new Move(startT,endT,startX,startY, startX +dx, startY+dy));	
				addRotation( o, dr[i], startT,endT);

			}
		}
	}
	
	private void addRotation(VisualObject o, double dr, long startT, long endT){
		Rotate r1 = new Rotate(startT,endT,0,dr);
		o.add(r1);

	}
	
	
	private Dimension[] calculateAllXY(String parentFolder, String text, int startX, int startY, double size, double angle){
		int[] widths = getAllImageWidth(parentFolder,text);
		Dimension[] output = new Dimension[widths.length];
		output[0] = new Dimension(startX,startY);
		double x1 = startX;
		double y1 = startY;
		//System.out.println("x1 = " + x1);
		for (int i = 0; i < widths.length - 1; i ++){
			int w1 = widths[i];
			int w2 = widths[i+1];
			
			double p1x = x1 + size * Math.cos(angle)*w1; 
			double p1y = y1 - size * Math.sin(angle)*w1; 	

			double p2x = p1x + space * Math.cos(angle);
			double p2y = p1y - space * Math.sin(angle);
			
			int x2 = (int) (p2x + size*Math.cos(angle)*w2);
			int y2 = (int) (p2y - size*Math.sin(angle)*w2);
			
			x1 = x2;
			y1 = y2;
			output[i+1] = new Dimension(x2,y2);
		}
		return output;
	}
	
	private String[] getAllFilePath(String text){
		String[] output = new String[text.length()];
		for (int i =0; i<text.length();i++){
			char ch = text.charAt(i);
			if (ch == ' '){
				output[i] = "null";
			} else {
				String s = OsuUtils.getCharFileName(ch);
				output[i] =  path + s + ".png";;
			}
		}
		return output;
	}
	
	private int[] getAllImageWidth(String parentFolder, String text){
		int[] output = new int[text.length()];
		for (int i =0; i<text.length();i++){
			char ch = text.charAt(i);
			if (ch == ' '){
				output[i] = spaceWidth;
			} else {
				String s = OsuUtils.getCharFileName(ch);
				String completePath = parentFolder + "\\" + path + s + ".png";
				output[i] = OsuUtils.getImageDim(completePath).width/2;
			}
		}
		
		return output;
	}

	
	/**
	 * 
	 * @param text
	 * @param startT
	 * @param endT
	 * @param x
	 * @param y
	 * @param size
	 * @param degrees
	 * @param radians
	 */
	public TextDisplay(CoordinateType c, String text, long startT, long endT, int x, int y, double size, double degrees, double[] radians){
		if (c.equals(CoordinateType.HitObject)){
			x = OsuUtils.hitObjectXToStoryboardX(x);
			y = OsuUtils.hitObjectYToStoryboardY(y);
		}
		//TODO
	}
	

	
	private VisualObject createCharacter( String filePath, int easing, long startT, long endT, int x, int y, double startFade,double endFade,double size){
		VisualObject o = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x, y);
		long t2 =0,t3=0;
		if (startT + 2*fadeDuration <= endT){
			t2 = startT+fadeDuration;
			t3 = endT-fadeDuration;
		} else {
			t2 = startT + (endT-startT)/2;
			t3= t2;
		}
		Command s1 = new VectorScale(startT,endT,size,size);
		o.add(s1);
		Command f1 = new Fade(Easing.QuintIn ,startT,t2,startFade,1);
		o.add(f1);
		Command f2 = new Fade(t2,t3);
		o.add(f2);
		Command f3 = new Fade(Easing.QuintOut,t3,endT,1,endFade);
		o.add(f3);
		add(o);
		return o;
	}
	
	@Override
	public void setALLXY(int x, int y){
		//create a new object and return that
	}
	
}
