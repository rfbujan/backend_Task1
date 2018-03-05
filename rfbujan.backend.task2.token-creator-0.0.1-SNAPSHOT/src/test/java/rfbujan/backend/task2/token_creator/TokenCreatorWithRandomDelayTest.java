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

public class TokenCreatorWithRandomDelayTest
{

    // object to be tested
    TokenCreatorWithRandomDelay serviceUnderTest;

    // test data
    String userName = "house";
    User validUser;
    User invalidUser;
    User nullUser;

    @Before
    public void setUp() throws Exception
    {
	serviceUnderTest = new TokenCreatorWithRandomDelay();
	validUser = new User(userName);
	// If the password matches the username in uppercase, the validation is a
	// success, otherwise is a failure
	invalidUser = new User('A' + userName);
	nullUser = new User(null);
	

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testIssueTokenAsync() throws InterruptedException, ExecutionException
    {
	ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
	UserToken result = serviceUnderTest.issueTokenAsync(validUser).get();

	assertTrue(result.getToken().startsWith(userName));
	String resultTimeString = result.getToken().substring(userName.length());

	// Check that the format of the timestamp is correst

	try
	{
	    DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;
	    FORMATTER.parse(resultTimeString);

	} catch (DateTimeParseException e)
	{
	    fail("The timestamp used in the usertoken is contains the wrong format");
	}
	ZonedDateTime resultTime = ZonedDateTime.parse(resultTimeString);
	// Check that the value of the time stamp is in a valid time range
	assertTrue("the time stamp used within the usertoken is out of the valid time range. is smaller!",
		now.isBefore(resultTime));
	assertTrue("the time stamp used within the usertoken is out of the valid time range. is greater!",
		now.plusSeconds(5).isAfter(resultTime));

    }

    @Test
    public void testIssueTokenAsyncInvalidUser() throws InterruptedException, ExecutionException
    {
	UserToken result = serviceUnderTest.issueTokenAsync(invalidUser).get();
	assertTrue(result.getToken().startsWith("invalid"));
    }
    
    @Test
    public void testIssueTokenAsyncNullUser() throws InterruptedException, ExecutionException
    {
	UserToken result = serviceUnderTest.issueTokenAsync(nullUser).get();
	assertTrue(result.getToken().startsWith("invalid"));
    }
}
