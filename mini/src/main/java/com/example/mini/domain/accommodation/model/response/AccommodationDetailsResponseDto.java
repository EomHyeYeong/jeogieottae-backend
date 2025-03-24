package com.example.mini.domain.accommodation.model.response;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.review.model.response.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDetailsResponseDto {
    private AccommodationResponseDto accomodation;
    private List<RoomResponseDto> rooms;
    private List<ReviewResponseDto> reviews;
    private Double avgStar;
    private boolean isLiked;


    public static AccommodationDetailsResponseDto toDto(Accommodation accommodation, List<RoomResponseDto> rooms, List<ReviewResponseDto> reviews, Double avgStar, Boolean isLiked) {
        return AccommodationDetailsResponseDto.builder()
            .accomodation(AccommodationResponseDto.toDto(accommodation))
            .rooms(rooms)
            .reviews(reviews)
            .avgStar(avgStar)
            .isLiked(isLiked)
            .build();
    }
}
