package com.example.mini.domain.like.fixture;

import com.example.mini.domain.like.entity.Like;
import com.example.mini.domain.member.entity.Member;
import com.example.mini.domain.accommodation.entity.Accommodation;

public class LikeEntityFixture {

	public static Like getLike(Member member, Accommodation accommodation) {
		return Like.builder()
			.member(member)
			.accommodation(accommodation)
			.isLiked(true)
			.build();
	}
}
