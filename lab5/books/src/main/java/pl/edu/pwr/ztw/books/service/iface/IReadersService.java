package pl.edu.pwr.ztw.books.service.iface;

import pl.edu.pwr.ztw.books.model.Reader;

import java.util.Collection;

public interface IReadersService {
    Collection<Reader> getReaders();
    Reader getReader(int id);
    Reader addReader(Reader reader);
    Reader updateReader(int id, Reader reader);
    boolean deleteReader(int id);
}
