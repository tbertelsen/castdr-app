package dk.tbertelsen.android.castdr.utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import dk.tbertelsen.android.castdr.ExpandFragmentTvChannels;
import dk.tbertelsen.android.castdr.FragmentMediaplayer;
import dk.tbertelsen.android.castdr.FragmentRadioChannels;
import dk.tbertelsen.android.castdr.MainActivity;
import dk.tbertelsen.android.drcast.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class MenuAdapter extends BaseAdapter {
	private final WeakReference<MainActivity> mFmainActivity;
	
	public final static int LIVETV = (1 << 0);
	public final static int LIVERADIO = (1 << 1);
	public final static int PROGRAMMER = (1 << 2);
	public final static int STREAM = (1 << 3);

	public final static int MASK_HOME = LIVETV + LIVERADIO + PROGRAMMER;

	public final static int MASK_STREAMING = MASK_HOME + STREAM;

	private LayoutInflater inflater;
	private int mask;
	private ArrayList<MainActivityMenuItem> data = new ArrayList<MainActivityMenuItem>();
	private int currentFame = LIVETV;

	private Bundle mBundle;

	public MenuAdapter(MainActivity mainActivity) {
		mFmainActivity = new WeakReference<MainActivity>(mainActivity);
		
		setMask(MASK_HOME);
		
		MainActivity activity = mFmainActivity.get();

		if (activity != null) {
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
		}
	}

	public MainActivityMenuItem createMenu(int id, String s, int icon) {
		MainActivityMenuItem item = new MainActivityMenuItem();
		item.id = id;
		item.string = s;
		item.drawable = icon;
		item.bundle = new Bundle();
		return item;
	}

	public MainActivityMenuItem createMenu(int id, String s, int icon,
			Bundle bundle) {
		MainActivityMenuItem item = createMenu(id, s, icon);
		item.bundle = bundle;

		return item;
	}

	public int getIndex(int type) {
		int index = 0;

		if (!data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				MainActivityMenuItem item = data.get(i);
				if (item.id == type) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	public int getCurrentFame() {
		return currentFame;
	}

	public void setMask(int m) {
		data.clear();
		this.notifyDataSetChanged();
		mask = m;

		MainActivity activity = mFmainActivity.get();

		if (activity != null) {
			if (mask != 0) {
				if ((mask & LIVETV) != 0) {
					data.add(createMenu(LIVETV,
							activity.getString(R.string.menu_livetv),
							android.R.drawable.ic_media_play));
				}

				if ((mask & LIVERADIO) != 0) {
					data.add(createMenu(LIVERADIO,
							activity.getString(R.string.menu_radio),
							android.R.drawable.ic_media_play));
				}

				if ((mask & PROGRAMMER) != 0) {
					data.add(createMenu(PROGRAMMER,
							activity.getString(R.string.menu_programs),
							android.R.drawable.ic_media_play));
				}

				if ((mask & STREAM) != 0) {
					data.add(createMenu(STREAM,
							activity.getString(R.string.menu_player),
							android.R.drawable.ic_media_play));
				}
			}	
		}
	}

	public void onOptionsItemSelected(int display, Bundle data) {
		int selected = 0;

		Fragment fragment = null;

		currentFame = display;

		if (data == null) {
			data = new Bundle();
		}

		mBundle = data;
		switch (display) {
		default:
		case LIVETV:
			setMask(MASK_HOME);

			fragment = new ExpandFragmentTvChannels();
			break;
		case LIVERADIO:
			setMask(MASK_HOME);
			fragment = new FragmentRadioChannels();

			// fragment = new FragmentServer();
			break;
		case PROGRAMMER:
			setMask(MASK_HOME);

			// fragment = new FragmentServer();
			break;
		case STREAM:
			setMask(MASK_STREAMING);

			fragment = new FragmentMediaplayer();
			break;
		}
		MainActivity activity = mFmainActivity.get();

		if (activity != null) {
			if (fragment != null) {
				fragment.setArguments(mBundle);
				activity.getSupportFragmentManager().beginTransaction()
						.replace(R.id.container, fragment).commit();
			}

			if (selected == 0) {
				selected = getIndex(currentFame);
			}

			// update selected item and title, then close the drawer
//			mNavigationDrawerFragment.setItemChecked(selected);
			MainActivityMenuItem item = getItem(selected);
			activity.setTitle(item.string);

			activity.invalidateOptionsMenu();
		}

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public MainActivityMenuItem getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MenuViewHolder holder = null;
		if (convertView == null) {
			holder = new MenuViewHolder();
			convertView = inflater.inflate(R.layout.drawer_list_item, null);

			holder.text = (TextView) convertView
					.findViewById(android.R.id.text1);
			holder.icon = (ImageView) convertView
					.findViewById(android.R.id.icon1);
			convertView.setTag(holder);
		} else {
			holder = (MenuViewHolder) convertView.getTag();
		}
		MainActivityMenuItem item = this.getItem(position);

		holder.text.setText(item.string);
		if (item.drawable == 0) {
			holder.icon.setVisibility(View.INVISIBLE);
		} else {
			holder.icon.setImageResource(item.drawable);
			holder.icon.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
	public static class MenuViewHolder {
		public TextView text;
		public ImageView icon;
	}

	public static class MainActivityMenuItem {
		public int id;
		public String string;
		public int drawable;
		public Bundle bundle;
	}
}