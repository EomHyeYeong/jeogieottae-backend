package com.example.mini.domain.accommodation.fixture;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.AccommodationImage;
import com.example.mini.domain.accommodation.entity.Room;
import com.example.mini.domain.accommodation.entity.RoomImage;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import org.testcontainers.shaded.com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AccommodationEntityFixture {

	public static Accommodation getAccomodation() {
		return Accommodation.builder()
			.id(1L)
			.name("테스트 호텔")
			.description("묵기 좋은 호텔")
			.postalCode("12345")
			.address("테스트 주소")
			.parkingAvailable(true)
			.cookingAvailable(true)
			.checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
			.checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
			.category(AccommodationCategory.SEOUL)
			.images(getAccomodationImageUrl())
			.reviews(Lists.newArrayList())
			.build();
	}

	public static Room getRoom(Accommodation accommodation) {
		return Room.builder()
			.id(1L)
			.name("테스트 객실")
			.baseGuests(2)
			.price(100000)
			.maxGuests(4)
			.extraPersonCharge(20000)
			.accommodation(accommodation)
			.images(getRoomImageUrl())
			.build();
	}

	public static Accommodation getAccomodationByCategory(AccommodationCategory category) {
		return Accommodation.builder()
				.name("테스트 호텔")
				.description("묵기 좋은 호텔")
				.postalCode("12345")
				.address("테스트 주소")
				.parkingAvailable(true)
				.cookingAvailable(true)
				.checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
				.checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
				.category(category)
				.build();
	}

	public static Accommodation getAccomodation1(AccommodationCategory category) {
		return Accommodation.builder()
				.name("테스트 호텔")
				.description("묵기 좋은 호텔")
				.postalCode("12345")
				.address("테스트 주소")
				.parkingAvailable(true)
				.cookingAvailable(true)
				.checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
				.checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
				.category(category)
				.build();
	}

	public static List<Accommodation> getAccomodationList() {
		Accommodation accommodation1 = Accommodation.builder()
				.id(1L)
				.name("제주도 펜션")
				.description("묵기 좋은 호텔")
				.postalCode("12345")
				.address("테스트 주소")
				.parkingAvailable(true)
				.cookingAvailable(true)
				.checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
				.checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
				.category(AccommodationCategory.JEJU)
				.images(getAccomodationImageUrl())
				.build();

		Accommodation accommodation2 = Accommodation.builder()
				.id(2L)
				.name("제주도 호텔")
				.description("묵기 좋은 호텔")
				.postalCode("12345")
				.address("테스트 주소")
				.parkingAvailable(true)
				.cookingAvailable(true)
				.checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
				.checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
				.category(AccommodationCategory.JEJU)
				.images(getAccomodationImageUrl())
				.build();
		return Lists.newArrayList(accommodation1, accommodation2);
	}

	public static List<Room> getRoomList() {
		Accommodation accommodation = Accommodation.builder()
				.id(2L)
				.name("제주도 호텔")
				.description("묵기 좋은 호텔")
				.postalCode("12345")
				.address("테스트 주소")
				.parkingAvailable(true)
				.cookingAvailable(true)
				.checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
				.checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
				.category(AccommodationCategory.JEJU)
				.images(getAccomodationImageUrl())
				.reviews(Lists.newArrayList())
				.build();

		Room room1 = Room.builder()
				.id(1L)
				.name("테스트 객실")
				.baseGuests(2)
				.price(100000)
				.maxGuests(4)
				.extraPersonCharge(20000)
				.accommodation(accommodation)
				.images(getRoomImageUrl())
				.build();

		Room room2 = Room.builder()
				.id(2L)
				.name("테스트 객실")
				.baseGuests(2)
				.price(100000)
				.maxGuests(4)
				.extraPersonCharge(20000)
				.accommodation(accommodation)
				.images(getRoomImageUrl())
				.build();

		return Lists.newArrayList(room1, room2);
	}

	public static List<AccommodationImage> getAccomodationImageUrl() {
		Accommodation accommodation = Accommodation.builder()
				.id(1L)
				.name("테스트 호텔")
				.description("묵기 좋은 호텔")
				.postalCode("12345")
				.address("테스트 주소")
				.parkingAvailable(true)
				.cookingAvailable(true)
				.checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
				.checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
				.category(AccommodationCategory.SEOUL)
				.build();

		return Arrays.asList(
				AccommodationImage.builder().id(1L).imgUrl("image url 1").accommodation(accommodation).build(),
				AccommodationImage.builder().id(2L).imgUrl("image url 2").accommodation(accommodation).build());
	}

	public static List<RoomImage> getRoomImageUrl() {
		Accommodation accommodation = Accommodation.builder()
				.id(1L)
				.name("테스트 호텔")
				.description("묵기 좋은 호텔")
				.postalCode("12345")
				.address("테스트 주소")
				.parkingAvailable(true)
				.cookingAvailable(true)
				.checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
				.checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
				.category(AccommodationCategory.SEOUL)
				.images(getAccomodationImageUrl())
				.build();

		Room room = Room.builder()
				.id(1L)
				.name("테스트 객실")
				.baseGuests(2)
				.price(100000)
				.maxGuests(4)
				.extraPersonCharge(20000)
				.accommodation(accommodation)
				.build();

		return Arrays.asList(
				RoomImage.builder().id(1L).imgUrl("image url 1").room(room).build(),
				RoomImage.builder().id(2L).imgUrl("image url 2").room(room).build());
	}
}
