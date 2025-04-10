package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.service.iface.IAuthorsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.service.iface.IBooksService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthorsService implements IAuthorsService {
    private static List<Author> authorsRepo = new ArrayList<>();
    private static int nextId = 4;

    static {
        authorsRepo.add(new Author(1, "Henryk Sienkiewicz"));
        authorsRepo.add(new Author(2, "Stanisław Reymont"));
        authorsRepo.add(new Author(3, "Adam Mickiewicz"));
    }

    @Autowired
    private IBooksService booksService;
    
    @Override
    public Collection<Author> getAuthors() {
        return authorsRepo;
    }

    @Override
    public Author getAuthor(int id) {
        return authorsRepo.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Author addAuthor(Author author) {
        author.setId(nextId++);
        authorsRepo.add(author);
        return author;
    }

    @Override
    public Author updateAuthor(int id, Author author) {
        Author existing = getAuthor(id);
        if (existing != null) {
            existing.setName(author.getName());
            if (booksService instanceof BooksService) {
                ((BooksService)booksService).updateAuthorInBooks(existing);
            }
            return existing;
        }
        return null;
    }

    @Override
    public boolean deleteAuthor(int id) {
        Author existing = getAuthor(id);
        if (existing == null)
            return false;
        boolean deleted = authorsRepo.removeIf(a -> a.getId() == id);
        if (deleted && booksService instanceof BooksService) {
            ((BooksService)booksService).removeAuthorFromBooks(id);
        }
        return deleted;
    }
}
