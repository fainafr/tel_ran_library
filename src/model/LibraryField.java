package model;

public class LibraryField {
	private String name;
	private Maps kind;
	public LibraryField(String name, Maps kind) {
		super();
		this.name = name;
		this.kind = kind;
	}
	public String getName() {
		return name;
	}
	public Maps getKind() {
		return kind;
	}
}
