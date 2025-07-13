package com.kalyan.smartmunicipality.marriage.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String fileName;
    private String fileData; // base64 string
}
