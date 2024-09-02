package technical.test.api.storage.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "books")
public class Book {
    private String isbn;
    private String title;
    private int releaseDate;
    private String authorId;
}
