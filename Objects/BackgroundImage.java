package Objects;

import Utils.Easing;

public class BackgroundImage extends VisualObject{
	private Easing easing;
	private long startTime;
	
	//0,0,"Love.Live!.jpg",0,0
	public BackgroundImage(String filePath, Easing easing, long startTime) {
		super(ObjectType.Background,Layer.Video, filePath);
	}

	@Override
	public String getHeader() {
		addQuotesToFilePath();
		return easing + "," + startTime + "," + filepath;
	}

	@Override
	public VisualObject Clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
