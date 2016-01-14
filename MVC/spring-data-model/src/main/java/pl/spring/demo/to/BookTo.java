package pl.spring.demo.to;

public class BookTo {
    private Long id;
    private String title;
    private String authors;

    public BookTo() {
    }

    public BookTo(Long id, String title, String authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }
    
    public BookTo(String title, String authors) {
    	this.title = title;
    	this.authors = authors;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		BookTo book = (BookTo) obj;
		return this.id.equals(book.id);
	}
	
	@Override
	public String toString() {
		return "BookTo [id=" + id + ", title=" + title + ", authors="
				+ authors + "]";
	}
}
