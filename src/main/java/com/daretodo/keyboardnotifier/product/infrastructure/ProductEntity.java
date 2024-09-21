package com.daretodo.keyboardnotifier.product.infrastructure;

import com.daretodo.keyboardnotifier.common.BaseTimeEntity;
import com.daretodo.keyboardnotifier.product.domain.Period;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "image_url")
    private String imageUrl;

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

    public static ProductEntity fromDomain(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.id = product.getId();
        productEntity.name = product.getName();
        productEntity.price = product.getPrice();
        productEntity.imageUrl = product.getImageUrl();
        productEntity.type = product.getProductType();
        productEntity.description = product.getDescription();
        productEntity.startDate = product.getPeriod().startDate();
        productEntity.endDate = product.getPeriod().endDate();
        productEntity.status = product.getStatus();
        productEntity.createdAt = product.getCreatedAt();
        productEntity.createdBy = product.getCreatedBy();
        productEntity.updatedAt = product.getUpdatedAt();
        productEntity.updatedBy = product.getUpdatedBy();
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
        return Product.builder()
            .id(id)
            .name(name)
            .price(price)
            .imageUrl(imageUrl)
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
}
