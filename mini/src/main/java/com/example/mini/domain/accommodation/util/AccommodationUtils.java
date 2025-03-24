package com.example.mini.domain.accommodation.util;

import com.example.mini.domain.accommodation.repository.AccommodationRepository;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
//import com.example.mini.domain.accommodation.repository.AccommodationSearchRepository;
import com.example.mini.domain.review.entity.Review;
import com.example.mini.global.api.exception.GlobalException;
import com.example.mini.global.api.exception.error.AccomodationErrorCode;
import com.example.mini.global.model.entity.BaseEntity;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class AccommodationUtils {

	public static <T> void checkPageException(Page<T> page) {
		if (page == null || page.getContent().isEmpty()) {
			throw new GlobalException(AccomodationErrorCode.RESOURCE_NOT_FOUND);
		}
	}

	public static Double calculateAverageStar(List<Review> reviews) {
		return reviews.isEmpty() ? 0.0 :
			BigDecimal.valueOf(reviews.stream().mapToDouble(Review::getStar).average().orElse(0.0))
				.setScale(1, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 입력된 숙소명에 대한 숙소 id를 반환하는 메서드
	 * @param keyword   검색할 숙소명
	 * @return          검색결과 id 리스트
	 */
//	public static List<Long> getIdByKeyword(String keyword, AccommodationSearchRepository accomodationSearchRepository) {
//		if (keyword.isEmpty()) {
//			return new ArrayList<>();
//		}
//		List<AccommodationSearchResponseDto> searches = accomodationSearchRepository.findAccommodationsByName(keyword);
//		return searches.stream().map(AccommodationSearchResponseDto::getId).toList();
//	}

	/**
	 * 입력된 지역명에 대한 숙소 id를 반환하는 메서드
	 * @param region    검색할 지역명
	 * @return          검색결과 id 리스트
	 */
	public static List<Long> getIdByRegion(String region, AccommodationRepository accommodationRepository) {
		if (region.isEmpty()) {
			return new ArrayList<>();
		}
		AccommodationCategory category = AccommodationCategory.fromName(region);
		return accommodationRepository.findByCategory(category).stream().map(BaseEntity::getId).toList();
	}

	/**
	 * 두 리스트의 공통 id를 추출해 반환하는 메서드
	 * 만약 한 리스트가 비어있을 경우 다른 리스트 전체를 반환
	 * @param keywordIList  숙소명 검색결과 리스트
	 * @param regionIdList  지역명 검색결과 리스트
	 * @return              두 리스트의 공통 id 리스트
	 */
	public static List<Long> getCommonId(List<Long> keywordIList, List<Long> regionIdList) {
		if (regionIdList.isEmpty() && keywordIList.isEmpty()) {
			throw new GlobalException(AccomodationErrorCode.RESOURCE_NOT_FOUND);
		}
		if (regionIdList.isEmpty()) {
			return keywordIList;
		}
		if (keywordIList.isEmpty()) {
			return regionIdList;
		}
		Set<Long> idSet1 = new HashSet<>(keywordIList);
		return regionIdList.stream()
			.filter(idSet1::contains)
			.collect(Collectors.toList());
	}
}
