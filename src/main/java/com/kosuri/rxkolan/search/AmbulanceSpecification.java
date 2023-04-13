package com.kosuri.rxkolan.search;


import com.kosuri.rxkolan.constant.Constants;
import com.kosuri.rxkolan.entity.Ambulance;
import com.kosuri.rxkolan.exception.BadRequestException;
import com.kosuri.rxkolan.model.search.SearchCriteria;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Slf4j
public class AmbulanceSpecification implements Specification<Ambulance> {
    private final SearchCriteria searchCriteria;

    public AmbulanceSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @SneakyThrows
    @Override
    public Predicate toPredicate(Root<Ambulance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = Objects.nonNull(searchCriteria.getValue()) ? searchCriteria.getValue().toString() : null;
        LocalDate dateBefore = Objects.isNull(searchCriteria.getDateBefore()) ? LocalDate.now().minusDays(30L) : searchCriteria.getDateBefore();
        LocalDate dateAfter = Objects.isNull(searchCriteria.getDateAfter()) ? LocalDate.now() : searchCriteria.getDateAfter();
        try {
            switch (Objects.requireNonNull(
                    SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
                case BEGINS_WITH:
                    if (searchCriteria.getFilterKey().equals(Constants.VIN_SEARCH_CONSTANT)) {
                        return cb.like(root.<String>get(Constants.VIN_SEARCH_CONSTANT), strToSearch + "%");
                    } else if (searchCriteria.getFilterKey().equals(Constants.AMB_REG_SEARCH_CONSTANT)) {
                        return cb.like(root.<String>get("ambulanceRegNumber"), strToSearch + "%");
                    }
                    else if (searchCriteria.getFilterKey().equals(Constants.PHONE_NUMBER_SEARCH_CONSTANT)) {
                        return cb.like(root.<String>get("phoneNumber"), strToSearch + "%");
                    }
                    return cb.like(root.<String>get(searchCriteria.getFilterKey()), strToSearch + "%");
                case EQUAL:
                     if (searchCriteria.getFilterKey().equals(Constants.VIN_SEARCH_CONSTANT)) {
                        return cb.equal(root.get(Constants.VIN_SEARCH_CONSTANT),strToSearch);
                    }
                    if (searchCriteria.getFilterKey().equals(Constants.AMB_REG_SEARCH_CONSTANT)) {
                        return cb.equal(root.get("ambulanceRegNumber"),strToSearch);
                    }
                    if (searchCriteria.getFilterKey().equals(Constants.PHONE_NUMBER_SEARCH_CONSTANT)) {
                        return cb.equal(root.get(Constants.PHONE_NUMBER_SEARCH_CONSTANT),strToSearch);
                    }
                    return cb.equal(root.<String>get(searchCriteria.getFilterKey()), strToSearch);
                case BETWEEN:
                    if (searchCriteria.getFilterKey().equals("registeredDate")) {
                        return cb.between(root.get("registeredDate"), dateBefore.atStartOfDay(), dateAfter.atTime(LocalTime.MAX));
                    }
                    break;
                default:
            }
        } catch (Exception ex) {
            log.error("invalid search request:{} failed with message:{}", searchCriteria, ex.getMessage());

            throw new BadRequestException(ex.getMessage());
        }

        throw new BadRequestException(MessageFormat.format("Search criteria is not supported:{0}", searchCriteria));
    }
}