package com.example.mini.domain.accommodation.converter;

import com.example.mini.domain.accommodation.repository.RoomRepository;
import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.model.response.AccommodationCardResponseDto;
import com.example.mini.domain.accommodation.service.AccommodationService;
import com.example.mini.global.model.dto.PagedResponse;
import com.example.mini.global.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccomodationConverter {

	private final RoomRepository roomRepository;

	/**
	 * Entity → Dto 변환 및 응답 객체로 변환하는 메서드
	 *
	 * @param accommodations    변환할 객체
	 * @return                  숙소 정보 목록을 포함한 응답 객체
	 */
	public PagedResponse<AccommodationCardResponseDto> convertToPagedResponse(
			Page<Accommodation> accommodations, LocalDateTime checkIn, LocalDateTime checkOut, Long memberId, AccommodationService accommodationService) {
		List<AccommodationCardResponseDto> content = accommodations.getContent().stream().map(accommodation -> {
			Integer minPrice = roomRepository.findMinPriceByAccommodationId(accommodation.getId());
			boolean isAvailable = roomRepository.findByAccommodationId(accommodation.getId())
				.stream()
				.anyMatch(room -> accommodationService.getReservationAvailable(checkIn, checkOut, room.getId()));
			boolean isLiked = accommodationService.getIsLiked(memberId, accommodation.getId());
			return AccommodationCardResponseDto.toDto(accommodation, minPrice, isAvailable, isLiked);
		}).collect(Collectors.toList());

		return new PagedResponse<>(accommodations.getTotalPages(), accommodations.getTotalElements(), content);
	}

	public static Optional<Long> convertToMemberId(UserDetailsImpl userDetails) {
		return (userDetails==null) ? Optional.empty() :userDetails.getMemberId().describeConstable();
	}
}
