package com.example.mini.domain.accommodation.controller;

import com.example.mini.domain.accommodation.model.request.AccommodationRequestDto;
import com.example.mini.domain.accommodation.model.response.AccommodationCardResponseDto;
import com.example.mini.domain.accommodation.model.response.AccommodationDetailsResponseDto;
import com.example.mini.domain.accommodation.model.response.RoomResponseDto;
import com.example.mini.domain.accommodation.service.AccommodationService;
import com.example.mini.global.api.ApiResponse;
import com.example.mini.global.api.exception.success.SuccessCode;
import com.example.mini.global.model.dto.PagedResponse;
import com.example.mini.global.security.details.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accommodations")
@Tag(name = "숙소 조회", description = "숙소 목록, 단건 조회 API")
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping
    @Operation(summary = "숙소 목록 조회", description = "체크인, 체크아웃, 지역, 검색어로 숙소 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<PagedResponse<AccommodationCardResponseDto>>> getAllAccommodations(
            @PageableDefault(size = 20) Pageable pageable,
            @ModelAttribute AccommodationRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        log.info("숙소 목록 조회: request={}", request);
        Long memberId = (userDetails!=null) ? userDetails.getMemberId() : null;
        PagedResponse<AccommodationCardResponseDto> response = accommodationService.getAllAccommodations(pageable, request, memberId);
        return ResponseEntity.ok(ApiResponse.SUCCESS(SuccessCode.ACCOMMODATIONS_RETRIEVED, response));
    }

    @GetMapping("/{accommodationId}")
    @Operation(summary = "숙소 상세정보 조회", description = "숙소 정보 및 객실 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<AccommodationDetailsResponseDto>> getAccommodationDetails(
        @PathVariable Long accommodationId,
        @ModelAttribute AccommodationRequestDto request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        log.info("숙소 상세정보 조회: request={}", request);
        Long memberId = (userDetails!=null) ? userDetails.getMemberId() : null;
        AccommodationDetailsResponseDto response = accommodationService.getAccommodationDetails(accommodationId, request, memberId);
        return ResponseEntity.ok(ApiResponse.SUCCESS(SuccessCode.ACCOMMODATION_DETAILS_RETRIEVED, response));
    }

    @GetMapping("/{accommodationId}/room/{roomId}")
    @Operation(summary = "객실 상세정보 조회", description = "객실 상세정보를 조회합니다.")
    public ResponseEntity<ApiResponse<RoomResponseDto>> getRoomDetail(
        @PathVariable Long accommodationId,
        @PathVariable Long roomId,
        @ModelAttribute AccommodationRequestDto request
    ) {
        log.info("객실 상세정보 조회: request={}", request);
        RoomResponseDto response = accommodationService.getRoomDetail(accommodationId, roomId, request);
        return ResponseEntity.ok(ApiResponse.SUCCESS(SuccessCode.ROOM_DETAILS_RETRIEVED, response));
    }

}