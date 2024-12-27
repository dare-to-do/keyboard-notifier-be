package com.daretodo.keyboardnotifier.product.infrastructure;

import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductRepository;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.daretodo.keyboardnotifier.product.infrastructure.QProductEntity.productEntity;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private static final int SIMILAR_PRODUCT_COUNT = 6;
    private final ProductJpaRepository productJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Integer saveAll(List<Product> products) {
        return productJpaRepository.saveAll(ProductEntity.fromDomain(products)).size();
    }

    @Override
    public Page<Product> findAllProducts(ProductStatus productStatus, ProductType productType,
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

        List<Product> products = productEntities.stream().map(ProductEntity::toProduct).toList();
        return PageableExecutionUtils.getPage(products, pageable, () -> query.fetch().size());
    }

    @Override
    public Product findById(Long id) {
        ProductEntity productEntity = findProductEntityById(id);
        return productEntity.toProduct();
    }

    @Override
    public List<Product> findSimilarProducts(Long id) {
        Product product = findById(id);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(productEntity.type.eq(product.getType()));
        booleanBuilder.and(productEntity.period.endDate.after(LocalDateTime.now()));
        booleanBuilder.and(productEntity.id.ne(id));

        List<ProductEntity> productEntities = queryFactory.selectFrom(productEntity)
                .where(booleanBuilder)
                .orderBy(productEntity.period.endDate.asc(), productEntity.viewCount.viewCount.desc())
                .limit(SIMILAR_PRODUCT_COUNT)
                .fetch();

        return productEntities.stream().map(ProductEntity::toProduct).toList();
    }

    @Override
    public void increaseViewCount(Product product) {
        ProductEntity productEntity = findProductEntityById(product.getId());
        productEntity.increaseViewCount();
    }

    private ProductEntity findProductEntityById(Long id) {
        return productJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
}
