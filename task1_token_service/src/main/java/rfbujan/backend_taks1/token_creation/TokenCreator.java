package rfbujan.backend_taks1.token_creation;

import rfbujan.backend_taks1.model.User;
import rfbujan.backend_taks1.model.UserToken;
import rfbujan.backend_taks1.token_creation.exception.TokenCreationException;

/**
 * Token creator that provides the means needed for generating a {@link UserToken} for a given {@link User}.
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
     * 
     * @throws TokenCreationException 
     * 				representing possible exceptions while requesting a new Token.
     */
    UserToken issueToken(User user) throws TokenCreationException;
}
