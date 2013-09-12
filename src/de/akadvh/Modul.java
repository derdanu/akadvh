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
public class Modul {
	
	private String name;
	private String beschreibung;
	private Noten noten = new Noten();
	
	public Modul() {
	
	}
	
	public Modul(String name) {
		this.name = name;
	}
	
	public Modul(String name, String beschreibung) {
		this.name = name;
		this.beschreibung = beschreibung;
	}
	
	public void addNote(Note note) {
		this.noten.addNote(note);
	}
	
	public void setNoten(Noten noten) {
		this.noten = noten;
	}
	
	public Noten getNoten() {
		return this.noten;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBeschreibung() {
		return beschreibung;
	}
	
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
		
	public String toString() {
		return name;
	}
}
