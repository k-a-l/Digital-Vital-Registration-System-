package com.kalyan.smartmunicipality.marriage.mapper;

import com.kalyan.smartmunicipality.citizen.model.Citizen;
import com.kalyan.smartmunicipality.marriage.dto.MarriageCertificateResponseDto;
import com.kalyan.smartmunicipality.marriage.model.MarriageCertificateRequest;
import org.springframework.stereotype.Service;

@Service
public class MarriageCertificateRequestMapper {

    public static MarriageCertificateResponseDto toMarriageCertificateResponseDto(MarriageCertificateRequest request) {
        String requestedByName = request.getRequestedBy() != null
                ? request.getRequestedBy().getFirstName() + " " + request.getRequestedBy().getLastName()
                : "N/A";

        String partnerName = "N/A";
        if (request.getForeignPartner() != null) {
            partnerName = request.getForeignPartner().getFullName();
        } else if (request.getHusband() != null && request.getWife() != null) {
            Citizen partner = request.getRequestedBy().getGender().name().equals("MALE")
                    ? request.getWife()
                    : request.getHusband();
            if (partner != null) {
                partnerName = partner.getFirstName() + " " + partner.getLastName();
            }
        }
        String partnerEmail = "N/A";
        if (request.getForeignPartner() != null) {
            partnerEmail = request.getForeignPartner().getEmail();
        } else if (request.getWife() != null && request.getHusband() != null) {
            Citizen partner = request.getRequestedBy().getGender().name().equals("MALE")
                    ? request.getWife()
                    : request.getHusband();
            if (partner != null) {
                partnerEmail = partner.getUserEmail();
            }
        }


        return MarriageCertificateResponseDto.builder()
                .id(request.getId())
                .requestedBy(requestedByName)
                .partnerName(partnerName)
                .marriageDate(request.getMarriageDate())
                .marriagePlace(request.getPlaceOfMarriage())
                .marriageStatus(request.getStatus())
                .partnerEmail(partnerEmail)
                .scheduledTime(request.getScheduledTime())
                .build();
    }
}
