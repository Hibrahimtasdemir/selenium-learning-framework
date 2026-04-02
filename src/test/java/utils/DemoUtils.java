package utils;

public final class DemoUtils {

    private static final long DEFAULT_PAUSE_MS = Long.getLong("demo.pause.ms", 1000L);

    private DemoUtils() {
    }

    public static void pause() {
        pause(DEFAULT_PAUSE_MS);
    }

    public static void pause(long milliseconds) {
        if (milliseconds <= 0) {
            return;
        }

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Demo pause was interrupted.", e);
        }
    }

    public static long getPauseDuration() {
        return DEFAULT_PAUSE_MS;
    }
}
