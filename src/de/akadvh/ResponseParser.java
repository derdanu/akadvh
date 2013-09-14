package de.akadvh;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class ResponseParser {

	/**
	 * 
	 * Gibt die erste Formular Aktion Uri zurück.
	 * 
	 * @param html
	 * @return String 	Uri
	 */
	public String parseHTMLFormActionUrl(String html) {
		
	    Document doc = Jsoup.parse(html);

	    Element Form = doc.select("form").first();
	    String FormActionUrl = Form.attr("action");

	    return FormActionUrl; 
	    
	}
	
	/**
	 * 
	 * Src Uri eines Frames von einem Frameset zurückgeben.
	 * 
	 * @param html
	 * @param framename Name des Frames Html Attribut name
	 * @return	String	Uri
	 */
	public String parseHTMLFrameSrc(String html, String framename) {
		
	    Document doc = Jsoup.parse(html);
		
		Element element = doc.select("frame[name=" + framename + "]").first();
	    String inhaltUrl = element.attr("src");

	    return inhaltUrl;
	    
	}
	
	/**
	 * 
	 * Src Uri eines IFrames zurückgeben
	 * 
	 * @param html	
	 * @param iframename	Name des IFrames Html Attribut name
	 * @return	String 	Uri
	 */
	public String parseHTMLiFrameSrc(String html, String iframename) {
		
	    Document doc = Jsoup.parse(html);
		
		Element element = doc.select("iframe[name=" + iframename + "]").first();
	    String inhaltUrl = element.attr("src");

	    return inhaltUrl;
	    
	}
	
	/**
	 * 
	 * Uri Liste aller Hyperlinks mit einem Html target Arrtibut zurückgeben.
	 * 
	 * @param html
	 * @param target	Name des a target Namen
	 * @return	List<String>	Liste mit Uri
	 */
	public List<String> parseHTMLLinksWithTarget(String html, String target) {
		
		List<String> links = new ArrayList<String>();
				
	    Document doc = Jsoup.parse(html);
		
		Elements elements = doc.select("a[target=" + target + "]");
		
		for (Element e: elements) {
			links.add(e.attr("href"));
		}
		
	    return links;
	    
	}
	
	/**
	 * 
	 * Uri eines Links mit einem Bild zurückgeben
	 * 
	 * @param html
	 * @param image	Name des Bildes
	 * @return	String	Uri
	 * @throws Exception
	 */
	public String parseHTMLLinkWithImage(String html, String image) throws Exception {
		
	    Document doc = Jsoup.parse(html);

        Elements links = doc.select("a");

        for (Element e : links) {
               
        	if (e.html().contains(image)) {
        		return e.attr("href");
        	}

        }
        
        throw new Exception("Link nicht gefunden");
	}
	
	/**
	 * 
	 * Html Input Attribute mit Typ gewissem image zurückgeben.
	 * 
	 * @param html
	 * @param image	Name des Bildes
	 * @return	String Uri
	 * @throws Exception
	 */
	public String parseHTMLInputWithImageName(String html, String image) throws Exception {
		
	    Document doc = Jsoup.parse(html);

        Elements links = doc.select("input[type=image]");

        for (Element e : links) {
               
        	if (e.attr("src").contains(image)) {
        		return e.attr("name");
        	}

        }
        
        throw new Exception("Name nicht gefunden");
	}
	
	/**
	 * 
	 * Liste mit Checkbox Namen zurückgeben
	 * 
	 * @param html
	 * @return	List<String>	Alle Attribute vom Typ namen
	 */
	public List<String> parseHTMLCheckboxNames(String html) {
		
		List<String> cbNames = new ArrayList<String>();
		
		Document doc = Jsoup.parse(html);
		
		Elements cb = doc.select("input[type=checkbox]");
		
		for (Element e: cb) {
			
			cbNames.add(e.attr("name"));
			
		}
		
		return cbNames;
	}
	
	
	/**
	 * 
	 * Liste mit HTML Select namen zurückgeben
	 * 
	 * @param html
	 * @return List<String>  Alle Attribute vom Typ namen
	 */
	public List<String> parseHTMLSelectNames(String html) {
		
		List<String> selectNames = new ArrayList<String>();
		
		Document doc = Jsoup.parse(html);
		
		Elements select = doc.select("select");
		
		for (Element e: select) {
			
			selectNames.add(e.attr("name"));
			
		}
		
		return selectNames;
	}
	
	/**
	 * 
	 * Namen des ersten Inputs vom Typ Submit zurückgeben. 
	 * 
	 * @param html
	 * @return	String Name
	 */
	public String parseHTMLSubmitName(String html) {
		
		Document doc = Jsoup.parse(html);
		
		Element cb = doc.select("input[type=submit]").first();
		
		return cb.attr("name");

	}
	
	/**
	 * 
	 * Modul Seite parsen und alle Link Uri zurückgeben
	 * 
	 * @param html
	 * @return List<String> Uri
	 */
	public List<String> parseModulePages(String html) {
		
		Document doc = Jsoup.parse(html);

        Elements links = doc.select("span[class=content] a");

        List<String> urls = new ArrayList<String>();
        
        for (Element e: links) {
        	
        	if (e.html().startsWith("<")) break;
        	
        	urls.add(e.attr("href"));
        	
        }
        
        return urls;
        
	}
	
	/**
	 * 
	 * Modul Tabelle Parsen und Liste mit Modul zurückgeben.
	 * 
	 * @param html
	 * @return	List<Modul> Liste mit Modulen auf dieser Seite
	 */
	public List<Modul> parseModuleTable(String html) {
	    
		Document doc = Jsoup.parse(html);

        Elements table = doc.select("table[width=700] td");

        int i = 1;
        String key = null;
        List<Modul> module = new ArrayList<Modul>();
        
        for (Element e : table) {
        	if (!(e.html().startsWith("<") || e.html().isEmpty())) {
            	if (i % 2 == 0) {
            		module.add(new Modul(key, Jsoup.parse(e.html()).text()));
            	} else {
            		key = e.html();
            	}
            	i++;
            }

        }
        
        return module;
	}
	
	/**
	 * 
	 * Modul Uri zu einem Modul zuückgeben
	 * 
	 * @param html
	 * @param modul	Modul
	 * @return String Uri
	 * @throws Exception
	 */
	public String parseModuleTableFindModulUrl(String html, Modul modul) throws Exception {
		
		Document doc = Jsoup.parse(html);
        Elements table = doc.select("table[width=700] td");

        String url = null;
        
        for (Element e : table) {
        	if (e.html().startsWith("<a")) url = e.html();

        	if (modul.getName().equals(e.html())) {

        		doc = Jsoup.parse(url);
        		Element link = doc.select("a").first();
        		
        		return link.attr("href");
        	}
        }

        throw new Exception("Link nicht gefunden");

	}
	
	/**
	 * 
	 * Noten zu einem Modul parsen
	 * 
	 * @param html
	 * @return Noten
	 */
	public Noten parseModuleNoten(String html) {
		
		Document doc = Jsoup.parse(html);
        Elements table = doc.select("table tr");

        Noten noten = new Noten();
        int i=0;
        for (Element e : table) {
        	if (i > 0) {
        		Elements td = e.select("td");
        		int j = 0;
        		Note note = new Note();
        		
        		for (Element l : td) {
        			switch (j) {
        				case 0:
        					note.setBezeichnung(Jsoup.parse(l.html()).text());
        					break;
        				case 1:
        					note.setDatum(l.html());
        					break;
        				case 2:
        					note.setAnmerkung(l.html());
        					break;
        				case 3:
        					note.setNote(l.html());
        					break;
        			}
        			j++;	
        		}
        			
        		noten.addNote(note);
        	}
        	
        	i++;
        }
		return noten;

	}
}
