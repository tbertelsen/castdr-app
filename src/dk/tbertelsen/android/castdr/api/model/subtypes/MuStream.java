package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuStream extends MuBase {
	private String Stream = "";

	public String getStream() {
		return Stream;
	}

	public void setStream(String stream) {
		Stream = stream;
	}

	public boolean isValid() {
		return false;
	}

	static public MuStream create(String data) {
		MuStream o = null;
		try {
			o = new Gson().fromJson(data, MuStream.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuStream();
		}
		return o;
	}

	static public ArrayList<MuStream> createArray(String data) {
		ArrayList<MuStream> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuStream>>() {
			}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuStream>();
		}
		return o;
	}
}
