package rfbujan.backend.task2.token_provider;


import java.util.concurrent.CompletableFuture;

import rfbujan.backend.task2.common.authentication.TokenAuthenticator;
import rfbujan.backend.task2.common.model.*;
import rfbujan.backend.task2.common.token.creator.TokenCreator;
import rfbujan.backend.task2.common.token.provider.TokenProvider;

public class SimpleAsyncTokenService implements TokenProvider
{

    private final TokenCreator tokenCreator;
    private final TokenAuthenticator tokenAuthenticator;

    public SimpleAsyncTokenService(TokenCreator tokenCreator, TokenAuthenticator tokenAuthentification)
    {
	super();
	this.tokenCreator = tokenCreator;
	this.tokenAuthenticator = tokenAuthentification;
    }

    @Override
    public CompletableFuture<UserToken> requestTokenAsync(Credentials credentials)
    {

	return tokenAuthenticator.authenticateAsync(credentials)
		.thenCompose((user) -> tokenCreator.issueTokenAsync(user));
    }

}
