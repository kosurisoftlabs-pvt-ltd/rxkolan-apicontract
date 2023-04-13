package com.kosuri.rxkolan.model.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

    private String filterKey;
    private Object value;
    private LocalDate dateBefore;
    private LocalDate dateAfter;
    private String operation;
    private String dataOption;

    public SearchCriteria(String filterKey, String operation,
                          Object value, LocalDate dateBefore, LocalDate dateAfter) {
        super();
        this.filterKey = filterKey;
        this.operation = operation;
        this.value = value;
        this.dateAfter = dateAfter;
        this.dateBefore = dateBefore;
    }
}