package de.akadvh;

import java.util.ArrayList;
import java.util.Iterator;
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
public class Noten implements Iterable<Note>{

	private List<Note> noten = new ArrayList<Note>();
	
	/**
	 * 
	 * Note hinzufügen
	 * 
	 * @param note Note
	 */
	public void addNote(Note note) {
		this.noten.add(note);
	}

	/**
	 * 
	 * Note zurückgeben
	 * 
	 * @param index
	 * @return Note
	 */
	public Note getNote(int index) {
		return this.noten.get(index);		
	}
	
	/**
	 * 
	 * Noten als Liste zurückgeben
	 * 
	 * @return List<Note>
	 */
	public List<Note> getNoten() {
		return noten;
	}
	
	/**
	 * 
	 * Anzahl der Noten
	 * 
	 * @return int
	 */
	public int Anzahl() {
		return noten.size();
	}
	
	public String toString() {
		
		String r = new String();
		
		for (Note n: noten) {
			r += n.getNote() + " (" + n.getBezeichnung() + ")" + "; " ;
		}
		
		return r;
	}

	@Override
	public Iterator<Note> iterator() {
		Iterator<Note> note = noten.iterator();
		return note;
	}
}
