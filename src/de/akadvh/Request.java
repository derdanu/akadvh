package de.akadvh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Daniel Falkner
 * 
 * This file is part of Akadvh.
 * 
 * Akadvh is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Akadvh is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Akadvh. If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class Request {

	private URL baseUrl;
	private String serverCookies;
	
	public Request() throws MalformedURLException {
		
		this.baseUrl = new URL("https://www.akadvh.de");
		this.serverCookies = new String();
	}
	
	public String Post(String uri, String postVar) throws IOException {
		
		URL url = new URL(this.baseUrl + uri);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; pl; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2");
		connection.setRequestMethod( "POST" );
		connection.setInstanceFollowRedirects(false);
		connection.setDoInput( true );
		connection.setDoOutput( true );
		connection.setUseCaches( false );
		connection.setRequestProperty( "Content-Type",
		                               "application/x-www-form-urlencoded" );
		connection.setRequestProperty( "Content-Length", String.valueOf(postVar.length()) );

		
		
		OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
		writer.write( postVar );
		writer.flush();


		BufferedReader reader = new BufferedReader(
		                          new InputStreamReader(connection.getInputStream()) );

		
		StringBuffer responseBuff = new StringBuffer();	
		for ( String line; (line = reader.readLine()) != null; )
		{
		  responseBuff.append(line);
		  responseBuff.append("\r");
		}

		writer.close();
		reader.close();
	
		CookieParser(connection);
		
		return responseBuff.toString();
		
	}
	
	public String Get(String uri) throws IOException {
		
		URL url = new URL(this.baseUrl + uri);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod( "GET" );
		connection.setDoInput( true );
		connection.setDoOutput( true );
		connection.setInstanceFollowRedirects(false);
		if (!serverCookies.isEmpty()) connection.addRequestProperty("Cookie", serverCookies);
		
		OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
		writer.flush();


		BufferedReader reader = new BufferedReader(
		                          new InputStreamReader(connection.getInputStream()) );

		
		StringBuffer responseBuff = new StringBuffer();	
		for ( String line; (line = reader.readLine()) != null; )
		{
		  responseBuff.append(line);
		  responseBuff.append("\r");
		}

		writer.close();
		reader.close();
		
		return responseBuff.toString();
	}
	
	private void CookieParser(HttpURLConnection connection) {
		
	    serverCookies = connection.getHeaderField("set-cookie");
		
	    if(serverCookies != null){
	        
	        serverCookies += "; ";
	    
	        Pattern p = Pattern.compile("wosid=(\\w*);");
		
		    Map<String, List<String>> headers = connection.getHeaderFields();
		    for (Entry<String, List<String>> e : headers.entrySet()) {
		    	
		        Matcher m = p.matcher(e.getValue().toString());
		    	while (m.find()) {
		 
		    		  serverCookies += m.group();
		    	}
		    }
	    
	    }
	}
}
