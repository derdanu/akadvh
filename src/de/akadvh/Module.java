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
public class Module implements Iterable<Modul> {

	private List<Modul> module = new ArrayList<Modul>();
	
	/**
	 * Modul hinzufügen
	 * 
	 * @param modul Modul
	 */
	public void addModul(Modul modul) {
		this.module.add(modul);
	}

	/**
	 * 
	 * Modul zurückgeben anhand des Index
	 * 
	 * @param index
	 * @return	Modul
	 */
	public Modul getModul(int index) {
		return this.module.get(index);		
	}

	/**
	 * 
	 * Modul anhand des Namens zurückgeben
	 * 
	 * @param name Name des Moduls
	 * @return Modul
	 * @throws Exception
	 */
	public Modul getModulByName(String name) throws Exception {
		
		for (Modul m: module) {
			if (m.getName().equals(name)) return m;
		}
		
		throw new Exception("Modul nicht gefunden");
		
	}

	/**
	 * 
	 * Anzahl der Module
	 * 
	 * @return	int	Anzahl
	 */
	public int Anzahl() {
		return module.size();
	}
	
	public String toString() {
		
		String r = new String();
		
		for (Modul m: module) {
			r += m.getName() + " ";
		}
		
		return r;
	}

	@Override
	public Iterator<Modul> iterator() {
		Iterator<Modul> modul = module.iterator();
		return modul;
	}

	
}
