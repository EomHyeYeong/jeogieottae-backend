package com.example.mini.domain.accommodation.model.response;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.AccommodationImage;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationCardResponseDto {
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
    private Integer minPrice;
    private boolean reservationAvailable;
    private int likeCount;
    private boolean isLiked;
    private List<String> accomodationImageUrls;

    public static AccommodationCardResponseDto toDto(Accommodation accommodation, Integer minPrice, boolean reservationAvailable, boolean isLiked) {
        int likeCount = 0;
        if (accommodation.getLikes() != null) {
            likeCount = accommodation.getLikes().size();
        }

        List<String> accomodationImageUrls = accommodation.getImages().stream()
            .map(AccommodationImage::getImgUrl)
            .collect(Collectors.toList());

        return AccommodationCardResponseDto.builder()
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
            .minPrice(minPrice)
            .reservationAvailable(reservationAvailable)
            .likeCount(likeCount)
            .accomodationImageUrls(accomodationImageUrls)
            .isLiked(isLiked)
            .build();
    }
}