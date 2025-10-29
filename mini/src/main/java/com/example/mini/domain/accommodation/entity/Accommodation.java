package com.example.mini.domain.accommodation.entity;

import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import com.example.mini.domain.like.entity.Like;
import com.example.mini.domain.reservation.entity.Reservation;
import com.example.mini.domain.review.entity.Review;
import com.example.mini.global.model.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Accommodation extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean parkingAvailable;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean cookingAvailable;

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime checkOut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationCategory category;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "accommodation")
    private List<Room> rooms = new ArrayList<>();

    @BatchSize(size = 1000)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @BatchSize(size = 1000)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation", cascade = CascadeType.ALL)
	private List<AccommodationImage> images;

    @BatchSize(size = 1000)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

	public int calculateMinimumRoomPrice() {
		return rooms.stream()
				.mapToInt(Room::getPrice)
				.min()
				.orElse(0);
	}

	public boolean isLiked(Long memberId) {
		return likes.stream().anyMatch(like -> like.getMember().getId().equals(memberId));
	}

    // 숙소 예약 가능 여부 판단 (rooms 중 하나라도 예약 가능하면 true, 모두 불가면 false)
    public boolean isReservationAvailable(LocalDateTime checkIn, LocalDateTime checkOut) {
        return rooms.stream().anyMatch(room -> room.isReservationAvailable(checkIn, checkOut));
    }

}
