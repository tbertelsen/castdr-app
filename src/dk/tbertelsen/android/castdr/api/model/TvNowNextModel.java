package dk.tbertelsen.android.castdr.api.model;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dk.tbertelsen.android.castdr.api.model.subtypes.MuBase;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuScheduleBroadcast;

public class TvNowNextModel extends MuBase {
	private String ChannelSlug = "";
	private MuScheduleBroadcast Now = new MuScheduleBroadcast();
	private ArrayList<MuScheduleBroadcast> Next = new ArrayList<MuScheduleBroadcast>();

	public String getChannelSlug() {
		return ChannelSlug;
	}

	public void setChannelSlug(String channelSlug) {
		ChannelSlug = channelSlug;
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

	static public TvNowNextModel create(String data) {
		TvNowNextModel o = null;
		try {
			o = new Gson().fromJson(data, TvNowNextModel.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new TvNowNextModel();
		}
		return o;
	}
}
