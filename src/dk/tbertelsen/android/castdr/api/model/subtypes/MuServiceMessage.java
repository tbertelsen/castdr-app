package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuServiceMessage extends MuBase {
	private String Id = "";
	private String Title = "";
	private String Message = "";
	private String Link = "";
	private String StartTime = "";
	private String EndTime = "";
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getLink() {
		return Link;
	}

	public void setLink(String link) {
		Link = link;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public boolean isValid() {
		return false;
	}

	static public MuServiceMessage create(String data) {
		MuServiceMessage o = null;
		try {
			o = new Gson().fromJson(data, MuServiceMessage.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuServiceMessage();
		}
		return o;
	}
	
	static public ArrayList<MuServiceMessage> createArray(String data) {
		ArrayList<MuServiceMessage> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuServiceMessage>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuServiceMessage>();
		}
		return o;
	}
}
