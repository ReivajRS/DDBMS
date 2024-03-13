package Extra;

public class Routines {
    public static boolean someThreadIsRunning(Thread[] threads) {
        for (Thread thread : threads)
            if (thread.isAlive()) {
                return true;
            }
        return false;
    }
}
