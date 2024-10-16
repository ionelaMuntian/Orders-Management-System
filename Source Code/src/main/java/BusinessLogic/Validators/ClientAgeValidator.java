package BusinessLogic.Validators;
import Model.Client;

/**
 * Validator implementation for validating client age.
 */
public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 10;
    private static final int MAX_AGE = 110;
    public void validate(Client t) {

        if (t.getClient_age() < MIN_AGE || t.getClient_age() > MAX_AGE) {
            throw new IllegalArgumentException("The Client Age limit is not respected!");
        }
    }
}

