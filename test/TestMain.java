package test;


import java.awt.Color;

import effects.Effects;
import effects.customEffects.TextDisplay;
import effects.customEffects.WaterDrop;
import Storyboard.Storyboard;
import Utils.OsuUtils;


public class TestMain {
	public static void main(String[] args){
		TestMain m = new TestMain();
		//m.testWaterDrop();
		m.testTextDisplay();
		
		
	}
	
	private void testTextDisplay(){
		String s = "1. Mattete Ainouta - Aqours";
		/*
		for (int i = 0; i<s.length();i++){
			char ch = s.charAt(i);
			System.out.println(""+ch+ "  =  " +OsuUtils.characterToUnicode(ch));
		}*/
		Storyboard sb = new  Storyboard(false);
		Effects e1 = new TextDisplay(s,3000, 10000,  0, 150, 0.2 , 0,true);
		sb.add(e1);
		System.out.println(sb.toString());
	}
	
	private void testWaterDrop(){
		Storyboard sb = new  Storyboard(false);
		Effects e1 = new WaterDrop(3132,3677,Color.PINK, 4);
		e1.setALLXY(124, 96);
		sb.add(e1);
		System.out.println(sb.toString());
	}
	
}
