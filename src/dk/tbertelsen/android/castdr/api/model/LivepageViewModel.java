package dk.tbertelsen.android.castdr.api.model;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dk.tbertelsen.android.castdr.api.model.subtypes.MuBase;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuChannel;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuList;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuNowNext;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuServiceMessage;

public class LivepageViewModel extends MuBase {
	private MuNowNext NowNext = new MuNowNext();
	private MuChannel Channel = new MuChannel();
	private ArrayList<MuList> TopSpots = new ArrayList<MuList>();
	private ArrayList<MuList> SelectedList = new ArrayList<MuList>();
	private ArrayList<MuList> PopularList = new ArrayList<MuList>();
	private MuServiceMessage ServiceMessage = new MuServiceMessage();

	public MuNowNext getNowNext() {
		return NowNext;
	}

	public void setNowNext(MuNowNext nowNext) {
		NowNext = nowNext;
	}

	public MuChannel getChannel() {
		return Channel;
	}

	public void setChannel(MuChannel channel) {
		Channel = channel;
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
		return (NowNext.isValid());
	}

	static public LivepageViewModel create(String data) {
		LivepageViewModel o = null;
		try {
			o = new Gson().fromJson(data, LivepageViewModel.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new LivepageViewModel();
		}
		return o;
	}
}
