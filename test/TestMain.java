package test;


import java.awt.Color;
import java.io.File;

import effects.*;
import effects.customEffects.*;
import Commands.*;
import Objects.*;
import Storyboard.Storyboard;
import Utils.Easing;
import Utils.OsuUtils;


public class TestMain {

	private File osufile;
	private String defaultPath = "C:\\Program Files (x86)\\osu!\\Songs\\514038 u's & Aqours - Love Live! School Idol Compilation";
	
	
	public TestMain(){
		osufile = OsuUtils.getOsuFile(defaultPath);
	}
	
	public static void main(String[] args){
		String[] waifus = {
				// 0 - 8
				"Ruby",
				"Mari",
				"Hanamaru",
				"Yoshiko",
				"Dia",
				"You",
				"Kanan",
				"Chika",
				"Riko",
				// 9-17
				"Maki",
				"Umi",
				"Eli",
				"Kotori",
				"Nico",
				"Rin",
				"Nozomi",
				"Honoka",
				"Hanayo",
				//18
				"all",
				//19
				"no skill",
				
		};
		TestMain m = new TestMain();
		for (int i = 0; i < waifus.length; i++){
			String path = OsuUtils.startPath + "\\" +  waifus[i] + "\\";
			if (i<9){
				m.createSB(path,waifus[i], 1,true);
			} else if (i<18){
				m.createSB(path,waifus[i], 0,true);
			} else if (i == 18){
				m.createSB(path,null, 1,true);
			} else {
				m.createSB(path,null, 1,false);
			}
			
		}
		
	}
	
	private void createSB(String path, String waifu, int groupID, boolean popup){

		Storyboard sb = new  Storyboard(false, osufile);
		//m.testWaterDrop();
		String BG = "Love.Live!.jpg";
		sb.disableBG((new Sprite(CoordinateType.Storyboard, Layer.Background, BG, 320, 240)));
		testBGs(sb);
		if (popup){
			Effects charPopup = new CharacterPopUp(sb.getOsuFile(),waifu,groupID);
			sb.add(charPopup);
		}
		testTextDisplay(sb);
		sb.exportSB(path);
	}
	
	private void testBGs(Storyboard sb){
		Effects e1 = new MultipleBG(sb.getOsuFile());
		sb.add(e1);
	}
	
	private void testTextDisplay(Storyboard sb){
		String s = "Storyboard by Dudehacker";
		long t1 = 1041137; //17:21:137
		long t2 = 1046470; //17:26:470
		long t3 = 1047137; //17:27:137
		Effects e1 = new TextDisplay(CoordinateType.HitObject, sb.getOsuFile(),s,t1, t2,  250, 350, 0,false);
		e1.addAll(new ColorCMD(t1, t2,Color.black));
		e1.removeAll(CommandName.Fade);
		e1.addAll(new Fade(Easing.QuintOut,t1, t2,0,1));
		e1.addAll(new Fade(Easing.CubicIn,t2, t3,1,0));
		sb.add(e1);
	}
	
	private void testWaterDrop(Storyboard sb){
		Effects e1 = new WaterDrop(CoordinateType.HitObject,3132,3677,Color.PINK, 4);
		e1.setALLXY(124, 96);
		sb.add(e1);
	}
	
}
