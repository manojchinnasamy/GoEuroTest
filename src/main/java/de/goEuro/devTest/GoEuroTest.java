/**
 * 
 */
package de.goEuro.devTest;

import java.io.IOException;
import java.net.UnknownHostException;
import org.json.JSONArray;

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
		WebService webServiceObject = new WebService();
		JsonCsvOps JsonCsvObject = new JsonCsvOps();
		if (args.length != 0 && args != null){
			
			cityName = args[0];
			jsonArray = webServiceObject.callWebService("http://api.goeuro.com/api/v2/position/suggest/en/"+cityName);
			if(jsonArray.length() <= 2){
				System.out.println("Returned a null response. Please check the input City name and try again");

			}else{

				arraytoCSV = JsonCsvObject.processJsonForCsv(jsonArray);
				JsonCsvObject.writeToCsv(arraytoCSV,cityName);
			}
		}else{
			System.out.println("Please enter the city to process");
		}
	}
}