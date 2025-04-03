package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.service.iface.IBooksService;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Reader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BooksService implements IBooksService {
    private static List<Book> booksRepo = new ArrayList<>();
    private static int nextId = 4;

    static {
        Author author1 = new Author(1, "Henryk Sienkiewicz");
        Author author2 = new Author(2, "Stanisław Reymont");
        Author author3 = new Author(3, "Adam Mickiewicz");

        booksRepo.add(new Book(1, "Potop", author1, 936));
        booksRepo.add(new Book(2, "Wesele", author2, 150));
        booksRepo.add(new Book(3, "Dziady", author3, 292));
    }

    @Override
    public Collection<Book> getBooks() {
        return booksRepo;
    }

    @Override
    public Book getBook(int id) {
        return booksRepo.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Book addBook(Book book) {
        book.setId(nextId++);
        booksRepo.add(book);
        return book;
    }

    @Override
    public Book updateBook(int id, Book book) {
        Book existing = getBook(id);
        if (existing != null) {
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setPages(book.getPages());
            return existing;
        }
        return null;
    }

    @Override
    public boolean deleteBook(int id) {
        return booksRepo.removeIf(b -> b.getId() == id);
    }

    public void updateAuthorInBooks(Author updatedAuthor) {
        for (Book book : booksRepo) {
            if (book.getAuthor() != null && book.getAuthor().getId() == updatedAuthor.getId()) {
                book.setAuthor(updatedAuthor);
            }
        }
    }

    public void removeAuthorFromBooks(int authorId) {
        for (Book book : booksRepo) {
            if (book.getAuthor() != null && book.getAuthor().getId() == authorId) {
                book.setAuthor(null);
            }
        }
    }

    public void updateReaderInBooks(Reader updatedReader) {
        for (Book book : booksRepo) {
            if (book.getReader() != null && book.getReader().getId() == updatedReader.getId()) {
                book.setReader(updatedReader);
            }
        }
    }

    public void removeReaderFromBooks(int readerId) {
        for (Book book : booksRepo) {
            if (book.getReader() != null && book.getReader().getId() == readerId) {
                book.setReader(null);
            }
        }
    }
}
