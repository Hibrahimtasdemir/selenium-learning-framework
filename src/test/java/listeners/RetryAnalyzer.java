package listeners;

import config.FrameworkConfig;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Map<String, Integer> RETRY_COUNTS = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        int maxRetryCount = FrameworkConfig.getRetryCount();

        if (maxRetryCount <= 0) {
            return false;
        }

        String retryKey = buildRetryKey(result);
        int currentRetryCount = RETRY_COUNTS.getOrDefault(retryKey, 0);

        if (currentRetryCount < maxRetryCount) {
            int nextRetryAttempt = currentRetryCount + 1;
            RETRY_COUNTS.put(retryKey, nextRetryAttempt);
            result.setAttribute("retry.pending", true);
            result.setAttribute("retry.attempt", nextRetryAttempt);
            return true;
        }

        RETRY_COUNTS.remove(retryKey);
        return false;
    }

    public static void clearRetryState(ITestResult result) {
        RETRY_COUNTS.remove(buildRetryKey(result));
    }

    private static String buildRetryKey(ITestResult result) {
        return result.getTestClass().getName()
                + "#"
                + result.getMethod().getMethodName()
                + "#"
                + Arrays.deepToString(result.getParameters());
    }
}
