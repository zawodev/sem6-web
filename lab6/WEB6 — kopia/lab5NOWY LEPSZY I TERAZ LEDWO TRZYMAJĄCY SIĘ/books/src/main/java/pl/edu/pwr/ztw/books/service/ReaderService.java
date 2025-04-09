package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Reader;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReaderService {
    private static List<Reader> readersRepo = new ArrayList<>();
    private static int nextId = 1;

    static {
        readersRepo.add(new Reader(nextId++, "Jan Kowalski"));
        readersRepo.add(new Reader(nextId++, "Anna Nowak"));
    }

    public Collection<Reader> getReaders() {
        return readersRepo;
    }

    public Reader getReader(int id) {
        return readersRepo.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Reader addReader(Reader reader) {
        reader.setId(nextId++);
        readersRepo.add(reader);
        return reader;
    }

    public Reader updateReader(int id, Reader reader) {
        Reader existing = getReader(id);
        if (existing != null) {
            existing.setName(reader.getName());
            return existing;
        }
        return null;
    }

    public boolean deleteReader(int id) {
        return readersRepo.removeIf(r -> r.getId() == id);
    }
}
