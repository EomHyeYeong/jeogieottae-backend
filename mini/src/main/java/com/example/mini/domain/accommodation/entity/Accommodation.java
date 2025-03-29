package com.example.mini.domain.accommodation.entity;

import com.example.mini.domain.accommodation.entity.enums.AccommodationCategory;
import com.example.mini.domain.like.entity.Like;
import com.example.mini.domain.review.entity.Review;
import com.example.mini.global.model.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Accommodation extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private String postalCode;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean parkingAvailable;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean cookingAvailable;

	@Column(nullable = false)
	private LocalDateTime checkIn;

	@Column(nullable = false)
	private LocalDateTime checkOut;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccommodationCategory category;

	@OneToMany(mappedBy = "accommodation")
	private List<Room> rooms = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation", cascade = CascadeType.ALL)
	private List<Like> likes;

	@OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
	private List<Review> reviews;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation", cascade = CascadeType.ALL)
	private List<AccommodationImage> images = new ArrayList<>();

}
