package dk.tbertelsen.android.castdr.utils;

import com.squareup.otto.Bus;

public enum BusProvider {
    INSTANCE;
    
    public final Bus BUS;
 
	private BusProvider() {
		BUS = new Bus();
	}
}

