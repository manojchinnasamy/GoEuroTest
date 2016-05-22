/**
 * 
 */
package de.goEuro.devTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConstants.Field;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;

/**
 * @author Manoj Kumar Chinnasamy
 * @version 1.0
 *
 */
public class GoEuroTest {
	
	public static void main(String[] args) throws IOException, UnknownHostException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		/*
		 * Gets argument args[0] from user and stores in cityName and process the Webservice to get JSONArray.
		 * Converts the jsonArray received from callWebService() and pass it to processJsonForCsv() and stores Json Array in string arraytoCSV. 
		 * Calls the function writeToCsv() with arraytoCSV parameter and writes the processed result to CSV file.
		 */
		
		String cityName = null;
		JSONArray arraytoCSV ;
		String jsonArray = null;
		if (args.length != 0){
			cityName = args[0];
			jsonArray = callWebService("http://api.goeuro.com/api/v2/position/suggest/en/"+cityName);
			if(jsonArray.length() <= 2){
				System.out.println("Returned a null response. Please check the input City name and try again");

			}else{

				arraytoCSV = processJsonForCsv(jsonArray);
				writeToCsv(arraytoCSV,cityName);
			}
		}else{
			System.out.println("Please enter the city to process");
		}
	}
	
	public static JSONArray processJsonForCsv(String jsonString) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		/*
		 * Function to process the JSON ARRAY received from webservice
		 * to save only specific fields based on the requirement to save in CSV format
		 * 
		 */
		

		try {  

			JSONArray jsonArray = new JSONArray(jsonString);
			for(int i=0; i<jsonArray.length();i++){
				JSONObject geo_pos =  (JSONObject)(jsonArray.getJSONObject(i).getJSONObject("geo_position"));
			       jsonArray.getJSONObject(i).put("latitude", geo_pos.get("latitude"));
			       jsonArray.getJSONObject(i).put("longitude", geo_pos.get("longitude"));
			       jsonArray.getJSONObject(i).remove("geo_position");
				   jsonArray.getJSONObject(i).remove("key");
			       jsonArray.getJSONObject(i).remove("fullName");
			       jsonArray.getJSONObject(i).remove("iata_airport_code");
			       jsonArray.getJSONObject(i).remove("country");
			       jsonArray.getJSONObject(i).remove("locationId");
			       jsonArray.getJSONObject(i).remove("inEurope");
			       jsonArray.getJSONObject(i).remove("countryId");
			       jsonArray.getJSONObject(i).remove("countryCode");
			       jsonArray.getJSONObject(i).remove("coreCountry");
			       jsonArray.getJSONObject(i).remove("distance");
			       jsonArray.getJSONObject(i).remove("names");
			       jsonArray.getJSONObject(i).remove("alternativeNames");
			}  
			return jsonArray;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
 public static void writeToCsv(JSONArray jsonArray,String cityName ) throws IOException{
	/*
	 * Process the Json Array and writes to CSV file(comma separated values)
	 * Gets the value of current directory and saves the csv file in the format of cityName_timestamp.csv
	 */
	 final String currentWorkingDir = System.getProperty("user.dir");
	 String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
	 final String fileName = currentWorkingDir+"/"+cityName+"_"+timeStamp+".csv";
	    File file=new File(fileName);
		String csv = CDL.toString(jsonArray);
        FileUtils.writeStringToFile(file, csv);
        System.out.println("CSV file saved successfully in the path:"+file);
 
 }
	public static String callWebService(String myURL) throws MalformedURLException {
		/*
		 * Uses java.net.URLConnection to retrieve JSON response as JSONARRAY from the GoEuro API and stores in a string variable.
		 * 
		 */
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		}catch (UnknownHostException me) {
            System.err.println("UnknownHostException while calling: " + me);
        } catch (MalformedURLException me) {
            System.err.println("MalformedURLException while calling: "  + myURL);
        }  catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		} 
 
		return sb.toString();
	}
 
}