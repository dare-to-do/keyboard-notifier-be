package com.daretodo.keyboardnotifier.product.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewCountTest {

    @Test
    void 조회수_증가() {
        // given
        ViewCount sut = ViewCount.from(0L);
        // when
        sut.increaseViewCount();
        // then
        assertThat(sut.getViewCount()).isEqualTo(1L);
    }

}