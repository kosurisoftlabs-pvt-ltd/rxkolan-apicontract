package com.kosuri.rxkolan.model.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableResponse<T> {

    private long total;
    private int pageNumber;
    private int pageSize;
    private boolean lastPage;
    private Collection<T> body;

    public PageableResponse(Page<T> page) {
        this.setTotal(page.getTotalElements());
        this.setPageSize(page.getNumberOfElements());
        this.setPageNumber(page.getNumber());
        this.setLastPage(page.isLast());
        final List<T> content = page.getContent();
        this.setBody(content);
    }

}