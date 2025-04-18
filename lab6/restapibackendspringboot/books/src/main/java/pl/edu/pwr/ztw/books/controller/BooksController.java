package pl.edu.pwr.ztw.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.service.iface.IBooksService;
import pl.edu.pwr.ztw.books.model.Book;

import pl.edu.pwr.ztw.books.service.iface.IAuthorsService;
import pl.edu.pwr.ztw.books.model.Author;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private IBooksService booksService;

    @Autowired
    private IAuthorsService authorsService;

    @Operation(summary = "pobierz wszystkie książki")
    @GetMapping
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @Operation(summary = "pobierz książkę po id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBook(@PathVariable("id") int id) {
        Book book = booksService.getBook(id);
        if (book != null)
            return new ResponseEntity<>(book, HttpStatus.OK);
        else
            return new ResponseEntity<>("książka nie znaleziona", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "dodaj nową książkę")
    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(booksService.addBook(book), HttpStatus.CREATED);
    }

    @Operation(summary = "zaktualizuj książkę")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
        Book updated = booksService.updateBook(id, book);
        if (updated != null)
            return new ResponseEntity<>(updated, HttpStatus.OK);
        else
            return new ResponseEntity<>("książka nie znaleziona", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "usuń książkę")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable("id") int id) {
        boolean deleted = booksService.deleteBook(id);
        if (deleted)
            return new ResponseEntity<>("książka usunięta", HttpStatus.OK);
        else
            return new ResponseEntity<>("książka nie znaleziona", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "zmień autora książki")
    @PutMapping("/{id}/author/{authorId}")
    public ResponseEntity<Object> changeBookAuthor(@PathVariable("id") int bookId,
                                                   @PathVariable("authorId") int authorId) {
        Book book = booksService.getBook(bookId);
        if (book == null)
            return new ResponseEntity<>("Książka nie znaleziona", HttpStatus.NOT_FOUND);

        Author newAuthor = authorsService.getAuthor(authorId);
        if (newAuthor == null)
            return new ResponseEntity<>("Nowy autor nie znaleziony", HttpStatus.NOT_FOUND);

        book.setAuthor(newAuthor);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
