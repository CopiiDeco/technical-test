package technical.test.api.representations;

import lombok.Builder;

@Builder
public class BookRepresentation {

    private String isbn;
    private String title;
    private int releaseDate;
    private String authorId;
}
