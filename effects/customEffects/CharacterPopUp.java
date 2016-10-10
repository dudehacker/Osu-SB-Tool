package effects.customEffects;

import java.awt.Color;
import java.io.File;
import java.util.*;

import Commands.*;
import Objects.*;
import Utils.*;
import effects.*;

public class CharacterPopUp extends Effects{
	private String waifu = null; private int groupID = 0;
	private final long duration = 2200;
	private long stayTime = 1200;
	private long fadeIn = 500;
	private File osuFile;
	private int volume = 100;
	private int[] group = 
			// u's = 0
			// Aqours = 1
		{
			//1. Mattete Ainouta - Aqours -> 01
				1,
			//2. Kaguya no Shiro de Odoritai - u's -> 02
				0,
			//3. Humming Friend - Aqours -> 03
				1,
			//4. Koi ni Naritai AQUARIUM - Aqours -> 01
				1,
			//5. Paradise Live - u's -> 4
				0,
			//6. Yume Kataruyori Yume Utaou - Aqours ->5
				1,
			//7. Yume no Tobira - u's ->6
				0,
			//8. Kitto Seishun ga Kikoeru - u's ->2
				0,
			//9. Music S.T.A.R.T - u's ->7
				0,
			//10. Snow Halation - u's ->8
				0,
			//11. Takaramonozu - u's ->4
				0,
			//12. Sore wa Bokutachi no Kiseki - u's ->9
				0,
			//13. Kimi no Kokoro wa Kagayaiteru Kai? - Aqours ->10
				1,
			//14. COLORFUL VOICE - u's ->11
				0,
			//15. Todokanai Hoshida to Shite mo - Aqours ->1
				1,
			//16. Kira-Kira Sensation - u's ->12
				0,
			//17. Bokura wa Ima no Naka de - u's ->13
				0,
			//18. No Brand Girls - u's ->14
				0,
			//19. Aozora Jumping Heart - Aqours ->3
				1,
			//20. Bokura no LIVE Kimi to no LIFE - u's ->15
				0,
			//21. Aqours*HEROES - Aqours ->10
				1,
			//22. MOMENT RING - u's ->16
				0,
			//23. Sayounara e Sayonara! - u's ->16
				0,
			//24. Aishiteru Banzai - u's ->17
				0,
		};
	
	public CharacterPopUp(File osuFile){
		this.osuFile = osuFile;
		try {
			ArrayList<Timing> timings = OsuUtils.getKiaiStartTiming(osuFile);
			ArrayList<Timing> songs = OsuUtils.getRedTimingPoints(osuFile);
			int i =0;
			for (Timing timing : timings){
				long t = timing.getOffset();
				i = OsuUtils.getSongIndex(songs, t);
				String charPath = charSpriteRNG(group[i],osuFile);
				String voicePath = charPath.replace(".png", ".wav");
				add(new Sample(t,voicePath,volume));
				Color color = null;
				double maxFade = 1;
				if (charPath.contains("Maki")){
					color = new Color(205,71,65);
					maxFade = 1;
				} else if (charPath.contains("Umi")){
					color = new Color(82,85,142);
				}
				else if (charPath.contains("Eli")){
					color = new Color(255,221,140);
				}
				else if (charPath.contains("Nozomi")){
					color = new Color(86,68,162);
				}else if (charPath.contains("Rin")){
					color = new Color(248,136,79);
				}
				else if (charPath.contains("Honoka")){
					color = new Color(255,122,34);
				}
				else if (charPath.contains("Kotori")){
					color = new Color(186,170,136);
				}else if (charPath.contains("Nico")){
					color = new Color(255,145,183);
				}
				else if (charPath.contains("Hanayo")){
					color = new Color(47,187,122);
				}
				// aqours
				else if (charPath.contains("Ruby")){
					color = AqoursColors.Ruby();
				}
				else if (charPath.contains("Mari")){
					color = AqoursColors.Mari();
				}
				else if (charPath.contains("Hanamaru")){
					color = AqoursColors.Hanamaru();
				}
				else if (charPath.contains("Yoshiko")){
					color = AqoursColors.Yoshiko();
				}
				else if (charPath.contains("Dia")){
					color = AqoursColors.Dia();
				}
				else if (charPath.contains("You")){
					color = AqoursColors.You();
				}
				else if (charPath.contains("Kanan")){
					color = AqoursColors.Kanan();
				}
				else if (charPath.contains("Chika")){
					color = AqoursColors.Chika();
				}
				else if (charPath.contains("Riko")){
					color = AqoursColors.Riko();
				}

				addFlash(t,color,maxFade);
				addCharacter(t,charPath);
				addHearts(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String charSpriteRNG(int groupID, File osuFile){
		String output = "";
		String folder = EffectsConstants.charPopupPath;
		if (waifu==null){
			File spriteFolder = new File(osuFile.getParent()+"\\"+folder+groupID + "\\");
			File[] files = spriteFolder.listFiles();
			ArrayList<File> sprites = new ArrayList<>();
			for (File f : files){
				if (f.isFile() && f.getName().contains(".png")){
					sprites.add(f);
				}
			}
			Random rng = new Random();
			int index = rng.nextInt(sprites.size());
			output = folder + groupID + "\\" + sprites.get(index).getName();
		} else {
			Random rng = new Random();
			int index = 1;
			if (this.groupID == 1){
				index = rng.nextInt(2);
			} else if (this.groupID == 0){
				index = rng.nextInt(4);
			}
			output = folder + this.groupID + "\\" + waifu + (index+1)+ ".png";
		}
		
		return output;
	}

	private void addFlash(long startT,Color color, double maxFade){
		long flashDuration = duration-500;
		String flashPath = EffectsConstants.charPopupPath + "flash.png";
		FlashBang flash = new FlashBang(osuFile,flashPath,startT,flashDuration,maxFade);
		if (color != null)
		flash.add(new ColorCMD(startT,startT+flashDuration,color));
		add(flash);
	}
	
	private void addCharacter(long startT, String charPath){
		long t1 = startT-fadeIn;
		double shadowTransparancy = 0.7;
		String fullPath = osuFile.getParent() +"\\"+ charPath;
		double size = OsuUtils.getImageToSBSize(fullPath)*0.7;
		double shadowSize = 1.5*size;
		Random rng = new Random();
		int direction = rng.nextInt(2); // 0 = to right, 1 = to left
		int startX = -300;
		int dx = 100;
		int endX = 1000;
		int x = EffectsConstants.x;
		int y = EffectsConstants.y+50;
		Sprite charSprite = new Sprite(CoordinateType.Storyboard, Layer.Background, charPath, 0, y);
		charSprite.add(new VectorScale(startT,startT+duration,size,size));
		charSprite.add(new Move(t1,startT,startX,y,x-dx,y));
		charSprite.add(new Move(startT,startT+stayTime,x-dx,y,x+dx,y));
		charSprite.add(new Move(startT+stayTime,startT+duration,x+dx,y,endX,y));
		if(direction == 1){
			charSprite.reflectMovementsToYAxis();
		} 
		add(charSprite);
		Sprite shadow = new Sprite(CoordinateType.Storyboard, Layer.Background, charPath, 0, y);
		shadow.add(new VectorScale(startT,startT+duration,shadowSize,shadowSize));
		shadow.add(new Fade(startT,startT+duration,shadowTransparancy,shadowTransparancy));
		shadow.mirrorAllMovementsFrom(charSprite);
		shadow.reflectMovementsToYAxis();
		add(shadow);
	}
	
	private void addHearts(long startT){
		String folder = EffectsConstants.charPopupPath;
		double maxFade = 1;
		int d1 = 150;
		int d2 = 100;
		int d3 = 500;
		double size = 0.4;
		int[] startX = {-200,-50,100,200,300,400,600,700}; 
		for (int i=0;i<startX.length;i++){
			Random rng = new Random();
			int startY = EffectsConstants.y*2 -100 + rng.nextInt(241);
			long t1 = startT-fadeIn;
			Sprite heart = new Sprite(CoordinateType.Storyboard, Layer.Background, folder+"heart_" + (i%4)+".png", 320, startY);
			Fade f_in = new Fade(Easing.QuadOut,t1,startT,0,maxFade);
			VectorScale scale = new VectorScale(t1,t1+duration,size,size);
			heart.add(f_in);
			heart.add(scale);
			heart.add(new Move(t1,t1+duration,startX[i]-1,startY,startX[i],startY));
			heart.add(new Move(t1,startT,startX[i],startY,startX[i],startY-d1));
			heart.add(new Move(startT,startT+stayTime,startX[i],startY-d1,startX[i],startY-d1-d2));
			heart.add(new Move(startT+stayTime,startT+duration-fadeIn,startX[i],startY-d1-d2,startX[i],startY-d1-d2-d3));
	
			add(heart);
		}
		
	}
	
}
