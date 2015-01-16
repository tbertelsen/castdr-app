package dk.tbertelsen.android.castdr.events;

import android.os.Bundle;

public class MenuEvent {
	private int fragment;
	private Bundle bundle = null;
	
	public MenuEvent(int fragment) {
		this.setFragment(fragment);
	}
	
	public MenuEvent(int fragment, Bundle bundle) {
		this.setFragment(fragment);
		this.setBundle(bundle);
	}

	public int getFragment() {
		return fragment;
	}

	public void setFragment(int fragment) {
		this.fragment = fragment;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle data) {
		this.bundle = data;
	}
}
