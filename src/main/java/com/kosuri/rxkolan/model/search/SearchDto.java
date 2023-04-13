package com.kosuri.rxkolan.model.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;

}