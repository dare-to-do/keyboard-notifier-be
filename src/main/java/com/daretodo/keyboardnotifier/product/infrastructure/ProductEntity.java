package com.daretodo.keyboardnotifier.product.infrastructure;

import com.daretodo.keyboardnotifier.common.BaseTimeEntity;
import com.daretodo.keyboardnotifier.product.domain.*;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startDate", column = @Column(name = "start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "end_date"))
    })
    private Period period;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private ProductStatus status;

    @Embedded
    @Column(name = "view_count")
    private ViewCount viewCount;

    @Version
    private Long version;

    public static ProductEntity fromDomain(Product product) {
        ProductEntity productEntity = new ProductEntity();
        String productImageUrl = convertFromListToString(product.getImageUrl());

        productEntity.name = product.getName();
        productEntity.price = product.getPrice();
        productEntity.priceUnit = product.getPriceUnit();
        productEntity.imageUrl = productImageUrl;
        productEntity.type = product.getType();
        productEntity.description = product.getDescription();
        productEntity.period = product.getPeriod().isEmpty() ? null : product.getPeriod().get();
        productEntity.status = product.getStatus();
        productEntity.createdAt = product.getCreatedAt();
        productEntity.createdBy = product.getCreatedBy();
        productEntity.updatedAt = product.getUpdatedAt();
        productEntity.updatedBy = product.getUpdatedBy();
        productEntity.viewCount = ViewCount.from(0L);
        return productEntity;
    }

    public static List<ProductEntity> fromDomain(List<Product> products) {
        List<ProductEntity> productEntities = new ArrayList<>();
        for (Product product : products) {
            productEntities.add(fromDomain(product));
        }
        return productEntities;
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

    public Product toProduct() {
        List<String> imageUrls = new ArrayList<>();
        if (imageUrl != null) {
            imageUrls.addAll(Arrays.asList(imageUrl.split(",")));
        }

        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .priceUnit(priceUnit)
                .imageUrl(imageUrls)
                .productUrl(productUrl)
                .type(type)
                .description(description)
                .period(period)
                .status(status)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .viewCount(viewCount)
                .build();
    }

    public void increaseViewCount() {
        viewCount.increaseViewCount();
    }
}
