package dk.tbertelsen.android.castdr.events;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentEvent {
	private Fragment fragment;
	private Bundle bundle = null;
	
	public FragmentEvent(Fragment fragment) {
		this.setFragment(fragment);
	}
	
	public FragmentEvent(Fragment fragment, Bundle bundle) {
		this.setFragment(fragment);
		this.setBundle(bundle);
	}

	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle data) {
		this.bundle = data;
	}
}
