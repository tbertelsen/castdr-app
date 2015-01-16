package dk.tbertelsen.android.castdr.api;

import java.util.concurrent.atomic.AtomicReference;

import dk.tbertelsen.android.castdr.api.model.FrontpageViewModel;

public class PageTvFront extends DrApi {
	private static PageTvFront instance = null;
	
	@SuppressWarnings("unused")
	private String TAG = this.getClass().getSimpleName();
	
	private AtomicReference<FrontpageViewModel> data = new AtomicReference<FrontpageViewModel>(new FrontpageViewModel());
	
	public static PageTvFront getInstance() {
		if (instance == null) {
			instance = new PageTvFront();	
		}		
		return instance;
	}
	
	public PageTvFront() {
		super(false);
	}
	
	public void load() {
		setUrl("page/tv/front");
		super.load();
	}
	
	public FrontpageViewModel get() {
		return data.get();
	}
	
	protected void update() {
		FrontpageViewModel d = FrontpageViewModel.create(getRawData());
		/*
		if (d != null) {
			Iterator<MuStreamingServer> i = d.iterator();
			MuStreamingServer channel = null;
			while (i.hasNext()) {
				channel = i.next();
				if (!channel.getName().contains(" WEB") ) {
					list.add(channel);
				}
			}
		}
		*/
		data.getAndSet(d);
	}

	/**
	 * TOFO: fix valid ?
	 */
	public boolean isValid() {
		return true;
	}

}
