/**
 * 
 */
package rfbujan.backend.task2.athenticator;

import static rfbujan.backend.task2.common.utils.Utils.randomDelay;

import java.util.concurrent.CompletableFuture;

import rfbujan.backend.task2.common.authentication.TokenAuthenticator;
import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.User;

/**
 * @author rafb
 *
 */
public class TokenAuthenticatorRandomDelay implements TokenAuthenticator
{

    @Override
    public CompletableFuture<User> authenticateAsync(Credentials credentials)
    {
	return CompletableFuture.supplyAsync(() ->
	{
	   return authenticate(credentials);
	});
    }
    
    private User authenticate(Credentials credentials) 
    {
	randomDelay();
	if (validate(credentials))
	{
	    return new User(credentials.getUsername());
	} else
	{
	    return User.invalidUser();
	}
    }

    private boolean validate(Credentials credentials)
    {
	String username = credentials.getUsername();
	String password = credentials.getPassword();
	return username.toUpperCase().equals(password);
    }

}
