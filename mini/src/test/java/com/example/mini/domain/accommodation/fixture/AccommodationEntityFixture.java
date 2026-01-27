package com.example.mini.domain.accommodation.fixture;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.entity.Room;
import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import com.example.mini.domain.like.entity.Like;
import com.example.mini.domain.member.entity.Member;
import com.example.mini.domain.review.fixture.ReviewEntityFixture;
import com.example.mini.domain.review.entity.Review;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AccommodationEntityFixture {

    public static Accommodation withRooms(List<Room> rooms) {
        Accommodation accommodation = Accommodation.builder().build();
        rooms.forEach(accommodation::addRoom);
        return accommodation;
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

    public static Accommodation baseAccommodation() {
        return Accommodation.builder()
                .name("test 숙소 이름")
                .description("test 숙소 설명")
                .postalCode("12345")
                .address("test 숙소 주소")
                .parkingAvailable(true)
                .cookingAvailable(true)
                .checkIn(LocalDateTime.of(2023, 7, 1, 14, 0))
                .checkOut(LocalDateTime.of(2023, 7, 2, 11, 0))
                .category(AccommodationCategory.SEOUL)
                .build();
    }

    public static Accommodation withCategory(AccommodationCategory category) {
        return Accommodation.builder()
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

    public static List<Accommodation> accommodationsForListView() {
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = LocalDateTime.now().plusDays(1);
        return List.of(
                Accommodation.builder().name("test name 1").checkIn(checkIn).checkOut(checkOut).cookingAvailable(false).parkingAvailable(true).postalCode("12345").address("test address 1").description("test description 1").category(AccommodationCategory.SEOUL).rooms(RoomEntityFixture.baseRoomList()).build(),
                Accommodation.builder().name("test name 2").checkIn(checkIn).checkOut(checkOut).cookingAvailable(false).parkingAvailable(true).postalCode("12345").address("test address 1").description("test description 1").category(AccommodationCategory.SEOUL).rooms(RoomEntityFixture.baseRoomList()).build(),
                Accommodation.builder().name("test name 3").checkIn(checkIn).checkOut(checkOut).cookingAvailable(false).parkingAvailable(true).postalCode("12345").address("test address 1").description("test description 1").category(AccommodationCategory.SEOUL).rooms(RoomEntityFixture.baseRoomList()).build()
        );
    }

    public static Accommodation withRoomsReviewsAndLikes() {
        List<Room> rooms = RoomEntityFixture.baseRoomList();
        Review review1 = ReviewEntityFixture.reviewWithStar(4);
        Review review2 = ReviewEntityFixture.reviewWithStar(3);

        return Accommodation.builder()
                .rooms(rooms)
                .reviews(List.of(review1, review2))
                .build();
    }

    public static List<Accommodation> accommodationsForListViewWithLiked(Member member) {
        Like like = Like.builder().member(member).isLiked(true).build();
        return List.of(
                Accommodation.builder().category(AccommodationCategory.SEOUL).build(),
                Accommodation.builder().category(AccommodationCategory.SEOUL).likes(List.of(like)).build()
        );
    }

    public static Accommodation withReviews() {
        List<Review> reviews = List.of(
                ReviewEntityFixture.reviewWithStar(5),
                ReviewEntityFixture.reviewWithStar(3),
                ReviewEntityFixture.reviewWithStar(3)
        );
        return Accommodation.builder()
                .reviews(reviews)
                .build();
    }

}
