package technical.test.api.storage.repositories;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import technical.test.api.storage.models.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    @Query("{ 'authorId' : ?0, 'releaseDate' : { $gte: ?1, $lte: ?2 } }")
    Flux<Book> findByAuthorIdAndReleaseDateBetween(String author, Integer dateMin, Integer dateMax);

    @Query("{ 'releaseDate' : { $gte: ?0, $lte: ?1 } }")
    Flux<Book> findByReleaseDateBetween(Integer dateMin, Integer dateMax);
}
