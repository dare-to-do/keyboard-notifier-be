package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.ProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ProductEventListener {
    private final ViewCountService viewCountService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleProductEvent(ProductEvent event) {
        handleProductReadEvent(event);
    }

    private void handleProductReadEvent(ProductEvent event) {
        if (!event.isReadEvent()) {
            return;
        }
        viewCountService.increaseViewCount(event.getProduct());
    }

}
