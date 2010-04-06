import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import json.JSONException;
import json.JSONObject;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class MakeRequest {
	
	public static JSONObject parseResponse(HttpResponse response) throws IllegalStateException, IOException, JSONException{
		
		HttpEntity entity = response.getEntity();
		
	    if (entity != null) {
	
	        // Read the content stream
	        InputStream instream        = entity.getContent();
	        Header      contentEncoding = response.getFirstHeader("Content-Encoding");
	
	        // convert content stream to a String
	        String resultString = convertStreamToString(instream);
	      //  Logger.getLogger("YahooMail").warning("raw response recieved is " +resultString);
	   	 
	        instream.close();
	//      resultString = resultString.substring(1, resultString.length() - 1);    // remove wrapping "[" and "]"
	     //   System.out.println("ResultString is " + resultString);
	
	        // Transform the String into a JSONObject
	        JSONObject jsonObjRecv = new JSONObject(resultString);
	
	        return jsonObjRecv;
	    }
	    
		return null;
	}
	
	private static String convertStreamToString(InputStream is) {

	        /*
	         * To convert the InputStream to String we use the BufferedReader.readLine()
	         * method. We iterate until the BufferedReader return null which means
	         * there's no more data to read. Each line will appended to a StringBuilder
	         * and returned as String.
	         *
	         * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android
	         */
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder  sb     = new StringBuilder();
	        String         line   = null;

	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        return sb.toString();
	    }
	
}
