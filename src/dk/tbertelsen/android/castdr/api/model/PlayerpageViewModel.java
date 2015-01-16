package dk.tbertelsen.android.castdr.api.model;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dk.tbertelsen.android.castdr.api.model.subtypes.MuBase;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuList;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuProgramCard;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuProgramCardListItem;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuServiceMessage;

public class PlayerpageViewModel extends MuBase {
	private MuProgramCard ProgramCard = new MuProgramCard();
	private MuProgramCardListItem NextEpisode = new MuProgramCardListItem();
	private ArrayList<MuList> RelationsList = new ArrayList<MuList>();
	private ArrayList<MuList> Theme = new ArrayList<MuList>();
	private ArrayList<MuList> TopSpots = new ArrayList<MuList>();
	private ArrayList<MuList> SelectedList = new ArrayList<MuList>();
	private ArrayList<MuList> PopularList = new ArrayList<MuList>();
	private MuServiceMessage ServiceMessage = new MuServiceMessage();

	public MuProgramCard getProgramCard() {
		return ProgramCard;
	}

	public void setProgramCard(MuProgramCard programCard) {
		ProgramCard = programCard;
	}

	public MuProgramCardListItem getNextEpisode() {
		return NextEpisode;
	}

	public void setNextEpisode(MuProgramCardListItem nextEpisode) {
		NextEpisode = nextEpisode;
	}

	public ArrayList<MuList> getRelationsList() {
		return RelationsList;
	}

	public void setRelationsList(ArrayList<MuList> relationsList) {
		RelationsList = relationsList;
	}

	public ArrayList<MuList> getTheme() {
		return Theme;
	}

	public void setTheme(ArrayList<MuList> theme) {
		Theme = theme;
	}

	public ArrayList<MuList> getTopSpots() {
		return TopSpots;
	}

	public void setTopSpots(ArrayList<MuList> topSpots) {
		TopSpots = topSpots;
	}

	public ArrayList<MuList> getSelectedList() {
		return SelectedList;
	}

	public void setSelectedList(ArrayList<MuList> selectedList) {
		SelectedList = selectedList;
	}

	public ArrayList<MuList> getPopularList() {
		return PopularList;
	}

	public void setPopularList(ArrayList<MuList> popularList) {
		PopularList = popularList;
	}

	public MuServiceMessage getServiceMessage() {
		return ServiceMessage;
	}

	public void setServiceMessage(MuServiceMessage serviceMessage) {
		ServiceMessage = serviceMessage;
	}

	public boolean isValid() {
		return (ProgramCard.isValid());
	}

	static public PlayerpageViewModel create(String data) {
		PlayerpageViewModel o = null;
		try {
			o = new Gson().fromJson(data, PlayerpageViewModel.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new PlayerpageViewModel();
		}
		return o;
	}
}
