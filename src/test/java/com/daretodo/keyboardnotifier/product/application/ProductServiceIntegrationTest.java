package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.controller.ProductsRequestCondition;
import com.daretodo.keyboardnotifier.product.domain.*;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
class ProductServiceIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품을_생성한다() {
        // given
        var sut = new ProductService(productRepository);
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        Product product1 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 1L)
                .set(javaGetter(Product::getName), "Product A")
                .setNotNull(javaGetter(Product::getPeriod))
                .sample();
        Product product2 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 2L)
                .set(javaGetter(Product::getName), "Product B")
                .setNotNull(javaGetter(Product::getPeriod))
                .sample();
        List<Product> products = List.of(product1, product2);

        // when
        var result = sut.createProducts(products);

        // then
        assertThat(result).isEqualTo(2);
    }

    @Test
    void 전체_상품을_조회한다() {
        // given
        var sut = new ProductService(productRepository);
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        Product product1 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 1L)
                .set(javaGetter(Product::getName), "Product A")
                .set(javaGetter(Product::getCreatedAt), LocalDateTime.now().minusDays(1))
                .setNotNull(javaGetter(Product::getUnit))
                .setNotNull(javaGetter(Product::getPeriod))
                .sample();
        Product product2 = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 2L)
                .set(javaGetter(Product::getName), "Product B")
                .set(javaGetter(Product::getCreatedAt), LocalDateTime.now())
                .setNotNull(javaGetter(Product::getUnit))
                .setNotNull(javaGetter(Product::getPeriod))
                .sample();

        ProductsRequestCondition condition = fixtureMonkey.giveMeBuilder(ProductsRequestCondition.class)
                .set(javaGetter(ProductsRequestCondition::getPage), 1)
                .set(javaGetter(ProductsRequestCondition::getSize), 10)
                .sample();

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
        var sut = new ProductService(productRepository);
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();

        Product product = fixtureMonkey.giveMeBuilder(Product.class)
                .set(javaGetter(Product::getId), 1L)
                .set(javaGetter(Product::getName), "Product A")
                .set(javaGetter(Product::getPrice), 1000L)
                .set(javaGetter(Product::getUnit), PriceUnit.KRW)
                .setNotNull(javaGetter(Product::getPeriod))
                .sample();

        List<Product> products = List.of(product);
        productRepository.saveAll(products);

        // when
        var result = sut.findProduct(1L);

        // then
        assertThat(result.name()).isEqualTo("Product A");
        assertThat(result.price()).isEqualTo(1000L);
    }

}