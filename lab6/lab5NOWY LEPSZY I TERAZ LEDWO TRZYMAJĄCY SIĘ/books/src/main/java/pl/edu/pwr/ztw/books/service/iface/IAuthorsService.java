package pl.edu.pwr.ztw.books.service.iface;

import pl.edu.pwr.ztw.books.model.Author;

import java.util.Collection;

public interface IAuthorsService {
    Collection<Author> getAuthors();
    Author getAuthor(int id);
    Author addAuthor(Author author);
    Author updateAuthor(int id, Author author);
    boolean deleteAuthor(int id);
}
