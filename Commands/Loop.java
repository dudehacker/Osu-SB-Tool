package Commands;

import java.util.ArrayList;

import Utils.OsuUtils;

public class Loop extends Command{

	private int loopCount;
	private ArrayList<Command> cmds;
	private String nl = OsuUtils.nl;
	
	public Loop(long startT,int loopCount) {
		super(CommandName.Loop, startT);
		this.setLoopCount(loopCount);
		setCmds(new ArrayList<>());
	}

	@Override
	public Command clone() {
		Loop newLoop = new Loop(startTime,loopCount);
		@SuppressWarnings("unchecked")
		ArrayList<Command> cmdClone = (ArrayList<Command>) cmds.clone();
		newLoop.setCmds(cmdClone);
		return newLoop;
	}

	@Override
	public String toString() {
		String output = " L," + startTime + "," + loopCount + nl;
		for (Command c : cmds){
			output += " " + c.toString() + nl;
		}
		return output;
	}

	public void addCommand(Command c){
		if (c.getName().equals(CommandName.Loop)){
			System.out.println("Can't add a loop within a loop!");
			System.exit(-1);
		}
		cmds.add(c);
	}
	
	public void removeCommandType(CommandName name){
		ArrayList<Command> newCmds = new ArrayList<Command>();
		for (Command c: cmds){
			if (!c.getName().equals(name)){
				newCmds.add(c);
			}
		}
		setCmds(newCmds);
	}
	
	public int getLoopCount() {
		return loopCount;
	}

	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	public ArrayList<Command> getCmds() {
		return cmds;
	}

	public void setCmds(ArrayList<Command> cmds) {
		this.cmds = cmds;
	}


}
