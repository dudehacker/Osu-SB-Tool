package Utils;

public class Range {
	private long start;
	private long end;
	
	public Range(long start, long end){
		if (end>=start){
			this.start = start;
			this.end = end;
		} else {
			System.out.println("end is smaller than start, cant create range!");
			System.exit(-1);
		}
	}
	
	public boolean contains(long value){
		if (start<= value && value <= end){
			return true;
		}
		return false;
	}
}
