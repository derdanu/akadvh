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

	private static final String version = "0.7";
	
	private String loginQueryString;
	private ResponseParser resP = new ResponseParser();
	private Request request = new Request();
	
	
	/**
	 * 
	 * Konstruktor
	 * 
	 * @param ac AkadvhCredentials
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 */
	public Akadvh(AkadvhCredentials ac) throws UnsupportedEncodingException, MalformedURLException {
		
		this.loginQueryString = ac.getLoginQueryString();

	}
	
	
	/**
	 * 
	 * Module holen
	 * 
	 * @return Module
	 * @throws Exception
	 */
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
	
	/**
	 * 
	 * Noten für ein Modul holen 
	 * 
	 * @param modul Modul
	 * @return Modul
	 * @throws Exception
	 */
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
	
	/**
	 * 
	 * Terminübersicht holen
	 * 
	 * @return String Base64 Codierter Binary String des Excel Dokuments
	 * @throws Exception
	 */
	public String holeTerminUebersicht() throws Exception {
		
		String r = getFrameAfterLogin();
		String frameUri = null;
		
		try {
			frameUri = resP.parseHTMLLinkWithImage(r, "navi1_lernraum.gif");
		} catch (Exception e) {
			throw e;
		}
		
		r = request.Get(frameUri);
		frameUri = resP.parseHTMLFrameSrc(r, "AKAD_header");

		r = request.Get(frameUri);
		
		try {
			frameUri = resP.parseHTMLLinkWithImage(r, "termine_o.gif");
		} catch (Exception e) {
			throw e;
		}
		
		r = request.Get(frameUri);
		frameUri = resP.parseHTMLFrameSrc(r, "AKAD_fcontent");
		
		r = request.Get(frameUri);
		String postUri = resP.parseHTMLFormActionUrl(r);

		List<String> cbNames = resP.parseHTMLCheckboxNames(r);
		String submitName = resP.parseHTMLSubmitName(r);
		
		QueryString qs = new QueryString();
		qs.addOption("allbox", "on");
		qs.addOption(submitName, "Terminübersicht erstellen");
		
		for (String s: cbNames) {
			qs.addOption(s, s);
		
		}
		
		r = request.Post(postUri, qs.getQueryString());
		
		postUri = resP.parseHTMLFormActionUrl(r);
		List<String> selectName = resP.parseHTMLSelectNames(r);
		String filterName = resP.parseHTMLInputWithImageName(r, "button_filtern.gif");
		
		qs = new QueryString();
		
		for (String s: selectName) {
			
			//3 Ort
			if (s.endsWith("3")) qs.addOption(s, "WONoSelectionString");
			//7 Typ
			if (s.endsWith("7")) qs.addOption(s, "WONoSelectionString");
			//9 Status
			if (s.endsWith("9")) qs.addOption(s, "2");// 2 Angmeldet
			
		}
		
		qs.addOption(filterName + ".x", "51");
		qs.addOption(filterName + ".y", "3");
		
		r = request.Post(postUri, qs.getQueryString());

		List<String> downloadUrls = resP.parseHTMLLinksWithTarget(r, "filedownload");
		
				
		return request.Get(downloadUrls.get(1)); // 0 = Pdf
				
	}
	
	/**
	 * 
	 * Erste Moduleseite
	 * 
	 * @return String	Htmlcode
	 * @throws Exception
	 */
	private String getFirstModulPage() throws Exception {
		
		String r = getFrameAfterLogin();
		String frameUri = null;
		
		try {
			frameUri = resP.parseHTMLLinkWithImage(r, "navi1_lernraum.gif");
		} catch (Exception e) {
			throw e;
		}
		
		r = request.Get(frameUri);
		frameUri = resP.parseHTMLFrameSrc(r, "AKAD_fcontent");

		return  request.Get(frameUri);
	}

	/**
	 * 
	 * Frame nach Einloggen zurückgeben.
	 * Einloggen etc wird hier erledigt.
	 * 
	 * @return String 	Htmlcode
	 * @throws Exception
	 */
	private String getFrameAfterLogin() throws Exception {
		
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
			 
		return request.Get(frameUri);
		
		
	}

	/**
	 * 
	 * Gibt die Version der Library zurück
	 * 
	 * @return String	Versionsnummer
	 */
	public static String getVersion() {
		return version;
	}
}
