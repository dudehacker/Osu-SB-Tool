package effects.customEffects;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import Commands.ColorCMD;
import Commands.Command;
import Commands.CommandName;
import Commands.Fade;
import Commands.MoveTypes;
import Commands.VectorScale;
import Objects.*;
import Utils.*;
import effects.Effects;
import effects.EffectsConstants;



public class MultipleBG extends Effects{
	private final long fadeDuration = 3000;
	private final int x = EffectsConstants.x;
	private final int y = EffectsConstants.y;
	private final double startFade = 0;
	private final double endFade = 0;
	private final String BGFolder = "SB\\BG\\";
	private final String coverFolder ="SB\\Covers\\";
	private final String BPMFolder = "SB\\UI\\";
	private final long songDuration = 1050813; //17:30:813
	//1=blue, 2=green, 3=red
	private final int[] songTypes  = {2,1,1,1,1,2,2,2,
							    3,1,3,3,2,3,3,2,
							    3,1,3,3,3,3,2,3}; 
	private final int[] covers = {1,2,3,1,4,5,6,2,7,8,4,9,10,11,1,12,13,14,3,15,10,16,16,17};
	private final double coverSize = 0.5;
	private final long coverDuration = 1000;
	private final int TitleBoxX = 600;
	private final int TitleBoxY = 400;
	private final int BPMboxX = TitleBoxX- 70;
	private final int BPMboxY = TitleBoxY - 60;
	private final int bpmX = TitleBoxX-120;
	private final int bpmNumberX = BPMboxX +25;
	private final int bpmTextX = bpmNumberX - 70;
	private File osuFile ;
	private double bpmSize = 0.2;
	private final int distance = 100;
	public MultipleBG(File osuFile){
		this.osuFile = osuFile;
		try {
			ArrayList<Timing> timings = OsuUtils.getRedTimingPoints(osuFile);
			double size = 480.0/768.0;
			String blackBGPath = EffectsConstants.sbPath + "black.jpg";
			VisualObject blackBG = new Sprite(CoordinateType.Storyboard, Layer.Background,blackBGPath,x, y);
			blackBG.add(new VectorScale(0,  songDuration,2,2));
			add(blackBG);
			for (int i = 0; i< timings.size();i++){
				Timing t = timings.get(i);
				long startTime = t.getOffset();
				long endTime = 0;
				if (i+1<timings.size()){
					endTime = timings.get(i+1).getOffset();
				} else {
					endTime = songDuration;
				}
				String filePath = BGFolder + (i+1) + ".jpg";
				double bgSize = size*1.2;
				createBG(filePath, startTime, endTime,x,y,startFade,endFade,bgSize);
				String coverPath = coverFolder + (covers[i]) + ".jpg";
				loadCover(coverPath,startTime,x,y,startFade,endFade,coverSize);
				String bpmBGPath = BPMFolder + "bpm.png";
				CreateBPMDisplay(bpmBGPath,startTime, endTime,TitleBoxX,TitleBoxY,startFade,endFade,size);	
				String bpmPath = BPMFolder + "bpm_"+songTypes[i] + ".png";
				CreateBPMDisplay(bpmPath,startTime+1, endTime,bpmX,TitleBoxY,startFade,endFade,0.7);
				String titlePath = BPMFolder + "title_"+songTypes[i] + ".png";
				CreateBPMDisplay(titlePath,startTime+1, endTime,BPMboxX,BPMboxY,startFade,endFade,0.7);
				String text = "" + t.getBPM() ;
				Color color = Color.WHITE;
				/*
				switch(songTypes[i]){
				case 1:
					color = new Color(34,153,255);
					break;
				case 2:
					color = new Color(0,238,51);
					break;
				case 3:
					color = new Color(255,51,102);
					break;
				}*/
				CreateBPM(color,text,startTime,endTime,bpmNumberX,BPMboxY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void CreateBPM(Color c,String text, long startT, long endT, int x, int y){
		TextDisplay e= new TextDisplay(CoordinateType.Storyboard, osuFile,text,startT, endT, x, y,bpmSize , 0,false);
		e.removeAll(CommandName.Fade);
		long t1=0,t2 =0,t3=0,t4=0;
		if (startT + 2*fadeDuration <= endT){
			t1 = startT;
			t2 = startT+fadeDuration/2;
			t3 = endT-fadeDuration;
			t4 = endT;
		} else {
			t2 = startT + (endT-startT)/2;
			t3= t2;
		}
		Command f1 = new Fade(Easing.QuintIn ,t1,t2,startFade,1);
		Command f2 = new Fade(t2,t3);
		Command f3 = new Fade(Easing.QuintOut,t3,t4,1,endFade);
		e.addAll(f1);
		e.addAll(f2);
		e.addAll(f3);
		ColorCMD color = new ColorCMD(startT,endT,c);
		e.addAll(color);
		add(e);
		String bpm = "BPM" ;
		TextDisplay bpmTxt = new TextDisplay(CoordinateType.Storyboard,osuFile,bpm,startT,endT,bpmTextX,BPMboxY,bpmSize*0.8,0,false);
		bpmTxt.removeAll(CommandName.Fade);
		bpmTxt.addAll(f1);
		bpmTxt.addAll(f2);
		bpmTxt.addAll(f3);
		bpmTxt.addAll(new ColorCMD(startT,endT,Color.white));
		add(bpmTxt);
	}
	
	private void CreateBPMDisplay(String filePath, long startT, long endT, int x, int y, double startFade,double endFade,double size){
		VisualObject bg = new Sprite(CoordinateType.Storyboard, Layer.Foreground, filePath, x, y);
		long t1=0,t2 =0,t3=0,t4=0;
		if (startT + 2*fadeDuration <= endT){
			t1 = startT;
			t2 = startT+fadeDuration/2;
			t3 = endT-fadeDuration;
			t4 = endT;
		} else {
			t2 = startT + (endT-startT)/2;
			t3= t2;
		}
		Command s1 = new VectorScale(startT,endT,size,size);
		bg.add(s1);
		Command f1 = new Fade(Easing.QuintIn ,t1,t2,startFade,1);
		bg.add(f1);
		Command f2 = new Fade(t2,t3);
		bg.add(f2);
		Command f3 = new Fade(Easing.QuintOut,t3,t4,1,endFade);
		bg.add(f3);
		add(bg);
	}
	
	
	private void loadCover(String filePath, long startT, int x, int y, double startFade,double endFade,double size){
		VisualObject o = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x, y);
		Command s1 = new VectorScale(startT-coverDuration,startT+3*coverDuration,size,size);
		o.add(s1);
		Command f1 = new Fade(Easing.QuintIn ,startT-coverDuration,startT,startFade,1);
		o.add(f1);
		Command f2 = new Fade(Easing.QuintOut ,startT,startT+3*coverDuration,1,endFade);
		o.add(f2);
		add(o);
		// effect object
		VisualObject o1 = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x, y);
		Command s2 = new VectorScale(startT,startT+400,size,size*4);
		o1.add(s2);
		Command f = new Fade(Easing.QuintOut ,startT,startT+400,1,0);
		o1.add(f);
		add(o1);
	}
	

	
	
	private VisualObject createBG( String filePath, long startT, long endT, int x, int y, double startFade,double endFade,double size){
		VisualObject o = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x, y);
		long t1=0,t2 =0,t3=0,t4=0;
		if (startT + 2*fadeDuration <= endT){
			t1 = startT-fadeDuration/2;
			t2 = startT+fadeDuration/2;
			t3 = endT-fadeDuration;
			t4 = endT;
		} else {
			t2 = startT + (endT-startT)/2;
			t3= t2;
		}
		Command s1 = new VectorScale(startT,endT,size,size);
		o.add(s1);
		Command f1 = new Fade(Easing.QuartInOut ,t1,t2,startFade,1);
		o.add(f1);
		Command f2 = new Fade(t2,t3);
		o.add(f2);
		Command f3 = new Fade(Easing.QuintOut,t3,t4,1,endFade);
		o.add(f3);
		o.addMove(MoveTypes.Upward, startT, endT, distance);
		add(o);
		return o;
	}
	
	
}
