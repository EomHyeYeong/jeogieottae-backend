package com.example.mini.domain.review.fixture;

import com.example.mini.domain.review.entity.Review;

public class ReviewEntityFixture {

    public static Review reviewWithStar(int star) {
        return Review.builder()
                .star(star)
                .comment("test review comment")
                .build();
    }
}
