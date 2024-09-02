package technical.test.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.storage.models.Author;
import technical.test.api.storage.models.Book;
import technical.test.api.storage.repositories.AuthorRepository;
import technical.test.api.storage.repositories.BookRepository;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Mono<Author> registerAuthor(String firstName, String lastName, int birthDate) {
        //we concat the first and last name to create the id
        //we use an underscore as separator and we convert everything to lowercase
        String id = (firstName + "_" + lastName).toLowerCase();
        return authorRepository.save(Author.builder().firstName(firstName).lastName(lastName).birthDate(birthDate).id(id).build());
    }

    @Override
    public Mono<Book> registerBook(String isbn, String title, int releaseDate, String authorId) {
        return bookRepository.save(Book.builder().isbn(isbn).title(title).releaseDate(releaseDate).authorId(authorId).build());
    }

    @Override
    public Mono<Book> registerBook(String isbn, String title, String releaseDate, String authorId) {
        return registerBook(isbn, title, Integer.parseInt(releaseDate), authorId);
    }

    @Override
    public Flux<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<Book> findBookByAuthorAndDateBetween(String author, Integer dateMin, Integer dateMax) {
        if(dateMin == null) {
            dateMin = 0;
        }
        if(dateMax == null) {
            dateMax = Integer.MAX_VALUE;
        }
        if(author == null) {
            return bookRepository.findByReleaseDateBetween( dateMin, dateMax);
        }
        return bookRepository.findByAuthorIdAndReleaseDateBetween(author, dateMin, dateMax);
    }

    @Override
    public Flux<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
}
