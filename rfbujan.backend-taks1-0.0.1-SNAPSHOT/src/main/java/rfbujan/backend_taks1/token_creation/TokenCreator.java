package rfbujan.backend_taks1.token_creation;

import rfbujan.backend_taks1.model.User;
import rfbujan.backend_taks1.model.UserToken;
import rfbujan.backend_taks1.token_creation.exception.TokenCreationException;

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
    UserToken issueToken(User user) throws TokenCreationException;
}
