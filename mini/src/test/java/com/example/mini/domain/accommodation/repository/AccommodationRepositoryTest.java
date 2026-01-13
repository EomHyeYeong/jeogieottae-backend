package com.example.mini.domain.accommodation.repository;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccommodationRepositoryTest {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @BeforeEach
    void setUp() {
        accommodationRepository.deleteAll();

        accommodationRepository.save(Accommodation.builder().name("그랜드 호텔").description("테스트").postalCode("12345").address("테스트 주소").parkingAvailable(true).cookingAvailable(false).checkIn(LocalDateTime.now().plusHours(6)).checkOut(LocalDateTime.now().plusDays(1)).category(AccommodationCategory.SEOUL).build());
        accommodationRepository.save(Accommodation.builder().name("코지 펜션").description("테스트").postalCode("12345").address("테스트 주소").parkingAvailable(true).cookingAvailable(false).checkIn(LocalDateTime.now().plusHours(6)).checkOut(LocalDateTime.now().plusDays(1)).category(AccommodationCategory.SEOUL).build());
        accommodationRepository.save(Accommodation.builder().name("호텔 파라다이스").description("테스트").postalCode("12345").address("테스트 주소").parkingAvailable(true).cookingAvailable(false).checkIn(LocalDateTime.now().plusHours(6)).checkOut(LocalDateTime.now().plusDays(1)).category(AccommodationCategory.JEJU).build());
        accommodationRepository.save(Accommodation.builder().name("리조트 선샤인").description("테스트").postalCode("12345").address("테스트 주소").parkingAvailable(true).cookingAvailable(false).checkIn(LocalDateTime.now().plusHours(6)).checkOut(LocalDateTime.now().plusDays(1)).category(AccommodationCategory.GANGWON).build());
        accommodationRepository.save(Accommodation.builder().name("모텔 블루").description("테스트").postalCode("12345").address("테스트 주소").parkingAvailable(true).cookingAvailable(false).checkIn(LocalDateTime.now().plusHours(6)).checkOut(LocalDateTime.now().plusDays(1)).category(AccommodationCategory.INCHEON).build());
        accommodationRepository.save(Accommodation.builder().name("구월 호텔").description("테스트").postalCode("12345").address("테스트 주소").parkingAvailable(true).cookingAvailable(false).checkIn(LocalDateTime.now().plusHours(6)).checkOut(LocalDateTime.now().plusDays(1)).category(AccommodationCategory.CHUNGNAM).build());
    }

    @Test
    @DisplayName("카테고리와 키워드가 모두 null이면 전체 숙소를 조회한다.")
    void findAllByFiltersWithoutFilters() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Accommodation> result = accommodationRepository.findAllByFilters(null, null, pageable);

        // then
        assertThat(result.getContent()).hasSize(6);
        assertThat(result.getTotalElements()).isEqualTo(6);
    }

    @Test
    @DisplayName("카테고리 조건만 있으면 해당 카테고리 숙소를 조회한다.")
    void findAllByFiltersWithCategory() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Accommodation> result = accommodationRepository.findAllByFilters(AccommodationCategory.SEOUL, null, pageable);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("키워드 조건만 있으면 숙소 이름에 키워드가 포함된 데이터를 조회한다.")
    void findAllByFiltersWithKeyword() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Accommodation> result = accommodationRepository.findAllByFilters(null, "호텔", pageable);

        // then
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("카테고리와 키워드 조건이 모두 있으면 둘 다 만족하는 숙소를 조회한다.")
    void findAllByFiltersWithCategoryAndKeyword() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Accommodation> result = accommodationRepository.findAllByFilters(AccommodationCategory.SEOUL, "호텔", pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("조건에 맞는 데이터가 없는 경우")
    void findAllByFiltersNoResults() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Accommodation> result = accommodationRepository.findAllByFilters(
                AccommodationCategory.BUSAN, "존재하지않는숙소", pageable);

        // then
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    @DisplayName("페이지 사이즈에 따라 결과가 제한된다")
    void findAllByFiltersWithPaging() {
        // given
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Page<Accommodation> result =
                accommodationRepository.findAllByFilters(null, null, pageable);

        // then
        assertThat(result.getContent()).hasSize(3); // 조회 된 현재 페이지 요소의 개수
        assertThat(result.getTotalElements()).isEqualTo(6); // 조회 된 요소의 전체 개수
        assertThat(result.getTotalPages()).isEqualTo(2); // 페이지 수
    }


}