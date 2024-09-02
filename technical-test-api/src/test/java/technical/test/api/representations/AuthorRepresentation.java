package technical.test.api.representations;

import lombok.Builder;

@Builder
public class AuthorRepresentation {

    private String firstName;
    private String lastName;
    private int birthDate;
    private String id;
}
