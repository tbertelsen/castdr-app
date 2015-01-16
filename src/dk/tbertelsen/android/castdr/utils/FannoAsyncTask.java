package dk.tbertelsen.android.castdr.utils;

import java.util.ArrayList;
import java.util.Iterator;

import dk.tbertelsen.android.castdr.events.FannoAsyncTaskEvent;

import android.os.AsyncTask;

public abstract class FannoAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	public final static int ON_NONE = 0;
	public final static int ON_PAUSE = 1;
	public final static int ON_STOP = 2;
	public final static int ON_DESTROY = 3;
	public final static int ON_ALL = 4;
	
	@SuppressWarnings("rawtypes")
	private static ArrayList<FannoAsyncTask> mInstances = new ArrayList<FannoAsyncTask>();
 
	public int cancel = ON_PAUSE;
	
	protected boolean multiInstance = false;
	
	public FannoAsyncTask() {
		BusProvider.INSTANCE.BUS.register(this);
		mInstances.add(this);
	}

	protected void onPreExecute() {
		update(true);
	}
	
	protected void onCancelled() {
		update(false);
	}

	@Override
	protected void onPostExecute(Result args) {
		update(false);
    }
	
	public static boolean isRunning() {
		if (mInstances.size() >0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void clear() {
		if (!mInstances.isEmpty()) {
			Iterator<FannoAsyncTask> iterator = mInstances.iterator();
			while(iterator.hasNext()) {
				FannoAsyncTask task = iterator.next();
				task.cancel(true);
			}
			mInstances.clear();
		}
	}

	private void update(boolean add) {
		if (!add) {
			BusProvider.INSTANCE.BUS.unregister(this);
			mInstances.remove(this);
		}
		BusProvider.INSTANCE.BUS.post(new FannoAsyncTaskEvent(isRunning()));
	}
}

