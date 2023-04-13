package com.kosuri.rxkolan.util;

import com.kosuri.rxkolan.model.pagination.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class PageUtil {

    public static <T> Page<T> toPage(final List<T> content, final int pageNumber, final int pageSize) {
        int totalPages = content.size() / pageSize;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        int max = pageNumber >= totalPages ? content.size() : pageSize * (pageNumber + 1);
        int min = pageNumber > totalPages ? max : pageSize * pageNumber;
        return new PageImpl<>(content.subList(min, max), pageable, content.size());
    }

    public static <T> PageableResponse<T> pageableResponse(Page<T> page) {
        return pageableResponse(page, page.getContent());
    }

    public static <T, E> PageableResponse<E> pageableResponse(final Page<T> page, final Collection<E> content) {
        return PageableResponse.<E>builder()
                .total(page.getTotalElements())
                .pageSize(page.getNumberOfElements())
                .pageNumber(page.getNumber())
                .lastPage(page.isLast())
                .body(content)
                .build();
    }

    public static <T, E> PageableResponse<E> pageableResponse(final PageableResponse<T> page, final Collection<E> content) {
        return PageableResponse.<E>builder()
                .total(page.getTotal())
                .pageSize(page.getPageSize())
                .pageNumber(page.getPageNumber())
                .lastPage(page.isLastPage())
                .body(content)
                .build();
    }

    public static <E> PageableResponse<E> emptyPageableResponse() {
        return PageableResponse.<E>builder()
                .total(0)
                .pageSize(0)
                .pageNumber(0)
                .lastPage(true)
                .body(Collections.emptyList())
                .build();
    }

    public static Pageable pageable(final int pageNumber, final int pageSize) {
        Pageable pageable = Pageable.unpaged();
        if (pageNumber >= 0 && pageSize > 0) {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return pageable;
    }

    public static Pageable pageable(final int pageNumber, final int pageSize, Sort.Order sortOrder) {
        Pageable pageable = Pageable.unpaged();
        if (pageNumber >= 0 && pageSize > 0 && (sortOrder != null)) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder));
        } else if (pageNumber >= 0 && pageSize > 0) {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return pageable;
    }

    public static Pageable pageable(final int pageNumber, final int pageSize, List<Sort.Order> sorts) {
        Pageable pageable = Pageable.unpaged();
        if (pageNumber >= 0 && pageSize > 0 && (sorts != null && !sorts.isEmpty())) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sorts));
        } else if (pageNumber >= 0 && pageSize > 0) {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return pageable;
    }

    private PageUtil() {
        //NOOP
    }

}