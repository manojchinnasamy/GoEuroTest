package de.goEuro.devTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonCsvOps {
	public JSONArray processJsonForCsv(String jsonString) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
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
	public  void writeToCsv(JSONArray jsonArray,String cityName ) throws IOException{
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
	
}
