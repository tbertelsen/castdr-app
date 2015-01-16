package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuMediaLink extends MuBase {
	private String Uri = "";
	private String FileFormat = "";
	private String Target = "";
	private int Bitrate = 0;
	
	public String getUri() {
		return Uri;
	}




	public void setUri(String uri) {
		Uri = uri;
	}




	public String getFileFormat() {
		return FileFormat;
	}




	public void setFileFormat(String fileFormat) {
		FileFormat = fileFormat;
	}




	public String getTarget() {
		return Target;
	}




	public void setTarget(String target) {
		Target = target;
	}




	public int getBitrate() {
		return Bitrate;
	}




	public void setBitrate(int bitrate) {
		Bitrate = bitrate;
	}


	public boolean isValid() {
		return false;
	}

	static public MuMediaLink create(String data) {
		MuMediaLink o = null;
		try {
			o = new Gson().fromJson(data, MuMediaLink.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuMediaLink();
		}
		return o;
	}
	
	static public ArrayList<MuMediaLink> createArray(String data) {
		ArrayList<MuMediaLink> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuMediaLink>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuMediaLink>();
		}
		return o;
	}
}
