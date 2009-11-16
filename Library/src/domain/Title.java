package domain;

public class Title implements Cloneable {
	
	private String name, author, publisher;
	
	public Title(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String autor) {
		this.author = autor;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public String toString() {
		return name + ", " + author + ", " + publisher;
	}
	
	@Override
	protected Object clone() {
		Title result = new Title(new String(name));
		result.setAuthor(new String(author));
		result.setPublisher(new String(publisher));
		return result;
	}
}
