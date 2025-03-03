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

}