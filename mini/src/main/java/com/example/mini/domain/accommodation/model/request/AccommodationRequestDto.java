package com.example.mini.domain.accommodation.model.request;

import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record AccommodationRequestDto(
		@NotNull(message = "체크인 날짜를 선택해주세요.") // todo: 기간 검증 로직, 예외처리 추가
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		@Schema(description = "체크인 날짜", example = "2025-08-27T16:00:00")
		LocalDateTime checkIn,

		@NotNull(message = "체크아웃 날짜를 선택해주세요.")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		@Schema(description = "체크아웃 날짜", example = "2025-08-28T16:00:00")
		LocalDateTime checkOut,

		@Schema(description = "지역", example = "서울")
		AccommodationCategory region,

		@Schema(description = "검색 키워드", example = "펜션")
		String keyword
) { }
