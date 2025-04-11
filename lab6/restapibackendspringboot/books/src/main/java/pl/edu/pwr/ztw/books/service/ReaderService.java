package pl.edu.pwr.ztw.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.model.Reader;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.service.iface.IBooksService;
import pl.edu.pwr.ztw.books.service.iface.IReadersService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReaderService implements IReadersService {
    private static List<Reader> readersRepo = new ArrayList<>();
    private static int nextId = 1;

    static {
        readersRepo.add(new Reader(nextId++, "Jan Kowalski"));
        readersRepo.add(new Reader(nextId++, "Anna Nowak"));
        readersRepo.add(new Reader(nextId++, "Piotr Wiśniewski"));
        readersRepo.add(new Reader(nextId++, "Katarzyna Lewandowska"));
        readersRepo.add(new Reader(nextId++, "Tomasz Zieliński"));
        readersRepo.add(new Reader(nextId++, "Ewa Wójcik"));
    }

    @Autowired
    private IBooksService booksService;
    
    @Override
    public Collection<Reader> getReaders() {
        return readersRepo;
    }

    @Override
    public Reader getReader(int id) {
        return readersRepo.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Reader addReader(Reader reader) {
        reader.setId(nextId++);
        readersRepo.add(reader);
        return reader;
    }

    @Override
    public Reader updateReader(int id, Reader reader) {
        Reader existing = getReader(id);
        if (existing != null) {
            existing.setName(reader.getName());
            if (booksService instanceof BooksService) {
                ((BooksService)booksService).updateReaderInBooks(existing);
            }
            return existing;
        }
        return null;
    }

    @Override
    public boolean deleteReader(int id) {
        Reader existing = getReader(id);
        if (existing == null)
            return false;
        boolean deleted = readersRepo.removeIf(a -> a.getId() == id);
        if (deleted && booksService instanceof BooksService) {
            ((BooksService)booksService).removeReaderFromBooks(id);
        }
        return deleted;
    }
}
