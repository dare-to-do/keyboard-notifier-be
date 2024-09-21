package com.daretodo.keyboardnotifier.product.infrastructure;

import static org.assertj.core.api.Assertions.*;

import com.daretodo.keyboardnotifier.config.QuerydslConfig;
import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/sql/product-repository-data.sql")
@Import({ProductRepositoryImpl.class, QuerydslConfig.class})
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepositoryImpl sut;

    @Nested
    class filter {

        @Test
        void 검색_필터링이_없는경우_모든_상품이_조회된다() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            Page<ProductEntity> products = sut.findAllProducts(null, null, pageRequest, null);

            // then
            assertThat(products.getTotalElements()).isEqualTo(5);
            assertThat(products.getContent()).extracting("name")
                .containsExactlyInAnyOrder("Product A", "Product B", "Product C", "Product D", "Product E");
        }

        @Test
        void 공재_진행중인_상품만_필터링_검색을_할수있다() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            Page<ProductEntity> products = sut.findAllProducts(ProductStatus.IN_PROGRESS, null, pageRequest, null);

            // then
            assertThat(products.getTotalElements()).isEqualTo(2);
            assertThat(products.getContent()).extracting("name").containsExactlyInAnyOrder("Product A", "Product D");
        }

        @Test
        void 상품유형별_필터링_검색을_할수있다() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            Page<ProductEntity> products = sut.findAllProducts(null, ProductType.KEYBOARD, pageRequest, null);

            // then
            assertThat(products.getTotalElements()).isEqualTo(1);
            assertThat(products.getContent()).extracting("name").containsExactlyInAnyOrder("Product A");
        }

        @Test
        void 공재_진행여부와_상품유형으로_필터링_검색을_할수있다() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            Page<ProductEntity> products = sut.findAllProducts(ProductStatus.IN_PROGRESS, ProductType.KEYBOARD, pageRequest, null);

            // then
            assertThat(products.getTotalElements()).isEqualTo(1);
            assertThat(products.getContent()).extracting("name").containsExactlyInAnyOrder("Product A");
        }
    }

    @Nested
    class sort {

        @Test
        void 최신순으로_정렬된_상품을_조회할수있다() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            Page<ProductEntity> products = sut.findAllProducts(null, null, pageRequest, ProductSortBy.NEWEST);

            // then
            assertThat(products.getTotalElements()).isEqualTo(5);
            assertThat(products.getContent()).extracting("name")
                .containsExactly("Product E", "Product D", "Product C", "Product B", "Product A");
        }

        @Test
        void 오래된순으로_정렬된_상품을_조회할수있다() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            Page<ProductEntity> products = sut.findAllProducts(null, null, pageRequest, ProductSortBy.OLDEST);

            // then
            assertThat(products.getTotalElements()).isEqualTo(5);
            assertThat(products.getContent()).extracting("name")
                .containsExactly("Product A", "Product B", "Product C", "Product D", "Product E");
        }

        @Test
        void 가격높은순으로_정렬된_상품을_조회할수있다() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            Page<ProductEntity> products = sut.findAllProducts(null, null, pageRequest, ProductSortBy.HIGH_PRICE);

            // then
            assertThat(products.getTotalElements()).isEqualTo(5);
            assertThat(products.getContent()).extracting("price")
                .containsExactly(3000L, 2500L, 2000L, 1500L, 1000L);
        }

        @Test
        void 가격낮은순으로_정렬된_상품을_조회할수있다() {
            // given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // when
            Page<ProductEntity> products = sut.findAllProducts(null, null, pageRequest, ProductSortBy.LOW_PRICE);

            // then
            assertThat(products.getTotalElements()).isEqualTo(5);
            assertThat(products.getContent()).extracting("price")
                .containsExactly(1000L, 1500L, 2000L, 2500L, 3000L);
        }
    }
}
