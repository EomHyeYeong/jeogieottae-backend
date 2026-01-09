package com.example.mini.domain.accommodation.repository;

import com.example.mini.domain.accommodation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
