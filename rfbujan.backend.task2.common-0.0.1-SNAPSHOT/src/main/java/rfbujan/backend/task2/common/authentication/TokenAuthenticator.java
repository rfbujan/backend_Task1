package rfbujan.backend.task2.common.authentication;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.User;

/**
 * This interface defines the list of operations related to Token
 * authentification.
 * <p>
 *
 */
public interface TokenAuthenticator
{
    /**
     * Validates credentials given as an argument and return an instance of a User
     * <p>
     * 
     * @param credentials
     *            {@link Credentials} object to be validated
     * @return User associated to given credentials
     */
    CompletableFuture<User> authenticateAsync(Credentials credentials);
}
