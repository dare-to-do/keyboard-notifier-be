package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.ProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(ProductEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
