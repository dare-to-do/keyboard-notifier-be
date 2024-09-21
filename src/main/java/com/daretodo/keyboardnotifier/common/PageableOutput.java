package com.daretodo.keyboardnotifier.common;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageableOutput<T> {

    private int page;
    private int size;
    private long totalCount;
    private List<T> content;

    public PageableOutput(Page<T> page) {
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalCount = page.getTotalElements();
        this.content = page.getContent();
    }
}
