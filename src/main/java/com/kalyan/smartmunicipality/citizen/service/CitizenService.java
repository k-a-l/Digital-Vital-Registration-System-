package com.kalyan.smartmunicipality.citizen.service;

import com.kalyan.smartmunicipality.citizen.dto.CitizenRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.enums.CitizenStatus;
import com.kalyan.smartmunicipality.citizen.enums.Gender;
import com.kalyan.smartmunicipality.citizen.mapper.CitizenDtoMapper;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.repository.CitizenRepository;
import com.kalyan.smartmunicipality.jwt.utils.JwtUtil;
import com.kalyan.smartmunicipality.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitizenService {
    private final JwtUtil jwtUtil;

    private final CitizenRepository citizenRepository;

    public CitizenService(JwtUtil jwtUtil, CitizenRepository citizenRepository) {
        this.jwtUtil = jwtUtil;

        this.citizenRepository = citizenRepository;
    }
    @CacheEvict(value = {"citizenCount", "citizenList","citizenById"}, allEntries = true)
    @CachePut(value = "citizen", key = "#result.id")
    public CitizenResponseDto createCitizen(CitizenRequestDto citizenRequestDto) {
        User user = jwtUtil.getCurrentUserFromToken();

        // Check if user already has a citizen record
        if (user.getCitizen() != null) {
            throw new RuntimeException("Citizen already exists for this user.");
        }

        Citizen citizen = CitizenDtoMapper.mapToEntity(citizenRequestDto);
        citizen.setUser(user); // 🔗 Link citizen to user

        // 🔽 Store redundant userId and email as well
        citizen.setUserId(user.getId());
        citizen.setUserEmail(user.getEmail());

        citizenRepository.save(citizen);

        return CitizenDtoMapper.mapToDto(citizen);
    }


    @Cacheable(value = "citizenList")
    public List<CitizenResponseDto> getAllCitizens() {
        return citizenRepository.findAll().stream().map(CitizenDtoMapper::mapToDto).collect(Collectors.toList());
    }


    @Cacheable(value = "citizenCount")
    public Long getCitizenCount() {
        return citizenRepository.count();
    }

    @CacheEvict(value = "citizenDeleteById", key = "#id")
    public void deleteCitizenById(Long id) {
        citizenRepository.deleteById(id);
    }

    public CitizenResponseDto updateCitizen(Long id, CitizenRequestDto dto) {
        Citizen citizen = citizenRepository.findById(id).orElseThrow(() -> new RuntimeException("Citizen not found"));

        CitizenDtoMapper.updateEntityFromDto(citizen, dto);

        Citizen updated = citizenRepository.save(citizen);

        return CitizenDtoMapper.mapToDto(updated);
    }

    @Cacheable(value = "citizenById", key = "#id")
    public CitizenResponseDto getCitizenById(Long id) {
        Optional<Citizen> citizen = citizenRepository.findById(id);
        if (citizen.isPresent()) {
            return CitizenDtoMapper.mapToDto(citizen.get());
        } else {
            throw new RuntimeException("Citizen not found");
        }
    }

    @Cacheable(value = "male")
    public Long getMaleCount() {
        return citizenRepository.countByGender(Gender.MALE);
    }

    @Cacheable(value = "female")
    public Long getFemaleCount() {
        return citizenRepository.countByGender(Gender.FEMALE);
    }

    @Cacheable(value = "others")
    public Long getOthersCount() {
        return citizenRepository.countByGender(Gender.OTHERS);
    }

    @Cacheable(value = "approvedList")
    public List<CitizenResponseDto> getApprovedCitizens(){
        return citizenRepository.findAll()
                .stream()
                .filter(cit->cit.getStatus().equals(CitizenStatus.APPROVED))
                .map(CitizenDtoMapper::mapToDto).collect(Collectors.toList());
    }

    @Cacheable(value = "pendingList")
    public List<CitizenResponseDto> getPendingCitizens(){
        return citizenRepository.findAll()
                .stream()
                .filter(cit->cit.getStatus().equals(CitizenStatus.PENDING))
                .map(CitizenDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
    @Cacheable(value = "rejectedList")
    public List<CitizenResponseDto> getRejectedCitizens(){
        return citizenRepository.findAll()
                .stream().filter(citizen -> citizen.getStatus().equals(CitizenStatus.REJECTED))
                .map(CitizenDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
    @CacheEvict(value = {
            "citizenById", "approvedList", "pendingList", "rejectedList", "citizenList", "citizenCount"
    }, allEntries = true)
    @Transactional
    public void rejectCitizen(Long id, String rejectionReason, Long verifiedBy){
        Citizen citizen = citizenRepository.findById(id).orElseThrow(()->new RuntimeException("Citizen not found"));
        citizen.setStatus(CitizenStatus.REJECTED);
        citizen.setReasonForRejection(rejectionReason);
        citizen.setVerifiedBy(verifiedBy);
        citizen.setVerifiedDate(LocalDate.now());
        citizenRepository.save(citizen);
    }
    @CacheEvict(value = {
            "citizenById", "approvedList", "pendingList", "rejectedList", "citizenList", "citizenCount"
    }, allEntries = true)
    @Transactional
    public void approveCitizen(Long id, Long verifiedBy){
        Citizen citizen = citizenRepository.findById(id).orElseThrow(()->new RuntimeException("Citizen not found"));
        citizen.setStatus(CitizenStatus.APPROVED);
        citizen.setVerifiedBy(verifiedBy);
        citizen.setReasonForRejection(null);
        citizen.setVerifiedDate(LocalDate.now());
        citizenRepository.save(citizen);
    }


    public Optional<Citizen> getCitizenByUserEmail(String email) {
        return citizenRepository.findByUserEmail(email);
    }

    public ResponseEntity<?> getMyCitizenStatus() {
        User user = jwtUtil.getCurrentUserFromToken();

        Optional<Citizen> optionalCitizen = citizenRepository.findByUser(user);

        if (optionalCitizen.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No citizen record found for this user");
        }

        Citizen citizen = optionalCitizen.get();

        return ResponseEntity.ok(Map.of(
                "citizenId", citizen.getId(),
                "status", citizen.getStatus()
        ));
    }

    public CitizenResponseDto getCitizenByEmail(String email) {
        Citizen citizen = citizenRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found for email: " + email));
        return CitizenDtoMapper.mapToDto(citizen);
    }

    public CitizenStatus getCitizenStatusByEmail(String email) {
        Citizen citizen = citizenRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found for email: " + email));
        return citizen.getStatus();
    }


}
