package com.example.mini.domain.accommodation.fixture;

import com.example.mini.domain.accommodation.entity.Room;
import com.example.mini.domain.reservation.fixture.ReservationEntityFixture;

import java.time.LocalDateTime;
import java.util.List;

public class RoomEntityFixture {

    public static Room roomWithPrice(int price) {
        return Room.builder()
                .price(price)
                .build();
    }

    public static Room roomReservableFor(LocalDateTime checkIn, LocalDateTime checkOut) {
        return Room.builder()
                .reservations(List.of(
                        ReservationEntityFixture.before(checkIn),
                        ReservationEntityFixture.after(checkOut)
                )).build();
    }

    public static Room roomNotReservableFor(LocalDateTime checkIn, LocalDateTime checkOut) {
        return Room.builder()
                .reservations(List.of(
                        ReservationEntityFixture.before(checkIn),
                        ReservationEntityFixture.overlapping(checkIn, checkOut)
                )).build();
    }

    public static Room baseRoom(Long id) {
        return Room.builder()
                .id(id)
                .name("테스트 객실 이름")
                .baseGuests(2)
                .price(100000)
                .maxGuests(4)
                .extraPersonCharge(20000)
                .build();
    }

    public static List<Room> baseRoomList() {
        return List.of(
                baseRoom(1L),
                baseRoom(2L),
                baseRoom(3L),
                baseRoom(4L)
        );
    }

}
