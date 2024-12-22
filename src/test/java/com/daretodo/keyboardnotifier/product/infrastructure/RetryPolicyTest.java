package com.daretodo.keyboardnotifier.product.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.OptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RetryPolicyTest {

    private RetryPolicy retryPolicy;
    private Runnable task;
    private int maxRetryCount;
    private String errorMessage;

    @BeforeEach
    public void setup() {
        retryPolicy = new RetryPolicy();
        task = mock(Runnable.class);
        maxRetryCount = 3;
        errorMessage = "error message";
    }

    @Test
    public void OptimisticLockingFailureException_발생안하면_한번만_실행() {
        // given
        doNothing().when(task).run();

        // when
        retryPolicy.retryWithOptimisticLock(task, maxRetryCount, errorMessage);

        // then
        verify(task, times(1)).run();
    }

    @Test
    public void 최대_재시도_수보다_더_시도시_RuntimeException() {
        // given
        doThrow(new OptimisticLockingFailureException("")).when(task).run();

        // when & then
        assertThrows(RuntimeException.class, () -> {
            retryPolicy.retryWithOptimisticLock(task, maxRetryCount, errorMessage);
        }, errorMessage);
    }

    @Test
    public void OptimisticLockingFailureException_발생시_지수_백오프() {
        // given
        doThrow(new OptimisticLockingFailureException(""))
                .doNothing().when(task).run();

        // when & then
        retryPolicy.retryWithOptimisticLock(task, maxRetryCount, errorMessage);
        verify(task, times(2)).run();
    }

}