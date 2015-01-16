package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuSubtitles extends MuBase {
	private String Format = "";
	private String Language = "";
	private String MimeType = "";
	private String Type = "";
	private String Uri = "";

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getMimeType() {
		return MimeType;
	}

	public void setMimeType(String mimeType) {
		MimeType = mimeType;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getUri() {
		return Uri;
	}

	public void setUri(String uri) {
		Uri = uri;
	}
	
	public boolean isValid() {
		return (MimeType.length() > 0);
	}

	static public MuSubtitles create(String data) {
		MuSubtitles o = null;
		try {
			o = new Gson().fromJson(data, MuSubtitles.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuSubtitles();
		}
		return o;
	}
	
	static public ArrayList<MuSubtitles> createArray(String data) {
		ArrayList<MuSubtitles> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuSubtitles>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuSubtitles>();
		}
		return o;
	}
}
