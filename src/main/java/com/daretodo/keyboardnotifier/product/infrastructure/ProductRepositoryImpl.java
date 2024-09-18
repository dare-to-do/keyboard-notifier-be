package com.daretodo.keyboardnotifier.product.infrastructure;

import static com.daretodo.keyboardnotifier.product.infrastructure.QProductEntity.*;

import com.daretodo.keyboardnotifier.product.application.ProductRepository;
import com.daretodo.keyboardnotifier.product.application.ProductResponse;
import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Integer saveAll(List<Product> products) {
        return productJpaRepository.saveAll(ProductEntity.fromDomain(products)).size();
    }

    @Override
    public Page<ProductEntity> findAllProducts(ProductStatus productStatus, ProductType productType, Pageable pageable, ProductSortBy sortBy) {
        BooleanBuilder builder = new BooleanBuilder();

        if (productStatus != null) {
            builder.and(productEntity.status.eq(productStatus));
        }
        if (productType != null) {
            builder.and(productEntity.type.eq(productType));
        }

        var query = queryFactory.selectFrom(productEntity).where(builder);

        if (sortBy != null) {
            switch (sortBy) {
                case NEWEST -> query.orderBy(productEntity.createdAt.desc());
                case HIGH_PRICE -> query.orderBy(productEntity.price.desc());
                case LOW_PRICE -> query.orderBy(productEntity.price.asc());
                case OLDEST -> query.orderBy(productEntity.createdAt.asc());
            }
        }

        List<ProductEntity> productEntities = query.offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(productEntities, pageable, () -> query.fetch().size());
    }
}
