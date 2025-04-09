package pl.edu.pwr.ztw.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.service.iface.IAuthorsService;
import pl.edu.pwr.ztw.books.model.Author;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    private IAuthorsService authorsService;

    @Operation(summary = "pobierz wszystkich autorów")
    @GetMapping
    public ResponseEntity<Object> getAuthors() {
        return new ResponseEntity<>(authorsService.getAuthors(), HttpStatus.OK);
    }

    @Operation(summary = "pobierz autora po id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuthor(@PathVariable("id") int id) {
        Author author = authorsService.getAuthor(id);
        if (author != null)
            return new ResponseEntity<>(author, HttpStatus.OK);
        else
            return new ResponseEntity<>("autor nie znaleziony", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "dodaj nowego autora")
    @PostMapping
    public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(authorsService.addAuthor(author), HttpStatus.CREATED);
    }

    @Operation(summary = "zaktualizuj autora")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable("id") int id, @RequestBody Author author) {
        Author updated = authorsService.updateAuthor(id, author);
        if (updated != null)
            return new ResponseEntity<>(updated, HttpStatus.OK);
        else
            return new ResponseEntity<>("autor nie znaleziony", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "usuń autora")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") int id) {
        boolean deleted = authorsService.deleteAuthor(id);
        if (deleted)
            return new ResponseEntity<>("autor usunięty", HttpStatus.OK);
        else
            return new ResponseEntity<>("autor nie znaleziony", HttpStatus.NOT_FOUND);
    }
}
