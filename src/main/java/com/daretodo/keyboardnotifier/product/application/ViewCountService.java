package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductRepository;
import com.daretodo.keyboardnotifier.product.infrastructure.RetryPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ViewCountService {
    static final int MAX_RETRY_COUNT = 5;
    static final String ERROR_MESSAGE = "조회수 업데이트에 실패했습니다.";

    private final ProductRepository productRepository;

    private final RetryPolicy retryPolicy;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseViewCount(Product product) {
        retryPolicy.retryWithOptimisticLock(() -> productRepository.increaseViewCount(product), MAX_RETRY_COUNT,
                ERROR_MESSAGE);
    }
}
