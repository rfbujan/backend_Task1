package rfbujan.backend_taks1.token_provider;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend_taks1.model.Credentials;
import rfbujan.backend_taks1.model.UserToken;

/**
 * Token provider that allows requests for a user token based on given credentials.
 * <p>
 *This token provider contemplates two implementations of user token requests:
 * <ul>
 * <li><b>Synchronous implementation</b> where requests are processed sequentially (blocking-API).
 * <li><b>Asynchronous implementation</b> where requests can be processed in a concurrent fashion (non-blocking API).
 * <ul>
 * <p>
 */
public interface TokenProvider
{
    /**
     * Creates a user token from given credentials. This implementation is a blocking method where callers need to wait
     * for the user token to be created and returned.
     * <p>
     * 
     * @param credentials
     *            {@link Credentials} from which a user token will be created.
     * @return {@link UserToken} created for given credentials.
     */
    public UserToken requestToken(Credentials credentials);

    /**
     * Creates a user token from given credentials. This implementation is a non-blocking method where callers do not
     * need to wait for the user token to be created and returned.
     * <p>
     * 
     * @param credentials
     *            {@link Credentials} from which a user token will be created.
     * @return {@link CompletableFuture} returned immediately to the callers which from which the requested user token
     *         can be retrieved once it is calculated.
     */
    public CompletableFuture<UserToken> requestTokenAsync(Credentials credentials);

}
