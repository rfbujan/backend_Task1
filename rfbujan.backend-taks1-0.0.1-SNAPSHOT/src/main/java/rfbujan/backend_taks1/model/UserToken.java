package rfbujan.backend_taks1.model;

/**
 * UserToken - Token granted to a user in order to perform further operations in
 * the system. It is the concatenation of the userId and the current time. For
 * example: user123_2017-01-01T10:00:00.000
 * 
 * @author rafb
 *
 */
public class UserToken
{
    public final String token;

    public UserToken(String token)
    {
	super();
	this.token = token;
    }

    public String getToken()
    {
        return token;
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
     * Another object is considered equal to a UserToken object if it is an
     * instance of UserToken and all attributes (i.e. token) are
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
