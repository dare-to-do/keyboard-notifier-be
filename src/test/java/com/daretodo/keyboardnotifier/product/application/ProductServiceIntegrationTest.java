package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.controller.ProductsRequestCondition;
import com.daretodo.keyboardnotifier.product.domain.*;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService sut;

    @Autowired
    private ProductRepository productRepository;

    private ProductFixtureFactory productFixtureFactory;
    private ProductFixtureBuilder productFixtureBuilder;

    @BeforeEach
    void setUp() {
        productFixtureFactory = new ProductFixtureFactory();
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
    @Transactional(readOnly = true)
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

        List<Product> products = List.of(product1, product2);
        productRepository.saveAll(products);

        // when
        var result = sut.findAllProducts(null, null, condition.getPageable(), ProductSortBy.NEWEST);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(0).name()).isEqualTo("Product B");
        assertThat(result.getContent().get(1).name()).isEqualTo("Product A");
    }

    @Test
    void 특정_상품을_조회한다() {
        // given
        Product product = productFixtureBuilder
                .name("Product A")
                .price(1000L)
                .build();

        List<Product> products = List.of(product);
        productRepository.saveAll(products);

        // when
        var result = sut.findProduct(1L);

        // then
        assertThat(result.name()).isEqualTo("Product A");
        assertThat(result.price()).isEqualTo(1000L);
    }

    @Test
    @Transactional(readOnly = true)
    void 유사한_상품을_조회한다() {
        // given
        List<Product> products = getProductsWithNameTypePeriod();
        productRepository.saveAll(products);

        // when
        var result = sut.findSimilarProducts(1L);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).name()).isEqualTo("Product D");
        assertThat(result.get(1).name()).isEqualTo("Product E");
    }

    private List<Product> getProductsWithNameTypePeriod() {
        Product product1 = productFixtureBuilder
                .name("Product A")
                .productType(ProductType.KEYBOARD)
                .period(createPeriod(1))
                .build();
        Product product2 = productFixtureBuilder
                .name("Product B")
                .productType(ProductType.PARTS)
                .period(createPeriod(2))
                .build();
        Product product3 = productFixtureBuilder
                .name("Product C")
                .productType(ProductType.KEYCAP)
                .period(createPeriod(3))
                .build();
        Product product4 = productFixtureBuilder
                .name("Product D")
                .productType(ProductType.KEYBOARD)
                .period(createPeriod(4))
                .build();
        Product product5 = productFixtureBuilder
                .name("Product E")
                .productType(ProductType.KEYBOARD)
                .period(createPeriod(5))
                .build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);
        return products;
    }

    private Period createPeriod(int index) {
        LocalDateTime now = LocalDateTime.now();
        return Period.of(now.minusDays(index), now.plusDays(index));
    }
}