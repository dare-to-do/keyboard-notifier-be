package com.daretodo.keyboardnotifier.product.infrastructure;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
public class RetryPolicy {

    public void retryWithOptimisticLock(Runnable task, int maxRetryCount, String errorMessage) {
        int retryCount = 0;

        while (shouldRetry(retryCount, maxRetryCount)) {
            try {
                task.run();
                return;
            } catch (OptimisticLockingFailureException e) {
                retryBackOff(retryCount++);
            }
        }

        throw new RuntimeException(errorMessage);
    }

    private boolean shouldRetry(int retryCount, int maxRetryCount) {
        return retryCount < maxRetryCount;
    }

    private void retryBackOff(int retryCount) {
        long backoffTime = (long) Math.pow(2, retryCount);
        try {
            Thread.sleep(backoffTime);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}