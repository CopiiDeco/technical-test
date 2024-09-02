package technical.test.api.endpoints;


import jakarta.annotation.Resource;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import technical.test.api.TestSupport;
import technical.test.api.representations.AuthorRepresentation;
import technical.test.api.representations.BookRepresentation;
import technical.test.api.storage.repositories.AuthorRepository;
import technical.test.api.storage.repositories.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient(timeout = "20000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryEndpointIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Resource
    TestSupport testSupport;

    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl("embedded"));
    }

    @Before
    public void cleanup() {
        bookRepository.deleteAll().block();
        authorRepository.deleteAll().block();
    }

    @Test
    public void given_author_should_add_entry_in_database() {
        // Given
        AuthorRepresentation author = AuthorRepresentation.builder()
                .firstName("isaac")
                .lastName("asimov")
                .birthDate(1920)
                .id("isaac_asimov")
                .build();

        // When
        final var authorRepresentationResponse = webTestClient
                .post()
                .uri(uri -> uri.path("/library/authors")
                        .queryParam("firstname", "isaac")
                        .queryParam("lastname", "asimov")
                        .queryParam("birthdate", "1920")
                        .build()
                ).exchange()
                .expectStatus()
                .isOk()
                .expectBody(AuthorRepresentation.class)
                .returnResult()
                .getResponseBody();

        // Then
        assertThat(authorRepresentationResponse).usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    public void given_book_should_add_entry_in_database() {
        // Given
        BookRepresentation book = BookRepresentation.builder()
                .isbn("1234-5678-90")
                .title("Fondation")
                .releaseDate(1951)
                .authorId("isaac_asimov")
                .build();

        // When
        final var bookRepresentationResponse = webTestClient
                .post()
                .uri(uri -> uri.path("/library/books")
                        .queryParam("isbn", "1234-5678-90")
                        .queryParam("title", "Fondation")
                        .queryParam("releaseDateYear", "1951")
                        .queryParam("authorRefId", "isaac_asimov")
                        .build()
                ).exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookRepresentation.class)
                .returnResult()
                .getResponseBody();

        // Then
        assertThat(bookRepresentationResponse).usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    public void given_books_in_database_should_return_all_books() {
        // given
        testSupport.loadBooks()
                .then(testSupport.loadAuthor()).block();

        // when
        List<BookRepresentation> books = webTestClient
                .get()
                .uri(uri -> uri.path("/library/books")
                        .build()
                ).exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookRepresentation.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(books).hasSize(14);
    }

    @Test
    public void given_books_in_database_should_return_books_based_on_criteria() {
        // given
        testSupport.loadBooks()
                        .then(testSupport.loadAuthor()).block();

        // when
        List<BookRepresentation> books = webTestClient
                .get()
                .uri(uri -> uri.path("/library/books")
                        .queryParam("authorRefId", "isaac_asimov")
                        .queryParam("yearFrom", "1970")
                        .queryParam("yearTo", "1990")
                        .build()
                ).exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookRepresentation.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(books).hasSize(5);
    }
}
