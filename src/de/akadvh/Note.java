package de.akadvh;

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
public class Note {

	private String bezeichnung;
	private String datum;
	private String anmerkung;
	private String note;
	
	/**
	 * 
	 * Konstruktor
	 * 
	 */
	public Note() {
		
	}
	
	/**
	 * 
	 * Konstruktor
	 * 
	 * @param bezeichnung
	 * @param datum
	 * @param anmerkung
	 * @param note
	 */
	public Note(String bezeichnung, String datum, String anmerkung, String note) {
		this.bezeichnung = bezeichnung;
		this.datum = datum;
		this.anmerkung = anmerkung;
		this.note = note;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public String getDatum() {
		return datum;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	public String getAnmerkung() {
		return anmerkung;
	}
	
	public void setAnmerkung(String anmerkung) {
		this.anmerkung = anmerkung;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note.replace(".", ",");
	}
	
	public String toString() {
		return "Note: " + note + " - Beschreibung: " + bezeichnung + " - Datum: " + datum + " Anmerkung: " + anmerkung;
	}
	
}
