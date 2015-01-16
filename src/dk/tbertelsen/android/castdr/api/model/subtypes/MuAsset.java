package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MuAsset extends MuBase {
	private String Kind = "";
	private String Uri = "";
	private int DurationInMilliseconds = 0;
	private boolean RestrictedToDenmark = false;
	private String StartPublish = "";
	private String EndPublish = "";
	private String Target = "";
	private boolean Encrypted = false;

	public String getKind() {
		return Kind;
	}

	public void setKind(String kind) {
		Kind = kind;
	}

	public String getUri() {
		return Uri;
	}

	public void setUri(String uri) {
		Uri = uri;
	}

	public int getDurationInMilliseconds() {
		return DurationInMilliseconds;
	}

	public void setDurationInMilliseconds(int durationInMilliseconds) {
		DurationInMilliseconds = durationInMilliseconds;
	}

	public boolean isRestrictedToDenmark() {
		return RestrictedToDenmark;
	}

	public void setRestrictedToDenmark(boolean restrictedToDenmark) {
		RestrictedToDenmark = restrictedToDenmark;
	}

	public Timestamp getStartPublish() {
		return getTimestamp(StartPublish);
	}

	public void setStartPublish(String startPublish) {
		StartPublish = startPublish;
	}

	public Timestamp getEndPublish() {
		return getTimestamp(EndPublish);
	}

	public void setEndPublish(String endPublish) {
		EndPublish = endPublish;
	}

	public String getTarget() {
		return Target;
	}

	public void setTarget(String target) {
		Target = target;
	}

	public boolean isEncrypted() {
		return Encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		Encrypted = encrypted;
	}

	public boolean isValid() {
		return (Kind.length() > 0);
	}

	static public MuAsset create(String data) {
		MuAsset o = null;
		try {
			o = new Gson().fromJson(data, MuAsset.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new MuAsset();
		}
		return o;
	}

	static public ArrayList<MuAsset> createArray(String data) {
		ArrayList<MuAsset> o = null;
		try {
			Type collectionType = new TypeToken<ArrayList<MuAsset>>() {
			}.getType();
			o = new Gson().fromJson(data, collectionType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new ArrayList<MuAsset>();
		}
		return o;
	}
}
