package effects;

import java.awt.Color;
import java.util.Random;

public class AqoursColors {

	private static final Color[] colors = {
			// Ruby
			new Color(208,88,214),
			// Mari
			new Color(149,92,181),
			// Hanamaru
			new Color(242,236,143),
			
			// Yoshiko
			new Color(173,173,173),
			// Dia
			new Color(240,79,90),
			// You
			new Color(111,189,222),
			
			// Kanan
			new Color(87,235,148),
			// Chika
			new Color(242,165,210),
			// Riko
			new Color(250,133,115),
			// White
			Color.white
		};
	
	public static Color Ruby(){
		return colors[0];
	}
	
	public static Color Mari(){
		return colors[1];
	}
	
	public static Color Hanamaru(){
		return colors[2];
	}
	
	public static Color Yoshiko(){
		return colors[3];
	}
	
	public static Color Dia(){
		return colors[4];
	}
	
	public static Color You(){
		return colors[5];
	}
	
	public static Color Kanan(){
		return colors[6];
	}
	
	public static Color Chika(){
		return colors[7];
	}
	
	public static Color Riko(){
		return colors[8];
	}
	
	public static Color getRandomColor(){
		Random rng = new Random();
		return colors[rng.nextInt(9)];
	}
	
	public static Color getRandomColorIncludingWhite(){
		Random rng = new Random();
		return colors[rng.nextInt(10)];
		
	}
	
}
