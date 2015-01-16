package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuNowNext extends MuBase {
	private String ChannelSlug = "";
	private String Channel = "";
	private MuScheduleBroadcast Now = new MuScheduleBroadcast();
	private ArrayList<MuScheduleBroadcast> Next = new ArrayList<MuScheduleBroadcast>();

	public String getChannelSlug() {
		return ChannelSlug;
	}

	public void setChannelSlug(String channelSlug) {
		ChannelSlug = channelSlug;
	}

	public String getChannel() {
		return Channel;
	}

	public void setChannel(String channel) {
		Log.e("set channel", "aaaaaaaaaa");
		Channel = channel;
	}

	public MuScheduleBroadcast getNow() {
		return Now;
	}

	public void setNow(MuScheduleBroadcast now) {
		
		Now = now;
	}

	public ArrayList<MuScheduleBroadcast> getNext() {
		return Next;
	}

	public void setNext(ArrayList<MuScheduleBroadcast> next) {
		Next = next;
	}
	
	public boolean isValid() {
		return (ChannelSlug.length() > 0);
	}

	static public MuNowNext create(String data) {
		MuNowNext o = null;
		try {
			o = new Gson().fromJson(data, MuNowNext.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuNowNext();
		}
		return o;
	}
	
	static public ArrayList<MuNowNext> createArray(String data) {
		ArrayList<MuNowNext> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuNowNext>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuNowNext>();
		}
		return o;
	}
}
