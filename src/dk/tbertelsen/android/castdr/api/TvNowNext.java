package dk.tbertelsen.android.castdr.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import dk.tbertelsen.android.castdr.api.model.TvNowNextModel;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuScheduleBroadcast;

public class TvNowNext extends DrApi {
	private static HashMap<String, TvNowNext> instance = new HashMap<String, TvNowNext>();
	
	@SuppressWarnings("unused")
	private String TAG = this.getClass().getSimpleName();
	
	private AtomicReference<ArrayList<MuScheduleBroadcast>> data = new AtomicReference<ArrayList<MuScheduleBroadcast>>(new ArrayList<MuScheduleBroadcast>());

	private String mSlug;
	
	public static TvNowNext getInstance(String slug) {
		if(!instance.containsKey(slug)) {
			instance.put(slug, new TvNowNext(slug));
		}
		return instance.get(slug);
	}
	
	public TvNowNext(String slug) {
		super(false);
		
		this.mSlug = slug;
	}
	
	public void load() {
		setUrl("page/tv/live/" + this.mSlug);
		super.load();
	}
	
	protected void update() {
		ArrayList<MuScheduleBroadcast> list = list();
		
		list.clear();
		
		TvNowNextModel d = TvNowNextModel.create(getRawData());
		if (d != null) {
			list.add(d.getNow());
			
			Iterator<MuScheduleBroadcast> i = d.getNext().iterator();
			MuScheduleBroadcast channel = null;
			while (i.hasNext()) {
				channel = i.next();
				list.add(channel);
			}
		}
		
		data.getAndSet(list);
	}
	
	public ArrayList<MuScheduleBroadcast> list() {
		ArrayList<MuScheduleBroadcast> list = data.get();
		if (list != null) {
			return list;
		}
		return new ArrayList<MuScheduleBroadcast>();
	}
	
	public boolean isValid() {
		if (!list().isEmpty()) {
			return true;
		}
		return false;
	}
}
