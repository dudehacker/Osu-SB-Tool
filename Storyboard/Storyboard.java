package Storyboard;
import java.io.File;
import java.util.ArrayList;




import effects.Effects;
import Objects.Layer;
import Objects.VisualObject;
import Utils.OsuUtils;

// Size = 640x480
public class Storyboard {
	private final static String nl = OsuUtils.nl;
	private File osuFile;
	private File outputFile; // OSB file or OSU file
	private ArrayList<VisualObject> background = new ArrayList<>();
	private ArrayList<VisualObject> SB_background = new ArrayList<>();
	private ArrayList<VisualObject> SB_fail = new ArrayList<>();
	private ArrayList<VisualObject> SB_pass = new ArrayList<>();
	private ArrayList<VisualObject> SB_foreground = new ArrayList<>();

	public Storyboard(boolean isDifficultySpecific){
		osuFile = OsuUtils.getOsuFile();
		//System.out.println(osuFile);
		if (isDifficultySpecific==false){
			outputFile = getSBFile();
		} else{
			outputFile = osuFile;
		}
		//System.out.println(outputFile);
	}
	
	public void add(Effects e){
		ArrayList<VisualObject> objects = e.getObjects();
		for (VisualObject o : objects){
			if (o.getLayer() == Layer.Video){
				background.add(o.Clone());
			} else if (o.getLayer() == Layer.Background){
				SB_background.add(o.Clone());
			} else if (o.getLayer() == Layer.Foreground){
				SB_foreground.add(o.Clone());
			} else if (o.getLayer() == Layer.Fail){
				SB_fail.add(o.Clone());
			} else if (o.getLayer() == Layer.Pass){
				SB_pass.add(o.Clone());
			}
			
		}
	}
	
	public void remove(Effects e){
		ArrayList<VisualObject> objects = e.getObjects();
		for (VisualObject o : objects){
			if (o.getLayer() == Layer.Video){
				background.remove(o);
			} else if (o.getLayer() == Layer.Background){
				SB_background.remove(o);
			} else if (o.getLayer() == Layer.Foreground){
				SB_foreground.remove(o);
			} else if (o.getLayer() == Layer.Fail){
				SB_fail.add(o.Clone());
			} else if (o.getLayer() == Layer.Pass){
				SB_pass.add(o.Clone());
			}
			
		}
	}
	
	public void sortAllEffects(){
		for (VisualObject o : background){
			o.sortByStartTime();
		}
		for (VisualObject o : SB_background){
			o.sortByStartTime();
		}
		for (VisualObject o : SB_foreground){
			o.sortByStartTime();
		}
		for (VisualObject o : SB_fail){
			o.sortByStartTime();
		}
		for (VisualObject o : SB_pass){
			o.sortByStartTime();
		}
	}
	
	private File getSBFile(){
		File output;
		String osuName = osuFile.getName();
		String osb = osuName.substring(0, osuName.indexOf(" [")) + ".osb";
		output = new File(osuFile.getParent() + "\\" + osb);
		return output;
	}
	
	public String toString(){
		String text = "[Events]" +nl;
		text+= "//Background and Video events" + nl;
		text += getLayerInfo(background);
		text+= "//Storyboard Layer 0 (Background)" + nl;
		text += getLayerInfo(SB_background);
		text+= "//Storyboard Layer 1 (Fail)" + nl;
		text += getLayerInfo(SB_fail);
		text+= "//Storyboard Layer 2 (Pass)" + nl;
		text += getLayerInfo(SB_pass);
		text+= "//Storyboard Layer 3 (Foreground)" + nl;
		text += getLayerInfo(SB_foreground);
		text+= "//Storyboard Sound Samples" + nl;
		return text;
	}
	
	private String getLayerInfo(ArrayList<VisualObject> layer){
		String output = "";
		if (layer.isEmpty()){
			return output;
		}
		for (VisualObject o : layer){
			output += o.toString() + nl;
		}
		return output;
	}
	
	public void exportSB(){
		OsuUtils.exportBeatmap(outputFile, toString());
	}

	public File getOsuFile() {
		return osuFile;
	}

	public void setOsuFile(File osuFile) {
		this.osuFile = osuFile;
	}
	
}
