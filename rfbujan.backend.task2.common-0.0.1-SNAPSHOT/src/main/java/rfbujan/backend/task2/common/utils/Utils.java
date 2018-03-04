package rfbujan.backend.task2.common.utils;

import java.util.Random;

public class Utils
{
    public static final Random random = new Random();

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
