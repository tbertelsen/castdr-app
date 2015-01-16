package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuPaging extends MuBase {
	private String Title = "";
	private String Source = "";
	private String Next = "";
	private String Previous = "";
	private String URI = "";
	private String TotalSize = "";
	
	public String getTitle() {
		return Title;
	}



	public void setTitle(String title) {
		Title = title;
	}



	public String getSource() {
		return Source;
	}



	public void setSource(String source) {
		Source = source;
	}



	public String getNext() {
		return Next;
	}



	public void setNext(String next) {
		Next = next;
	}



	public String getPrevious() {
		return Previous;
	}



	public void setPrevious(String previous) {
		Previous = previous;
	}



	public String getURI() {
		return URI;
	}



	public void setURI(String uRI) {
		URI = uRI;
	}



	public String getTotalSize() {
		return TotalSize;
	}



	public void setTotalSize(String totalSize) {
		TotalSize = totalSize;
	}



	public boolean isValid() {
		return false;
	}

	static public MuPaging create(String data) {
		MuPaging o = null;
		try {
			o = new Gson().fromJson(data, MuPaging.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuPaging();
		}
		return o;
	}
	
	static public ArrayList<MuPaging> createArray(String data) {
		ArrayList<MuPaging> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuPaging>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuPaging>();
		}
		return o;
	}
}
