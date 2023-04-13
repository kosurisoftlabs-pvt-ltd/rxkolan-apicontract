package com.kosuri.rxkolan.search;


import com.kosuri.rxkolan.entity.Ambulance;
import com.kosuri.rxkolan.model.search.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AmbulanceSpecificationBuilder {

    private final List<SearchCriteria> params;

    public AmbulanceSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final AmbulanceSpecificationBuilder with(String key,
                                                   String operation, Object value, LocalDate dateAfter, LocalDate dateBefore) {
        params.add(new SearchCriteria(key, operation, value, dateBefore, dateAfter));
        return this;
    }

    public final AmbulanceSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<Ambulance> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<Ambulance> result = new AmbulanceSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria
                    .getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new AmbulanceSpecification(criteria))
                    : Specification.where(result).or(
                    new AmbulanceSpecification(criteria));
        }
        return result;
    }

}
