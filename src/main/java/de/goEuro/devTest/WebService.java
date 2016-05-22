package de.goEuro.devTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class WebService {
	
	public String callWebService(String myURL) throws MalformedURLException, IOException {
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
        }  catch (IOException e) {
			throw new IOException("Exception while calling URL:"+ myURL, e);
		} 
 
		return sb.toString();
	}
 
}
