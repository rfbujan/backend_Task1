package rfbujan.backend.task2.token_provider;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rfbujan.backend.task2.common.authentication.TokenAuthenticator;
import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.User;
import rfbujan.backend.task2.common.model.UserToken;
import rfbujan.backend.task2.common.token.creator.TokenCreator;

public class SimpleAsyncTokenServiceExceptionTest
{

    @Mock
    TokenCreator mokedTokenCreator;
    @Mock
    TokenAuthenticator mockedTokenAuthenticator;
    @InjectMocks
    SimpleAsyncTokenService tokenProviderToTest;

    // create test data
    Credentials dummyCredetials = new Credentials("dummyUser", "dummyPassword");
    Credentials dummyExceptionCredetials = new Credentials("dummyExceptionUser", "dummyExceptionPassword");
    String dummyTokenString = "dummyToken";
    
    CompletableFuture<User> dummyExceptionUser = CompletableFuture.supplyAsync(() ->
    {
	throw new RuntimeException();
    });
    
    CompletableFuture<User> dummyUser = CompletableFuture.supplyAsync(() ->
    {
	try
	{
	    Thread.sleep(2000);

	} catch (InterruptedException e)
	{
	   fail("not exception is expected at this point");
	}
	return new User(dummyTokenString);
    });
    
    CompletableFuture<UserToken> dummyToken = CompletableFuture.supplyAsync(() ->
    {
	throw new RuntimeException();
    });

    @Before
    public void setUp() throws Exception
    {
	MockitoAnnotations.initMocks(this);

	// Stubbing the methods of the mocked objects
	// nominal case
	when(mockedTokenAuthenticator.authenticateAsync(dummyCredetials)).thenReturn(dummyUser);
	when(mockedTokenAuthenticator.authenticateAsync(dummyExceptionCredetials)).thenReturn(dummyExceptionUser);
	when(mokedTokenCreator.issueTokenAsync(any(User.class))).thenReturn(dummyToken);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testRequestTokenAsync() throws InterruptedException, ExecutionException
    {
	
	UserToken token = tokenProviderToTest.requestTokenAsync(dummyCredetials).get();
	assertTrue(token.getToken().startsWith("invalid"));
    }

    @Test
    public void testRequestTokenAsync2() throws InterruptedException, ExecutionException
    {
	CompletableFuture<UserToken> token = tokenProviderToTest.requestTokenAsync(dummyExceptionCredetials);
	assertTrue(token.get().getToken().startsWith("invalid"));
	
    }
}
