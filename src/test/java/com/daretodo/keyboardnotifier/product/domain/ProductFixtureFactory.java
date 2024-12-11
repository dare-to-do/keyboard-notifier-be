package com.daretodo.keyboardnotifier.product.domain;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;

public class ProductFixtureFactory {
    private final ArbitraryBuilder<Product> productBuilder;

    public ProductFixtureFactory() {
        this.productBuilder = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .defaultNotNull(true)
                .build()
                .giveMeBuilder(Product.class);
    }

    public ProductFixtureBuilder create() {
        return new ProductFixtureBuilder(productBuilder);
    }
}