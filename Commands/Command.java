package Commands;

import java.util.Comparator;

public abstract class  Command {
	private final CommandName name;
	//    http://easings.net/
	protected int easing = 0;
	protected long startTime;
	protected long endTime;
	
	public Command(CommandName n, int easing, long startT, long endT){
		checkInputParameter(easing,startT,endT);
		name =n;
	}
	
	public Command(CommandName n, long startT, long endT){
		name =n;
		checkInputParameter(0,startT,endT);
	}
	
	private void checkInputParameter(int easing, long  startT, long endT){
		if (endT<startT){
			throw new IllegalArgumentException("start time "+ startT+ " can't be greater than end time "+endT+"!");
		}
		
		this.easing =easing;
		startTime = startT;
		endTime = endT;
	}
	
	public abstract Command clone();
	
	
	public abstract String toString();
	

	public static Comparator<Command> StartTimeComparator = new Comparator<Command>() {
		@Override
		public int compare(Command c1, Command c2) {
			long t1 = c1.startTime;
			long t2 = c2.startTime;
			/* For ascending order */
			return (int) (t1 - t2);
		}
	};
	
	public static Comparator<Command> EndTimeComparator = new Comparator<Command>() {
		@Override
		public int compare(Command c1, Command c2) {
			long t1 = c1.endTime;
			long t2 = c2.endTime;
			/* For ascending order */
			return (int) (t1 - t2);
		}
	};
	public CommandName getName(){
		return name;
	}
	
	public int getEasing() {
		return easing;
	}


	public void setEasing(int easing) {
		this.easing = easing;
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public long getEndTime() {
		return endTime;
	}


	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
		
}
