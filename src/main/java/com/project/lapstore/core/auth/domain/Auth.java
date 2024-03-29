package com.project.lapstore.core.auth.domain;

import static jakarta.persistence.GenerationType.*;

import com.project.lapstore.core.common.entity.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Auth extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auth_id")
	private Long id;

	@Column(name = "auth_user_id", nullable = false, unique = true)
	private Long userId;

	@Column(name = "auth_refresh_token", nullable = false, unique = true)
	private String refreshToken;

	@Builder
	private Auth(Long userId, String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}

	public static Auth of(Long userId, String refreshToken) {
		return Auth.builder()
			.userId(userId)
			.refreshToken(refreshToken)
			.build();
	}

}
