package dk.tbertelsen.android.castdr.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicReference;

import android.util.Log;

abstract public class DrApi {
	abstract protected void update();
	
	private String TAG = this.getClass().getSimpleName();
	
	public static String CACHEPATH = null;
	
	private AtomicReference<String> rawData = new AtomicReference<String>();
	
	private String path = null;
	private String url;
	
	private int responceCode = HttpURLConnection.HTTP_OK;
	private String errorMessage = null;
	private String errorResponce = null;

	private String mServerUrl = "http://www.dr.dk/mu-online/api/";
	private String mApiVersion = "1.2";
	
	@SuppressWarnings("unused")
	private String mContentType = "application/json";
	private String mCharset = "UTF-8";
	
	public DrApi(boolean p) {
		if (p) {
			path = p+System.getProperty("file.separator");			
		}
	}
	
	public DrApi() {
		path = CACHEPATH+System.getProperty("file.separator");
	}
	
	public DrApi(String data) {
		setRawData(data);
	}

	private String getServerRootUrl() {
		return mServerUrl + mApiVersion + "/";
	}
	
	private void setRawData(String a) {
		rawData.getAndSet(a);
	}
	
	protected String getRawData() {
		return rawData.get();
	}

	private void reset() {
		setRawData(null);
		responceCode = HttpURLConnection.HTTP_OK;
		errorMessage = null;
		errorResponce = null;
	}
	
	public void load() {
		loadGet();
		update();
	}
	
	public String getErrorMessage() {
		if (responceCode != HttpURLConnection.HTTP_OK) {
			return errorMessage;
		} else {
			return "";
		}
	}
	
	public String getErrorResponce() {
		if (responceCode != HttpURLConnection.HTTP_OK) {
			if (errorResponce != null) {
				return errorResponce;
			} else {
				return "Unknown";
			}
		} else {
			return "";
		}
	}
	
	public boolean isError() {
		if (responceCode != HttpURLConnection.HTTP_OK) {
			return true;
		} else {
			return false;
		}
	}
	
	private void loadGet() {
		reset();
        HttpURLConnection c = null;
		try {	
			URL url = new URL(this.url);

			Log.i(TAG, "Downloading: " + url.toString());
			c = (HttpURLConnection) url.openConnection();
	        
//	        c.setRequestProperty ("Authorization", getAuth());
	        
	        c.setRequestMethod("GET");
	        c.setDoInput(true);
//	        c.setDoOutput(true);
	        c.setUseCaches(false);
	        
	        // Make server believe we are form data...
//	        c.setRequestProperty("Content-Type", "application/json");
//	        DataOutputStream out = new DataOutputStream (c.getOutputStream ());
	        // Write out the bytes of the content string to the stream.
//	        out.writeBytes(this.buildRequest());
//	        out.flush();
//	        out.close();
	        c.connect();
	        responceCode = c.getResponseCode();
	        /*
	    	if (status != HttpURLConnection.HTTP_OK) {
	    		if (status == HttpURLConnection.HTTP_MOVED_TEMP
	    			|| status == HttpURLConnection.HTTP_MOVED_PERM
	    				|| status == HttpURLConnection.HTTP_SEE_OTHER)
	    	}
	     */
        
			String responce = convertStreamToString(c.getInputStream());
			saveCache(responce);
			setRawData(responce);
		} catch (Exception e) {
			readErrorResponce(e, c);
		}
	}
	
	protected String loadPut(String u, String data) {
		reset();
        HttpURLConnection c = null;
        String responce = null;
		try {
			URL url = new URL(getServerRootUrl() + u);
			Log.i(TAG, "Sending: " + url.toString());
			Log.i(TAG, "Data: " + data);
			c = (HttpURLConnection) url.openConnection();
	        
//	        c.setRequestProperty ("Authorization", getAuth());
	        c.setRequestProperty("Content-Type", "application/json");
	        
	        c.setRequestMethod("PUT");
	        c.setDoInput(true);
	        c.setDoOutput(true);
//	        c.setUseCaches(false);
	        
	        OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());
        	out.write(data);
        	out.flush();
        	out.close();
	        
	        // Make server believe we are form data...
//	        DataOutputStream out = new DataOutputStream (c.getOutputStream ());
	        // Write out the bytes of the content string to the stream.
//	        out.writeBytes(this.buildRequest());
//	        out.flush();
//	        out.close();
	        c.connect();
			
			responce = convertStreamToString(c.getInputStream());
		} catch (Exception e) {
			readErrorResponce(e, c);
		}
        return responce;
	}
	
	private void readErrorResponce(Exception e, HttpURLConnection c) {
		responceCode = -1;
		errorResponce = "Unknown";
		if (c != null) {
			InputStream stream = c.getErrorStream();
			if (stream!=null) {
				try {
					errorResponce = convertStreamToString(c.getErrorStream());
				} catch (NullPointerException e1) {
					e1.printStackTrace();
				}
				try {
					responceCode = c.getResponseCode();
					errorMessage = c.getResponseMessage();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				switch (responceCode) {
					case HttpURLConnection.HTTP_BAD_REQUEST:
						if (errorMessage == null) {
							errorMessage = "HTTP_BAD_REQUEST";
						}
						break;
					case HttpURLConnection.HTTP_UNAUTHORIZED:
						if (errorMessage == null) {
							errorMessage = "HTTP_UNAUTHORIZED";
						}
						break;
					case HttpURLConnection.HTTP_NOT_FOUND:
						if (errorMessage == null) {
							errorMessage = "HTTP_NOT_FOUND";
						}
						break;
					case HttpURLConnection.HTTP_INTERNAL_ERROR:
						if (errorMessage == null) {
							errorMessage = "HTTP_INTERNAL_ERROR";
						}
					default:
						Log.i(TAG, "responceCode: " + responceCode);
						Log.i(TAG, "errorMessage: " + errorMessage);
						Log.i(TAG, "errorResponce: " + errorResponce);
				}
				return;
			}
		}
		errorMessage = e.getMessage();
		Log.e(TAG, "responceCode: "+responceCode);
		Log.e(TAG, "errorMessage: "+errorMessage);
	}

	
	protected void setUrl(String url) {
		this.url = getServerRootUrl()+url;
	}
	
    protected String convertStreamToString(InputStream is) throws NullPointerException {
    	if (is != null) {
        	BufferedReader reader;
    		try {
    			reader = new BufferedReader(new InputStreamReader(is, mCharset),8*1024);
    		} catch (UnsupportedEncodingException e1) {
    			reader = new BufferedReader(new InputStreamReader(is),8*1024);
    		}
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
            	Log.e(TAG, "IOException", e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                	Log.e(TAG, "IOException", e);
                }
            }
            if (sb.length()>0) {
            	return sb.toString();
            }
    	} else {
    		throw new NullPointerException() ;
    	}
        return null;
    }
    
    public int getResponceCode() {
    	return responceCode;
    }
    
    private void saveCache(String data) {
    	if (data!=null && path!=null) {
    		String filename = md5(this.url);
    		if (filename!=null) {
            	try {
            		FileOutputStream outputStream = new FileOutputStream(path+filename+".txt");
	          		OutputStreamWriter out = new OutputStreamWriter(outputStream);
	          		out.write(data);
					out.close();
					Log.i(TAG, "saveCache: Sucess");
          		} catch (IOException e) {
            		Log.e(TAG, "Failed to save: "+this.url+" ("+path+filename+".txt)");
          		}
    		}
    	}
    }
    
	protected void readCache() {
    	reset();
    	if (path!=null) {
    		File file = new File(path+md5(this.url)+".txt");
     		if (file.exists()) {
     	    	// try opening the filename.txt
    	    	try {
    	    		FileInputStream inputStream = new FileInputStream(file.getPath());
    	    		setRawData(convertStreamToString(inputStream));
    	    		inputStream.close();
    			} catch (Exception e) {
            		Log.e(TAG, "Cache: Failed to load: "+this.url+" ("+file.getPath()+")");
    			}
    		} else {
    			Log.i(TAG, "Cache: Failed to load: "+this.url+" ("+file.getPath()+")");
    		}
    	}
    	update();
    }
    
    public String md5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        
        StringBuilder hex = new StringBuilder(hash.length * 2);
        
        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10) hex.append('0');
            hex.append(Integer.toHexString(i));
        }

        return hex.toString();
    }
}
