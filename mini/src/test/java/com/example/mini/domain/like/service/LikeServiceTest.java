package com.example.mini.domain.like.service;

import com.example.mini.domain.accommodation.entity.Accommodation;
import com.example.mini.domain.accommodation.repository.AccommodationRepository;
import com.example.mini.domain.like.entity.Like;
import com.example.mini.domain.like.model.response.AccomodationResponse;
import com.example.mini.domain.like.repository.LikeRepository;
import com.example.mini.domain.member.entity.Member;
import com.example.mini.domain.member.repository.MemberRepository;
import com.example.mini.global.api.exception.GlobalException;
import com.example.mini.global.api.exception.error.LikeErrorCode;
import com.example.mini.global.model.dto.PagedResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikeServiceTest {

  @Mock
  private LikeRepository likeRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private AccommodationRepository accommodationRepository;

  @InjectMocks
  private LikeService likeService;

  public LikeServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addLikeShouldToggleLikeWhenLikeExists() {
    // Given
    Long memberId = 1L;
    Long accomodationId = 1L;
    Member member = new Member();
    Accommodation accommodation = new Accommodation();
    Like like = new Like(member, accommodation, true);

    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(accommodationRepository.findById(accomodationId)).thenReturn(Optional.of(accommodation));
    when(likeRepository.findByMemberIdAndAccomodationId(memberId, accomodationId))
        .thenReturn(Optional.of(like));

    // When
    boolean result = likeService.toggleLike(memberId, accomodationId);

    // Then
    assertFalse(result);
    verify(likeRepository).save(any(Like.class));
  }

  @Test
  void addLikeShouldAddLikeWhenLikeDoesNotExist() {
    // Given
    Long memberId = 1L;
    Long accomodationId = 1L;
    Member member = new Member();
    Accommodation accommodation = new Accommodation();

    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(accommodationRepository.findById(accomodationId)).thenReturn(Optional.of(accommodation));
    when(likeRepository.findByMemberIdAndAccomodationId(memberId, accomodationId))
        .thenReturn(Optional.empty());

    // When
    likeService.toggleLike(memberId, accomodationId);

    // Then
    verify(likeRepository).save(any(Like.class));
  }

  @Test
  void addLikeShouldThrowExceptionWhenMemberNotFound() {
    // Given
    Long memberId = 1L;
    Long accomodationId = 1L;

    when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

    // When & Then
    GlobalException exception = assertThrows(GlobalException.class, () ->
        likeService.toggleLike(memberId, accomodationId));
    assertEquals(LikeErrorCode.MEMBER_NOT_FOUND, exception.getErrorCode());
  }

  @Test
  void addLikeShouldThrowExceptionWhenAccomodationNotFound() {
    // Given
    Long memberId = 1L;
    Long accomodationId = 1L;
    Member member = new Member();

    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(accommodationRepository.findById(accomodationId)).thenReturn(Optional.empty());

    // When & Then
    GlobalException exception = assertThrows(GlobalException.class, () ->
        likeService.toggleLike(memberId, accomodationId));
    assertEquals(LikeErrorCode.ACCOMODATION_NOT_FOUND, exception.getErrorCode());
  }

  @Test
  void getLikedAccomodationsShouldReturnLikedAccomodations() {
    // Given
    Long memberId = 1L;
    Pageable pageable = PageRequest.of(0, 10);
    Member member = new Member();
    Accommodation accommodation = Accommodation.builder()
        .name("Test Accommodation")
        .description("Test Description")
        .postalCode("12345")
        .address("Test Address")
        .build();
    Like like = new Like(member, accommodation, true);
    Page<Like> likes = new PageImpl<>(Collections.singletonList(like));

    when(likeRepository.findByMemberIdAndIsLiked(memberId, true, pageable)).thenReturn(likes);

    // When
    PagedResponse<AccomodationResponse> result = likeService.getLikedAccomodations(memberId, 1);

    // Then
    assertEquals(1, result.getTotalElements());
    AccomodationResponse response = result.getContent().get(0);
    assertEquals("Test Accommodation", response.getName());
    assertEquals("Test Description", response.getDescription());
    assertEquals("12345", response.getPostalCode());
    assertEquals("Test Address", response.getAddress());
  }
}
