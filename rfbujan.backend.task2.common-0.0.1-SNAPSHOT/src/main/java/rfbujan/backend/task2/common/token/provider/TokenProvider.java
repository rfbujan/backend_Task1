package rfbujan.backend.task2.common.token.provider;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.UserToken;


/**
 * Token provider that allows requests for a user token based on given credentials.
 * <p>
 *
 */
public interface TokenProvider
{

    /**
     * Creates a user token from given credentials. This implementation is a
     * non-blocking method where callers do not need to wait for the user token to
     * be created and returned.
     * <p>
     * 
     * @param credentials
     *            {@link Credentials} from which a user token will be created.
     * @return {@link CompletableFuture} returned immediately to the callers which
     *         from which the requested user token can be retrieved once it is
     *         calculated.
     */
    public CompletableFuture<UserToken> requestTokenAsync(Credentials credentials);

}
