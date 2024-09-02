package technical.test.api.storage.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "authors")
public class Author {
    private String firstName;
    private String lastName;
    private Integer birthDate;
    private String id;
}
