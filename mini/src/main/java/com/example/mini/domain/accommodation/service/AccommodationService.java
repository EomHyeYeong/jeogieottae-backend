package com.example.mini.domain.accommodation.service;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.model.request.AccommodationRequestDto;
import com.example.mini.domain.accommodation.model.response.AccommodationCardResponseDto;
import com.example.mini.domain.accommodation.model.response.AccommodationDetailsResponseDto;
import com.example.mini.domain.accommodation.model.response.RoomResponseDto;
import com.example.mini.domain.accommodation.repository.AccommodationRepository;
import com.example.mini.domain.accommodation.repository.RoomRepository;
import com.example.mini.domain.accommodation.entity.Room;
import com.example.mini.domain.reservation.entity.Reservation;
import com.example.mini.domain.reservation.repository.ReservationRepository;
import com.example.mini.domain.review.entity.Review;
import com.example.mini.global.api.exception.GlobalException;
import com.example.mini.global.api.exception.error.AccomodationErrorCode;
import com.example.mini.global.model.dto.PagedResponse;
import com.example.mini.global.model.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
//    private final AccommodationSearchRepository accommodationSearchRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    // 숙소 목록조회
    public PagedResponse<AccommodationCardResponseDto> getAccommodations(Pageable pageable, AccommodationRequestDto request, Long memberId) {
        Page<Accommodation> accommodations = accommodationRepository.findAllByFilters(request.region(), request.keyword(), pageable);
        if (accommodations.getContent().isEmpty()) throw new GlobalException(AccomodationErrorCode.RESOURCE_NOT_FOUND);

        List<AccommodationCardResponseDto> contents = accommodations.map(accommodation -> {
            int minPrice = accommodation.calculateMinimumRoomPrice();
            boolean reservationAvailable = isAccommodationReservationAvailable(accommodation, request.checkIn(), request.checkOut());
            boolean liked = accommodation.isLiked(memberId);
            return AccommodationCardResponseDto.toDto(accommodation, minPrice, reservationAvailable, liked);
        }).toList();

        return new PagedResponse<>(accommodations.getTotalPages(), accommodations.getTotalElements(), contents);
    }

    // 숙소 상세정보 조회
    public AccommodationDetailsResponseDto getAccommodationDetails(Long accommodationId, AccommodationRequestDto request, Long memberId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
            .orElseThrow(() -> new GlobalException(AccomodationErrorCode.RESOURCE_NOT_FOUND));
        List<RoomResponseDto> rooms = accommodation.getRooms().stream()
                .map(room -> RoomResponseDto.toDto(room, isRoomReservationAvailable(request.checkIn(), request.checkOut(), room.getId()))).toList();
        Double avgStar = calculateAverageStar(accommodation.getReviews());
        boolean isLiked = accommodation.isLiked(memberId);

        return AccommodationDetailsResponseDto.toDto(accommodation, rooms, avgStar, isLiked);
    }

    // 객실 조회
    public RoomResponseDto getRoomDetail(Long accommodationId, Long roomId, AccommodationRequestDto request) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new GlobalException(AccomodationErrorCode.RESOURCE_NOT_FOUND));
        if(!accommodationId.equals(room.getAccommodation().getId()))
            throw new GlobalException(AccomodationErrorCode.INVALID_ROOM_REQUEST);
        boolean reservationAvailable = isRoomReservationAvailable(request.checkIn(), request.checkOut(), roomId);
        return RoomResponseDto.toDto(room, reservationAvailable);
    }

    // 객실(room) 예약가능 여부
    public boolean isRoomReservationAvailable(LocalDateTime checkIn, LocalDateTime checkOut, Long roomId) {
        List<Long> list = Collections.singletonList(roomId);
        List<Reservation> reservations = reservationRepository.findOverlappingReservations(list, checkIn, checkOut);
        return reservations.isEmpty();
    }

    // 리뷰 평점 계산
    private Double calculateAverageStar(List<Review> reviews) {
        double average = reviews.stream().mapToDouble(Review::getStar).average().orElse(0.0);
        return Math.round(average * 10) / 10.0;  // 소수점 1자리 반올림
    }

    // 숙소(accommodation) 예약 가능여부 (모든 room 예약 불가능: false)
    private boolean isAccommodationReservationAvailable(Accommodation accommodation, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<Long> roomIds = accommodation.getRooms().stream().map(BaseEntity::getId).toList();
        List<Reservation> reservations = reservationRepository.findOverlappingReservations(roomIds, checkIn, checkOut);
        List<Long> reservedRoomIds = reservations.stream().map(reservation -> reservation.getRoom().getId()).toList();
        return accommodation.getRooms().stream().anyMatch(room -> !reservedRoomIds.contains(room.getId()));
    }

}