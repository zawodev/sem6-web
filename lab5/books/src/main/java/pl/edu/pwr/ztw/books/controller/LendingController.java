package pl.edu.pwr.ztw.books.controller;

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

    @Operation(summary = "wypożycz książkę dla czytelnika")
    @PostMapping("/{bookId}")
    public ResponseEntity<Object> lendBook(@PathVariable("bookId") int bookId, @RequestParam("reader") String reader) {
        String result = lendingService.lendBook(bookId, reader);
        if(result.startsWith("książka została wypożyczona"))
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
