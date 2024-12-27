package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.ProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventListener {
    private final ViewCountService viewCountService;

    @EventListener
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
