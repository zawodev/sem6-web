package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.model.LendingInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class LendingService {
    @Autowired
    private BooksService booksService;

    @Autowired
    private ReaderService readerService;

    public LendingInfo getLending(int bookId) {
        Book book = booksService.getBook(bookId);
        if (book != null) {
            return new LendingInfo(book.getId(), book.getTitle(), book.getBorrower());
        }
        return null;
    }

    public List<LendingInfo> getAllLendings() {
        List<LendingInfo> list = new ArrayList<>();
        for (Book book : booksService.getBooks()) {
            list.add(new LendingInfo(book.getId(), book.getTitle(), book.getBorrower()));
        }
        return list;
    }

    public boolean borrowBook(int bookId, int readerId) {
        Book book = booksService.getBook(bookId);
        Reader reader = readerService.getReader(readerId);

        if (book != null && reader != null) {
            book.setBorrower(reader);
            return true;
        }
        return false;
    }

    public boolean returnBook(int bookId) {
        Book book = booksService.getBook(bookId);
        if (book != null && book.getBorrower() != null) {
            book.setBorrower(null);
            return true;
        }
        return false;
    }
}
