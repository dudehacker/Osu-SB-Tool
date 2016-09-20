package test;


import java.awt.Color;

import effects.Effects;
import effects.customEffects.TextDisplay;
import effects.customEffects.WaterDrop;
import Storyboard.Storyboard;


public class TestMain {
	public static void main(String[] args){
		TestMain m = new TestMain();
		//m.testWaterDrop();
		m.testTextDisplay();
		
		
	}
	
	private void testTextDisplay(){
		String s = "*‚Ð•½‚©";
		Storyboard sb = new  Storyboard(false);
		Effects e1 = new TextDisplay(s,3000, 5000,  100, 100, 2, 90,true);
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
