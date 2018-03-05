package rfbujan.backend_taks1.model;

import net.jcip.annotations.ThreadSafe;

/**
 * User - Identifies a given customer within the system. For simplicity, it just
 * contains userId which will match the user name of the given customer.
 * 
 * This class is immutable and therefore thread-safe.
 * 
 */
@ThreadSafe
public class User
{
    public final String userId;

    public User(String userName)
    {
	super();
	this.userId = userName;
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
