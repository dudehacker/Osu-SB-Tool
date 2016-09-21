package effects.customEffects;

import Commands.*;
import Objects.*;
import Utils.*;
import effects.*;

/**
 * 
 * @author DH
 *
 */
public class TextDisplay extends Effects{
	private String path = EffectsConstants.textPath;
	private int space = EffectsConstants.space;
	private double startFade = EffectsConstants.fade;
	private double endFade = 0;
	private long fadeDuration = 1000;
	
	// need testing for size
	private double size = 1;
	
	/**
	 * 
	 * @param text Text to display
	 * @param startT Start Time
	 * @param endT End Time
	 * @param x X position of first character
	 * @param y Y position of first character
	 * @param size Size of text
	 * @param degrees the angle between the text and X-axis in degrees
	 */
	public TextDisplay(String text, long startT, long endT, int x, int y, double size, double degrees, boolean rotate){
		int tempX = x, tempY = y;
		double radian = OsuUtils.degreesToRadian(degrees);
		for (int i =0; i<text.length();i++){
			char ch = text.charAt(i);
			// create the Visual Object for this character if it is not a space
			if (ch != ' '){
				String s = ""+ch;
				// use Unicode for the font name if the char is not supported in windows or Japanese character
				if (ExceptionCharacters.contains(ch) || OsuUtils.isCharacterJapanese(ch) || Character.isUpperCase(ch)){
					s = OsuUtils.characterToUnicode(ch);
				} 
				String filePath = path + s + ".png";
				VisualObject o = createCharacter(filePath,Easing.Linear,startT,endT,tempX,tempY,startFade,endFade,size);
				if (rotate){
					Command r1 = new Rotate(startT,endT,radian);
					o.add(r1);
				}
			}
			tempX += (int) (space * Math.cos(radian));
			tempY += (int) (space * Math.sin(radian));
		}
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
	public TextDisplay(String text, long startT, long endT, int x, int y, double size, double degrees, double[] radians){
		int tempX = x, tempY = y;
		double radian = OsuUtils.degreesToRadian(degrees);
		for (int i =0; i<text.length();i++){
			char ch = text.charAt(i);
			// create the Visual Object for this character if it is not a space
			if (ch != ' '){
				String s = ""+ch;
				// use Unicode for the font name if the char is not supported in windows or Japanese character
				if (ExceptionCharacters.contains(ch) || OsuUtils.isCharacterJapanese(ch) || Character.isUpperCase(ch)){
					s = OsuUtils.characterToUnicode(ch);
				} 
				String filePath = path + s + ".png";
				VisualObject o = createCharacter(filePath,Easing.Linear,startT,endT,tempX,tempY,startFade,endFade, size);
				if (radians.length == text.length()){
					Command r1 = new Rotate(startT,endT,radians[i]);
					o.add(r1);
				}
				tempX += (int) (space * Math.cos(radian));
				tempY += (int) (space * Math.sin(radian));
			}
		}
	}
	

	
	private VisualObject createCharacter(String filePath, int easing, long startT, long endT, int x, int y, double startFade,double endFade,double size){
		VisualObject o = new Sprite(Layer.Foreground, filePath, x, y);
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
