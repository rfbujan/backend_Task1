package rfbujan.backend.task2.common.model;

/**
 * Credentials - A tuple of username and password that are used to authenticate
 * a customer.
 * 
 * @author rafb
 *
 */
public class Credentials
{
    private final String username;
    private final String password;
    
    public Credentials(String username, String password)
    {
	super();
	this.username = username;
	this.password = password;
	
    }
    
    
    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    @Override
    public int hashCode()
    {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((username == null) ? 0 : username.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Credentials other = (Credentials) obj;
	if (password == null)
	{
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	if (username == null)
	{
	    if (other.username != null)
		return false;
	} else if (!username.equals(other.username))
	    return false;
	return true;
    }

    
}
