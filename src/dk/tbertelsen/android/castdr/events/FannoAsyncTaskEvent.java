package dk.tbertelsen.android.castdr.events;

public class FannoAsyncTaskEvent {
	private boolean running;
	
	public FannoAsyncTaskEvent(boolean running) {
		this.setRunning(running);
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}

