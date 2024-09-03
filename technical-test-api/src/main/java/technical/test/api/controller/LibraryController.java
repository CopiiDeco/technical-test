package technical.test.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.services.LibraryService;
import technical.test.api.storage.models.Author;
import technical.test.api.storage.models.Book;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @RequestMapping(path = "/books", method = RequestMethod.GET)
    public Flux<Book> findBooks(@RequestParam(required = false) String authorRefId, @RequestParam(required = false) Integer yearFrom, @RequestParam(required = false) Integer yearTo) {
        if(authorRefId == null && yearFrom == null && yearTo == null) {
            return libraryService.findAllBooks();
        }

        return libraryService.findBookByAuthorAndDateBetween(authorRefId, yearFrom, yearTo);
    }

    @RequestMapping( path = "/books" , method = RequestMethod.POST)
    public Mono<Book> registerBook(String isbn, String title, String releaseDateYear, String authorRefId, @RequestBody(required = false) Book book) {
        if(book != null) {
            return libraryService.registerBook(book.getIsbn(), book.getTitle(), book.getReleaseDate(), book.getAuthorId());
        }
        return libraryService.registerBook(isbn, title, releaseDateYear, authorRefId);
    }

    @RequestMapping(path = "/authors" , method = RequestMethod.POST)
    public Mono<Author> registerAuthor(String firstname, String lastname, String birthdate, @RequestBody(required = false) Author author) {
        if(author != null) {
            return libraryService.registerAuthor(author.getFirstName(), author.getLastName(), author.getBirthDate());
        }
        return libraryService.registerAuthor(firstname, lastname, birthdate);
    }

    @RequestMapping(path = "/authors" , method = RequestMethod.GET)
    public Flux<Author> findAllAuthors() {
        return libraryService.findAllAuthors();
    }
}
