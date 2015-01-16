package dk.tbertelsen.android.castdr.events;

import android.os.Bundle;

public class NavigationDrawerEvent {
	private int page;
	private Bundle bundle = null;
	
	public NavigationDrawerEvent(int page) {
		this.setPage(page);
	}
	
	public NavigationDrawerEvent(int page, Bundle bundle) {
		this.setPage(page);
		this.setBundle(bundle);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle data) {
		this.bundle = data;
	}
}
