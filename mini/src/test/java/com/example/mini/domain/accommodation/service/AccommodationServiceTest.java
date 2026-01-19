package com.example.mini.domain.accommodation.service;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.Room;
import com.example.mini.domain.accommodation.fixture.AccommodationEntityFixture;
import com.example.mini.domain.accommodation.fixture.RoomEntityFixture;
import com.example.mini.domain.accommodation.model.request.AccommodationRequestDto;
import com.example.mini.domain.accommodation.model.response.AccommodationCardResponseDto;
import com.example.mini.domain.accommodation.model.response.AccommodationDetailsResponseDto;
import com.example.mini.domain.accommodation.model.response.RoomResponseDto;
import com.example.mini.domain.accommodation.repository.AccommodationRepository;
import com.example.mini.domain.accommodation.repository.RoomRepository;
import com.example.mini.domain.member.entity.Member;
import com.example.mini.domain.member.fixture.MemberEntityFixture;
import com.example.mini.global.api.exception.GlobalException;
import com.example.mini.global.model.dto.PagedResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    @InjectMocks
    private AccommodationService accommodationService;

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private RoomRepository roomRepository;


    @Test
    @DisplayName("필터 조건에 맞는 숙소 목록을 페이징하여 조회한다.")
    void getAccommodationsSuccess() {
        // given
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusDays(1);

        AccommodationRequestDto request =
                new AccommodationRequestDto(checkIn, checkOut, null, null);

        Pageable pageable = PageRequest.of(0, 10);

        List<Accommodation> accommodations = AccommodationEntityFixture.accommodationsForListView();

        Page<Accommodation> page =
                new PageImpl<>(accommodations, pageable, 1);

        given(accommodationRepository.findAllByFilters(null, null, pageable))
                .willReturn(page);

        // when
        PagedResponse<AccommodationCardResponseDto> response =
                accommodationService.getAccommodations(pageable, request, 1L);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getTotalElements()).isEqualTo(3);
        assertThat(response.getContent()).hasSize(3);

        verify(accommodationRepository).findAllByFilters(null, null, pageable);
    }

    @Test
    @DisplayName("좋아요한 숙소는 liked 필드가 true로 설정된다.")
    void getAccommodations_WithLikedAccommodation() {
        // given
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusDays(1);
        Pageable pageable = PageRequest.of(0, 10);

        AccommodationRequestDto request =
                new AccommodationRequestDto(checkIn, checkOut, null, null);
        Member member = MemberEntityFixture.getMember();

        List<Accommodation> accommodations = AccommodationEntityFixture.accommodationsForListViewWithLiked(member);

        Page<Accommodation> page =
                new PageImpl<>(accommodations, pageable, 1);

        given(accommodationRepository.findAllByFilters(null, null, pageable))
                .willReturn(page);

        // when
        PagedResponse<AccommodationCardResponseDto> response =
                accommodationService.getAccommodations(pageable, request, 1L);

        // then
        assertThat(response.getContent().get(1).isLiked()).isTrue();
    }

    @Test
    @DisplayName("숙소 ID로 숙소 상세 정보를 조회한다.")
    void getAccommodationDetailSuccess() {
        // given
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusDays(1);
        Long accommodationId = 1L;
        Long memberId = 1L;
        AccommodationRequestDto request = new AccommodationRequestDto(checkIn, checkOut, null, null);

        Accommodation accommodation = AccommodationEntityFixture.withRoomsReviewsAndLikes();

        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));

        // when
        AccommodationDetailsResponseDto response =
                accommodationService.getAccommodationDetail(accommodationId, request, memberId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getRooms()).hasSize(4);
        assertThat(response.getAvgStar()).isEqualTo(3.5);

        verify(accommodationRepository).findById(accommodationId);
    }

    @Test
    @DisplayName("존재하지 않는 숙소 ID로 조회 시 예외가 발생한다.")
    void getAccommodationDetailNotFound() {
        // given
        given(accommodationRepository.findById(1L))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() ->
                accommodationService.getAccommodationDetail(1L, null, 1L)
        ).isInstanceOf(GlobalException.class);
    }

    @Test
    @DisplayName("리뷰가 없는 경우 평점은 0.0이다.")
    void getAccommodationDetail_NoReviews() {
        given(accommodationRepository.findById(1L))
                .willReturn(Optional.ofNullable(AccommodationEntityFixture.baseAccommodation()));

        AccommodationDetailsResponseDto response = accommodationService.getAccommodationDetail(1L, null, null);

        assertThat(response.getAvgStar()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("객실 ID로 상세 정보를 조회한다.")
    void getRoomDetail() {
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusDays(1);
        AccommodationRequestDto request = new AccommodationRequestDto(checkIn, checkOut, null, null);
        Long roomId = 3L;

        // given
        List<Room> rooms = RoomEntityFixture.baseRoomList();
        Accommodation accommodation = AccommodationEntityFixture.withRooms(rooms);
        Room room = accommodation.getRooms().get(2);

        given(roomRepository.findById(roomId))
                .willReturn(Optional.of(room));

        // when
        RoomResponseDto result =
                accommodationService.getRoomDetail(1L, roomId, request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(roomId);
        verify(roomRepository).findById(roomId);
    }

}