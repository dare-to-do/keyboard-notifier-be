package com.daretodo.keyboardnotifier.product.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewCount {
    private long viewCount;

    public static ViewCount from(long count) {
        return new ViewCount(count);
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

}
