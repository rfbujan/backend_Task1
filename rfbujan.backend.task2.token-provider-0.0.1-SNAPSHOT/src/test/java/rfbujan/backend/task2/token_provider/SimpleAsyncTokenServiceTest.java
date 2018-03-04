package rfbujan.backend.task2.token_provider;

import static org.junit.Assert.assertTrue;
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

public class SimpleAsyncTokenServiceTest
{

    @Mock
    TokenCreator mokedTokenCreator;
    @Mock
    TokenAuthenticator mockedTokenAuthenticator;
    @InjectMocks
    SimpleAsyncTokenService tokenProviderToTest;

    // create test data
    Credentials dummyCredetials = new Credentials("dummyUser", "dummyPassword");
    String dummyTokenString = "dummyToken";
    
    CompletableFuture<User> dummyUser = CompletableFuture.supplyAsync(() ->
    {
	try
	{
	    Thread.sleep(2000);

	} catch (InterruptedException e)
	{
	    e.printStackTrace();
	}
	return new User(dummyTokenString);
    });
    
    CompletableFuture<UserToken> dummyToken = CompletableFuture.supplyAsync(() ->
    {
	try
	{
	    Thread.sleep(2000);

	} catch (InterruptedException e)
	{
	    e.printStackTrace();
	}
	return new UserToken(dummyTokenString);
    });

    @Before
    public void setUp() throws Exception
    {
	MockitoAnnotations.initMocks(this);

	// Stubbing the methods of the mocked objects
	// nominal case
	when(mockedTokenAuthenticator.authenticateAsync(dummyCredetials)).thenReturn(dummyUser);
	when(mokedTokenCreator.issueTokenAsync(any(User.class))).thenReturn(dummyToken);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testRequestTokenAsync() throws InterruptedException, ExecutionException
    {
	
	UserToken token = tokenProviderToTest.requestTokenAsync(dummyCredetials).get();
	assertTrue(token.getToken().startsWith(dummyTokenString));
    }

    @Test
    public void testRequestTokenAsync2() throws InterruptedException, ExecutionException
    {
	long start = System.nanoTime();
	CompletableFuture<UserToken> token = tokenProviderToTest.requestTokenAsync(dummyCredetials);
	long invocationTime = ((System.nanoTime() - start)/1_000_000);
	assertTrue(invocationTime < 1000);
	assertTrue(token.get().getToken().startsWith(dummyTokenString));
	
    }
}
