//package com.example.mini.domain.accommodation.service;
//
//import com.example.mini.domain.accommodation.entity.Accommodation;
//import com.example.mini.domain.accommodation.entity.Room;
//import com.example.mini.domain.accommodation.fixture.AccommodationEntityFixture;
//import com.example.mini.domain.accommodation.model.response.*;
//import com.example.mini.domain.accommodation.repository.AccommodationRepository;
//import com.example.mini.domain.accommodation.repository.AccommodationSearchRepository;
//import com.example.mini.domain.accommodation.repository.RoomRepository;
//import com.example.mini.domain.reservation.entity.Reservation;
//import com.example.mini.domain.reservation.repository.ReservationRepository;
//import com.example.mini.global.api.exception.GlobalException;
//import com.example.mini.global.api.exception.error.AccomodationErrorCode;
//import com.example.mini.global.model.dto.PagedResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.*;
//import org.testcontainers.shaded.com.google.common.collect.Lists;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AccomodationServiceTest {
//
//    @Mock
//    private AccommodationRepository accomodationRepository;
//
//    @Mock
//    private AccommodationSearchRepository accomodationSearchRepository;
//
//    @Mock
//    private RoomRepository roomRepository;
//
//    @Mock
//    private ReservationRepository reservationRepository;
//
//    @InjectMocks
//    private AccommodationService accomodationService;
//
//    private Accommodation accommodation;
//    private List<Accommodation> accomodationList;
//    private Room room;
//    private List<Room> roomList;
//    Pageable pageable;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//
//        accommodation = AccommodationEntityFixture.getAccommodation();
//        accomodationList = AccommodationEntityFixture.getAccomodationList();
//        room = AccommodationEntityFixture.getRoom(accommodation);
//        roomList = AccommodationEntityFixture.getRoomList();
//        pageable = PageRequest.of(0, 20);
//    }
//
//    @Test
//    void 전체_숙소_목록조회_성공() {
//        Page<Accommodation> page = new PageImpl<>(accomodationList, pageable, accomodationList.size());
//        List<Room> roomList = Arrays.asList(room);
//
//        // When
//        when(accomodationRepository.findAll(pageable)).thenReturn(page);
//        when(roomRepository.findMinPriceByAccommodationId(anyLong())).thenReturn(50000);
//        when(roomRepository.findByAccomodationId(anyLong())).thenReturn(roomList);
//
//        // Then
//        PagedResponse<AccommodationCardResponseDto> result = accomodationService.getAllAccommodations(1);
//        assertEquals(2, result.getTotalElements());
//        assertEquals(1, result.getTotalPages());
//        assertEquals("제주도 펜션", result.getContent().get(0).getName());
//
//        verify(accomodationRepository).findAll(pageable);
//    }
//
//    @Test
//    void 전체숙소_목록조회_리소스_없음_예외_발생() {
//        Page<Accommodation> page = new PageImpl<>(new ArrayList<Accommodation>(), pageable, 0);
//        when(accomodationRepository.findAll(pageable)).thenReturn(page);
//
//        GlobalException exception = assertThrows(GlobalException.class, () -> accomodationService.getAllAccommodations(1));
//        assertEquals(AccomodationErrorCode.RESOURCE_NOT_FOUND, exception.getErrorCode());
//    }
//
//    @Test
//    void 숙소_검색_목록조회_성공() {
//        Page<Accommodation> page = new PageImpl<>(accomodationList, pageable, accomodationList.size());
//        List<Long> idList = Lists.newArrayList(1L, 2L);
//        List<Room> roomList = Arrays.asList(room);
//        List<AccomodationSearch> searches = Lists.newArrayList(new AccomodationSearch(1L, "제주도 펜션"), new AccomodationSearch(2L, "제주도 호텔"));
//        List<Reservation> reservationList = new ArrayList<Reservation>();
//
//
//        when(accomodationRepository.findByIdList(idList, pageable)).thenReturn(page);
//        when(accomodationSearchRepository.findAccommodationsByName(anyString())).thenReturn(searches);
//        when(roomRepository.findMinPriceByAccommodationId(anyLong())).thenReturn(50000);
//        when(roomRepository.findByAccomodationId(anyLong())).thenReturn(roomList);
//        when(reservationRepository.findOverlappingReservations(idList, LocalDateTime.now(), LocalDateTime.now()))
//                .thenReturn(reservationList);
//
//        PagedResponse<AccommodationCardResponseDto> result = accomodationService.searchByAccommodationName("제주도", "", "", "", 1);
//        assertEquals(2, result.getTotalElements());
//        assertEquals(1, result.getTotalPages());
//        assertEquals("제주도 펜션", result.getContent().get(0).getName());
//
//        verify(accomodationRepository).findByIdList(anyList(), eq(pageable));
//    }
//
//    @Test
//    void 숙소_검색_목록조회_리소스_없음_예외_발생() {
//        Page<Accommodation> page = new PageImpl<>(Arrays.asList(), pageable, 0);
//        List<Long> idList = Lists.newArrayList();
//        List<Room> roomList = Arrays.asList();
//        List<AccomodationSearch> searches = Lists.newArrayList(new AccomodationSearch(1L, "제주도 펜션"), new AccomodationSearch(2L, "제주도 호텔"));
//        List<Reservation> reservationList = new ArrayList<Reservation>();
//
//        when(accomodationRepository.findByIdList(idList, pageable)).thenReturn(page);
//        when(accomodationSearchRepository.findAccommodationsByName(anyString())).thenReturn(searches);
//        when(roomRepository.findMinPriceByAccommodationId(anyLong())).thenReturn(50000);
//        when(roomRepository.findByAccomodationId(anyLong())).thenReturn(roomList);
//        when(reservationRepository.findOverlappingReservations(idList, LocalDateTime.now(), LocalDateTime.now()))
//                .thenReturn(reservationList);
//
//        GlobalException exception = assertThrows(GlobalException.class, () -> accomodationService.searchByAccommodationName("존재하지않는숙소이름", "", "", "", 1));
//        assertEquals(AccomodationErrorCode.RESOURCE_NOT_FOUND, exception.getErrorCode());
//    }
//
//    @Test
//    void 숙소_상세_정보조회_성공() {
//        List<Long> idList = Lists.newArrayList(1L, 2L);
//        List<Reservation> reservationList = new ArrayList<Reservation>();
//
//        // When
//        when(accomodationRepository.findById(anyLong())).thenReturn(Optional.ofNullable(accommodation));
//        when(roomRepository.findByAccomodationId(any())).thenReturn(roomList);
//        when(reservationRepository.findOverlappingReservations(idList, LocalDateTime.now(), LocalDateTime.now()))
//                .thenReturn(reservationList);
//
//
//        // Then
//        AccommodationDetailsResponseDto response = accomodationService.getAccomodationDetails(1L, "", "");
//        assertEquals("테스트 호텔", response.getAccommodation().getName());
//        assertEquals(2, response.getRooms().size());
//        assertEquals("테스트 객실", response.getRooms().get(0).getName());
//
//        verify(accomodationRepository).findById(any());
//        verify(roomRepository).findByAccomodationId(any());
//    }
//
//    @Test
//    void 객실_상세_정보조회_성공() {
//        // When
//        when(roomRepository.findById(any())).thenReturn(Optional.ofNullable(room));
//
//        // Then
//        RoomResponseDto response = accomodationService.getRoomDetail(1L, 1L, "", "");
//        assertEquals("테스트 객실", response.getName());
//
//        verify(roomRepository).findById(any());
//    }
//
//    @Test
//    void 객실_상세_정보조회_리소스_없음_예외_발생() {
//        when(roomRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
//
//        GlobalException exception = assertThrows(GlobalException.class, () -> accomodationService.getRoomDetail(1L, 1L, "", ""));
//        assertEquals(AccomodationErrorCode.RESOURCE_NOT_FOUND, exception.getErrorCode());
//    }
//
//    @Test
//    void 객실_상세_정보조회_path_variable의_accomodation_id_불일치_예외_발생() {
//        when(roomRepository.findById(anyLong())).thenReturn(Optional.ofNullable(roomList.get(0)));
//
//        GlobalException exception = assertThrows(GlobalException.class, () -> accomodationService.getRoomDetail(accommodation.getId(), roomList.get(0).getId(), "", ""));
//        assertEquals(AccomodationErrorCode.INVALID_ROOM_REQUEST, exception.getErrorCode());
//    }
//}