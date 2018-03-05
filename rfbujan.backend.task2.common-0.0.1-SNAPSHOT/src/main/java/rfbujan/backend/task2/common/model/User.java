package rfbujan.backend.task2.common.model;

import net.jcip.annotations.ThreadSafe;

/**
 * User - Identifies a given customer within the system. For simplicity, it just contains userId which will match the
 * user name of the given customer.
 * <p>
 * When {@link #userId} starts with the keyword {@value #INVALID} then that {@link User} should be considered as
 * invalid.
 * <p>
 * 
 * This class is immutable and therefore thread-safe.
 * 
 */
@ThreadSafe
public class User
{
    private final String userId;

    public final static String INVALID = "invalid";

    public User(String userId)
    {
	super();
	this.userId = userId;
    }

    public String getUserId()
    {
	return userId;
    }

    /**
     * Factory method that creates an invalid {@link User}. When {@link #userId} starts with the keyword
     * {@value #INVALID} then that {@link User} should be considered as invalid.
     * 
     * @return invalid {@link User}
     */
    public static final User invalidUser()
    {
	return new User(INVALID);
    }

    /**
     * checks that the {@link #userId} is not <code>null</code> and that it does not start with "invalid" keyword.
     * 
     * @return <b>True</b> if the User is valid, <b>False</b> otherwise.
     */
    public boolean isValid()
    {
	return userId != null && !userId.equals("invalid");
    }

    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((userId == null) ? 0 : userId.hashCode());
	return result;
    }

    /**
     * Another object is considered equal to a User object if it is an instance of User and all attributes (i.e. userId)
     * are equals among each other.
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
	User other = (User) obj;
	if (userId == null)
	{
	    if (other.userId != null)
		return false;
	} else if (!userId.equals(other.userId))
	    return false;
	return true;
    }

}
