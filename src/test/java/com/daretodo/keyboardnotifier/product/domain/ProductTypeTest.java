package com.daretodo.keyboardnotifier.product.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTypeTest {

    @Nested
    class create {
        @Test
        void 한국어로_상품타입_생성() {
            // given
            String typeName = "키보드";

            // when
            ProductType sut = ProductType.from(typeName);

            // then
            assertEquals(ProductType.KEYBOARD, sut);
        }

        @Test
        void 영어로_상품타입_생성() {
            // given
            String typeName = "KEYBOARD";

            // when
            ProductType sut = ProductType.from(typeName);

            // then
            assertEquals(ProductType.KEYBOARD, sut);
        }

        @Test
        void 없는상품은_기타로_생성() {
            // given
            String typeName = "지정타입아님";

            // when
            ProductType sut = ProductType.from(typeName);

            // then
            assertEquals(ProductType.ETC, sut);
        }
    }
}