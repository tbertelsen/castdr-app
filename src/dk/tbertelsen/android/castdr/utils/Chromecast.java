package dk.tbertelsen.android.castdr.utils;

import java.io.IOException;
import java.lang.ref.WeakReference;

import android.os.Bundle;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.media.MediaRouter.RouteInfo;
import android.util.Log;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import dk.tbertelsen.android.castdr.MainActivity;

public class Chromecast {
	public static final String TAG = "Chromecast";

	private final WeakReference<MainActivity> mFmainActivity;

	private MediaRouter mMediaRouter;
	private MediaRouteSelector mMediaRouteSelector;
	private MediaRouterCallback mMediaRouterCallback;
	private Listener mCastClientListener;
	private ConnectionFailedListener mConnectionFailedListener;
	private HelloWorldChannel mHelloWorldChannel;
	private ConnectionCallbacks mConnectionCallbacks;
	private GoogleApiClient mApiClient;

	private boolean mApplicationStarted;
	private boolean mWaitingForReconnect;
	private String mSessionId;
	private CastDevice mSelectedDevice;

	private String mApplicationId;

	public Chromecast(MainActivity mainActivity, String applicationId) {
		mFmainActivity = new WeakReference<MainActivity>(mainActivity);

		MainActivity activity = mFmainActivity.get();

		if (activity != null) {
			mApplicationId = applicationId;

			mMediaRouter = MediaRouter.getInstance(activity
					.getApplicationContext());

			mMediaRouteSelector = new MediaRouteSelector.Builder()
					.addControlCategory(
							CastMediaControlIntent
									.categoryForCast(mApplicationId)).build();

			mMediaRouterCallback = new MediaRouterCallback();

			mCastClientListener = new Cast.Listener() {

				@Override
				public void onApplicationStatusChanged() {
					if (mApiClient != null) {
						Log.d(TAG, "onApplicationStatusChanged: "
								+ Cast.CastApi.getApplicationStatus(mApiClient));
					}
				}

				@Override
				public void onVolumeChanged() {
					if (mApiClient != null) {
						Log.d(TAG,
								"onVolumeChanged: "
										+ Cast.CastApi.getVolume(mApiClient));
					}
				}

				@Override
				public void onApplicationDisconnected(int errorCode) {
					teardown();
				}
			};
			mConnectionCallbacks = new ConnectionCallbacks();

			mConnectionFailedListener = new ConnectionFailedListener();

			mHelloWorldChannel = new HelloWorldChannel();
		}

	}

	public MediaRouteSelector getMediaRouteSelector() {
		return mMediaRouteSelector;
	}

	public void setSelectedDevice(CastDevice selectedDevice) {
		mSelectedDevice = selectedDevice;
	}

	public GoogleApiClient getApiClient() {
		return mApiClient;
	}

	public void addCallback() {
		mMediaRouter.addCallback(mMediaRouteSelector, mMediaRouterCallback,
				MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
	}

	public void removeCallback() {
		mMediaRouter.removeCallback(mMediaRouterCallback);
	}

	public void launch() {
		MainActivity activity = mFmainActivity.get();

		if (activity != null) {
			if (mSelectedDevice != null) {
				Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions
						.builder(mSelectedDevice, mCastClientListener);

				mApiClient = new GoogleApiClient.Builder(activity)
						.addApi(Cast.API, apiOptionsBuilder.build())
						.addConnectionCallbacks(mConnectionCallbacks)
						.addOnConnectionFailedListener(
								mConnectionFailedListener).build();

			}
		}
	}

	public void connect() {
		if (mApiClient != null) {
			mApiClient.connect();
		}
	}

	public void teardown() {
		Log.d("teardown", "teardown");
		if (mApiClient != null) {
			if (mApplicationStarted) {
				if (mApiClient.isConnected() || mApiClient.isConnecting()) {
					try {
						Cast.CastApi.stopApplication(mApiClient, mSessionId);
						if (mHelloWorldChannel != null) {
							Cast.CastApi.removeMessageReceivedCallbacks(
									mApiClient,
									mHelloWorldChannel.getNamespace());
							mHelloWorldChannel = null;
						}
					} catch (IOException e) {
						Log.e(TAG, "Exception while removing channel", e);
					}
					mApiClient.disconnect();
				}
				mApplicationStarted = false;
			}
			mApiClient = null;
		}
		mSelectedDevice = null;
		setWaitingForReconnect(false);
		mSessionId = null;
	}

	public boolean isWaitingForReconnect() {
		return mWaitingForReconnect;
	}

	public void setWaitingForReconnect(boolean mWaitingForReconnect) {
		this.mWaitingForReconnect = mWaitingForReconnect;
	}

	public String getApplicationId() {
		return mApplicationId;
	}

	private class MediaRouterCallback extends MediaRouter.Callback {
		@Override
		public void onRouteSelected(MediaRouter router, RouteInfo info) {
			setSelectedDevice(CastDevice.getFromBundle(info.getExtras()));

//			String routeId = info.getId();
			launch();
		}

		@Override
		public void onRouteUnselected(MediaRouter router, RouteInfo info) {
			teardown();
		}
	}

	private class ConnectionCallbacks implements
			GoogleApiClient.ConnectionCallbacks {
		@Override
		public void onConnected(Bundle connectionHint) {
			if (isWaitingForReconnect()) {
				setWaitingForReconnect(false);
				connect();
			} else {
				try {
					Cast.CastApi
							.launchApplication(getApiClient(),
									getApplicationId(), false)
							.setResultCallback(
									new ResultCallback<Cast.ApplicationConnectionResult>() {
										@Override
										public void onResult(
												Cast.ApplicationConnectionResult result) {
											Status status = result.getStatus();
											if (status.isSuccess()) {
												/**
												ApplicationMetadata applicationMetadata = result
														.getApplicationMetadata();
												String sessionId = result
														.getSessionId();
												String applicationStatus = result
														.getApplicationStatus();
												boolean wasLaunched = result
														.getWasLaunched();
												
												 * TODO: ....
												 */
											} else {
												teardown();
											}
										}
									});
				} catch (Exception e) {
					Log.e(TAG, "Failed to launch application", e);
				}
			}
		}

		@Override
		public void onConnectionSuspended(int cause) {
			setWaitingForReconnect(true);
		}
	}

	private class ConnectionFailedListener implements
			GoogleApiClient.OnConnectionFailedListener {
		@Override
		public void onConnectionFailed(ConnectionResult result) {
			teardown();
		}
	}
	
	class HelloWorldChannel implements Cast.MessageReceivedCallback {
		public String getNamespace() {
			return "urn:x-cast:com.example.custom";
		}

		@Override
		public void onMessageReceived(CastDevice castDevice, String namespace,
				String message) {
			Log.d(TAG, "onMessageReceived: " + message);
		}
	}
}