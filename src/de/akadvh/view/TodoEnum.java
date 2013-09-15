package de.akadvh.view;

public enum TodoEnum {
	Nothing (""),
	Modul ("M"),
	Notenuebersicht ("N"),
	Terminuebersicht ("T");

	private String todo;
	
	private TodoEnum (String todo) {
		this.todo = todo;
	}
	
	public static TodoEnum getEnum(String todo) {
		if (todo.equals("M")) return Modul;
		if (todo.equals("N")) return Notenuebersicht;
		if (todo.equals("T")) return Terminuebersicht;
		return Nothing;
	}
	
}
