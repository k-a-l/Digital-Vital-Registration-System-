package com.kalyan.smartmunicipality.citizen.controller;

import com.kalyan.smartmunicipality.citizen.dto.CitizenRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.enums.CitizenStatus;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.service.CitizenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/citizen")
public class CitizenController {
    private CitizenService citizenService;
    public CitizenController(CitizenService citizenService){
        this.citizenService=citizenService;
    }

    @PostMapping
    public ResponseEntity<CitizenResponseDto> createCitizen(@Valid @RequestBody CitizenRequestDto citizenRequestDto){
        CitizenResponseDto citizenResponseDto=citizenService.createCitizen(citizenRequestDto);
        return ResponseEntity.ok(citizenResponseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizens(){
        List<CitizenResponseDto> citizens= citizenService.getAllCitizens();
        return ResponseEntity.ok(citizens);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCitizenCount(){
        Long count=citizenService.getCitizenCount();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/update")
    public ResponseEntity<CitizenResponseDto> updateCitizen(@RequestParam Long id, @Valid @RequestBody CitizenRequestDto citizenRequestDto){
        CitizenResponseDto citizenResponseDto=citizenService.updateCitizen(id, citizenRequestDto);
        return ResponseEntity.ok(citizenResponseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCitizenById(@PathVariable Long id){
        citizenService.deleteCitizenById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/male-count")
    public ResponseEntity<Long> getMaleCount(){
        Long count=citizenService.getMaleCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/female-count")
    public ResponseEntity<Long> getFemaleCount(){
        Long count=citizenService.getFemaleCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/others-count")
    public ResponseEntity<Long> getOthersCount(){
        Long count=citizenService.getOthersCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/pending-list")
    public ResponseEntity<List<CitizenResponseDto>> getPendingCitizens(){
        List<CitizenResponseDto> citizens=citizenService.getPendingCitizens();
        return ResponseEntity.ok(citizens);
    }

    @GetMapping("/approved-list")
    public ResponseEntity<List<CitizenResponseDto>> getApprovedCitizens(){
        List<CitizenResponseDto> citizens=citizenService.getApprovedCitizens();
        return ResponseEntity.ok(citizens);
    }

    @GetMapping("/rejected-list")
    public ResponseEntity<List<CitizenResponseDto>> getRejectedCitizens(){
        List<CitizenResponseDto> citizens=citizenService.getRejectedCitizens();
        return ResponseEntity.ok(citizens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenResponseDto> getCitizenById(@PathVariable Long id){
        CitizenResponseDto citizenResponseDto=citizenService.getCitizenById(id);
        return ResponseEntity.ok(citizenResponseDto);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectCitizen(@PathVariable Long id,
                                           @RequestBody Map<String, String> body,
                                           @RequestParam Long verifierId) {
        String reason = body.get("reason");

        citizenService.rejectCitizen(id, reason, verifierId);
        Map<String, String> response = Map.of("message", "Citizen rejected successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Map<String, String>> approveCitizen(@PathVariable Long id,
                                           @RequestParam Long verifierId) {
        citizenService.approveCitizen(id, verifierId);
        Map<String, String> response = Map.of("message", "Citizen approved successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyCitizen() {
        return citizenService.getMyCitizenStatus();
    }

    @GetMapping("/by-email")
    public ResponseEntity<CitizenResponseDto> getCitizenByEmail(@RequestParam String email) {
        return ResponseEntity.ok(citizenService.getCitizenByEmail(email));
    }

    @GetMapping("/status-by-email")
    public ResponseEntity<CitizenStatus> getStatusByEmail(@RequestParam String email) {
        return ResponseEntity.ok(citizenService.getCitizenStatusByEmail(email));
    }


}



