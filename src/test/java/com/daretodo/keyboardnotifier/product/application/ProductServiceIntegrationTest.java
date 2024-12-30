package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.controller.ProductsRequestCondition;
import com.daretodo.keyboardnotifier.product.domain.*;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductEntity;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductJpaRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.daretodo.keyboardnotifier.product.infrastructure.ProductRepositoryImpl.SIMILAR_PRODUCT_COUNT;
import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService sut;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private ProductFixtureBuilder productFixtureBuilder;

    @BeforeEach
    void setUp() {
        ProductFixtureFactory productFixtureFactory = new ProductFixtureFactory();
        productFixtureBuilder = productFixtureFactory.create();
    }

    @Test
    void 상품을_생성한다() {
        // given
        Product product1 = productFixtureBuilder
                .name("Product A")
                .build();
        Product product2 = productFixtureBuilder
                .name("Product B")
                .build();
        List<Product> products = List.of(product1, product2);

        // when
        var result = sut.createProducts(products);

        // then
        assertThat(result).isEqualTo(2);
    }

    @Test
    void 전체_상품을_조회한다() {
        // given
        ProductsRequestCondition condition = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build()
                .giveMeBuilder(ProductsRequestCondition.class)
                .set(javaGetter(ProductsRequestCondition::getPage), 1)
                .set(javaGetter(ProductsRequestCondition::getSize), 10)
                .sample();

        Product product1 = productFixtureBuilder
                .name("Product A")
                .build();
        Product product2 = productFixtureBuilder
                .name("Product B")
                .build();

        List<ProductEntity> products = List.of(ProductEntity.fromDomain(product1), ProductEntity.fromDomain(product2));
        productJpaRepository.saveAll(products);

        // when
        var result = sut.findAllProducts(null, null, condition.getPageable(), ProductSortBy.NEWEST);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(0).name()).isEqualTo("Product B");
        assertThat(result.getContent().get(1).name()).isEqualTo("Product A");
    }

    @Test
    void 단일_상품을_조회한다() {
        // given
        Product product = productFixtureBuilder
                .name("Product A")
                .price(1000L)
                .build();

        ProductEntity productEntity = ProductEntity.fromDomain(product);
        ProductEntity saved = productJpaRepository.save(productEntity);

        // when
        var result = sut.findProduct(saved.getId());

        // then
        assertThat(result.name()).isEqualTo("Product A");
        assertThat(result.price()).isEqualTo(1000L);
    }

    @Test
    void 유사한_상품을_조회한다() {
        // given
        List<ProductEntity> products = getProductEntitiesWithNameTypePeriod();

        List<ProductEntity> savedEntities = productJpaRepository.saveAll(products);

        // when
        var result = sut.findSimilarProducts(savedEntities.get(0).getId());

        // then
        assertThat(result.size()).isEqualTo(SIMILAR_PRODUCT_COUNT);
        assertThat(result.get(0).name()).isEqualTo("Product D");
        assertThat(result.get(1).name()).isEqualTo("Product E");
    }

    private List<ProductEntity> getProductEntitiesWithNameTypePeriod() {
        Product product1 = productFixtureBuilder
                .id(1L)
                .name("Product A")
                .productType(ProductType.KEYBOARD)
                .period(createPeriod(1))
                .build();
        Product product2 = productFixtureBuilder
                .id(2L)
                .name("Product B")
                .productType(ProductType.PARTS)
                .period(createPeriod(2))
                .build();
        Product product3 = productFixtureBuilder
                .id(3L)
                .name("Product C")
                .productType(ProductType.KEYCAP)
                .period(createPeriod(3))
                .build();
        Product product4 = productFixtureBuilder
                .id(4L)
                .name("Product D")
                .productType(ProductType.KEYBOARD)
                .period(createPeriod(4))
                .build();
        Product product5 = productFixtureBuilder
                .id(5L)
                .name("Product E")
                .productType(ProductType.KEYBOARD)
                .period(createPeriod(5))
                .build();
        Product product6 = productFixtureBuilder
                .id(6L)
                .name("Product F")
                .productType(ProductType.KEYCAP)
                .period(createPeriod(6))
                .build();
        Product product7 = productFixtureBuilder
                .id(7L)
                .name("Product G")
                .productType(ProductType.KIT)
                .period(createPeriod(7))
                .build();

        return List.of(
                ProductEntity.fromDomain(product1),
                ProductEntity.fromDomain(product2),
                ProductEntity.fromDomain(product3),
                ProductEntity.fromDomain(product4),
                ProductEntity.fromDomain(product5),
                ProductEntity.fromDomain(product6),
                ProductEntity.fromDomain(product7)
        );
    }

    private Period createPeriod(int index) {
        LocalDateTime now = LocalDateTime.now();
        return Period.of(now.minusDays(index), now.plusDays(index));
    }
}