package de.akadvh.view;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.UnrecognizedOptionException;

import de.akadvh.Akadvh;

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
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Options options = new Options();
		options.addOption("u", "user", true, "Benutzername");
		options.addOption("p", "pass", true, "Passwort");
		options.addOption("c", "console", false, "Consolenmodus");
		options.addOption("v", "verbose", false, "Mehr Ausgabe");
		options.addOption("m", "modul", true, "Modul");
		options.addOption("n", "noten", false, "Notenuebersicht erstellen");
		options.addOption("t", "termin", false, "Terminuebersicht (angemeldete Module) downloaden");
		options.addOption("version", false, "Version");
		options.addOption("h", "help", false, "Hilfe");

		CommandLineParser parser = new PosixParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if (cmd.hasOption("help")) {
							
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java -jar akadvh.jar", options );
				System.exit(0);
			}
			
			if (cmd.hasOption("version")) {
				
				System.out.println("Akadvh Version: " + Akadvh.getVersion());
				System.exit(0);
			
			}
			

			
			if (cmd.hasOption("console")) {
			
					
				ConsoleView cv = new ConsoleView(cmd.getOptionValue("user"),
												cmd.getOptionValue("pass"),
												cmd.getOptionValue("modul"),
												cmd.hasOption("noten"),
												cmd.hasOption("termin"),
												cmd.hasOption("verbose"));
				

				
			} else {
				
				SwingView sv = new SwingView(cmd.getOptionValue("user"),
											cmd.getOptionValue("pass"));
				
			}
			
			
		} catch (UnrecognizedOptionException e1) {
			System.out.println(e1.getMessage());
			System.out.println("--help fuer Hilfe");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
