package dk.tbertelsen.android.castdr.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import dk.tbertelsen.android.castdr.api.model.ChannelModel;

public class AllRadioChannels extends DrApi {
	private static AllRadioChannels instance = null;
	
	@SuppressWarnings("unused")
	private String TAG = this.getClass().getSimpleName();
	
	private AtomicReference<ArrayList<ChannelModel>> data = new AtomicReference<ArrayList<ChannelModel>>(new ArrayList<ChannelModel>());
	
	public static AllRadioChannels getInstance() {
		if(instance == null) {
			instance = new AllRadioChannels();
		}
		return instance;
	}
	
	public AllRadioChannels() {
		super(false);
	}
	
	public void load() {
		setUrl("channel/all-active-dr-radio-channels");
		super.load();
	}
	
	protected void update() {
		ArrayList<ChannelModel> list = list();
		
		list.clear();

		ArrayList<ChannelModel> d = ChannelModel.createArray(getRawData());
		if (d != null) {
			Iterator<ChannelModel> i = d.iterator();
			ChannelModel channel = null;
			while (i.hasNext()) {
				channel = i.next();
				list.add(channel);
			}
		}
		
		Collections.sort(list, new Comparator<ChannelModel>() {
			@Override
		    public int compare(ChannelModel o1, ChannelModel o2) {
		        return o1.getTitle().compareTo(o2.getTitle());
		    }
		});
		
		data.getAndSet(list);
	}
	
	public ArrayList<ChannelModel> list() {
		ArrayList<ChannelModel> list = data.get();
		if (list != null) {
			return list;
		}
		return new ArrayList<ChannelModel>();
	}
	
	public boolean isValid() {
		if (!list().isEmpty()) {
			return true;
		}
		return false;
	}
}
