package rfbujan.backend.task2.token_creator;

import static rfbujan.backend.task2.common.utils.Utils.randomDelay;

import java.util.concurrent.CompletableFuture;

import net.jcip.annotations.ThreadSafe;
import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.User;
import rfbujan.backend.task2.common.model.UserToken;
import rfbujan.backend.task2.common.token.creator.TokenCreator;

/**
 * {@link TokenCreatorWithRandomDelay} that creates a new {@link UserToken} based on a given {@link User}, with a random
 * delay between 0 and 5 seconds. This allows to simulate unpredictable delays, caused by everything from sever load to
 * network delays.
 * <p>
 * If the userId of the provided User starts with A, the call will fail.
 * <p>
 * This implementation is thread-safe since it has no state and the methods has no side effects (referential
 * transparency).
 * <p>
 */
@ThreadSafe
public class TokenCreatorWithRandomDelay implements TokenCreator
{

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException
     *             if the input is <code>null</code> or any of its attributes (i.e. {@link User#getUserId()})
     * 
     */
    @Override
    public CompletableFuture<UserToken> issueTokenAsync(User user)
    {
	checkInputStatus(user);
	return CompletableFuture.supplyAsync(() ->
	{
	    return issueToken(user);
	});
    }

    /*
     * makes sure that the input is not corrupted with null values
     */
    private void checkInputStatus(User user)
    {
	if (user == null || user.getUserId() == null)
	{
	    throw new IllegalArgumentException("ERROR: Input corrupted. User cannot be null");
	}

    }

    private UserToken issueToken(User user)
    {
	randomDelay();
	if (validateUser(user))
	{
	    return new UserToken(user.getUserId());
	} else
	{
	    return UserToken.invalidUserToken();
	}
    }

    /*
     * If the userId of the provided User starts with A, the call will fail.
     */
    private boolean validateUser(User user)
    {
	return !user.getUserId().startsWith("A");
    }

}
