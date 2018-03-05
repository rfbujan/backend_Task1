package rfbujan.backend.task2.common.utils;

import java.util.Random;

/**
 * Utility class that gathers common util functionality for the task 2 solution.
 * <p>
 */
public class Utils
{
    /**
     * instance of {@link Random} class.
     */
    public static final Random random = new Random();

    /**
     * Method to simulate a random delay between 0 and 5 seconds.
     */
    public static void randomDelay()
    {
	int delay = random.nextInt(5000);
	try
	{
	    Thread.sleep(delay);
	} catch (InterruptedException e)
	{
	    throw new RuntimeException(e);
	}
    }
}
