package dk.tbertelsen.android.castdr.api.model;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import dk.tbertelsen.android.castdr.api.model.subtypes.MuBase;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuStreamingServer;

public class ManifestModel extends MuBase {
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

	public ManifestModel() {
	}

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

	public boolean isValid() {
		return (Title.length() > 0);
	}

	static public ManifestModel create(String data) {
		ManifestModel o = null;
		try {
			o = new Gson().fromJson(data, ManifestModel.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ManifestModel();
		}
		return o;
	}

	static public ArrayList<ManifestModel> createArray(String data) {
		ArrayList<ManifestModel> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<ManifestModel>>() {
			}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<ManifestModel>();
		}
		return o;
	}
}
