package de.akadvh;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
public class QueryString {

	private Map<String, String> options = new HashMap<String, String>();
	
	/**
	 * 
	 * Option zum QueryString hinzufügen
	 * key=value
	 * 
	 * @param key	Schlüssel
	 * @param value	Wert
	 */
	public void addOption(String key, String value) {
		
		options.put(key, value);
		
	}
	
	/**
	 * 
	 * QueryString erstellen und zurückgeben
	 * 
	 * @return String	QueryString
	 * @throws UnsupportedEncodingException
	 */
	public String getQueryString() throws UnsupportedEncodingException {
	
		StringBuffer sb = new StringBuffer();
		
		for( Map.Entry<String, String> e : options.entrySet() )
		{
		  sb.append(e.getKey());
		  sb.append("=");
		  sb.append(URLEncoder.encode(e.getValue(), "UTF-8" ));
		  sb.append("&");
		}
	
		return sb.toString();
		
	}
	
}
