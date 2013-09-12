package de.akadvh;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

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
public class Akadvh {

	private static final String version = "0.3";
	
	private String loginQueryString;
	private ResponseParser resP = new ResponseParser();
	private Request request = new Request();
	
	
	public Akadvh(AkadvhCredentials ac) throws UnsupportedEncodingException, MalformedURLException {
		
		this.loginQueryString = ac.getLoginQueryString();

	}
	
	
	public Module holeModule() throws Exception {
	
		String r = getFirstModulPage();
	
		List<String> urls = resP.parseModulePages(r); 
		List<Modul> moduleL = resP.parseModuleTable(r);
		
		for (String url: urls) {
			r = request.Get(url);
			moduleL.addAll(resP.parseModuleTable(r));
		}
		
		Module module = new Module();
		
		for (Modul m: moduleL) {
			module.addModul(m);		
		}
		
		return module;
				    

	}
	
	public Modul holeNoten(Modul modul) throws Exception {
		
		String r = getFirstModulPage();
		List<String> urls = resP.parseModulePages(r); 
		String uri = null;
		
		try {
			uri = resP.parseModuleTableFindModulUrl(r, modul);
		} catch (Exception e) {

			for (String url: urls) {
				r = request.Get(url);
				try {
					uri = resP.parseModuleTableFindModulUrl(r, modul);
					break;
				} catch (Exception e1) {
					// todo
				}
			}

		}
		
		r = request.Get(uri);
		
		try {
			uri = resP.parseHTMLLinkWithImage(r, "navi3_noten.gif");
		} catch (Exception e) {
			throw e;
		}

		r = request.Get(uri);

		uri = resP.parseHTMLiFrameSrc(r, "iframe");
		
		r = request.Get(uri);
		
		modul.setNoten(resP.parseModuleNoten(r));
		
		return modul;
		
		
	}
	
	private String getFirstModulPage() throws Exception {
		
		String r = request.Get("/cgi/WebObjects.dll/AKADFrontend");
				
		String loginUri = resP.parseHTMLFormActionUrl(r);
		
		String frameUri = null;

		try {
			r = request.Post(loginUri, loginQueryString);
			frameUri = resP.parseHTMLFrameSrc(r, "AKAD_inhalt");			
		} catch (Exception e) {
			throw new Exception("Benutername und/oder Passwort falsch");
		}


		r = request.Get(frameUri);
		frameUri = resP.parseHTMLFrameSrc(r, "AKAD_header");
			 
		r = request.Get(frameUri);
		
		try {
			frameUri = resP.parseHTMLLinkWithImage(r, "navi1_lernraum.gif");
		} catch (Exception e) {
			throw e;
		}
		
		r = request.Get(frameUri);
		frameUri = resP.parseHTMLFrameSrc(r, "AKAD_fcontent");

		return  request.Get(frameUri);
	}


	public static String getVersion() {
		return version;
	}
}
