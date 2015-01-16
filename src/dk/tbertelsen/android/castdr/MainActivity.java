package dk.tbertelsen.android.castdr;

import com.google.android.gms.cast.CastMediaControlIntent;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import com.squareup.otto.Subscribe;

import dk.tbertelsen.android.castdr.api.DrApi;
import dk.tbertelsen.android.castdr.events.MenuEvent;
import dk.tbertelsen.android.castdr.utils.BusProvider;
import dk.tbertelsen.android.castdr.utils.Chromecast;
import dk.tbertelsen.android.castdr.utils.MenuAdapter;
import dk.tbertelsen.android.drcast.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.MediaRouteActionProvider;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;

import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	public static final String TAG = "MainActivity";

	public static final double VOLUME_INCREMENT = 0.05;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private MenuAdapter mMenuAdapter;

	public Bundle mBundle;

	private Chromecast mChromecast;

	final static int INITIAL_HIDE_DELAY = 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMenuAdapter = new MenuAdapter(this);

		DrApi.CACHEPATH = this.getFilesDir().getPath();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		mNavigationDrawerFragment.setAdapter(mMenuAdapter);

		ChangeMenu(new MenuEvent(MenuAdapter.LIVETV, mBundle));

		mChromecast = new Chromecast(this,
				CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID);
		// mChromecast = new Chromecast(this, getString(R.string.app_id));
	}

	@Subscribe
	public void ChangeMenu(MenuEvent event) {
		mMenuAdapter.onOptionsItemSelected(event.getFragment(),
				event.getBundle());
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if (mMenuAdapter != null) {
			mMenuAdapter.onOptionsItemSelected(position + 1, null);
		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);

			MenuItem mediaRouteMenuItem = menu
					.findItem(R.id.media_route_menu_item);
			MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat
					.getActionProvider(mediaRouteMenuItem);
			mediaRouteActionProvider.setRouteSelector(mChromecast
					.getMediaRouteSelector());

			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Log.d("MainActivity", "onBackPressed");
		super.onBackPressed();
	}

	@Override
	public void onResume() {
		super.onResume();
		BusProvider.INSTANCE.BUS.register(this);
		mChromecast.addCallback();
	}

	@Override
	public void onPause() {
		if (isFinishing()) {
			mChromecast.removeCallback();
		}
		super.onPause();
		BusProvider.INSTANCE.BUS.unregister(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mChromecast.addCallback();
	}

	@Override
	protected void onStop() {
		mChromecast.removeCallback();
		super.onStop();
	}
}
