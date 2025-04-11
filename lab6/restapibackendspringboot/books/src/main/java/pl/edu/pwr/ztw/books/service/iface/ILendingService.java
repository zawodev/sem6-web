package pl.edu.pwr.ztw.books.service.iface;

import pl.edu.pwr.ztw.books.model.LendingInfo;
import java.util.List;

public interface ILendingService {
    LendingInfo getLending(int bookId);
    List<LendingInfo> getAllLendings();
    boolean borrowBook(int bookId, int readerId);
    boolean returnBook(int bookId);
}
