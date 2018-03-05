package rfbujan.backend_taks1.token_provider;

import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junit.framework.TestCase;
import rfbujan.backend_taks1.authentication.TokenAuthenticator;
import rfbujan.backend_taks1.authentication.exception.TokenAuthentificationException;
import rfbujan.backend_taks1.model.*;
import rfbujan.backend_taks1.token_creation.TokenCreator;
import rfbujan.backend_taks1.token_creation.exception.TokenCreationException;

public class TokenProviderTask1Test extends TestCase
{
    @Mock
    TokenCreator mokedTokenCreator;
    @Mock
    TokenAuthenticator mockedTokenAuthenticator;
    @InjectMocks
    TokenProviderTask1 tokenProviderToTest;

    // create test data
    Credentials dummyCredetials = new Credentials("dummyUser", "dummyPassword");
    User dummyUser = new User("dummyUserId");
    UserToken dummyToken = new UserToken("dummyToken");
    Credentials dummyExceptionAuthentificationCredetials = new Credentials("dummyAuthExceptionUser",
	    "dummyAuthExceptionPassword");
    Credentials dummyExceptionCreatorCredetials = new Credentials("dummyCreationExceptionUser",
	    "dummyCreationExceptionPassword");
    User dummyExceptionUser = new User("dummyExceptionUser");

    UserToken invalidToken = new UserToken(TokenProviderTask1.INVALID_TOKEN);

    protected void setUp() throws Exception
    {
	MockitoAnnotations.initMocks(this);

	// Stubbing the methods of the mocked objects
	// nominal case
	when(mockedTokenAuthenticator.authenticate(dummyCredetials)).thenReturn(dummyUser);
	when(mokedTokenCreator.issueToken(dummyUser)).thenReturn(dummyToken);

	// authentification exception
	when(mockedTokenAuthenticator.authenticate(dummyExceptionAuthentificationCredetials))
		.thenThrow(new TokenAuthentificationException());

	// creation exception
	when(mockedTokenAuthenticator.authenticate(dummyExceptionCreatorCredetials)).thenReturn(dummyExceptionUser);
	when(mokedTokenCreator.issueToken(dummyExceptionUser)).thenThrow(new TokenCreationException());
    }

    @Test
    public void testRequestToken()
    {
	assertEquals(tokenProviderToTest.requestToken(dummyCredetials), dummyToken);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRequestTokenAsync() throws InterruptedException, ExecutionException
    {
	assertEquals(tokenProviderToTest.requestTokenAsync(dummyCredetials).get(), dummyToken);
    }

    @Test
    public void testRequestTokenInvalidAuthentificationTest()
    {
	assertEquals(tokenProviderToTest.requestToken(dummyExceptionAuthentificationCredetials), invalidToken);
    }

    @Test
    public void testRequestTokenAsyncInvalidAuthentificationTest() throws InterruptedException, ExecutionException
    {
	assertEquals(tokenProviderToTest.requestTokenAsync(dummyExceptionAuthentificationCredetials).get(),
		invalidToken);
    }

    @Test
    public void testRequestTokenInvalidCreationTest()
    {
	assertEquals(tokenProviderToTest.requestToken(dummyExceptionCreatorCredetials), invalidToken);
    }

    @Test
    public void testRequestTokenAsyncInvalidCreationTest() throws InterruptedException, ExecutionException
    {
	assertEquals(tokenProviderToTest.requestTokenAsync(dummyExceptionCreatorCredetials).get(), invalidToken);
    }

}
