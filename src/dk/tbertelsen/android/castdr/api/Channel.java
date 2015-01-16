package dk.tbertelsen.android.castdr.api;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import dk.tbertelsen.android.castdr.api.model.ChannelModel;

public class Channel extends DrApi {
	private static HashMap<String, Channel> instance = new HashMap<String, Channel>();
	
	@SuppressWarnings("unused")
	private String TAG = this.getClass().getSimpleName();
	
	private AtomicReference<ChannelModel> data = new AtomicReference<ChannelModel>(new ChannelModel());

	private String id;
	
	public static Channel getInstance(String id) {
		if(!instance.containsKey(id)) {
			instance.put(id, new Channel(id));
		}
		return instance.get(id);
	}
	
	public Channel(String id) {
		super(false);
		this.id = id;
	}
	
	public void load() {
		setUrl("channel/" + id);
		super.load();
	}
	
	public ChannelModel get() {
		return data.get();
	}
	
	protected void update() {
		ChannelModel d = ChannelModel.create(getRawData());
		data.getAndSet(d);
	}

	/**
	 * TOFO: fix valid ?
	 */
	public boolean isValid() {
		return true;
	}

}
