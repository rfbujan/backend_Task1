package rfbujan.backend.task2.token_creator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import rfbujan.backend.task2.common.model.User;
import rfbujan.backend.task2.common.model.UserToken;

public class TokenCreatorWithRandomDelayExceptionTest
{

    // object to be tested
    TokenCreatorWithRandomDelay serviceUnderTest;

    // test data
    User nullUser;

    @Before
    public void setUp() throws Exception
    {
	serviceUnderTest = new TokenCreatorWithRandomDelay();
	nullUser = new User(null);
	

    }

    @Test
    public void testIssueTokenAsyncCurruptedUser() throws InterruptedException, ExecutionException
    {
	try
	{
	   serviceUnderTest.issueTokenAsync(nullUser).get();
	    fail("IllegalArgumentException expected at this point");
	} catch (IllegalArgumentException e)
	{
	    assertTrue(true);
	}
	
    }
    
    @Test
    public void testIssueTokenAsyncNullUser() throws InterruptedException, ExecutionException
    {
	try
	{
	    serviceUnderTest.issueTokenAsync(null).get();
	    fail("IllegalArgumentException expected at this point");
	} catch (IllegalArgumentException e)
	{
	    assertTrue(true);
	}
	
    }
}
