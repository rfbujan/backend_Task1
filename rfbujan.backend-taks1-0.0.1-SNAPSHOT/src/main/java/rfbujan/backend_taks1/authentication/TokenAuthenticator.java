package rfbujan.backend_taks1.authentication;

import rfbujan.backend_taks1.authentication.exception.TokenAuthentificationException;
import rfbujan.backend_taks1.model.Credentials;
import rfbujan.backend_taks1.model.User;

/**
 * This interface defines the list of operations related to Token authentification.
 * <p>
 *
 */
public interface TokenAuthenticator
{
    /**
     * Validates credentials given as an argument and return an instance of a User
     * <p>
     * @param credentials
     * 			{@link Credentials} object to be validated
     * @return
     * 		User associated to given credentials	
     */
    User authenticate(Credentials credentials) throws TokenAuthentificationException;
}
