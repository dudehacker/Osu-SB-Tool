package effects.customEffects;

import java.awt.Color;

import Commands.*;
import Objects.*;
import Utils.Easing;
import Utils.OsuUtils;
import effects.*;

public class WaterDrop extends Effects{
	private int x = EffectsConstants.x;
	private int y = EffectsConstants.y;
	private int easing = Easing.Linear;
	private double startFade = 1;
	private double endFade = 0;
	private double startSize = 0.2;
	private double endSize = 1;
	private long timeDelay = 200;
	
	
	/**
	 * 
	 * @param startT Start Time
	 * @param endT End Time
	 */
	public WaterDrop(CoordinateType coord,long startT, long endT){
		super();
		createEffect(coord,easing,startT,endT,startSize,endSize,startFade,endFade);
	}
	
/**
 * 
 * @param startT Start time
 * @param endT End Time
 * @param c Color
 */
	public WaterDrop(CoordinateType coord,long startT, long endT, Color c){
		super();
		VisualObject object = createEffect(coord,easing,startT,endT,startSize,endSize,startFade,endFade);
		Command c1 = new ColorCMD(startT,endT,c);
		object.add(c1);
	}
	
	/**
	 * 
	 * @param startT Start time
	 * @param endT End Time
	 * @param c Color
	 * @param n number of rings
	 */
	public WaterDrop(CoordinateType coord,long startT, long endT, Color c, int n){
		super();
		for (int i = 0; i< n ; i++){
			VisualObject object = createEffect(coord,easing,startT+timeDelay*i,endT+timeDelay*i,x,y,startSize,endSize,startFade,endFade);
			Command c1 = new ColorCMD(startT,endT,c);
			object.add(c1);
		}
		
	}
	
	/**
	 * 
	 * @param startT Start Time
	 * @param endT End Time
	 * @param c Color
	 * @param n Number of rings
	 * @param x X position for center of all rings
	 * @param y Y position for center of all rings
	 */
	public WaterDrop(CoordinateType coord,long startT, long endT, Color c, int n, int x, int y){
		super();
		for (int i = 0; i< n ; i++){
			VisualObject object = createEffect(coord,easing,startT+timeDelay*i,endT+timeDelay*i,x,y,startSize,endSize,startFade,endFade);
			Command c1 = new ColorCMD(startT,endT,c);
			object.add(c1);
		}
		
	}
	
	/**
	 * 
	 * @param startT Start Time
	 * @param endT End Time
	 * @param colors Array of colors , 1 color per ring
	 * @param n Number of rings
	 */
	public WaterDrop(CoordinateType coord,long startT, long endT, Color[] colors, int n){
		super();
		if ( colors.length == n){
			for (Color c : colors){
				for (int i = 0; i< n ; i++){
					VisualObject object = createEffect(coord,easing,startT+timeDelay*i,endT+timeDelay*i,startSize,endSize,startFade,endFade);
					Command c1 = new ColorCMD(startT,endT,c);
					object.add(c1);
				}
			}
			
		}
		
		
	}
	
	/**
	 * 
	 * @param startT Start Time
	 * @param endT End Time
	 * @param c color
	 * @param startSize Start Size
	 * @param endSize End Size
	 */
	public WaterDrop(CoordinateType coord,long startT, long endT, Color c, double startSize, double endSize){
		super();
		VisualObject object = createEffect(coord,easing,startT,endT,startSize,endSize,startFade,endFade);
		Command c1 = new ColorCMD(startT,endT,c);
		object.add(c1);
	}
	
	/**
	 * 
	 * @param startT Start Time
	 * @param endT End Time
	 * @param c Color 
	 * @param startSize Start Size
	 * @param endSize End Size
	 * @param startFade Start Fade
	 * @param endFade End Fade
	 */
	public WaterDrop(CoordinateType coord,long startT, long endT, Color c, double startSize, double endSize, double startFade, double endFade){
		super();
		VisualObject object = createEffect(coord,easing,startT,endT,startSize,endSize,startFade,endFade);
		Command c1 = new ColorCMD(startT,endT,c);
		object.add(c1);
	}
	
	
	private VisualObject createEffect(CoordinateType c, int easing, long startT, long endT, double startSize, double endSize, double startFade,double endFade){
		VisualObject o = new Sprite(c,Layer.Background, EffectsConstants.ring, x, y);
		Command c1 = new VectorScale(easing,startT,endT,startSize,endSize);
		Command c2 = new Fade(startT,endT,startFade,endFade);
		o.add(c1);
		o.add(c2);
		add(o);
		return o;
	}
	
	private VisualObject createEffect(CoordinateType coord,int easing, long startT, long endT,int x, int y, double startSize, double endSize, double startFade,double endFade){
		VisualObject o = new Sprite(coord,Layer.Background, EffectsConstants.ring, x, y);
		Command c1 = new VectorScale(easing,startT,endT,startSize,endSize);
		Command c2 = new Fade(startT,endT,startFade,endFade);
		o.add(c1);
		o.add(c2);
		add(o);
		return o;
	}
	
	/**
	 * Set XY for all rings based on hit object coordinate
	 */
	public void setALLXY(int x, int y){
		for (VisualObject o:getObjects()){
			o.setX(OsuUtils.hitObjectXToStoryboardX(x));
			o.setY(OsuUtils.hitObjectYToStoryboardY(y));
		}
	}
	
}
