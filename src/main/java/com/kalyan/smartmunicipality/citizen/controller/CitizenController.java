package com.kalyan.smartmunicipality.citizen.controller;

import com.kalyan.smartmunicipality.citizen.dto.CitizenRequestDto;
import com.kalyan.smartmunicipality.citizen.dto.CitizenResponseDto;
import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.citizen.service.CitizenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



}
