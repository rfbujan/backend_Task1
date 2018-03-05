package rfbujan.backend.task2.athenticator;

import java.util.concurrent.ExecutionException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;
import rfbujan.backend.task2.common.model.Credentials;
import rfbujan.backend.task2.common.model.User;

public class TokenAuthenticatorRandomDelayTest extends TestCase
{
    //object to be tested
    TokenAuthenticatorRandomDelay serviceUnderTest;
    
    //test data
    String userName = "house";
    User validUser;
    User invalidUser;
    Credentials validCredentials;
    Credentials invalidCredentials;
    
    protected void setUp() throws Exception
    {
	serviceUnderTest = new TokenAuthenticatorRandomDelay();
	validUser = new User(userName);
	invalidUser = User.invalidUser();
	
	//If the password matches the username in uppercase, the validation is a success, otherwise is a failure
	validCredentials = new Credentials(userName, userName.toUpperCase());
	invalidCredentials = new Credentials(userName, userName);
	
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testAuthenticateAsyncValidCredentials() throws InterruptedException, ExecutionException
    {
	User result = serviceUnderTest.authenticateAsync(validCredentials).get();
	assertEquals(validUser, result);
    }
    @Test
    public void testAuthenticateAsyncInvalidCredentials() throws InterruptedException, ExecutionException
    {
	User result = serviceUnderTest.authenticateAsync(invalidCredentials).get();
	assertEquals(invalidUser, result);
    }
    
    
}
