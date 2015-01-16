package dk.tbertelsen.android.castdr.events;

public class SystemUiVisibilityChangeEvent {
	private int flags = 0;
	
	public SystemUiVisibilityChangeEvent(int bundle) {
		this.setFlags(bundle);
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}
}
