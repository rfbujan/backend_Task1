package rfbujan.backend.task2.athenticator;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

import junit.framework.TestCase;
import rfbujan.backend.task2.common.model.Credentials;

public final class TokenAuthenticatorRandomDelayExceptionTest extends TestCase
{
    // object to be tested
    TokenAuthenticatorRandomDelay serviceUnderTest;

    // test data
    Credentials nullCredentials;

    protected void setUp() throws Exception
    {
	serviceUnderTest = new TokenAuthenticatorRandomDelay();

	// If the password matches the username in uppercase, the validation is a success, otherwise is a failure
	nullCredentials = new Credentials(null, null);

    }

    @Test
    public void testAuthenticateAsyncCurruptedCredentials() throws InterruptedException, ExecutionException
    {

	try
	{
	    serviceUnderTest.authenticateAsync(nullCredentials).get();
	    fail("IllegalArgumentException expected at this point");
	} catch (IllegalArgumentException e)
	{
	    assertTrue(true);
	}
    }

    @Test
    public void testAuthenticateAsyncNullCredentials() throws InterruptedException, ExecutionException
    {
	try
	{
	    serviceUnderTest.authenticateAsync(null).get();
	    fail("IllegalArgumentException expected at this point");
	} catch (IllegalArgumentException e)
	{
	    assertTrue(true);
	}

    }

}
