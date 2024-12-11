package com.daretodo.keyboardnotifier.product.infrastructure;

import com.daretodo.keyboardnotifier.common.BaseTimeEntity;
import com.daretodo.keyboardnotifier.product.domain.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Entity
@Table(name = "product")
@Getter
public class ProductEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_unit")
    private PriceUnit priceUnit;

    @Column(name = "image_url", length = 5000)
    private String imageUrl;

    @Column(name = "product_url", length = 500)
    private String productUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 40)
    private ProductType type;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private ProductStatus status;

    @Column(name = "view_count")
    private Long viewCount;

    @Version
    private Long version;

    public static ProductEntity fromDomain(Product product) {
        ProductEntity productEntity = new ProductEntity();
        String productImageUrl = convertFromListToString(product.getImageUrl());

        productEntity.id = product.getId();
        productEntity.name = product.getName();
        productEntity.price = product.getPrice();
        productEntity.priceUnit = product.getUnit();
        productEntity.imageUrl = productImageUrl;
        productEntity.type = product.getProductType();
        productEntity.description = product.getDescription();
        productEntity.startDate = product.getPeriod().startDate();
        productEntity.endDate = product.getPeriod().endDate();
        productEntity.status = product.getStatus();
        productEntity.createdAt = product.getCreatedAt();
        productEntity.createdBy = product.getCreatedBy();
        productEntity.updatedAt = product.getUpdatedAt();
        productEntity.updatedBy = product.getUpdatedBy();
        productEntity.viewCount = 0L;
        productEntity.version = 0L;
        return productEntity;
    }

    public static List<ProductEntity> fromDomain(List<Product> products) {
        List<ProductEntity> productEntities = new ArrayList<>();
        for (Product product : products) {
            productEntities.add(fromDomain(product));
        }
        return productEntities;
    }

    public Product toProduct() {
        List<String> imageUrls = new ArrayList<>();
        for (String imageUrl : imageUrl.split(",")) {
            imageUrls.add(imageUrl);
        }
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .unit(priceUnit)
                .imageUrl(imageUrls)
                .productUrl(productUrl)
                .productType(type)
                .description(description)
                .period(Period.of(startDate, endDate))
                .status(status)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .build();
    }

    private static String convertFromListToString(List<String> imageUrls) {
        StringBuilder sb = new StringBuilder();
        for (String imageUrl : imageUrls) {
            sb.append(imageUrl).append(",");
        }
        if (sb.isEmpty()) {
            return "";
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public void updateViewCount() {
        this.viewCount++;
    }
}
