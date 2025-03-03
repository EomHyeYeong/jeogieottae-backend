package com.example.mini.domain.accommodation.model.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@AllArgsConstructor
@Document(indexName = "accommodation")
public class AccommodationSearchResponseDto {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
