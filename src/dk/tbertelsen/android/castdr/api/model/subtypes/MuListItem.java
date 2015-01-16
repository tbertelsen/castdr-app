package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuListItem extends MuBase {
	private String Title = "";
	private String Subtitle = "";
	private String PrimaryImageUri = "";
	private String Type = "";
	
	public String getTitle() {
		return Title;
	}



	public void setTitle(String title) {
		Title = title;
	}



	public String getSubtitle() {
		return Subtitle;
	}



	public void setSubtitle(String subtitle) {
		Subtitle = subtitle;
	}



	public String getPrimaryImageUri() {
		return PrimaryImageUri;
	}



	public void setPrimaryImageUri(String primaryImageUri) {
		PrimaryImageUri = primaryImageUri;
	}



	public String getType() {
		return Type;
	}



	public void setType(String type) {
		Type = type;
	}



	public boolean isValid() {
		return false;
	}

	static public MuListItem create(String data) {
		MuListItem o = null;
		try {
			o = new Gson().fromJson(data, MuListItem.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuListItem();
		}
		return o;
	}
	
	static public ArrayList<MuListItem> createArray(String data) {
		ArrayList<MuListItem> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuListItem>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuListItem>();
		}
		return o;
	}
}
