package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.entity.Ambulance;
import com.kosuri.rxkolan.model.ambulance.AmbulanceUpdateRequest;
import com.kosuri.rxkolan.model.pagination.PageableResponse;
import com.kosuri.rxkolan.model.search.SearchCriteria;
import com.kosuri.rxkolan.model.search.SearchDto;
import com.kosuri.rxkolan.repository.AmbulanceRepository;
import com.kosuri.rxkolan.model.ambulance.AmbulanceCreationRequest;
import com.kosuri.rxkolan.model.ambulance.AmbulanceResponse;
import com.kosuri.rxkolan.search.AmbulanceSpecificationBuilder;
import com.kosuri.rxkolan.service.AmbulanceService;
import com.kosuri.rxkolan.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmbulanceServiceImpl implements AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;


    @Override
    public AmbulanceResponse createAmbulance(AmbulanceCreationRequest ambulanceCreationRequest,
                                             List<MultipartFile> vehicleRC, List<MultipartFile> licenseCertificate,
                                             MultipartFile numberPlatePhoto) {
        log.info("Create Ambulance Triggered For Owner Name {}",ambulanceCreationRequest.getOwnerName());
        return null;
    }

    @Override
    public AmbulanceResponse updateExistingAmbulance(String ambulanceId, AmbulanceUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public AmbulanceResponse fetchExistingAmbulanceById(String ambulanceId) {
        return null;
    }

    @Override
    public PageableResponse<AmbulanceResponse> fetchAllAmbulanceBySearchCriteria(SearchDto request, Pageable pageable) {
        AmbulanceSpecificationBuilder builder = new AmbulanceSpecificationBuilder();
        List<SearchCriteria> criteriaList = request.getSearchCriteriaList();
        if (criteriaList != null) {
            criteriaList.forEach(x -> {
                x.setDataOption(request.getDataOption());
                builder.with(x);
            });
        }

        Page<Ambulance> searchResult = ambulanceRepository.findAll(builder.build(), pageable);
        return PageUtil.pageableResponse(searchResult.map(this::buildAmbulanceResponse));
    }

    private AmbulanceResponse buildAmbulanceResponse(Ambulance ambulance) {
        return AmbulanceResponse.builder().build();
    }

    @Override
    public AmbulanceResponse verifyAmbulance(String ambulanceId) {
        return null;
    }
}
