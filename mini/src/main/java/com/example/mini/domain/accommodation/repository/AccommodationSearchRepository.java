package com.example.mini.domain.accommodation.repository;

import com.example.mini.domain.accommodation.model.response.AccommodationSearchResponseDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AccommodationSearchRepository extends ElasticsearchRepository<AccommodationSearchResponseDto, Long> {
    List<AccommodationSearchResponseDto> findAccommodationsByName(String keyword);
}
