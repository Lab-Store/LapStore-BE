package com.project.lapstore.core.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lapstore.core.auth.domain.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {
	Optional<Auth> findByRefreshToken(String refreshToken);

	Optional<Auth> findByUserId(Long userId);

	@Modifying
	@Query("update Auth a SET a.refreshToken = :refreshToken where a.id = :id")
	void updateRefreshToken(@Param("id") Long id, @Param("refreshToken") String refreshToken);
}
