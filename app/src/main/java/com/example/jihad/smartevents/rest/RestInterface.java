package com.example.jihad.smartevents.rest;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestInterface {
	public static String get(String urlString, Map<String, String> parameters){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			urlString += "?";

			List<String> parameterNames = new ArrayList<String>(parameters.keySet());
			for(int i=0;i<parameterNames.size()-1;i++){
				String parameterName = parameterNames.get(i);
				urlString += parameterName + "=" + parameters.get(parameterNames.get(i)) + "&";
			}

			urlString += parameterNames.get(parameterNames.size()-1) + "=" + parameters.get(parameterNames.get(parameterNames.size()-1));

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();


			System.out.println("conn.setRequestMethod : " + conn.getRequestMethod());

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

	public static String post(String urlString, Map<String, String> parameters){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			System.out.println("conn.setRequestMethod : " + conn.getRequestMethod());



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
}
