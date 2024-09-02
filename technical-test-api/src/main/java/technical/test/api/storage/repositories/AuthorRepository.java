package technical.test.api.storage.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import technical.test.api.storage.models.Author;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
