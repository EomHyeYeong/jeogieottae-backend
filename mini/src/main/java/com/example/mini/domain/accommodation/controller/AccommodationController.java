package com.example.mini.domain.accommodation.controller;

import com.example.mini.domain.accommodation.converter.AccomodationConverter;
import com.example.mini.domain.accommodation.model.request.AccommodationRequestDto;
import com.example.mini.domain.accommodation.model.response.AccommodationCardResponseDto;
import com.example.mini.domain.accommodation.model.response.AccommodationDetailsResponseDto;
import com.example.mini.domain.accommodation.model.response.RoomResponseDto;
import com.example.mini.domain.accommodation.service.AccommodationService;
import com.example.mini.global.api.ApiResponse;
import com.example.mini.global.api.exception.success.SuccessCode;
import com.example.mini.global.model.dto.PagedResponse;
import com.example.mini.global.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<AccommodationCardResponseDto>>> getAllAccommodations(
        @RequestParam(value="page", defaultValue = "1") int page,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Optional<Long> memberId = AccomodationConverter.convertToMemberId(userDetails);
        PagedResponse<AccommodationCardResponseDto> response = accommodationService.getAllAccommodations(page, memberId);
        return ResponseEntity.ok(ApiResponse.SUCCESS(SuccessCode.ACCOMMODATIONS_RETRIEVED, response));
    }

//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<PagedResponse<AccommodationCardResponseDto>>> getAllAccommodationsBySearch(
//        @RequestParam(value = "accommodationName", defaultValue = "") String name,
//        @RequestParam(value = "region", defaultValue = "") String region,
//        @ModelAttribute AccommodationRequestDto request,
//        @RequestParam(value= "page", defaultValue = "1") int page,
//        @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
//        Optional<Long> memberId = convertToMemberId(userDetails);
//        PagedResponse<AccommodationCardResponseDto> response =
//                accomodationService.getAllAccommodationsBySearch(name, region, request, page, memberId);
//        return ResponseEntity.ok(ApiResponse.SUCCESS(SuccessCode.ACCOMMODATION_SEARCH_SUCCESS, response));
//    }

    @GetMapping("/{accomodationId}")
    public ResponseEntity<ApiResponse<AccommodationDetailsResponseDto>> getAccomodationDetails(
        @PathVariable Long accomodationId,
        @ModelAttribute AccommodationRequestDto request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Optional<Long> memberId = AccomodationConverter.convertToMemberId(userDetails);
        AccommodationDetailsResponseDto response = accommodationService
            .getAccomodationDetails(accomodationId, request.getCheckIn(), request.getCheckOut(), memberId);
        return ResponseEntity.ok(ApiResponse.SUCCESS(SuccessCode.ACCOMMODATION_DETAILS_RETRIEVED, response));
    }

    @GetMapping("/{accomodationId}/room/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponseDto>> getRoomDetail(
        @PathVariable Long accomodationId,
        @PathVariable Long roomId,
        @ModelAttribute AccommodationRequestDto request
    ) {
        RoomResponseDto response = accommodationService
            .getRoomDetail(accomodationId, roomId, request.getCheckIn(), request.getCheckOut());
        return ResponseEntity.ok(ApiResponse.SUCCESS(SuccessCode.ROOM_DETAILS_RETRIEVED, response));
    }

}