package dk.tbertelsen.android.castdr.api.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import dk.tbertelsen.android.castdr.api.model.subtypes.MuBase;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuStreamingServer;

public class ChannelModel extends MuBase {
	private String Type = "";
	private ArrayList<MuStreamingServer> StreamingServers = new ArrayList<MuStreamingServer>();
	private String Url = "";
	private String SourceUrl = "";
	private boolean WebChannel = false;
	private String Slug = "";
	private String Urn = "";
	private String PrimaryImageUri = "";
	private String Title = "";
	private String Subtitle = "";

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public ArrayList<MuStreamingServer> getStreamingServers() {
		return StreamingServers;
	}

	public void setStreamingServers(
			ArrayList<MuStreamingServer> streamingServers) {
		StreamingServers = streamingServers;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getSourceUrl() {
		return SourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		SourceUrl = sourceUrl;
	}

	public boolean isWebChannel() {
		return WebChannel;
	}

	public void setWebChannel(boolean webChannel) {
		WebChannel = webChannel;
	}

	public String getSlug() {
		return Slug;
	}

	public void setSlug(String slug) {
		Slug = slug;
	}

	public String getUrn() {
		return Urn;
	}

	public void setUrn(String urn) {
		Urn = urn;
	}

	public String getPrimaryImageUri() {
		return PrimaryImageUri;
	}

	public void setPrimaryImageUri(String primaryImageUri) {
		PrimaryImageUri = primaryImageUri;
	}

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
	
	/**
	 * Funktions that are not standart
	 */
	
	public MuStreamingServer getStreamingServer(String type) {
		Iterator<MuStreamingServer> iterator = getStreamingServers().iterator();
		
		while (iterator.hasNext()) {
			MuStreamingServer server = iterator.next();
			if (server.getLinkType().equals(type)) {
				return server;
			}
		}
		return new MuStreamingServer();
	}
	
	public boolean isValid() {
		return (Title.length() > 0);
	}

	static public ChannelModel create(String data) {
		ChannelModel o = null;
		try {
			o = new Gson().fromJson(data, ChannelModel.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ChannelModel();
		}
		return o;
	}

	static public ArrayList<ChannelModel> createArray(String data) {
		ArrayList<ChannelModel> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<ChannelModel>>() {
			}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<ChannelModel>();
		}
		return o;
	}
}
