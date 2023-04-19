package com.kosuri.rxkolan.service.impl;

import com.kosuri.rxkolan.config.AppProperties;
import com.kosuri.rxkolan.constant.ErrorConstants;
import com.kosuri.rxkolan.entity.Ambulance;
import com.kosuri.rxkolan.entity.ApprovalStatus;
import com.kosuri.rxkolan.entity.DocumentType;
import com.kosuri.rxkolan.entity.ServiceOfferedEnum;
import com.kosuri.rxkolan.exception.BadRequestException;
import com.kosuri.rxkolan.model.ambulance.AmbulanceUpdateRequest;
import com.kosuri.rxkolan.model.pagination.PageableResponse;
import com.kosuri.rxkolan.model.search.SearchCriteria;
import com.kosuri.rxkolan.model.search.SearchDto;
import com.kosuri.rxkolan.repository.AmbulanceRepository;
import com.kosuri.rxkolan.model.ambulance.AmbulanceCreationRequest;
import com.kosuri.rxkolan.model.ambulance.AmbulanceResponse;
import com.kosuri.rxkolan.search.AmbulanceSpecificationBuilder;
import com.kosuri.rxkolan.security.TokenProvider;
import com.kosuri.rxkolan.service.AmbulanceService;
import com.kosuri.rxkolan.service.DocumentService;
import com.kosuri.rxkolan.service.UserService;
import com.kosuri.rxkolan.util.PageUtil;
import com.kosuri.rxkolan.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmbulanceServiceImpl implements AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;
    private final TokenProvider tokenProvider;
    private final DocumentService documentService;
    private final AppProperties appProperties;
    private final UserService userService;

    @Override
    @Transactional
    public AmbulanceResponse createAmbulance(AmbulanceCreationRequest ambulanceCreationRequest,
                                             List<MultipartFile> vehicleRC, List<MultipartFile> licenseCertificate,
                                             MultipartFile numberPlatePhoto, HttpServletRequest request) {
        log.info("Create Ambulance Triggered For Owner Name {}",ambulanceCreationRequest.getOwnerName());
        Ambulance ambulance = new Ambulance();
        String authToken = RequestUtil.getJwtFromRequest(request);
        String username = tokenProvider.getUserNameFromToken(authToken);
        Optional<Ambulance> ambulanceOptional =  ambulanceRepository.findByAmbulanceRegNumber(ambulanceCreationRequest.getRegistrationNumber());
        if(ambulanceOptional.isEmpty() && ambulanceCreationRequest.getServiceName().equals(ServiceOfferedEnum.AM.name())) {
            ambulance.setAmbulanceRegNumber(ambulanceCreationRequest.getRegistrationNumber());
            ambulance.setBaseLocation(ambulanceCreationRequest.getBaseLocation());
            ambulance.setState(ambulanceCreationRequest.getState());
            ambulance.setAdditionalFeatures(ambulanceCreationRequest.getAdditionalFeatures());
            ambulance.setRtoRegisteredLocation(ambulanceCreationRequest.getRtoRegisteredLocation());
            ambulance.setOwnerName(ambulanceCreationRequest.getOwnerName());
            ambulance.setPhoneNumber(ambulanceCreationRequest.getContactNumber());
            ambulance.setVehicleBrand(ambulanceCreationRequest.getVehicleBrand());
            ambulance.setVehicleModel(ambulanceCreationRequest.getVehicleModel());
            ambulance.setVin(ambulanceCreationRequest.getVin());
            ambulance.setVerified(false);
            ambulance.setUserEmail(username);
            ambulance.setRegisteredDate(LocalDate.now(ZoneOffset.UTC));
            ambulance = ambulanceRepository.save(ambulance);
            if(CollectionUtils.isNotEmpty(vehicleRC)){
                ambulance.setAmbulanceRelatedImages(documentService.uploadDocuments(vehicleRC, DocumentType.VEHICLE_RC,ambulance.getAmbulanceRegNumber(),appProperties.getAwsConfig().getVehicleRc(), ApprovalStatus.PENDING));
            }
            if(CollectionUtils.isNotEmpty(licenseCertificate)){
                ambulance.setAmbulanceRelatedImages(documentService.uploadDocuments(licenseCertificate, DocumentType.AMBULANCE_LICENSE_CERTIFICATE,ambulance.getAmbulanceRegNumber(),appProperties.getAwsConfig().getLicenseCertificate(), ApprovalStatus.PENDING));
            }
            if(Objects.nonNull(numberPlatePhoto)){
                ambulance.setAmbulanceRelatedImages(documentService.uploadDocuments(List.of(numberPlatePhoto), DocumentType.AMBULANCE_LICENSE_PLATE,ambulance.getAmbulanceRegNumber(),appProperties.getAwsConfig().getLicensePlate(), ApprovalStatus.PENDING));
            }
            ambulance = ambulanceRepository.save(ambulance);
        }else{
            log.error("Ambulance with Registration Number {} Has already been Registered In The System",ambulanceCreationRequest.getRegistrationNumber());
            throw new BadRequestException(ErrorConstants.AMBULANCE_REG_ALREADY_EXISTS);
        }
        return new AmbulanceResponse(ambulance);
    }

    @Override
    @Transactional
    public AmbulanceResponse updateExistingAmbulance(@NotNull(message = "Ambulance ID Cannot Be Null") String ambulanceId, AmbulanceUpdateRequest updateRequest) {
       log.info("Update Existing Ambulance Data For Ambulance Id {}",ambulanceId);
        Optional<Ambulance> ambulanceOptional =  ambulanceRepository.findByAmbulanceRegNumber(ambulanceId);
        Ambulance ambulance;
        if(ambulanceOptional.isPresent()){
            ambulance = ambulanceOptional.get();
            ambulance.setBaseLocation(updateRequest.getLocation());
            ambulance.setActive(updateRequest.getAvailable());
            ambulanceRepository.save(ambulance);
        }else{
            log.error("Ambulance with Registration Number {} Is Not Present In The System",ambulanceId);
            throw new BadRequestException(ErrorConstants.AMBULANCE_REG_NOT_FOUND);
        }
        return new AmbulanceResponse(ambulance);
    }

    @Override
    public AmbulanceResponse fetchExistingAmbulanceById(String ambulanceId) {
        Optional<Ambulance> ambulanceOptional =  ambulanceRepository.findByAmbulanceRegNumber(ambulanceId);
        if(ambulanceOptional.isPresent()){
            return new AmbulanceResponse(ambulanceOptional.get());
        }else{
            log.error("Ambulance with Registration Number {} Is Not Present In The System",ambulanceId);
            throw new BadRequestException(ErrorConstants.AMBULANCE_REG_NOT_FOUND);
        }
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
        return PageUtil.pageableResponse(searchResult.map(AmbulanceResponse::new));
    }

    @Override
    @Transactional
    public AmbulanceResponse verifyAmbulance(String ambulanceId, HttpServletRequest request) {
        Optional<Ambulance> ambulanceOptional =  ambulanceRepository.findByAmbulanceRegNumber(ambulanceId);
        Ambulance ambulance;
        if(ambulanceOptional.isPresent()){
            ambulance =  ambulanceOptional.get();
            ambulance.setVerified(true);
            String authToken = RequestUtil.getJwtFromRequest(request);
            String username = tokenProvider.getUserNameFromToken(authToken);
            ambulance.setVerifiedBy(username);
            return new AmbulanceResponse(ambulanceOptional.get());
        }else{
            log.error("Ambulance with Registration Number {} Is Not Present In The System",ambulanceId);
            throw new BadRequestException(ErrorConstants.AMBULANCE_REG_NOT_FOUND);
        }
    }
}
