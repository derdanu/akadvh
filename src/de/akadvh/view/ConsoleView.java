package de.akadvh.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Scanner;

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
	private String todo;
	private String modulname;
	private Scanner sc;
	
	public ConsoleView(String username, String password, String modulname, Boolean noten, Boolean verbose) {

		this.username = username;
		this.password = password;
		this.modulname = modulname;

		sc = new Scanner(System.in); 

		if (username == null) {
			System.out.println("Benutername: ");
			this.username = sc.nextLine(); 
		}

		if (password == null) {
		    System.out.println("Passwort: ");
		    this.password = sc.nextLine(); 
		}
		
	    Akadvh vh = null;
		
	    try {
			vh = new Akadvh(new AkadvhCredentials(this.username, this.password));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    if (modulname == null && noten == false) {
		    System.out.println("M für Modul, N für Notenübersicht: ");
		    this.todo = sc.nextLine();	    	
	    } else if (noten == true) {
	    	this.todo = "N";
	    } else {
	    	this.todo = "M";
	    }
 

		
	    if (this.todo.equals("M")) {
		   
	    	if (modulname == null) {
		    	System.out.println("Modul (z.B. CPP01): ");
			    this.modulname = sc.nextLine(); 
			    System.out.println("Bitte warten...");   		
	    	} 
		    
			Modul modul = new Modul(this.modulname.toUpperCase());
			try {
				vh.holeNoten(modul);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
			for (Note n: modul.getNoten()) {
				if (verbose) {
					System.out.print(modul.getName() + " ");
				}
				System.out.println(n.getNote() + " (" + n.getBezeichnung() + ") " + n.getDatum());	
			}
			
			
	    } else if (todo.equals("N")) {
	    	
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
	
	    } else {
	    	System.out.println("Nichts zu tun");	    	
	    }
	    
		
	}
	
		
}
