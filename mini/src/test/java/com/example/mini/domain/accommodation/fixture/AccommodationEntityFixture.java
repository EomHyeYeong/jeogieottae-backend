package com.example.mini.domain.accommodation.fixture;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.Room;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import com.example.mini.domain.like.entity.Like;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AccommodationEntityFixture {

    public static Accommodation withRooms(List<Room> rooms) {
        return Accommodation.builder()
                .rooms(rooms)
                .build();
    }

    public static Accommodation withRoomsPrice(int... prices) {
        List<Room> rooms = Arrays.stream(prices)
                .mapToObj(RoomEntityFixture::roomWithPrice)
                .toList();

        return withRooms(rooms);
    }

    public static Accommodation withLikes(List<Like> likes) {
        return Accommodation.builder()
                .likes(likes)
                .build();
    }

    public static Accommodation getDefaultAccommodation() {
        return Accommodation.builder()
                .id(1L)
                .name("테스트 호텔 이름")
                .description("테스트 호텔 설명")
                .postalCode("12345")
                .address("테스트 호텔 주소")
                .parkingAvailable(true)
                .cookingAvailable(true)
                .checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
                .checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
                .category(AccommodationCategory.SEOUL)
                .build();
    }

    public static Accommodation getAccommodationByCategory(AccommodationCategory category) {
        return Accommodation.builder()
                .id(1L)
                .name("테스트 호텔 이름")
                .description("테스트 호텔 설명")
                .postalCode("12345")
                .address("테스트 호텔 주소")
                .parkingAvailable(true)
                .cookingAvailable(true)
                .checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
                .checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
                .category(category)
                .build();
    }
}
