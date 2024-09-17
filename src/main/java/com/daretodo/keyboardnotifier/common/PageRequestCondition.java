package com.daretodo.keyboardnotifier.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public abstract class PageRequestCondition {

    @Schema(description = "페이지 번호", example = "1")
    private Integer page;

    @Schema(description = "페이지 크기", example = "10")
    private Integer size;

    @JsonIgnore
    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
