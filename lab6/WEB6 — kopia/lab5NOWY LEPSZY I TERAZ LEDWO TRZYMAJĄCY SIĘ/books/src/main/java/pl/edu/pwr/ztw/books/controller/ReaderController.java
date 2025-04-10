package pl.edu.pwr.ztw.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import pl.edu.pwr.ztw.books.service.ReaderService;
import pl.edu.pwr.ztw.books.model.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/readers")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @Operation(summary = "pobierz wszystkich czytelników")
    @GetMapping
    public ResponseEntity<Object> getReaders() {
        return new ResponseEntity<>(readerService.getReaders(), HttpStatus.OK);
    }

    @Operation(summary = "pobierz czytelnika po id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getReader(@PathVariable int id) {
        Reader reader = readerService.getReader(id);
        return reader != null
                ? new ResponseEntity<>(reader, HttpStatus.OK)
                : new ResponseEntity<>("Reader not found", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "dodaj nowego czytelnika")
    @PostMapping
    public ResponseEntity<Object> addReader(@RequestBody Reader reader) {
        return new ResponseEntity<>(readerService.addReader(reader), HttpStatus.CREATED);
    }

    @Operation(summary = "zaktualizuj czytelnika")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReader(@PathVariable int id, @RequestBody Reader reader) {
        Reader updated = readerService.updateReader(id, reader);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>("Reader not found", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "usuń czytelnika")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReader(@PathVariable int id) {
        return readerService.deleteReader(id)
                ? new ResponseEntity<>("Reader deleted", HttpStatus.OK)
                : new ResponseEntity<>("Reader not found", HttpStatus.NOT_FOUND);
    }
}
