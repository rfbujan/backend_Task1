package rfbujan.backend.task2.common.model;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import net.jcip.annotations.ThreadSafe;

/**
 * UserToken - Token granted to a user in order to perform further operations in the system. It is the concatenation of
 * the userId and the current time. For example: user123_2017-01-01T10:00:00.000
 * <p>
 * When {@link #token} starts with the keyword {@value #INVALID} then that {@link UserToken} should be considered as
 * invalid.
 * <p>
 * This class is immutable and therefore thread-safe
 *
 */
@ThreadSafe
public class UserToken
{
    private final String token;
    public final static String INVALID = "invalid";

    public UserToken(String userId)
    {
	super();
	this.token = generateTokenWithUTC(userId);
    }

    /**
     * Factory method that creates an invalid {@link UserToken}. When {@link #token} starts with the keyword
     * {@value #INVALID} then that {@link UserToken} should be considered as invalid.
     * 
     * @return
     * 		invalid {@link UserToken}
     */
    public static final UserToken invalidUserToken()
    {
	return new UserToken(INVALID);
    }

    /**
     * checks that the {@link #userId} is not <code>null</code> and that it does not start with "invalid" keyword.
     * 
     * @return <b>True</b> if the User is valid, <b>False</b> otherwise.
     */
    public boolean isValid()
    {
	return token != null && !token.startsWith("invalid");
    }
    
    public String getToken()
    {
	return token;
    }

    private String generateTokenWithUTC(String userId)
    {
	String utc = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
	return userId + utc;
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((token == null) ? 0 : token.hashCode());
	return result;
    }

    /**
     * Another object is considered equal to a UserToken object if it is an instance of UserToken and all attributes
     * (i.e. token) are equals among each other.
     * <p>
     */
    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	UserToken other = (UserToken) obj;
	if (token == null)
	{
	    if (other.token != null)
		return false;
	} else if (!token.equals(other.token))
	    return false;
	return true;
    }

}
