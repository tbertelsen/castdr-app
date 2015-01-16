package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuChapter extends MuBase {
	private String Title = "";
	private String Description = "";
	private int OffsetInMiliseconds = 0;
	private String PrimaryImageUri = "";

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getOffsetInMiliseconds() {
		return OffsetInMiliseconds;
	}

	public void setOffsetInMiliseconds(int offsetInMiliseconds) {
		OffsetInMiliseconds = offsetInMiliseconds;
	}

	public String getPrimaryImageUri() {
		return PrimaryImageUri;
	}

	public void setPrimaryImageUri(String primaryImageUri) {
		PrimaryImageUri = primaryImageUri;
	}

	public boolean isValid() {
		return (Title.length() > 0);
	}

	static public MuChapter create(String data) {
		MuChapter o = null;
		try {
			o = new Gson().fromJson(data, MuChapter.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuChapter();
		}
		return o;
	}

	static public ArrayList<MuChapter> createArray(String data) {
		ArrayList<MuChapter> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuChapter>>() {
			}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuChapter>();
		}
		return o;
	}
}
