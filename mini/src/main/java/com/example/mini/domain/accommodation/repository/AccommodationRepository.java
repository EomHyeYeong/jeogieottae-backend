package com.example.mini.domain.accommodation.repository;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> findByCategory(AccommodationCategory category);

    Page<Accommodation> findAll(Pageable pageable);

    Page<Accommodation> findByIdIn(List<Long> idList, Pageable pageable);
}