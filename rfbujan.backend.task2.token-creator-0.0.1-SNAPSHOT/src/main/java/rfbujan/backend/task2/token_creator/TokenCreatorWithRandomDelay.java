package rfbujan.backend.task2.token_creator;

import static rfbujan.backend.task2.common.utils.Utils.randomDelay;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend.task2.common.model.User;
import rfbujan.backend.task2.common.model.UserToken;
import rfbujan.backend.task2.common.token.creator.TokenCreator;

public class TokenCreatorWithRandomDelay implements TokenCreator
{

    @Override
    public CompletableFuture<UserToken> issueTokenAsync(User user)
    {
		return CompletableFuture.supplyAsync(() ->
		{
		    return issueToken(user);
		});
    }

    private UserToken issueToken(User user)
    {
		randomDelay();
		if (user.startsWithA())
		{
		    return UserToken.invalidUserToken();
		} else
		{
		    return new UserToken(user.getUserId());
		}
    }

}
