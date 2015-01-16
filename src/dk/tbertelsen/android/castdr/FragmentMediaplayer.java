package dk.tbertelsen.android.castdr;

import com.squareup.otto.Subscribe;

import dk.tbertelsen.android.castdr.events.SystemUiVisibilityChangeEvent;
import dk.tbertelsen.android.castdr.utils.BusProvider;
import dk.tbertelsen.android.castdr.utils.FannoAsyncTask;
import dk.tbertelsen.android.drcast.R;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.MediaController;

import android.widget.VideoView;

public class FragmentMediaplayer extends Fragment {
	@SuppressWarnings("unused")
	private String TAG = this.getClass().getSimpleName();

	private VideoView myVideoView;
	private MediaController mediaControls;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.video_player, container,
				false);

		if (mediaControls == null) {
			mediaControls = new MediaController(getActivity(), false);
			mediaControls.show(0);
		}

		myVideoView = (VideoView) rootView.findViewById(R.id.video);

		if (getArguments().containsKey("uri")) {
			Uri uri = Uri.parse(getArguments().getString("uri"));

			try {
				// set the media controller in the VideoView
				myVideoView.setMediaController(mediaControls);
				// set the uri of the video to be played
				myVideoView.setVideoURI(uri);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			myVideoView.requestFocus();
			// we also set an setOnPreparedListener in order to know when the
			// video file is ready for playback
			myVideoView.setOnPreparedListener(new OnPreparedListener() {

				public void onPrepared(MediaPlayer mediaPlayer) {
					myVideoView.start();
				}
			});
		}
		return rootView;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/*
	 * @Override public void onActivityCreated(Bundle savedInstanceState) {
	 * super.onActivityCreated(savedInstanceState); View decorView =
	 * getActivity().getWindow().getDecorView();
	 * decorView.setOnSystemUiVisibilityChangeListener( new
	 * View.OnSystemUiVisibilityChangeListener() {
	 * 
	 * @Override public void onSystemUiVisibilityChange(int flags) { boolean
	 * visible = (flags & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
	 * mediaControls.setVisibility(visible ? View.VISIBLE : View.GONE); } }); }
	 */

	@Subscribe
	public void systemUiVisibilityChange(SystemUiVisibilityChangeEvent event) {
		int flags = event.getFlags();
		boolean visible = (flags == 0);
		if (Build.VERSION.SDK_INT >= 14) {
			visible = (flags & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
		}
		if (visible) {
			mediaControls.show(4500);
		} else {
			mediaControls.hide();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		BusProvider.INSTANCE.BUS.register(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		BusProvider.INSTANCE.BUS.unregister(this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		FannoAsyncTask.clear();
	}
}
