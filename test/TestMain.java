package test;


import java.awt.Color;

import effects.*;
import effects.customEffects.*;
import Commands.*;
import Objects.*;
import Storyboard.Storyboard;
import Utils.OsuUtils;


public class TestMain {
	public static void main(String[] args){
		TestMain m = new TestMain();
		Storyboard sb = new  Storyboard(false);
		//m.testWaterDrop();

		m.testBGs(sb);
		m.testCharPopup(sb);
		//m.testTextDisplay(sb);
		sb.exportSB();
	}
	
	
	private void testCharPopup(Storyboard sb){
		Effects charPopup = new CharacterPopUp(sb.getOsuFile());
		sb.add(charPopup);
		
	}
	
	private void testBGs(Storyboard sb){
		Effects e1 = new MultipleBG(sb.getOsuFile());
		sb.add(e1);
	}
	
	private void testTextDisplay(Storyboard sb){
		String s = "1. ‘Ò‚Á‚Ä‚Äˆ¤‚Ì‚¤‚½ - Aqours";
		
		/*for (int i = 0; i<s.length();i++){
			char ch = s.charAt(i);
			System.out.println(""+ch+ "  =  " +OsuUtils.characterToUnicode(ch));
		}*/
		Effects e1 = new TextDisplay(CoordinateType.HitObject, sb.getOsuFile(),s,3000, 10000,  0, 350, 45,false);
		sb.add(e1);
	}
	
	private void testWaterDrop(Storyboard sb){
		Effects e1 = new WaterDrop(CoordinateType.HitObject,3132,3677,Color.PINK, 4);
		e1.setALLXY(124, 96);
		sb.add(e1);
	}
	
}
