package de.akadvh.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import de.akadvh.Akadvh;
import de.akadvh.AkadvhCredentials;
import de.akadvh.Modul;
import de.akadvh.Module;
import de.akadvh.Note;

/**
 * 
 * @author Daniel Falkner
 * 
 * This file is part of Akadvh ConsoleView.
 * 
 * Akadvh ConsoleView is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Akadvh ConsoleView is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Akadvh ConsoleView. If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class ConsoleView {

	private String username;
	private String password;
	private String modulname;
	private Scanner sc;
	private Akadvh vh = null;
	private Boolean verbose; 
	private TodoEnum todo;

	public ConsoleView(String username, String password, String modulname, Boolean noten, Boolean termin, Boolean verbose) {

		this.username = username;
		this.password = password;
		this.modulname = modulname;
		this.verbose = verbose;
		
		sc = new Scanner(System.in); 

		if (username == null) {
			System.out.println("Benutername: ");
			this.username = sc.nextLine(); 
		}

		if (password == null) {
		    System.out.println("Passwort: ");
		    this.password = sc.nextLine(); 
		}
		
		
	    try {
			vh = new Akadvh(new AkadvhCredentials(this.username, this.password));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    

	    
	    if (modulname == null && noten == false && termin == false) {
		    System.out.println("M für Modul, N für Notenübersicht, T für Terminübersicht: ");
		    String todoInput = sc.nextLine();
		    this.todo = TodoEnum.getEnum(todoInput); 	
	    } else if (noten == true) {
	    	todo = TodoEnum.Notenuebersicht;
	    } else if (termin == true) {
	    	todo = TodoEnum.Terminuebersicht;
	    } else {
	    	todo = TodoEnum.Modul;
	    }
		
	    switch (todo) {
	    
	    	case Modul:
	    		this.modul(modulname);
	    		break;
	    	case Notenuebersicht:
	    		this.notenUebersicht();
	    		break;
	    	case Terminuebersicht:
	    		this.terminUebersicht();
	    		break;
	    	default:
		    	System.out.println("Nichts zu tun");	    	
	    
	    }
    
		System.exit(0);

	}
	
	
	private void modul(String modulname) {
		
    	if (modulname == null) {
	    	System.out.println("Modul (z.B. CPP01): ");
		    this.modulname = sc.nextLine(); 
		    System.out.println("Bitte warten...");   		
    	} 
	    
		Modul modul = new Modul(this.modulname.toUpperCase());
		try {
			vh.holeNoten(modul);
			for (Note n: modul.getNoten()) {
				if (verbose) {
					System.out.print(modul.getName() + " ");
				}
				System.out.println(n.getNote() + " (" + n.getBezeichnung() + ") " + n.getDatum());	
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}

		
	}
	
	private void notenUebersicht() {
		
		String filename = "Notenuebersicht.csv";
    	
    	System.out.println("Erstelle Datei '" + filename + "' - Bitte warten...");

    	PrintStream org = System.out;
    	
		try {
			File file = new File(filename);  
			FileOutputStream fis = new FileOutputStream(file);
			PrintStream out = new PrintStream(fis);  
			System.setOut(out); 
			
			Module module = vh.holeModule();
			for (Modul m: module) {
				vh.holeNoten(m);
				for (Note n: m.getNoten()) {
					System.out.println("\"" + m.getName() + "\";\"" + n.getNote() + "\";\"" + n.getBezeichnung() + "\";\"" + n.getDatum() + "\"");
				}
			}
			
		} catch (Exception e) {
			System.setOut(org);				
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
		
	private void terminUebersicht() {
		
		String filename = "Terminübersicht.xls";
    	
    	try {
    		
	    	System.out.println("Erstelle Datei '" + filename + "' - Bitte warten...");
    		
    		String document = vh.holeTerminUebersicht();
    		
    		File file = new File(filename);				
			
    		OutputStream writer = new FileOutputStream(file);
    		writer.write(DatatypeConverter.parseBase64Binary(document));
    		writer.flush();
    		writer.close();
   
    		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
	}
}
