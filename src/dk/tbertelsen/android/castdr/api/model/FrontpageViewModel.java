package dk.tbertelsen.android.castdr.api.model;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dk.tbertelsen.android.castdr.api.model.subtypes.MuBase;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuList;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuNowNext;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuServiceMessage;

public class FrontpageViewModel extends MuBase {
	private ArrayList<MuNowNext> Live = new ArrayList<MuNowNext>();
	private MuList LastChance = new MuList();
	private MuList SelectedTheme = new MuList();
	private MuList Themes = new MuList();
	private MuList News = new MuList();
	private MuList TopSpots = new MuList();
	private MuList SelectedList = new MuList();
	private MuList PopularList = new MuList();
	private MuServiceMessage ServiceMessage = new MuServiceMessage();

	public ArrayList<MuNowNext> getLive() {
		return Live;
	}

	public void setLive(ArrayList<MuNowNext> live) {
		Live = live;
	}

	public MuList getLastChance() {
		return LastChance;
	}

	public void setLastChance(MuList lastChance) {
		LastChance = lastChance;
	}

	public MuList getSelectedTheme() {
		return SelectedTheme;
	}

	public void setSelectedTheme(MuList selectedTheme) {
		SelectedTheme = selectedTheme;
	}

	public MuList getThemes() {
		return Themes;
	}

	public void setThemes(MuList themes) {
		Themes = themes;
	}

	public MuList getNews() {
		return News;
	}

	public void setNews(MuList news) {
		News = news;
	}

	public MuList getTopSpots() {
		return TopSpots;
	}

	public void setTopSpots(MuList topSpots) {
		TopSpots = topSpots;
	}

	public MuList getSelectedList() {
		return SelectedList;
	}

	public void setSelectedList(MuList selectedList) {
		SelectedList = selectedList;
	}

	public MuList getPopularList() {
		return PopularList;
	}

	public void setPopularList(MuList popularList) {
		PopularList = popularList;
	}

	public MuServiceMessage getServiceMessage() {
		return ServiceMessage;
	}

	public void setServiceMessage(MuServiceMessage serviceMessage) {
		ServiceMessage = serviceMessage;
	}

	public boolean isValid() {
		return (Live.size() > 0);
	}

	static public FrontpageViewModel create(String data) {
		FrontpageViewModel o = null;
		try {
			o = new Gson().fromJson(data, FrontpageViewModel.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new FrontpageViewModel();
		}
		return o;
	}
}
