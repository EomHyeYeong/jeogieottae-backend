package com.example.mini.domain.accommodation.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccommodationRequestDto {
	private String checkIn;
	private String checkOut;
}
