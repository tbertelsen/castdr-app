package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuStreamQuality extends MuBase {
	private int Kbps = 0;
	private ArrayList<MuStream> Streams = new ArrayList<MuStream>();

	public int getKbps() {
		return Kbps;
	}

	public void setKbps(int kbps) {
		Kbps = kbps;
	}

	public ArrayList<MuStream> getStreams() {
		return Streams;
	}

	public void setStreams(ArrayList<MuStream> streams) {
		Streams = streams;
	}

	public boolean isValid() {
		return false;
	}

	static public MuStreamQuality create(String data) {
		MuStreamQuality o = null;
		try {
			o = new Gson().fromJson(data, MuStreamQuality.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuStreamQuality();
		}
		return o;
	}
	
	static public ArrayList<MuStreamQuality> createArray(String data) {
		ArrayList<MuStreamQuality> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuStreamQuality>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuStreamQuality>();
		}
		return o;
	}
}
