package effects.customEffects;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import Commands.ColorCMD;
import Commands.Command;
import Commands.CommandName;
import Commands.Fade;
import Commands.SpriteMove;
import Commands.CameraMove;
import Commands.VectorScale;
import Objects.*;
import Utils.*;
import effects.Effects;
import effects.EffectsConstants;
import effects.SongTitles;



public class MultipleBG extends Effects{
	private final long fadeIn = 1500;
	private final long fadeOut = 3000;
	private final int x = EffectsConstants.x;
	private final int y = EffectsConstants.y;
	private final double startFade = 0;
	private final double endFade = 0;

	private final String BGFolder = "SB\\BG\\";
	private final String coverFolder ="SB\\Covers\\";
	private final String BPMFolder = "SB\\UI\\";
	//1=blue, 2=green, 3=red
	private final int[] songTypes  = {2,1,1,1,1,2,2,2,
							    3,1,3,3,2,3,3,2,
							    3,1,3,3,3,3,2,3}; 
	private final int[] covers = {1,2,3,1,4,5,6,2,7,8,4,9,10,11,1,12,13,14,3,15,10,16,16,17};
	private final int[] move = {
			// 1. Mattete Ainouta - Aqours -> 01
			CameraMove.Upward,
			// 2. Kaguya no Shiro de Odoritai - u's -> 02
			CameraMove.toTopRight,
			// 3. Humming Friend - Aqours -> 03
			CameraMove.toTopRight,
			// 4. Koi ni Naritai AQUARIUM - Aqours -> 01
			CameraMove.toRight,
			// 5. Paradise Live - u's -> 4
			CameraMove.toBottomLeft,
			// 6. Yume Kataruyori Yume Utaou - Aqours ->5
			CameraMove.v_r,
			// 7. Yume no Tobira - u's ->6
			CameraMove.v_r,
			// 8. Kitto Seishun ga Kikoeru - u's ->2
			CameraMove.toTopRight,
			// 9. Music S.T.A.R.T - u's ->7
			CameraMove.toTopLeft,
			// 10. Snow Halation - u's ->8
			CameraMove.v_r,
			// 11. Takaramonozu - u's ->4
			CameraMove.v_r,
			// 12. Sore wa Bokutachi no Kiseki - u's ->9
			CameraMove.v_l,
			// 13. Kimi no Kokoro wa Kagayaiteru Kai? - Aqours ->10
			CameraMove.toTopRight,
			// 14. COLORFUL VOICE - u's ->11
			CameraMove.toTopLeft,
			// 15. Todokanai Hoshida to Shite mo - Aqours ->1
			CameraMove.toTopLeft,
			// 16. Kira-Kira Sensation - u's ->12
			CameraMove.toBottomRight,
			// 17. Bokura wa Ima no Naka de - u's ->13
			CameraMove.v_r,
			// 18. No Brand Girls - u's ->14
			CameraMove.v_reversed_r,
			// 19. Aozora Jumping Heart - Aqours ->3
			CameraMove.toTopRight,
			// 20. Bokura no LIVE Kimi to no LIFE - u's ->15
			CameraMove.toRight,
			// 21. Aqours*HEROES - Aqours ->10
			CameraMove.toBottomLeft,
			// 22. MOMENT RING - u's ->16
			CameraMove.toLeft,
			// 23. Sayounara e Sayonara! - u's ->16
			CameraMove.toRight,
			// 24. Aishiteru Banzai - u's ->17
			CameraMove.toBottomRight
	};
	private final double coverSize = 0.5;
	private final int TitleBoxX = 600;
	private final int TitleBoxY = 400;
	private final int titleDY = 75;
	private final int BPMboxX = TitleBoxX- 90;
	private final int BPMboxY = TitleBoxY - 65;
	private final int bpmX = TitleBoxX-120;
	private final int bpmNumberX = BPMboxX +25;
	private final int bpmTextX = bpmNumberX - 70;
	private File osuFile ;
	private double bpmSize = 0.2;
	private final double speed = 100.0/47567.0;
	
	public MultipleBG(File osuFile){
		this.osuFile = osuFile;
		try {
			ArrayList<Timing> startTimings = OsuUtils.getRedTimingPoints(osuFile);
			ArrayList<Timing> kiaiTimings = OsuUtils.getFirstKiaiOfEachRedTiming(osuFile);
			long[] endTimings = OsuUtils.getLastNoteTimingOfEachSection(osuFile);
			String blackBGPath = EffectsConstants.sbPath + "black.jpg";
			VisualObject blackBG = new Sprite(CoordinateType.Storyboard, Layer.Background,blackBGPath,x, y);
			blackBG.add(new VectorScale(0, endTimings[endTimings.length-1],2,2));
			add(blackBG);
			for (int i = 0; i< startTimings.size();i++){
				Timing t = startTimings.get(i);
				long startTime = t.getOffset();
				long kiaiStart = kiaiTimings.get(i).getOffset();
				long endTime = endTimings[i];
				String filePath = BGFolder + (i+1) + ".jpg";
				String fullBGPath = osuFile.getParent() + "\\" + filePath;
				double size = OsuUtils.getImageToSBSize(fullBGPath);
				createBG(filePath, startTime, endTime,x,y,startFade,endFade,size,move[i]);
				String coverPath = coverFolder + (covers[i]) + ".jpg";
				loadCover(coverPath,startTime,x,y,startFade,endFade,coverSize);
				String bpmBGPath = BPMFolder + "bpm.png";
				String shootingStarPath = EffectsConstants.sbPath + "shooting star.png";
				addFlashBang(kiaiStart);
				createShootingStars(shootingStarPath, kiaiStart, endTime-kiaiStart, TitleBoxY, t.getBPM());
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
				addText(color,text,startTime,endTime,bpmNumberX,BPMboxY);
				String en = SongTitles.en[i];
				addText(color,en,startTime,endTime,bpmNumberX,TitleBoxY+titleDY);
				String jp = SongTitles.jp[i];
				addText(color,jp,startTime,endTime,bpmNumberX,TitleBoxY-titleDY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addFlashBang(long startT){
		String filename = EffectsConstants.charPopupPath + "flash2.jpg";
		long duration = 200;
		FlashBang flash = new FlashBang(osuFile, filename,startT-1,duration);
		add(flash);
	}
	
	private void createShootingStars(String filePath, long startT, long duration, int y, int bpm){
		double bulletSpeed = 400+bpm;
		int n = 50;
		double size = 0.4;
		double transparency = 1;
		int dy = 35;
		int distance = 800;
		int dt = (int) (distance*1000.0/bulletSpeed);
		for (int i = 0; i < n; i++){
			Random rng = new Random();
			int newY = y+ rng.nextInt(2*dy)-dy;
			long bulletDelay = rng.nextInt(dt);
			VisualObject bullet = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x,newY);
			MachineGun mg = new MachineGun(Easing.SineOut,startT+bulletDelay, bullet,size,transparency , bulletSpeed, SpriteMove.toLeft,duration);
			add(mg);
			//System.out.println(mg.toString());
		}
		
	}
	

	
	private void addText(Color c,String text, long startT, long endT, int x, int y){
		TextDisplay e= new TextDisplay(CoordinateType.Storyboard, osuFile,text,startT, endT, x, y,bpmSize , 0,false);
		e.removeAll(CommandName.Fade);
		long t1 = startT-fadeIn;
		long t2 = startT;
		long t3 = endT;
		long t4 = endT+fadeOut;
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
		VisualObject bg = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x, y);
		long t1 = startT-fadeIn;
		long t2 = startT;
		long t3 = endT;
		long t4 = endT+fadeOut;
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
	
	
	private void loadCover(String filePath, long endT, int x, int y, double startFade,double endFade,double size){
		VisualObject o = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x, y);
		long coverDuration = 1500;
		long delay = 600;
		long t1 = endT-coverDuration-fadeIn + delay;
		long t3 = endT - fadeIn + delay;
		long t2 = t3-500;
		if (t1<0){
			t1 = 0;
		}
		Command s1 = new VectorScale(t1,t3,size,size);
		o.add(s1);
		Command f1 = new Fade(Easing.QuintIn ,t1,t3-delay,startFade,1);
		o.add(f1);
		Command f2 = new Fade(Easing.Linear ,t3-delay,t3,1,0);
		o.add(f2);
		add(o);
		// effect object
		VisualObject o1 = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x, y);
		Command s2 = new VectorScale(t2,t3,size,size*4);
		o1.add(s2);
		Command f = new Fade(Easing.QuintOut ,t2,t3,1,0);
		o1.add(f);
		add(o1);
		//System.out.println(o);
		//System.out.println(o1);
	}
	

	
	
	private VisualObject createBG( String filePath, long startT, long endT, int x, int y, double startFade,double endFade,double size, int move){
		VisualObject o = new Sprite(CoordinateType.Storyboard, Layer.Background, filePath, x, y);
		int distance = (int) ((endT-startT) * speed);
		long t1 = startT-fadeIn;
		long t2 = startT;
		long t3 = endT;
		long t4 = endT+fadeOut;
		Command f1 = new Fade(Easing.QuartInOut ,t1,t2,startFade,1);
		o.add(f1);
		Command f2 = new Fade(t2,t3);
		o.add(f2);
		Command f3 = new Fade(Easing.QuintOut,t3,t4,1,endFade);
		o.add(f3);
		Command s = new VectorScale(startT,endT,size,size);
		o.add(s);
		o.addMove(move, startT, endT, distance,true);
		add(o);
		return o;
	}
	
	
}
