package com.example.mini.domain.accommodation.repository;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import com.example.mini.domain.accommodation.fixture.AccommodationEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccommodationRepositoryTest { /*모두 통과*/

	@Autowired
	private AccommodationRepository accommodationRepository;

	private Accommodation testAccommodation1;
	private Accommodation testAccommodation2;

	@BeforeEach
	void setUp() {
		// 기존 데이터를 삭제하여 중복을 방지
		accommodationRepository.deleteAll();

		// 테스트 엔티티 생성 및 저장
		testAccommodation1 = AccommodationEntityFixture.getAccomodationByCategory(AccommodationCategory.SEOUL);
		testAccommodation2 = AccommodationEntityFixture.getAccomodationByCategory(AccommodationCategory.BUSAN);
		accommodationRepository.saveAll(Arrays.asList(testAccommodation1, testAccommodation2));
	}

//	@Test
//	void testFindByCategoryName() {
//		List<Accommodation> seoulAccommodations = accommodationRepository.findByCategory(AccommodationCategory.SEOUL);
//		assertThat(seoulAccommodations).contains(testAccommodation1);
//		assertThat(seoulAccommodations).doesNotContain(testAccommodation2);
//	}

	@Test
	void testFindAll() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Accommodation> accomodationPage = accommodationRepository.findAll(pageable);
		assertThat(accomodationPage.getContent()).hasSize(2);
	}

//	@Test
//	void testFindByIdList() {
//		List<Long> idList = Arrays.asList(testAccommodation1.getId(), testAccommodation2.getId());
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Accommodation> accomodationPage = accommodationRepository.findByIdIn(idList, pageable);
//		List<Long> returnedIds = accomodationPage.getContent().stream().map(Accommodation::getId).collect(Collectors.toList());
//		assertThat(returnedIds).containsExactlyInAnyOrderElementsOf(idList);
//	}
}
