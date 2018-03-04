package rfbujan.backend.task2.common.model;

/**
 * User - Identifies a given customer within the system. For simplicity, it just
 * contains userId which will match the username of the given customer.
 * 
 * @author rafb
 *
 */
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

    public static final User invalidUser()
    {
	return new User(INVALID);
    }

    public boolean startsWithA()
    {
	return userId.startsWith("A");
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
     * Another object is considered equal to a User object if it is an
     * instance of User and all attributes (i.e. userId) are
     * equals among each other.
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
