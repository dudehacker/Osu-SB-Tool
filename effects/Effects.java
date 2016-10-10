package effects;

import java.util.ArrayList;
import java.util.Comparator;

import Commands.Command;
import Commands.CommandName;
import Objects.VisualObject;
import Utils.Sample;


// Effects is multiple objects
public class Effects {
	private long startTime=Long.MAX_VALUE;
	private long endTime = Long.MIN_VALUE;
	private ArrayList<VisualObject> objects = new ArrayList<>();
	private ArrayList<Sample> samples = new ArrayList<>();
	
	
	public long getStartTime(){
		return startTime;
	}
	
	public long getEndTime(){
		return endTime;
	}
	
	public VisualObject getObjectFromIndex(int index){
		return getObjects().get(index).Clone();
	}
	
	public void removeObjectFromIndex(int index){
		getObjects().remove(index);
	}
	
	public void add(Command c){
		getObjects().get(0).add(c);
		update();
	}
	
	public void add(VisualObject o){
		getObjects().add(o);
		update();
	}
	
	public void add(Sample s){
		getSamples().add(s.clone());
	}
	
	public void add(Effects e){
		for (VisualObject o : e.objects){
			add(o);
		}
	}
	
	
	public void remove(VisualObject o){
		getObjects().remove(o);
		update();
	}
	
	public void removeAll(CommandName name){
		for (VisualObject o : objects){
			o.removeAll(name);
		}
	}
	
	public void addAll(Command c){
		for (VisualObject o : objects){
			o.add(c.clone());
		}
	}
	
	private void update() {
		if (getObjects().size()>0){
			long s = Long.MAX_VALUE;
			long e = Long.MIN_VALUE;
			for (VisualObject o : getObjects()){
				long startT = o.getStartTime();
				long endT = o.getEndTime();
				if (startT<s){
					s = startT;
				}
				if (endT>e){
					e = endT;
				}
			}
		setStartTime(s);
		endTime = e;
		}
		
	}
	
	public static Comparator<Effects> StartTimeComparator = new Comparator<Effects>() {
		@Override
		public int compare(Effects e1, Effects e2) {
			long t1 = e1.getStartTime();
			long t2 = e2.getStartTime();
			/* For ascending order */
			return (int) (t1 - t2);
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public Effects clone(){
		Effects e = new Effects();
		e.setObjects((ArrayList<VisualObject>) getObjects().clone());
		return e;
	}
	
	public String toString(){
		String output="";
		for (VisualObject o : getObjects()){
			output+=o.toString();
		}
		
		return output;
	}
	
	public void setALLXY(int x, int y){
		for (VisualObject o:getObjects()){
			o.setX(x);
			o.setY(y);
		}
	}

	public void sort() {
		for (VisualObject o : getObjects()){
			o.sortByStartTime();
		}
		
	}

	public ArrayList<VisualObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<VisualObject> objects) {
		this.objects = objects;
	}

	protected void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public ArrayList<Sample> getSamples() {
		return samples;
	}

	public void setSamples(ArrayList<Sample> samples) {
		this.samples = samples;
	}
	
}
