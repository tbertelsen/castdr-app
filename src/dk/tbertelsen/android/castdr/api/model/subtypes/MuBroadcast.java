package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuBroadcast extends MuBase {
	private String BroadcastDate = "";
	private String AnnouncedStartTime = "";
	private String AnnouncedEndTime = "";
	private String ProductionCountry = "";
	private int ProductionYear = 0;
	private boolean VideoWidescreen = false;
	private boolean SubtitlesTTV = false;
	private boolean VideoHD = false;
	private String WhatsOnUri = "";
	private String Channel = "";
	private String ChannelSlug = "";

	public String getBroadcastDate() {
		return BroadcastDate;
	}

	public void setBroadcastDate(String broadcastDate) {
		BroadcastDate = broadcastDate;
	}

	public String getAnnouncedStartTime() {
		return AnnouncedStartTime;
	}

	public void setAnnouncedStartTime(String announcedStartTime) {
		AnnouncedStartTime = announcedStartTime;
	}

	public String getAnnouncedEndTime() {
		return AnnouncedEndTime;
	}

	public void setAnnouncedEndTime(String announcedEndTime) {
		AnnouncedEndTime = announcedEndTime;
	}

	public String getProductionCountry() {
		return ProductionCountry;
	}

	public void setProductionCountry(String productionCountry) {
		ProductionCountry = productionCountry;
	}

	public int getProductionYear() {
		return ProductionYear;
	}

	public void setProductionYear(int productionYear) {
		ProductionYear = productionYear;
	}

	public boolean isVideoWidescreen() {
		return VideoWidescreen;
	}

	public void setVideoWidescreen(boolean videoWidescreen) {
		VideoWidescreen = videoWidescreen;
	}

	public boolean isSubtitlesTTV() {
		return SubtitlesTTV;
	}

	public void setSubtitlesTTV(boolean subtitlesTTV) {
		SubtitlesTTV = subtitlesTTV;
	}

	public boolean isVideoHD() {
		return VideoHD;
	}

	public void setVideoHD(boolean videoHD) {
		VideoHD = videoHD;
	}

	public String getWhatsOnUri() {
		return WhatsOnUri;
	}

	public void setWhatsOnUri(String whatsOnUri) {
		WhatsOnUri = whatsOnUri;
	}

	public String getChannel() {
		return Channel;
	}

	public void setChannel(String channel) {
		Channel = channel;
	}

	public String getChannelSlug() {
		return ChannelSlug;
	}

	public void setChannelSlug(String channelSlug) {
		ChannelSlug = channelSlug;
	}

	public boolean isValid() {
		return (BroadcastDate.length() > 0);
	}

	static public MuBroadcast create(String data) {
		MuBroadcast o = null;
		try {
			o = new Gson().fromJson(data, MuBroadcast.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuBroadcast();
		}
		return o;
	}

	static public ArrayList<MuBroadcast> createArray(String data) {
		ArrayList<MuBroadcast> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuBroadcast>>() {
			}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuBroadcast>();
		}
		return o;
	}
}
