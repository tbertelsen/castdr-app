package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuStreamingServer extends MuBase {
	private String Server = "";
	private String LinkType = "";
	private ArrayList<MuStreamQuality> Qualities = new ArrayList<MuStreamQuality>();
	private boolean DynamicUserQualityChange = false;
	
	public String getServer() {
		return Server;
	}

	public void setServer(String server) {
		Server = server;
	}

	public String getLinkType() {
		return LinkType;
	}

	public void setLinkType(String linkType) {
		LinkType = linkType;
	}

	public ArrayList<MuStreamQuality> getQualities() {
		return Qualities;
	}

	public void setQualities(ArrayList<MuStreamQuality> qualities) {
		Qualities = qualities;
	}

	public boolean isDynamicUserQualityChange() {
		return DynamicUserQualityChange;
	}

	public void setDynamicUserQualityChange(boolean dynamicUserQualityChange) {
		DynamicUserQualityChange = dynamicUserQualityChange;
	}

	public boolean isValid() {
		return (Server.length() > 0);
	}

	static public MuStreamingServer create(String data) {
		MuStreamingServer o = null;
		try {
			o = new Gson().fromJson(data, MuStreamingServer.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuStreamingServer();
		}
		return o;
	}
	
	static public ArrayList<MuStreamingServer> createArray(String data) {
		ArrayList<MuStreamingServer> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuStreamingServer>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuStreamingServer>();
		}
		return o;
	}
}
