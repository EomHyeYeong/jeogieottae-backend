package com.example.mini.domain.accommodation.repository;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("""
                SELECT a FROM Accommodation a
                WHERE (:category IS NULL OR a.category = :category)
                  AND (:keyword IS NULL OR a.name LIKE %:keyword%)
            """)
    Page<Accommodation> findAllByFilters(
            @Param("category") AccommodationCategory category,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}