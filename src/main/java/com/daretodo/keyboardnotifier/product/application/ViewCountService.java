package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductRepository;
import com.daretodo.keyboardnotifier.product.infrastructure.RetryPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewCountService {
    static final int MAX_RETRY_COUNT = 5;
    static final String VIEW_COUNT_UPDATE_ERROR_MESSAGE = "조회수 업데이트에 실패했습니다.";

    private final ProductRepository productRepository;

    private final RetryPolicy retryPolicy;

    public void increaseViewCount(Product product) {
        retryPolicy.retryWithOptimisticLock(() -> productRepository.increaseViewCount(product), MAX_RETRY_COUNT,
                VIEW_COUNT_UPDATE_ERROR_MESSAGE);
    }
}
