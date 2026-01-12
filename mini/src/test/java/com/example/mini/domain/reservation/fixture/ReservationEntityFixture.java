package com.example.mini.domain.reservation.fixture;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.Room;
import com.example.mini.domain.member.entity.Member;
import com.example.mini.domain.reservation.entity.Reservation;
import com.example.mini.domain.reservation.entity.enums.ReservationStatus;
import java.time.LocalDateTime;

public class ReservationEntityFixture {

	public static Reservation getReservation(Member member, Accommodation accommodation, Room room) {
		return Reservation.builder()
			.id(1L)
			.peopleNumber(2)
			.extraCharge(0)
			.totalPrice(100000)
			.checkIn(LocalDateTime.now().minusDays(2))
			.checkOut(LocalDateTime.now().minusDays(1))
			.accommodation(accommodation)
			.member(member)
			.room(room)
			.status(ReservationStatus.CONFIRMED)
			.build();
	}

	// 기준 기간과 겹치지 않는 예약 (이전 기간)
	public static Reservation before(
			LocalDateTime checkIn
	) {
		return Reservation.builder()
				.checkIn(checkIn.minusDays(3))
				.checkOut(checkIn.minusDays(1))
				.build();
	}


	// 기준 기간과 겹치지 않는 예약 (이후 기간)
	public static Reservation after(
			LocalDateTime checkOut
	) {
		return Reservation.builder()
				.checkIn(checkOut.plusDays(1))
				.checkOut(checkOut.plusDays(2))
				.build();
	}

	// 기준 기간과 겹치는 예약
	public static Reservation overlapping(
			LocalDateTime checkIn,
			LocalDateTime checkOut
	) {
		return Reservation.builder()
				.checkIn(checkIn.minusHours(1))
				.checkOut(checkOut.minusHours(1))
				.build();
	}

}