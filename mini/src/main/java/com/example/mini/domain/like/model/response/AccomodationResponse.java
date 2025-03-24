package com.example.mini.domain.like.model.response;

import com.example.mini.domain.accommodation.entity.Accommodation;

import com.example.mini.domain.accommodation.entity.AccommodationImage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccomodationResponse {
    private String name;
    private String description;
    private String postalCode;
    private String address;
    private List<String> accomodationImageUrls;

    public static AccomodationResponse toDto(Accommodation accommodation) {
        List<String> accomodationImageUrls = accommodation.getImages().stream()
            .map(AccommodationImage::getImgUrl)
            .collect(Collectors.toList());

        return AccomodationResponse.builder()
            .name(accommodation.getName())
            .description(accommodation.getDescription())
            .postalCode(accommodation.getPostalCode())
            .address(accommodation.getAddress())
            .accomodationImageUrls(accomodationImageUrls)
            .build();
    }
}