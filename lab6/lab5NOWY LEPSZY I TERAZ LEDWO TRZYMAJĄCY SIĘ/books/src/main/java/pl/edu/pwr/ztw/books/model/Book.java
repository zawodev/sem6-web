package pl.edu.pwr.ztw.books.model;

public class Book {
    private int id;
    private String title;
    private Author author;
    private int pages;
    private Reader borrower; // null jeśli książka nie jest wypożyczona?

    public Book() {}

    public Book(int id, String title, Author author, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.borrower = null;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public Reader getBorrower() { return borrower; }
    public void setBorrower(Reader reader) { this.borrower = reader; }
}
