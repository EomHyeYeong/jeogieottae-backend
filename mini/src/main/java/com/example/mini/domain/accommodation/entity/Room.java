package com.example.mini.domain.accommodation.entity;

import com.example.mini.domain.reservation.entity.Reservation;
import com.example.mini.global.model.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Room extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer baseGuests;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private Integer maxGuests;

	@Column(nullable = false)
	private Integer extraPersonCharge;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accommodation_id")
	private Accommodation accommodation;

	@Builder.Default
	@BatchSize(size = 1000)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
	private List<RoomImage> images = new ArrayList<>();

	@Builder.Default
	@BatchSize(size = 1000)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
	private List<Reservation> reservations = new ArrayList<>();

	// room 예약 가능 여부 판단
	public boolean isReservationAvailable(LocalDateTime checkIn, LocalDateTime checkOut) {
		for (Reservation reservation : reservations) {
			if (checkIn.isBefore(reservation.getCheckOut()) && checkOut.isAfter(reservation.getCheckIn())) {
				return false; // 기간이 겹침 → 예약 불가
			}
		}
		return true;
	}

}