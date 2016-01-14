package pl.spring.demo.java_fx.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookAdd {

	private final StringProperty title = new SimpleStringProperty();
	private final StringProperty authors = new SimpleStringProperty();

	public final String getTitle() {
		return title.get();
	}

	public final void setTitle(String value) {
		title.set(value);
	}

	public StringProperty titleProperty() {
		return title;
	}
	public final String getAuthors() {
		return authors.get();
	}
	
	public final void setAuthors(String value) {
		authors.set(value);
	}
	
	public StringProperty authorsProperty() {
		return authors;
	}


	@Override
	public String toString() {
		return "PersonSearch [name=" + title + ", authors=" + authors + "]";
	}

}
