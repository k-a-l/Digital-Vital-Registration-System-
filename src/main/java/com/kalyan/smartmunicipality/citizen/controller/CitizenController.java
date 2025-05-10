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


}
