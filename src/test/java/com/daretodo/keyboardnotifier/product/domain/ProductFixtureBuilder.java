package com.daretodo.keyboardnotifier.product.domain;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;


public class ProductFixtureBuilder {
    private final ArbitraryBuilder<Product> builder;
    private final Map<String, Object> fields = new HashMap<>();

    public ProductFixtureBuilder(ArbitraryBuilder<Product> builder) {
        this.builder = builder;
    }

    public ProductFixtureBuilder id(Long id) {
        fields.put("id", id);
        return this;
    }

    public ProductFixtureBuilder name(String name) {
        fields.put("name", name);
        return this;
    }

    public ProductFixtureBuilder price(Long price) {
        fields.put("price", price);
        return this;
    }

    public ProductFixtureBuilder unit(PriceUnit unit) {
        fields.put("unit", unit);
        return this;
    }

    public ProductFixtureBuilder imageUrl(List<String> imageUrl) {
        fields.put("imageUrl", imageUrl);
        return this;
    }

    public ProductFixtureBuilder productUrl(String productUrl) {
        fields.put("productUrl", productUrl);
        return this;
    }

    public ProductFixtureBuilder productType(ProductType productType) {
        fields.put("productType", productType);
        return this;
    }

    public ProductFixtureBuilder description(String description) {
        fields.put("description", description);
        return this;
    }

    public ProductFixtureBuilder period(Period period) {
        fields.put("period", period);
        return this;
    }

    public ProductFixtureBuilder status(ProductStatus status) {
        fields.put("status", status);
        return this;
    }

    public ProductFixtureBuilder createdAt(LocalDateTime createdAt) {
        fields.put("createdAt", createdAt);
        return this;
    }

    public ProductFixtureBuilder createdBy(String createdBy) {
        fields.put("createdBy", createdBy);
        return this;
    }

    public ProductFixtureBuilder updatedAt(LocalDateTime updatedAt) {
        fields.put("updatedAt", updatedAt);
        return this;
    }

    public ProductFixtureBuilder updatedBy(String updatedBy) {
        fields.put("updatedBy", updatedBy);
        return this;
    }

    public Product build() {
        fields.forEach((fieldName, value) ->
                builder.set(getField(fieldName), value)
        );
        return builder.sample();
    }

    private JavaGetterMethodPropertySelector<Product, ?> getField(String fieldName) {
        return switch (fieldName) {
            case "id" -> javaGetter(Product::getId);
            case "name" -> javaGetter(Product::getName);
            case "price" -> javaGetter(Product::getPrice);
            case "unit" -> javaGetter(Product::getUnit);
            case "imageUrl" -> javaGetter(Product::getImageUrl);
            case "productUrl" -> javaGetter(Product::getProductUrl);
            case "productType" -> javaGetter(Product::getProductType);
            case "description" -> javaGetter(Product::getDescription);
            case "period" -> javaGetter(Product::getPeriod);
            case "status" -> javaGetter(Product::getStatus);
            case "createdAt" -> javaGetter(Product::getCreatedAt);
            case "createdBy" -> javaGetter(Product::getCreatedBy);
            case "updatedAt" -> javaGetter(Product::getUpdatedAt);
            case "updatedBy" -> javaGetter(Product::getUpdatedBy);
            default -> throw new IllegalArgumentException("Unknown field name: " + fieldName);
        };
    }
}

