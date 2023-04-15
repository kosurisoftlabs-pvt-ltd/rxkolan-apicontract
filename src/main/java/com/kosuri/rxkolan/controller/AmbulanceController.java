package com.kosuri.rxkolan.controller;


import com.kosuri.rxkolan.model.ambulance.AmbulanceCreationRequest;
import com.kosuri.rxkolan.model.ambulance.AmbulanceResponse;
import com.kosuri.rxkolan.model.ambulance.AmbulanceUpdateRequest;
import com.kosuri.rxkolan.model.pagination.PageableResponse;
import com.kosuri.rxkolan.model.search.SearchDto;
import com.kosuri.rxkolan.service.AmbulanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/ambulance")
@RequiredArgsConstructor
public class AmbulanceController {

    private final AmbulanceService ambulanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AmbulanceResponse> createAmbulance(@RequestPart("ambulance") @Valid AmbulanceCreationRequest ambulanceCreationRequest,
                                                             @RequestPart(value = "vehicleRC", required = false) List<MultipartFile> vehicleRC,
                                                             @RequestPart(value = "licenseCertificate", required = false) List<MultipartFile> licenseCertificate,
                                                             @RequestPart(value = "numberPlatePhoto", required = false) MultipartFile numberPlatePhoto,
                                                             HttpServletRequest request){
       AmbulanceResponse ambulanceResponse =  ambulanceService.createAmbulance(ambulanceCreationRequest,vehicleRC,licenseCertificate,numberPlatePhoto,request);
       return  ResponseEntity.ok(ambulanceResponse);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AmbulanceResponse> updateExistingAmbulance(@PathVariable("id") String ambulanceId, @RequestBody @Valid AmbulanceUpdateRequest updateRequest){
        AmbulanceResponse ambulanceResponse =  ambulanceService.updateExistingAmbulance(ambulanceId,updateRequest);
        return  ResponseEntity.ok(ambulanceResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AmbulanceResponse> fetchAmbulanceById(@PathVariable("id") String ambulanceId){
        AmbulanceResponse ambulanceResponse =  ambulanceService.fetchExistingAmbulanceById(ambulanceId);
        return  ResponseEntity.ok(ambulanceResponse);
    }

    @PostMapping("/search")
    @Operation(summary = "Fetch All the Ambulances In a Paginated Format")
    public PageableResponse<AmbulanceResponse> fetchAllAmbulances(@RequestBody SearchDto request,
                                                               @PageableDefault(direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable pageable) {
        return ambulanceService.fetchAllAmbulanceBySearchCriteria(request, pageable);
    }

    @PatchMapping("/verify/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Verify Existing Ambulance By ID By Admin")
    public ResponseEntity<AmbulanceResponse> createAmbulance(@PathVariable("id") String ambulanceId, HttpServletRequest request){
        AmbulanceResponse ambulanceResponse =  ambulanceService.verifyAmbulance(ambulanceId,request);
        return  ResponseEntity.ok(ambulanceResponse);
    }
}
