package pl.edu.pwr.ztw.books;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
	@RequestMapping("/")
	public String run() {
		return "http://localhost:8080/swagger-ui.html";
	}
}
