package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class MuProgramCardListItem extends MuBase {
	private String Type = "";
	private String SeriesTitle = "";
	private String SeriesSlug = "";
	private String SeriesUrn = "";	
	private boolean IsNewSeries = false;
	private String SeriesHostName = "";		
	private String PrimaryChannel = "";
	private String PrimaryChannelSlug = "";		
	private boolean PrePremiere = false;
	private boolean ExpiresSoon = false;
	private int DurationInMilliseconds = 0;
	private int ProgressSeconds = 0;		
	private String OnlineGenreText = "";		
	private boolean RePremiere = false;		
	private String HostName = "";		
	private String RectificationStatus = "";		
	private boolean RectificationAuto = false;		
	private String RectificationText = "";		
	private String RectificationLink = "";		
	private String RectificationLinkText = "";
	private MuAsset PrimaryAsset = new MuAsset();	
	private String PrimaryBroadcastDay = "";
	private String PrimaryBroadcastStartTime = "";
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

	public String getSeriesTitle() {
		return SeriesTitle;
	}

	public void setSeriesTitle(String seriesTitle) {
		SeriesTitle = seriesTitle;
	}

	public String getSeriesSlug() {
		return SeriesSlug;
	}

	public void setSeriesSlug(String seriesSlug) {
		SeriesSlug = seriesSlug;
	}

	public String getSeriesUrn() {
		return SeriesUrn;
	}

	public void setSeriesUrn(String seriesUrn) {
		SeriesUrn = seriesUrn;
	}

	public boolean isIsNewSeries() {
		return IsNewSeries;
	}

	public void setIsNewSeries(boolean isNewSeries) {
		IsNewSeries = isNewSeries;
	}

	public String getSeriesHostName() {
		return SeriesHostName;
	}

	public void setSeriesHostName(String seriesHostName) {
		SeriesHostName = seriesHostName;
	}

	public String getPrimaryChannel() {
		return PrimaryChannel;
	}

	public void setPrimaryChannel(String primaryChannel) {
		PrimaryChannel = primaryChannel;
	}

	public String getPrimaryChannelSlug() {
		return PrimaryChannelSlug;
	}

	public void setPrimaryChannelSlug(String primaryChannelSlug) {
		PrimaryChannelSlug = primaryChannelSlug;
	}

	public boolean isPrePremiere() {
		return PrePremiere;
	}

	public void setPrePremiere(boolean prePremiere) {
		PrePremiere = prePremiere;
	}

	public boolean isExpiresSoon() {
		return ExpiresSoon;
	}

	public void setExpiresSoon(boolean expiresSoon) {
		ExpiresSoon = expiresSoon;
	}

	public Integer getDurationInMilliseconds() {
		return DurationInMilliseconds;
	}

	public void setDurationInMilliseconds(Integer durationInMilliseconds) {
		DurationInMilliseconds = durationInMilliseconds;
	}

	public Integer getProgressSeconds() {
		return ProgressSeconds;
	}

	public void setProgressSeconds(Integer progressSeconds) {
		ProgressSeconds = progressSeconds;
	}

	public String getOnlineGenreText() {
		return OnlineGenreText;
	}

	public void setOnlineGenreText(String onlineGenreText) {
		OnlineGenreText = onlineGenreText;
	}

	public boolean isRePremiere() {
		return RePremiere;
	}

	public void setRePremiere(boolean rePremiere) {
		RePremiere = rePremiere;
	}

	public String getHostName() {
		return HostName;
	}

	public void setHostName(String hostName) {
		HostName = hostName;
	}

	public String getRectificationStatus() {
		return RectificationStatus;
	}

	public void setRectificationStatus(String rectificationStatus) {
		RectificationStatus = rectificationStatus;
	}

	public boolean isRectificationAuto() {
		return RectificationAuto;
	}

	public void setRectificationAuto(boolean rectificationAuto) {
		RectificationAuto = rectificationAuto;
	}

	public String getRectificationText() {
		return RectificationText;
	}

	public void setRectificationText(String rectificationText) {
		RectificationText = rectificationText;
	}

	public String getRectificationLink() {
		return RectificationLink;
	}

	public void setRectificationLink(String rectificationLink) {
		RectificationLink = rectificationLink;
	}

	public String getRectificationLinkText() {
		return RectificationLinkText;
	}

	public void setRectificationLinkText(String rectificationLinkText) {
		RectificationLinkText = rectificationLinkText;
	}

	public MuAsset getPrimaryAsset() {
		return PrimaryAsset;
	}

	public void setPrimaryAsset(MuAsset primaryAsset) {
		PrimaryAsset = primaryAsset;
	}

	public String getPrimaryBroadcastDay() {
		return PrimaryBroadcastDay;
	}

	public void setPrimaryBroadcastDay(String primaryBroadcastDay) {
		PrimaryBroadcastDay = primaryBroadcastDay;
	}

	public Timestamp getPrimaryBroadcastStartTime() {
		return getTimestamp(PrimaryBroadcastStartTime);
	}

	public void setPrimaryBroadcastStartTime(String primaryBroadcastStartTime) {
		PrimaryBroadcastStartTime = primaryBroadcastStartTime;
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
	
	static public MuProgramCardListItem create(String data) {
		MuProgramCardListItem o = null;
		try {
			o = new Gson().fromJson(data, MuProgramCardListItem.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuProgramCardListItem();
		}
		return o;
	}
}
