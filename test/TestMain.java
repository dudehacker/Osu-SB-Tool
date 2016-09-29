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
		
		//m.testWaterDrop();
		//m.testTextDisplay();
		m.testBGs();
		
	}
	
	private void testBGs(){
		Storyboard sb = new  Storyboard(false);
		Effects e1 = new MultipleBG(sb.getOsuFile());
		sb.add(e1);
		System.out.println(sb.toString());
		sb.exportSB();
	}
	
	private void testTextDisplay(){
		String s = "1. ‘Ò‚Á‚Ä‚Äˆ¤‚Ì‚¤‚½ - Aqours";
		
		/*for (int i = 0; i<s.length();i++){
			char ch = s.charAt(i);
			System.out.println(""+ch+ "  =  " +OsuUtils.characterToUnicode(ch));
		}*/
		Storyboard sb = new  Storyboard(false);
		Effects e1 = new TextDisplay(CoordinateType.HitObject, sb.getOsuFile(),s,3000, 10000,  0, 350, 45,false);
		sb.add(e1);
		System.out.println(sb.toString());
		sb.exportSB();
	}
	
	private void testWaterDrop(){
		Storyboard sb = new  Storyboard(false);
		Effects e1 = new WaterDrop(CoordinateType.HitObject,3132,3677,Color.PINK, 4);
		e1.setALLXY(124, 96);
		sb.add(e1);
		System.out.println(sb.toString());
	}
	
}
