package effects.customEffects;

import effects.Effects;
import effects.EffectsConstants;

/**
 * Solid color BG to fade in and fade out, default white color, sorta looks like a FlashBang grenade
 * @author DH
 *
 */
public class FlashBang extends Effects{
	private String path = EffectsConstants.textPath;
	private double startFade = EffectsConstants.fade;
	private double endFade = 0;
	private long fadeDuration = 1000;
	
	
}
