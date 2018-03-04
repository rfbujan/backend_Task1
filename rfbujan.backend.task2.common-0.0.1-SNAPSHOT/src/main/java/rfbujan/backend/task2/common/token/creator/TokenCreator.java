package rfbujan.backend.task2.common.token.creator;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend.task2.common.model.User;
import rfbujan.backend.task2.common.model.UserToken;

/**
 * Interface that defines the list of operations related to the creation of a token
 *
 */
public interface TokenCreator
{
    /**
     * Creates a token for a given user
     * <p>
     * 
     * @param user
     * 		{@link User} representing a user for which a token has to be created.
     * @return
     * 		{@link UserToken} representing the token return for a given user.
     */
    CompletableFuture<UserToken> issueTokenAsync(User user);
}
