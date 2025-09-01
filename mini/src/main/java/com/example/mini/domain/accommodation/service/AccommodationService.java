package com.example.mini.domain.accommodation.service;

import com.example.mini.domain.accommodation.converter.AccomodationConverter;
import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.model.request.AccommodationRequestDto;
import com.example.mini.domain.accommodation.model.response.AccommodationCardResponseDto;
import com.example.mini.domain.accommodation.model.response.AccommodationDetailsResponseDto;
import com.example.mini.domain.accommodation.model.response.RoomResponseDto;
import com.example.mini.domain.accommodation.repository.AccommodationRepository;
import com.example.mini.domain.accommodation.repository.RoomRepository;
import com.example.mini.domain.accommodation.util.AccommodationUtils;
import com.example.mini.domain.accommodation.entity.Room;
//import com.example.mini.domain.accommodation.repository.AccommodationSearchRepository;
import com.example.mini.domain.like.entity.Like;
import com.example.mini.domain.like.repository.LikeRepository;
import com.example.mini.domain.reservation.entity.Reservation;
import com.example.mini.domain.reservation.repository.ReservationRepository;
import com.example.mini.domain.review.entity.Review;
import com.example.mini.domain.review.model.response.ReviewResponseDto;
import com.example.mini.global.api.exception.GlobalException;
import com.example.mini.global.api.exception.error.AccomodationErrorCode;
import com.example.mini.global.model.dto.PagedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
//    private final AccommodationSearchRepository accommodationSearchRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final LikeRepository likeRepository;
    private final AccomodationConverter accomodationConverter;

    // 숙소 목록조회
    public PagedResponse<AccommodationCardResponseDto> getAllAccommodations(
            Pageable pageable, AccommodationRequestDto request, Long memberId
    ) {
        Page<Accommodation> accommodations = accommodationRepository.findAll(pageable);
        AccommodationUtils.checkPageException(accommodations);
        return accomodationConverter.convertToPagedResponse(accommodations, null, null, memberId, this);
    }

    // 숙소 상세정보 조회
    public AccommodationDetailsResponseDto getAccommodationDetails(Long accommodationId, AccommodationRequestDto request, Long memberId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
            .orElseThrow(() -> new GlobalException(AccomodationErrorCode.RESOURCE_NOT_FOUND));

        List<RoomResponseDto> rooms = getRoomResponseDto(accommodationId, request.checkIn(), request.checkOut());
        List<ReviewResponseDto> reviews = getReviewResponse(accommodation.getReviews());
        Double avgStar = AccommodationUtils.calculateAverageStar(accommodation.getReviews());
        boolean isLiked = false;
        if (memberId!=null)
            isLiked = getIsLiked(memberId, accommodationId);

        return AccommodationDetailsResponseDto.toDto(accommodation, rooms, reviews, avgStar, isLiked);
    }

    // 객실 조회
    public RoomResponseDto getRoomDetail(Long accommodationId, Long roomId, AccommodationRequestDto request) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new GlobalException(AccomodationErrorCode.RESOURCE_NOT_FOUND));
        if(!accommodationId.equals(room.getAccommodation().getId()))
            throw new GlobalException(AccomodationErrorCode.INVALID_ROOM_REQUEST);
        boolean reservationAvailable = getReservationAvailable(request.checkIn(), request.checkOut(), roomId);
        return RoomResponseDto.toDto(room, reservationAvailable);
    }

    /**
     * 해당 객실의 예약가능 여부를 반환하는 메서드
     * @param checkIn   예약할 checkIn 정보
     * @param checkOut  예약할 checkOut 정보
     * @param roomId    조회할 객실 id
     * @return          예약가능 여부
     */
    public boolean getReservationAvailable(LocalDateTime checkIn, LocalDateTime checkOut, Long roomId) {
        List<Long> list = Collections.singletonList(roomId);
        List<Reservation> reservations = reservationRepository.findOverlappingReservations(list, checkIn, checkOut);
        return reservations.isEmpty();
    }


    /**
     * 숙소 상세정보의 객실 데이터 반환 메서드
     * @param accommodationId    해당 숙소의 id
     * @param checkIn           체크인 시간 ( default: 당일 ~ 익일 )
     * @param checkOut          체크아웃 시간
     * @return                  객실 정보가 담긴 객체 리스트 반환
     */
    private List<RoomResponseDto> getRoomResponseDto(Long accommodationId, LocalDateTime checkIn, LocalDateTime checkOut) {
        return roomRepository.findByAccommodationId(accommodationId).stream()
            .map(room -> RoomResponseDto.toDto(room, getReservationAvailable(checkIn, checkOut, room.getId())))
            .toList();
    }


    /**
     * 해당 숙소의 최근 작성된 리뷰 5개를 반환하는 메서드
     * @param reviews  조회할 리뷰 리스트
     * @return         최근 작성된 리뷰 객체 리스트 반환
     */
    private List<ReviewResponseDto> getReviewResponse(List<Review> reviews) {
        return reviews.stream()
            .sorted(Comparator.comparing(Review::getCreatedAt).reversed())
            .limit(5)
            .map(review -> new ReviewResponseDto(review.getComment(), review.getStar()))
            .toList();
    }


    public boolean getIsLiked(Long memberId, Long accomodationId) {
        boolean isLiked = false;
        Optional<Like> optionalIsLiked = likeRepository.findByMemberIdAndAccommodationId(memberId, accomodationId);
        isLiked = optionalIsLiked.map(Like::isLiked).orElse(false);
//        Optional<Like> optionalIsLiked = likeRepository.findByMemberIdAndAccomodationId(memberId, accomodationId);
//        isLiked = optionalIsLiked.map(Like::isLiked).orElse(false);
        return isLiked;
    }
}