package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductEvent;
import com.daretodo.keyboardnotifier.product.domain.ProductEvent.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventService {
    private final ProductEventPublisher productEventPublisher;

    public void publishReadEvent(Product product) {
        ProductEvent productEvent = ProductEvent.of(product, EventType.READ);
        productEventPublisher.publish(productEvent);
    }

}
