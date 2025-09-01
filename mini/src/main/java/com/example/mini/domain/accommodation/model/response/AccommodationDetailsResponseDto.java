package com.example.mini.domain.accommodation.model.response;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.AccommodationImage;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import com.example.mini.domain.review.model.response.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDetailsResponseDto {

    private Long accommodationId;
    private String accommodationName;
    private String description;
    private String postalCode;
    private String address;
    private Boolean parkingAvailable;
    private Boolean cookingAvailable;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private AccommodationCategory category;
    private List<String> accommodationImageUrls;
    private List<RoomResponseDto> rooms;
    private Double avgStar;
    private boolean liked;


    public static AccommodationDetailsResponseDto toDto(Accommodation accommodation, List<RoomResponseDto> rooms, Double avgStar, Boolean isLiked) {
        List<String> accommodationImageUrls = accommodation.getImages().stream()
                .map(AccommodationImage::getImgUrl).toList();

        return AccommodationDetailsResponseDto.builder()
                .accommodationId(accommodation.getId())
                .accommodationName(accommodation.getName())
                .description(accommodation.getDescription())
                .postalCode(accommodation.getPostalCode())
                .address(accommodation.getAddress())
                .parkingAvailable(accommodation.getParkingAvailable())
                .cookingAvailable(accommodation.getCookingAvailable())
                .checkIn(accommodation.getCheckIn())
                .checkOut(accommodation.getCheckOut())
                .category(accommodation.getCategory())
                .accommodationImageUrls(accommodationImageUrls)
                .rooms(rooms)
                .avgStar(avgStar)
                .liked(isLiked)
                .build();
    }
}
