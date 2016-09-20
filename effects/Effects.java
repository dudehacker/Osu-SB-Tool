package effects;

import java.util.ArrayList;
import java.util.Comparator;

import Objects.VisualObject;


// Effects is multiple objects
public class Effects {
	private long startTime=Long.MAX_VALUE;
	private long endTime = Long.MIN_VALUE;
	private ArrayList<VisualObject> objects = new ArrayList<>();
	
	
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
	
	public void add(VisualObject o){
		getObjects().add(o);
		update();
	}
	
	public void remove(VisualObject o){
		getObjects().remove(o);
		update();
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
		startTime = s;
		endTime = e;
		}
		
	}
	
	public static Comparator<Effects> StartTimeComparator = new Comparator<Effects>() {
		@Override
		public int compare(Effects e1, Effects e2) {
			long t1 = e1.startTime;
			long t2 = e2.startTime;
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
	
}