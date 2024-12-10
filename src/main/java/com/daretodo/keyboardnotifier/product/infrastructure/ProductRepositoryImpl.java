package com.daretodo.keyboardnotifier.product.infrastructure;

import static com.daretodo.keyboardnotifier.product.infrastructure.QProductEntity.*;

import com.daretodo.keyboardnotifier.product.application.ProductRepository;
import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final JPAQueryFactory queryFactory;
    private static final int SIMILAR_PRODUCT_COUNT = 6;

    @Override
    public Integer saveAll(List<Product> products) {
        return productJpaRepository.saveAll(ProductEntity.fromDomain(products)).size();
    }

    @Override
    public Page<ProductEntity> findAllProducts(ProductStatus productStatus, ProductType productType,
                                               Pageable pageable, ProductSortBy sortBy) {
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

    @Override
    public ProductEntity findById(Long id) {
        return productJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Override
    public List<ProductEntity> findSimilarProducts(Long id) {
        ProductEntity product = findById(id);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(productEntity.type.eq(product.getType()));
        booleanBuilder.and(productEntity.endDate.after(LocalDateTime.now()));
        booleanBuilder.and(productEntity.id.ne(id));

        return queryFactory.selectFrom(productEntity)
                .where(booleanBuilder)
                .orderBy(productEntity.endDate.asc(), productEntity.viewCount.desc())
                .limit(SIMILAR_PRODUCT_COUNT)
                .fetch();
    }

    @Override
    public void updateViewCount(Long id) {
        findById(id).updateViewCount();
    }

}
