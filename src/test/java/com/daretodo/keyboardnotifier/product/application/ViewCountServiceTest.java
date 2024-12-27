package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.infrastructure.RetryPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.daretodo.keyboardnotifier.product.application.ViewCountService.VIEW_COUNT_UPDATE_ERROR_MESSAGE;
import static com.daretodo.keyboardnotifier.product.application.ViewCountService.MAX_RETRY_COUNT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ViewCountServiceTest {

    @Mock
    private RetryPolicy retryPolicy;

    @InjectMocks
    private ViewCountService viewCountService;

    private Product product;

    @BeforeEach
    public void setup() {
        product = Product.builder().build();
    }

    @Test
    public void 조회수_증가_성공_시_종료() {
        // given
        doNothing().when(retryPolicy).retryWithOptimisticLock(any(Runnable.class), anyInt(), anyString());

        // when
        viewCountService.increaseViewCount(product);

        // then
        verify(retryPolicy, times(1)).retryWithOptimisticLock(any(Runnable.class), eq(MAX_RETRY_COUNT), eq(VIEW_COUNT_UPDATE_ERROR_MESSAGE));
    }

    @Test
    public void 최대_재시도_수보다_더_시도시_RuntimeException() {
        // given
        doThrow(new RuntimeException()).when(retryPolicy).retryWithOptimisticLock(any(Runnable.class), anyInt(), anyString());

        // when & then
        assertThrows(RuntimeException.class, () -> viewCountService.increaseViewCount(product), VIEW_COUNT_UPDATE_ERROR_MESSAGE);
    }
}