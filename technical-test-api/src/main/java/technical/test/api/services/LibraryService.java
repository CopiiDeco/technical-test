package technical.test.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.storage.models.Author;
import technical.test.api.storage.models.Book;

public interface LibraryService {

    Mono<Author> registerAuthor(String firstname, String lastname, int birthdate);
    Mono<Book> registerBook(String isbn, String title, int releaseDate, String authorId);
    Mono<Book> registerBook(String isbn, String title, String releaseDate, String authorId);
    Flux<Book> findAllBooks();
    Flux<Book> findBookByAuthorAndDateBetween(String author, Integer dateMin, Integer dateMax);
    Flux<Author> findAllAuthors();

}
