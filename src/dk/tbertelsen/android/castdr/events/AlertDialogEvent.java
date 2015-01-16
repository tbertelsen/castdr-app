package dk.tbertelsen.android.castdr.events;

import android.content.DialogInterface.OnCancelListener;
import dk.tbertelsen.android.castdr.api.DrApi;

public class AlertDialogEvent {
	private DrApi api;
	private OnCancelListener onCancel;
	
	public AlertDialogEvent(DrApi api) {
		this.setApi(api);
	}
	
	public AlertDialogEvent(DrApi api, OnCancelListener onCancelListener) {
		this.setApi(api);
		this.setOnCancelListener(onCancelListener);
	}

	public DrApi getApi() {
		return api;
	}
	
	public void setApi(DrApi api) {
		this.api = api;
	}

	public OnCancelListener getOnCancelListener() {
		return onCancel;
	}

	public void setOnCancelListener(OnCancelListener onCancel) {
		this.onCancel = onCancel;
	}
}
