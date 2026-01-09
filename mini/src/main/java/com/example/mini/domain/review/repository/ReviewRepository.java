package com.example.mini.domain.review.repository;

import com.example.mini.domain.reservation.entity.Reservation;
import com.example.mini.domain.review.entity.Review;
import com.example.mini.domain.accommodation.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r WHERE r.reservation = :reservation")
  boolean existsByReservation(Reservation reservation);

  Page<Review> findByAccommodationOrderByCreatedAtDesc(Accommodation accommodation, Pageable pageable);
}