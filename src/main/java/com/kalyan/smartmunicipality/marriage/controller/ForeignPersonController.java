package com.kalyan.smartmunicipality.marriage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalyan.smartmunicipality.marriage.model.ForeignPerson;
import com.kalyan.smartmunicipality.marriage.service.ForeignPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/foreign-marriage")
public class ForeignPersonController {
    private final ForeignPersonService foreignPersonService;
    private final ObjectMapper objectMapper;

    @PostMapping("/save")
    public ResponseEntity<ForeignPerson> save(@RequestPart("foreignPerson") String foreignPersonJson,
                                             @RequestPart(required = true) MultipartFile passportPhoto,
                                             @RequestPart(required = true) MultipartFile personPhoto,
                                             @RequestPart(required = true) MultipartFile personCitizenship) throws JsonProcessingException {
        ForeignPerson foreignPerson = objectMapper.readValue(foreignPersonJson, ForeignPerson.class);

        ForeignPerson save = foreignPersonService.save(foreignPerson, passportPhoto, personPhoto, personCitizenship);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);

    }



}
