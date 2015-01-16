package dk.tbertelsen.android.castdr;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import dk.tbertelsen.android.castdr.api.Channel;
import dk.tbertelsen.android.castdr.api.PageTvFront;
import dk.tbertelsen.android.castdr.api.model.FrontpageViewModel;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuNowNext;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuScheduleBroadcast;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuStreamingServer;
import dk.tbertelsen.android.castdr.events.AlertDialogEvent;
import dk.tbertelsen.android.castdr.events.MenuEvent;
import dk.tbertelsen.android.castdr.helper.FlowTextHelper;
import dk.tbertelsen.android.castdr.utils.BusProvider;
import dk.tbertelsen.android.castdr.utils.FannoAsyncTask;
import dk.tbertelsen.android.castdr.utils.MenuAdapter;
import dk.tbertelsen.android.drcast.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExpandFragmentTvChannels extends ListFragment {
	@SuppressWarnings("unused")
	private String TAG = this.getClass().getSimpleName();

	private ChannelsAdapter mChannelsAdapter = null;

	public PageTvFront mPageTvFront;

	private DisplayImageOptions mImageOptions;

	private final UpdateUiHandler mUpdateUiHandler = new UpdateUiHandler(this);

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.expandablelist, container,
				false);

		mPageTvFront = PageTvFront.getInstance();

		mChannelsAdapter = new ChannelsAdapter(mPageTvFront.get());

		ExpandableListView list = (ExpandableListView) rootView
				.findViewById(android.R.id.list);
		list.setAdapter(mChannelsAdapter);

		if (savedInstanceState == null) {
			loadChannels();
		}

		return rootView;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).build();
	}

	private static class UpdateUiHandler extends Handler {
		private final WeakReference<ExpandFragmentTvChannels> mFragment;

		public UpdateUiHandler(ExpandFragmentTvChannels fragment) {
			mFragment = new WeakReference<ExpandFragmentTvChannels>(fragment);
		}

		@Override
		public void handleMessage(Message msg) {
			ExpandFragmentTvChannels fragment = mFragment.get();
			if (fragment != null) {
				fragment.loadChannels();
			}
		}
	}

	private void delayedUpdate() {
		mUpdateUiHandler.removeMessages(0);
		mUpdateUiHandler.sendEmptyMessageDelayed(0, 60000);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// MuNowNext project = mChannelsAdapter.getItem(position);
		// if (project != null) {
		/**
		 * TODO: add click event
		 */
		// FragmentTvNowNext fragment = new FragmentTvNowNext();
		// BusProvider.INSTANCE.BUS.post(new FragmentEvent(fragment));
		// BusProvider.INSTANCE.BUS.post(new
		// FragmentEvent(MenuAdapter.PROJECT));
		// }
	}

	public void loadChannels() {
		mChannelsAdapter.setLoading(true);
		new RetreiveChannels().execute();
	}

	@Override
	public void onPause() {
		super.onPause();
		mUpdateUiHandler.removeMessages(0);
	}

	@Override
	public void onResume() {
		super.onResume();
		loadChannels();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		FannoAsyncTask.clear();
	}

	/*
	 * RetreiveChannels
	 */
	class RetreiveChannels extends FannoAsyncTask<Void, Void, Void> {
		public RetreiveChannels() {
			super();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			mPageTvFront.load();

			FrontpageViewModel foo = mPageTvFront.get();

			Iterator<MuNowNext> iterator = foo.getLive().iterator();

			while (iterator.hasNext()) {
				MuNowNext nownext = iterator.next();
				Channel channel = Channel.getInstance(nownext.getChannelSlug());
				channel.load();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			super.onPostExecute(args);
			if (!this.isCancelled()) {
				if (mPageTvFront.isError()) {
					BusProvider.INSTANCE.BUS.post(new AlertDialogEvent(
							mPageTvFront, new OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									/*
									 * TODO: ERROR handel
									 */
									// BusProvider.INSTANCE.BUS.post(new
									// FragmentEvent(MenuAdapter.HOME, new
									// Bundle()));
								}
							}));
				} else {
					mChannelsAdapter.update(mPageTvFront.get());
					delayedUpdate();
				}
			}
			mChannelsAdapter.setLoading(false);
		}
	}

	/*
	 * ChannelsAdapter
	 */
	@SuppressLint("InflateParams")
	private class ChannelsAdapter extends BaseExpandableListAdapter {
		private FrontpageViewModel data = new FrontpageViewModel();
		private LayoutInflater inflater;
		private boolean loading = false;

		public ChannelsAdapter(FrontpageViewModel data) {
			this.data = data;
			inflater = (LayoutInflater) getActivity().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
		}

		public void update(FrontpageViewModel data) {
			this.data = data;
			this.notifyDataSetChanged();
		}

		public void setLoading(boolean l) {
			loading = l;
			this.notifyDataSetChanged();
		}

		@Override
		public int getGroupCount() {
			if (loading) {
				return data.getLive().size() + 1;
			} else {
				return data.getLive().size();
			}
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return data.getLive().get(groupPosition).getNext().size();
		}

		@Override
		public MuNowNext getGroup(int groupPosition) {
			if (!data.getLive().isEmpty()
					&& data.getLive().size() > groupPosition) {
				return data.getLive().get(groupPosition);
			}
			return null;
		}

		@Override
		public MuScheduleBroadcast getChild(int groupPosition, int childPosition) {
			if (!data.getLive().isEmpty()
					&& data.getLive().size() > groupPosition) {
				return data.getLive().get(groupPosition).getNext()
						.get(childPosition);
			}
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			ViewGroupHolder holder = null;
			if (convertView == null) {
				holder = new ViewGroupHolder();
				convertView = inflater.inflate(R.layout.nowlistviewitem, null);

				holder.image = (ImageView) convertView.findViewById(R.id.image);
				holder.now = (TextView) convertView.findViewById(R.id.now);
				holder.progress = (ProgressBar) convertView
						.findViewById(R.id.progress);
				holder.play = (ImageButton) convertView.findViewById(R.id.play);

				convertView.setTag(holder);
			} else {
				holder = (ViewGroupHolder) convertView.getTag();
			}
			MuNowNext entry = this.getGroup(groupPosition);

			holder.progress.setVisibility(View.GONE);
			holder.play.setVisibility(View.GONE);

			if (entry != null) {
				Channel channel = Channel.getInstance(entry.getChannelSlug());

				MuStreamingServer server = channel.get().getStreamingServer("HLS");
				
				if (server.getLinkType().equals("HLS")) {
					holder.play.setVisibility(View.VISIBLE);
					String url = server.getServer()
							+ "/"
							+ server.getQualities().get(0).getStreams()
									.get(0).getStream();

					final Bundle args = new Bundle();
					args.putString("uri", url);

					holder.play.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							BusProvider.INSTANCE.BUS.post(new MenuEvent(
									MenuAdapter.STREAM, args));
						}
					});
				}

				holder.now.setText(entry.getNow().getTitle());
				ImageLoader.getInstance().displayImage(
						channel.get().getPrimaryImageUri(), holder.image,
						mImageOptions);

				Timestamp start = entry.getNow().getStartTime();
				Timestamp end = entry.getNow().getEndTime();
				Date now = new Date();

				if (start.getTime() <= now.getTime()) {
					if (end.getTime() >= now.getTime()) {
						holder.progress.setMax((int) (end.getTime() - start
								.getTime()));
						holder.progress
								.setProgress((int) (now.getTime() - start
										.getTime()));
						holder.progress.setVisibility(View.VISIBLE);
					}
				}
			} else {
				holder.now.setText(R.string.loading);
				holder.image.setImageResource(R.drawable.ic_launcher);
			}

			if ((groupPosition % 2) == 0) {
				convertView.setBackgroundResource(R.drawable.list_item);
			} else {
				convertView.setBackgroundResource(R.drawable.list_item_1);
			}
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewChildHolder holder = null;
			if (convertView == null) {
				holder = new ViewChildHolder();
				convertView = inflater.inflate(R.layout.nextlistviewitem, null);

				holder.image = (ImageView) convertView.findViewById(R.id.image);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.next = (TextView) convertView.findViewById(R.id.next);
				holder.description = (TextView) convertView
						.findViewById(R.id.description);

				convertView.setTag(holder);
			} else {
				holder = (ViewChildHolder) convertView.getTag();
			}
			MuScheduleBroadcast entry = this.getChild(groupPosition,
					childPosition);

			if (entry != null) {
				String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(entry
						.getStartTime());

				holder.time.setText(time);
				holder.next.setText(entry.getTitle());
				// holder.description.setText(entry.getDescription());

				ImageLoader.getInstance().displayImage(
						entry.getProgramCard().getPrimaryImageUri(),
						holder.image, mImageOptions,
						new ImageLoadingListener() {
							@Override
							public void onLoadingStarted(String imageUri,
									View view) {
								// TODO Auto-generated method stub
							}

							@Override
							public void onLoadingFailed(String imageUri,
									View view, FailReason failReason) {
								// TODO Auto-generated method stub
							}

							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								/**
								 * Code done to ensure FlowTextHelper works
								 * correct (it need to re-apply after image is
								 * loaded could be fixed by adding a new default
								 * image)).
								 */
								notifyDataSetChanged();
							}

							@Override
							public void onLoadingCancelled(String imageUri,
									View view) {
								// TODO Auto-generated method stub
							}
						});

				// Display display =
				// context.getWindowManager().getDefaultDisplay();
				DisplayMetrics display = getResources().getDisplayMetrics();

				FlowTextHelper.tryFlowText(entry.getDescription(),
						holder.image, holder.description, display, 25);
			} else {
				holder.next.setText(R.string.loading);
			}

			if ((groupPosition % 2) == 0) {
				convertView.setBackgroundResource(R.drawable.list_item);
			} else {
				convertView.setBackgroundResource(R.drawable.list_item_1);
			}
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
	}

	public static class ViewGroupHolder {
		public ImageView image;
		public TextView now;
		public ProgressBar progress;
		public ImageButton play;
	}

	public static class ViewChildHolder {
		public ImageView image;
		public TextView time;
		public TextView next;
		public TextView description;
	}
}
