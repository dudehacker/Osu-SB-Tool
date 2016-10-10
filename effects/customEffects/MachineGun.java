package effects.customEffects;

import Objects.*;
import Utils.Easing;
import effects.AqoursColors;
import effects.Effects;
import Commands.*;

public class MachineGun extends Effects {
	
	private VisualObject bullet;
	private double bulletSpeed;
	private int direction;
	private int magazine;
	private Loop loop;
	/**
	 * @param startTime Start Time of loop
	 * @param bullet Sprite to use as bullet
	 * @param bulletSpeed how fast will bullet travel? in SB coordinate, distance/s
	 * @param direction refer to SpriteMove class
	 * @param duration how long will this effect last, in ms
	 */
	public MachineGun(int easing, long startTime, VisualObject bullet, double bulletSize, double transparency, double bulletSpeed, int direction, long duration){
		//System.out.println("duration = " + duration + " fireRate = " + fireRate );
		setParameters(easing, startTime, bullet, bulletSize, transparency, bulletSpeed,direction,duration);
	}
	
	public MachineGun( long startTime, VisualObject bullet, double bulletSize, double transparency, double bulletSpeed, int direction, long duration){
		//System.out.println("duration = " + duration + " fireRate = " + fireRate );
		setParameters(Easing.Linear, startTime, bullet, bulletSize, transparency, bulletSpeed,direction,duration);
	}
	
	private void setParameters(int easing, long startT,VisualObject bullet, double bulletSize, double transparency, double bulletSpeed, int direction, long duration){
		int startX = 900;
		int endX = 100;
		int distance = Math.abs(endX-startX);
		long endT = (long) (distance*1000.0/bulletSpeed);
		long fadeIn = 400;
		long fadeOut = 200;
		magazine = (int) Math.floor(duration/endT);
		if (magazine<=0){
			System.out.println("magazine size has to be positive!");
			System.exit(-1);
		}
		setStartTime(startT);
		this.setBullet(bullet);
		this.setBulletSpeed(bulletSpeed);
		this.setDirection(direction);
		this.setMagazine(magazine);

		int y = bullet.getY();
		long endTime = startT + duration;
		//System.out.println("mag = " +magazine + "  endT = " + endT + "  speed = " + bulletSpeed);
		bullet.add(new VectorScale(
				startT,
				endTime,
				bulletSize,
				bulletSize
				) );
		setLoop(new Loop(startT,magazine));
		bullet.add(new ColorCMD(startT,endTime,AqoursColors.getRandomColorIncludingWhite()));
		bullet.add(loop);
		
		// x = x0 + vt + 1/2 * a* t^2
		loop.addCommand(new Move(easing,0,endT,startX,y,endX,y));
		loop.addCommand(new Fade(0,fadeIn,transparency,transparency));
		loop.addCommand(new Fade(fadeIn,endT-fadeOut,transparency,0));
		add(bullet);
	}

	public Loop getLoop() {
		return loop;
	}

	public void setLoop(Loop loop) {
		this.loop = loop;
	}

	public int getMagazine() {
		return magazine;
	}

	public void setMagazine(int magazine) {
		this.magazine = magazine;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(double bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public VisualObject getBullet() {
		return bullet;
	}

	public void setBullet(VisualObject bullet) {
		this.bullet = bullet;
	}
}
