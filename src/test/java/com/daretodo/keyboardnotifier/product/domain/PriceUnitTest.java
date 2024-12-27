package com.daretodo.keyboardnotifier.product.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceUnitTest {

    @Nested
    class create {
        @Test
        void 한국어로_생성() {
            // given
            String name = "원";

            // when
            PriceUnit sut = PriceUnit.from(name);

            // then
            assertThat(sut).isEqualTo(PriceUnit.KRW);
        }

        @Test
        void 심볼로_생성() {
            // given
            String symbol = "₩";

            // when
            PriceUnit sut = PriceUnit.from(symbol);

            // then
            assertThat(sut).isEqualTo(PriceUnit.KRW);
        }

        @Test
        void 알수없음_생성() {
            // given
            String name = "모르는화폐";

            // when
            PriceUnit sut = PriceUnit.from(name);

            // then
            assertThat(sut).isEqualTo(PriceUnit.UNKNOWN);
        }
    }


}