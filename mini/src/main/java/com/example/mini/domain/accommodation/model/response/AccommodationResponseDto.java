package com.example.mini.domain.accommodation.model.response;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.AccommodationImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationResponseDto {

    private Long id;
    private String name;
    private String description;
    private String postalCode;
    private String address;
    private Boolean parkingAvailable;
    private Boolean cookingAvailable;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String category;
    private List<String> accomodationImageUrls;

    public static AccommodationResponseDto toDto(Accommodation accommodation) {
        List<String> accomodationImageUrls = accommodation.getImages().stream()
            .map(AccommodationImage::getImgUrl)
            .collect(Collectors.toList());

        return AccommodationResponseDto.builder()
            .id(accommodation.getId())
            .name(accommodation.getName())
            .description(accommodation.getDescription())
            .postalCode(accommodation.getPostalCode())
            .address(accommodation.getAddress())
            .parkingAvailable(accommodation.getParkingAvailable())
            .cookingAvailable(accommodation.getCookingAvailable())
            .checkIn(accommodation.getCheckIn())
            .checkOut(accommodation.getCheckOut())
            .category(accommodation.getCategory().getName())
            .accomodationImageUrls(accomodationImageUrls)
            .build();
    }
}