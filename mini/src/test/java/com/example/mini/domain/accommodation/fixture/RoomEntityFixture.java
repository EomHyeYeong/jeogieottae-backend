package com.example.mini.domain.accommodation.fixture;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.Room;

public class RoomEntityFixture {

	public static Room getDefaultRoom(Accommodation accommodation) {
		return Room.builder()
			.name("테스트 객실")
			.baseGuests(2)
			.price(100000)
			.maxGuests(4)
			.extraPersonCharge(20000)
			.accommodation(accommodation)
			.build();
	}
}
