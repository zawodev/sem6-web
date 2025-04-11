package pl.edu.pwr.ztw.books.controller;

import pl.edu.pwr.ztw.books.model.LendingInfo;
import pl.edu.pwr.ztw.books.model.Reader;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.service.LendingService;

@RestController
@RequestMapping("/lending")
public class LendingController {
    @Autowired
    private LendingService lendingService;

    @Operation(summary = "pobierz informacje o wypożyczeniach wszystkich książek")
    @GetMapping
    public ResponseEntity<Object> getAllLendings() {
        return new ResponseEntity<>(lendingService.getAllLendings(), HttpStatus.OK);
    }
    
    @Operation(summary = "pobierz informacje o wypożyczeniu dla danej książki")
    @GetMapping("/{bookId}")
    public ResponseEntity<Object> getLending(@PathVariable int bookId) {
        LendingInfo lendingInfo = lendingService.getLending(bookId);
        return lendingInfo != null
                ? new ResponseEntity<>(lendingInfo, HttpStatus.OK)
                : new ResponseEntity<>("książka nie jest wypożyczona", HttpStatus.OK);
    }

    @Operation(summary = "wypożycz książkę dla czytelnika")
    @PutMapping("/{bookId}/borrow/{readerId}")
    public ResponseEntity<Object> borrowBook(@PathVariable int bookId, @PathVariable int readerId) {
        return lendingService.borrowBook(bookId, readerId)
                ? new ResponseEntity<>("książka została wypożyczona", HttpStatus.OK)
                : new ResponseEntity<>("książka albo czytelnik nie została znaleziona", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "zwróć książkę")
    @PutMapping("/{bookId}/return")
    public ResponseEntity<Object> returnBook(@PathVariable int bookId) {
        return lendingService.returnBook(bookId)
                ? new ResponseEntity<>("książka zwrócona", HttpStatus.OK)
                : new ResponseEntity<>("książka nie znaleziona lub nie zwrócona", HttpStatus.NOT_FOUND);
    }
}
