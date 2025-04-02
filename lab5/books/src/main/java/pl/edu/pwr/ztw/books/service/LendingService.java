package pl.edu.pwr.ztw.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.service.iface.IBooksService;

@Service
public class LendingService {
    @Autowired
    private IBooksService booksService;

    public String lendBook(int bookId, String reader) {
        Book book = booksService.getBook(bookId);
        if (book == null)
            return "książka nie znaleziona";
        if (book.getBorrower() != null)
            return "książka jest już wypożyczona dla: " + book.getBorrower();
        book.setBorrower(reader);
        return "książka została wypożyczona dla: " + reader;
    }
}
