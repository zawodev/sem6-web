package pl.edu.pwr.ztw.books.model;

public class LendingInfo {
    private int bookId;
    private String bookTitle;
    private Reader borrower; // null, gdy książka nie jest wypożyczona

    public LendingInfo(int bookId, String bookTitle, Reader borrower) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrower = borrower;
    }
    
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    public Reader getBorrower() {
        return borrower;
    }
    public void setBorrower(Reader borrower) {
        this.borrower = borrower;
    }
}
