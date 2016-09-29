package Commands;

import Utils.OsuUtils;

public class Fade extends Command{

	private double startOpacity;
	private double endOpacity;
	// The opacity of the object (how transparent it is).
	// 0 to 1, with decimals accepted. 0 is invisible, 1 is fully visible.
	public Fade(long startT,long endT){
		super(CommandName.Fade, 0, startT, endT);
		checkParameter(1, 1);
	}
	
	public Fade(int easing, long startT, long endT, double startO, double endO) {
		super(CommandName.Fade, easing, startT, endT);
		checkParameter(startO, endO);
	}
	
	public Fade(long startT, long endT, double startO, double endO){
		super(CommandName.Fade, startT, endT);
		checkParameter(startO, endO);
	}

	private void checkParameter(double startO, double endO){
		if (getStartOpacity() >1){
			throw new IllegalArgumentException("Start Opacity can't be greater than 1");
		} else if(getStartOpacity() < 0){
			throw new IllegalArgumentException("Start Opacity can't be negative");
		}
		if (getEndOpacity() >1){
			throw new IllegalArgumentException("End Opacity can't be greater than 1");
		} else if(getEndOpacity() < 0){
			throw new IllegalArgumentException("End Opacity can't be negative");
		}
		setStartOpacity(startO);
		setEndOpacity(endO);
	}
	
	@Override
	public Fade clone() {
		return new Fade(easing,startTime,endTime,getStartOpacity(),getEndOpacity());
	}

	@Override
	public String toString() {
		String s = OsuUtils.formatDoubleToString(getStartOpacity());
		String e = OsuUtils.formatDoubleToString(getEndOpacity());
		if (getStartOpacity() == getEndOpacity()){
			return " F," + easing + "," +startTime + "," +endTime + ","+s;
		}
		return " F," + easing + "," +startTime + "," +endTime + ","+s+","+e;
	}

	public double getEndOpacity() {
		return endOpacity;
	}

	public void setEndOpacity(double endOpacity) {
		this.endOpacity = endOpacity;
	}

	public double getStartOpacity() {
		return startOpacity;
	}

	public void setStartOpacity(double startOpacity) {
		this.startOpacity = startOpacity;
	}

}
