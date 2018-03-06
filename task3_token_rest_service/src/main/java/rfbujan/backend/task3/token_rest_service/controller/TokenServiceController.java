package rfbujan.backend.task3.token_rest_service.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rfbujan.backend.task2.athenticator.TokenAuthenticatorRandomDelay;
import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.UserToken;
import rfbujan.backend.task2.common.token.provider.TokenProvider;
import rfbujan.backend.task2.token_creator.TokenCreatorWithRandomDelay;
import rfbujan.backend.task2.token_provider.SimpleAsyncTokenService;

/**
 * Spring controller that exposes the {@link SimpleAsyncTokenService} methods as HTTP endpoints.
 * <p>
 */
@RestController
public class TokenServiceController {

	private final TokenProvider simpleAsyncTokenProvider = new SimpleAsyncTokenService(new TokenCreatorWithRandomDelay(), new TokenAuthenticatorRandomDelay());
	
	@RequestMapping("/token")
	public CompletableFuture<UserToken> requestToken(@RequestParam(value="username") String name, @RequestParam(value="password") String password)
	{
		return simpleAsyncTokenProvider.requestTokenAsync(new Credentials(name, password));
	}
}
