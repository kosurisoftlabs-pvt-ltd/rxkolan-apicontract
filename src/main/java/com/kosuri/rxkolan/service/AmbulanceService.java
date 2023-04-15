package com.kosuri.rxkolan.service;

import com.kosuri.rxkolan.model.ambulance.AmbulanceCreationRequest;
import com.kosuri.rxkolan.model.ambulance.AmbulanceResponse;
import com.kosuri.rxkolan.model.ambulance.AmbulanceUpdateRequest;
import com.kosuri.rxkolan.model.pagination.PageableResponse;
import com.kosuri.rxkolan.model.search.SearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AmbulanceService {
    AmbulanceResponse createAmbulance(AmbulanceCreationRequest ambulanceCreationRequest, List<MultipartFile> vehicleRC, List<MultipartFile> licenseCertificate, MultipartFile numberPlatePhoto, HttpServletRequest request);

    AmbulanceResponse updateExistingAmbulance(String ambulanceId, AmbulanceUpdateRequest updateRequest);

    AmbulanceResponse fetchExistingAmbulanceById(String ambulanceId);

    PageableResponse<AmbulanceResponse> fetchAllAmbulanceBySearchCriteria(SearchDto request, Pageable pageable);

    AmbulanceResponse verifyAmbulance(String ambulanceId, HttpServletRequest servletRequest);
}
