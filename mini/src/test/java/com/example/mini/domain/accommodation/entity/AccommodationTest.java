package com.example.mini.domain.accommodation.entity;

import com.example.mini.domain.accommodation.fixture.AccommodationEntityFixture;
import com.example.mini.domain.accommodation.fixture.RoomEntityFixture;
import com.example.mini.domain.like.entity.Like;
import com.example.mini.domain.like.fixture.LikeEntityFixture;
import com.example.mini.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccommodationTest {

    @Test
    @DisplayName("여러 객실 중 최소 객실 가격을 반환한다.")
    void calculateMinimumRoomPrice() {
        // given
        Accommodation accommodation =
                AccommodationEntityFixture.withRoomsPrice(100_000, 80_000, 120_000);

        // when
        int minPrice = accommodation.calculateMinimumRoomPrice();

        // then
        assertThat(minPrice).isEqualTo(80_000);
    }

    @Test
    @DisplayName("해당 멤버가 좋아요를 누른 숙소라면 true를 반환한다.")
    void isLikedTrue() {
        // given
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();

        List<Like> likes = List.of(
                LikeEntityFixture.likeBy(member1),
                LikeEntityFixture.unlikedBy(member2)
        );

        Accommodation accommodation = AccommodationEntityFixture.withLikes(likes);

        // when
        boolean liked = accommodation.isLiked(1L);

        // then
        assertThat(liked).isTrue();
    }

    @Test
    @DisplayName("해당 멤버가 좋아요를 누르지 않은 숙소라면 false를 반환한다.")
    void isLikedFalse() {
        // given
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();

        List<Like> likes = List.of(
                LikeEntityFixture.likeBy(member1),
                LikeEntityFixture.unlikedBy(member2)
        );

        Accommodation accommodation = AccommodationEntityFixture.withLikes(likes);

        // when
        boolean liked = accommodation.isLiked(2L);

        // then
        assertThat(liked).isFalse();
    }

    @Test
    @DisplayName("좋아요가 하나도 없으면 false를 반환한다.")
    void isLikedWhenNoLikes() {
        // given
        Accommodation accommodation =
                AccommodationEntityFixture.withLikes(List.of());

        // when
        boolean liked = accommodation.isLiked(1L);

        // then
        assertThat(liked).isFalse();
    }

    @Test
    @DisplayName("해당 숙소의 객실이 하나라도 예약 가능하면 true를 반환한다.")
    void isReservationAvailableTrue() {
        // given
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusDays(1);

        List<Room> rooms = List.of(
                RoomEntityFixture.roomReservableFor(checkIn, checkOut),
                RoomEntityFixture.roomNotReservableFor(checkIn, checkOut)
        );

        Accommodation accommodation = AccommodationEntityFixture.withRooms(rooms);

        // when
        boolean isReservationAvailable = accommodation.isReservationAvailable(checkIn, checkOut);

        // then
        assertThat(isReservationAvailable).isTrue();
    }

    @Test
    @DisplayName("해당 숙소의 객실이 모두 예약 불가능하면 false를 반환한다.")
    void isReservationAvailableFalse() {
        // given
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusDays(1);

        List<Room> rooms = List.of(
                RoomEntityFixture.roomNotReservableFor(checkIn, checkOut),
                RoomEntityFixture.roomNotReservableFor(checkIn, checkOut)
        );

        Accommodation accommodation = AccommodationEntityFixture.withRooms(rooms);

        // when
        boolean isReservationAvailable = accommodation.isReservationAvailable(checkIn, checkOut);

        // then
        assertThat(isReservationAvailable).isFalse();
    }

}