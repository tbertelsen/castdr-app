package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class MuScheduleBroadcast extends MuBase {
	private String Title = "";
	private String Description = "";
	private String Subtitle = "";
	private String StartTime = "";
	private String EndTime = "";
	private MuProgramCardListItem ProgramCard = new MuProgramCardListItem();
	
	private String Channel = "";
	private String ChannelSlug = "";

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



	public String getSubtitle() {
		return Subtitle;
	}



	public void setSubtitle(String subtitle) {
		Subtitle = subtitle;
	}



	public Timestamp getStartTime() {
		return getTimestamp(StartTime);
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public Timestamp getEndTime() {
		return getTimestamp(EndTime);
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}



	public MuProgramCardListItem getProgramCard() {
		return ProgramCard;
	}



	public void setProgramCard(MuProgramCardListItem programCard) {
		ProgramCard = programCard;
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
		return (Title.length() > 0);
	}

	static public MuScheduleBroadcast create(String data) {
		MuScheduleBroadcast o = null;
		try {
			o = new Gson().fromJson(data, MuScheduleBroadcast.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuScheduleBroadcast();
		}
		return o;
	}
}
