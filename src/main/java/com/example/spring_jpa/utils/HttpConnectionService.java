package com.example.spring_jpa.utils;

import com.example.spring_jpa.api.v1.EquipController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Component
public class HttpConnectionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionService.class);

    HttpURLConnection conn = null;
    public HttpURLConnection getHttpURLConnection (String strUrl, String method ) {
		URL url;

		try {

			url = new URL(strUrl);

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("MalformedURLException !!");
		} catch(IOException e) {
			LOGGER.error("IOException !!");
		}

        return conn;
	}

    public String getHttpRespons ( HttpURLConnection con ) {
		StringBuilder sb = null;

		try {

			if ( con.getResponseCode() == 200 ) {

				sb = readResponseData ( con.getInputStream() );

			}
			else {
				System.out.println(conn.getResponseCode());
				System.out.println(conn.getResponseMessage());
				System.out.println(conn.getErrorStream());

				return null;
			}
		} catch ( IOException e ) {
			LOGGER.error("HttpConnectionService getHttpRespons IOException !! ");
		} finally {
			con.disconnect();
		}

		if ( sb == null ) return null;

		return sb.toString();

	}

    public StringBuilder readResponseData (InputStream in) throws IOException {

		if ( in == null ) return null;

		StringBuilder sb = new StringBuilder();
		String line = "";
		InputStreamReader ir = null;
		BufferedReader br = null;

		try {
			ir = new InputStreamReader(in);
			br = new BufferedReader(ir);
			while( (line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("HttpConnectionService readResponseData IOException  !! ");
		} finally {
			if ( ir != null ) ir.close();
			if ( br != null ) br.close();
		}

		return sb;
	}
}
