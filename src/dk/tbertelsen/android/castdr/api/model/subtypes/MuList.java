package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuList extends MuBase {
	private String Type = "";
	private boolean IsRepremiere = false;
	private ArrayList<MuListItem> Items = new ArrayList<MuListItem>();
	private MuPaging Paging = new MuPaging();
	private int TotalSize = 0;
		
	
	public String getType() {
		return Type;
	}



	public void setType(String type) {
		Type = type;
	}



	public boolean isIsRepremiere() {
		return IsRepremiere;
	}



	public void setIsRepremiere(boolean isRepremiere) {
		IsRepremiere = isRepremiere;
	}



	public ArrayList<MuListItem> getItems() {
		return Items;
	}



	public void setItems(ArrayList<MuListItem> items) {
		Items = items;
	}



	public MuPaging getPaging() {
		return Paging;
	}



	public void setPaging(MuPaging paging) {
		Paging = paging;
	}



	public int getTotalSize() {
		return TotalSize;
	}



	public void setTotalSize(int totalSize) {
		TotalSize = totalSize;
	}



	public boolean isValid() {
		return false;
	}

	static public MuList create(String data) {
		MuList o = null;
		try {
			o = new Gson().fromJson(data, MuList.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuList();
		}
		return o;
	}
	
	static public ArrayList<MuList> createArray(String data) {
		ArrayList<MuList> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuList>>(){}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuList>();
		}
		return o;
	}
}
