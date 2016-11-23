package com.example.jihad.smartevents.rest;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RESTInterface {


	public static String get(String urlString, Map<String, String> parameters){
		return sendRequest(urlString, "GET", parameters);
	}

	public static String post(String urlString, Map<String, String> parameters){
		return sendRequest(urlString, "POST", parameters);
	}

	public static String sendRequest(String urlString, String method, Map<String, String> parameters){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", "application/json");
			
			

			String input = "{";

			List<String> keys = new ArrayList<>(parameters.keySet());
			
			for(int i=0;i<keys.size()-1;i++){
				String key = keys.get(i);
				input += "\"" + key + "\":\"" + parameters.get(key) + "\", ";
			}
			String lastKey = keys.get(keys.size()-1);
			input += "\"" + lastKey + "\":\"" + parameters.get(lastKey) + "\"}";
			
			System.out.println("Input : " + input);
			
			// "{\"email\":\"dureyantonin@gmail.com\",\"password\":\"azerty01\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output = "", line;
			while ((line = br.readLine()) != null) {
				output += line;
			}

			conn.disconnect();
			
			return output;

		} catch (Exception e) {
			
			e.printStackTrace();

			return null;
		}
	}
/*
	public static void main(String[] args) {

		Map<String, String> parameters = new HashMap<>();
		parameters.put("email", "dureyantonin@gmail.com");
		parameters.put("password", "azerty01");
		
		System.out.println(post("http://localhost:8081/connection", parameters));

	}
	*/

}
