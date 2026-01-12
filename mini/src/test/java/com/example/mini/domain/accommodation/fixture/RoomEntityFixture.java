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

    public static Room roomReservableFor(Long id, LocalDateTime checkIn, LocalDateTime checkOut) {
        return Room.builder()
                .id(id)
                .reservations(List.of(
                        ReservationEntityFixture.before(checkIn),
                        ReservationEntityFixture.after(checkOut)
                )).build();
    }

    public static Room roomNotReservableFor(Long id, LocalDateTime checkIn, LocalDateTime checkOut) {
        return Room.builder()
                .id(id)
                .reservations(List.of(
                        ReservationEntityFixture.before(checkIn),
                        ReservationEntityFixture.overlapping(checkIn, checkOut)
                )).build();
    }

    public static Room getDefaultRoom() {
        return Room.builder()
                .id(1L)
                .name("테스트 객실 이름")
                .baseGuests(2)
                .price(100000)
                .maxGuests(4)
                .extraPersonCharge(20000)
                .build();
    }

}
