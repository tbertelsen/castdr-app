package dk.tbertelsen.android.castdr;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import dk.tbertelsen.android.castdr.api.AllRadioChannels;
import dk.tbertelsen.android.castdr.api.model.ChannelModel;
import dk.tbertelsen.android.castdr.api.model.subtypes.MuStreamingServer;
import dk.tbertelsen.android.castdr.events.AlertDialogEvent;
import dk.tbertelsen.android.castdr.events.MenuEvent;
import dk.tbertelsen.android.castdr.utils.BusProvider;
import dk.tbertelsen.android.castdr.utils.FannoAsyncTask;
import dk.tbertelsen.android.castdr.utils.MenuAdapter;
import dk.tbertelsen.android.drcast.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FragmentRadioChannels extends ListFragment {
	@SuppressWarnings("unused")
	private String TAG = this.getClass().getSimpleName();
	
	private ChannelsAdapter mChannelsAdapter = null;

	public AllRadioChannels mAllRadioChannelst;

	private DisplayImageOptions mImageOptions;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   	
    	View rootView = inflater.inflate(R.layout.list, container, false);

    	mAllRadioChannelst = AllRadioChannels.getInstance();
		
    	mChannelsAdapter = new ChannelsAdapter(getActivity(), mAllRadioChannelst.list());
		setListAdapter(mChannelsAdapter);
		
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
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	}
	
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {     
//    	MuNowNext project = mChannelsAdapter.getItem(position);
		//if (project != null) {
			/**
			 * TODO: add click event
			 */
//			FragmentTvNowNext fragment = new FragmentTvNowNext();
//			BusProvider.INSTANCE.BUS.post(new FragmentEvent(fragment));
//			BusProvider.INSTANCE.BUS.post(new FragmentEvent(MenuAdapter.PROJECT));
//		}
    }
    
	public void loadChannels() {
		mChannelsAdapter.setLoading(true);
		new RetreiveChannels().execute();
	}
	
	@Override
    public void onResume() {
    	super.onResume();
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
			mAllRadioChannelst.load();
			return null;
		}
		
    	@Override
		protected void onPostExecute(Void args) {
    		super.onPostExecute(args);
    		if (!this.isCancelled()) {
    			if (mAllRadioChannelst.isError()) {
					BusProvider.INSTANCE.BUS.post(new AlertDialogEvent(mAllRadioChannelst, new OnCancelListener() {
        				@Override
        				public void onCancel(DialogInterface dialog) {
        					/*
        					 * TODO: ERROR handel
        					 */
//        					BusProvider.INSTANCE.BUS.post(new FragmentEvent(MenuAdapter.HOME, new Bundle()));
        				}
        			}));
				} else {
					mChannelsAdapter.update(mAllRadioChannelst.list());
				}
    		}
    		mChannelsAdapter.setLoading(false);
	    }
	}
    
    /*
     * ChannelsAdapter
     */
    @SuppressLint("InflateParams")
	private class ChannelsAdapter extends BaseAdapter {
        private ArrayList<ChannelModel> data = new ArrayList<ChannelModel>();
        private LayoutInflater inflater;
		private boolean loading = false;
 
        public ChannelsAdapter(Context context, ArrayList<ChannelModel> data) {
        	this.data = data;
            inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        
        public void update(ArrayList<ChannelModel> data) {
        	this.data = data;
        	this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
        	if (loading) {
        		return data.size()+1;
        	} else {
                return data.size();        		
        	}
        }
        
        public void setLoading(boolean l) {
            loading  = l;
            this.notifyDataSetChanged();
        }
        
        @Override
        public ChannelModel getItem(int position) {
        	if (!data.isEmpty() && data.size() > position) {
        		return data.get(position);
        	}
        	return null;
        }
 
        @Override
        public long getItemId(int position) {
            return 0;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.nowlistviewitem, null);
                
                holder.image = (ImageView)convertView.findViewById(R.id.image);
                holder.now = (TextView)convertView.findViewById(R.id.now);                
                holder.progress = (ProgressBar)convertView.findViewById(R.id.progress);
                holder.play = (ImageButton)convertView.findViewById(R.id.play);

                
                
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            ChannelModel entry = this.getItem(position);
            
            holder.progress.setVisibility(View.GONE);
            holder.play.setVisibility(View.GONE);
            
            if (entry != null) {
            	MuStreamingServer server = entry.getStreamingServer("HLS");
            	
        		if (server.getLinkType().equals("HLS")) {
        			holder.play.setVisibility(View.VISIBLE);
        			String url = server.getServer() + "/" + server.getQualities().get(0).getStreams().get(0).getStream();
        			
                	final Bundle args = new Bundle();
        			args.putString("uri", url);
        			
                	holder.play.setOnClickListener(new OnClickListener() {
    					@Override
    					public void onClick(View v) {
    						BusProvider.INSTANCE.BUS.post(new MenuEvent(MenuAdapter.STREAM, args));
    					}
                	});
        		}
            	
               	holder.now.setText(entry.getTitle());
              	ImageLoader.getInstance().displayImage(entry.getPrimaryImageUri(), holder.image, mImageOptions);
              	
            } else {
            	holder.now.setText(R.string.loading);
            }
        	
    		if ((position % 2) == 0) {
    			convertView.setBackgroundResource(R.drawable.list_item);
    		} else {
    			convertView.setBackgroundResource(R.drawable.list_item_1);
    		}
            return convertView;
        }
    }
 
    public static class ViewHolder {
    	public ImageView image;
        public TextView now;
        public ProgressBar progress;
        public ImageButton play;
    }
}
