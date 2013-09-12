package de.akadvh;

import java.io.UnsupportedEncodingException;

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
public class AkadvhCredentials {

	private String user;
	private String pass;
	
	public AkadvhCredentials(String user, String pass) {
		
		this.user = user;
		this.pass = pass;
	
	}
	
	public String getLoginQueryString() throws UnsupportedEncodingException {
		
		QueryString qs = new QueryString();
		qs.addOption("nickname", this.user);
		qs.addOption("password", this.pass);
		qs.addOption("AnmeldenDeutsch.x", "46");
		qs.addOption("AnmeldenDeutsch.y", "2");
		qs.addOption("language", "de");
		    
		return qs.getQueryString();
	}
	
}
