package com.project.lapstore.api.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lapstore.api.auth.annotation.NoAuth;
import com.project.lapstore.api.auth.jwt.JwtAuthorization;
import com.project.lapstore.core.auth.dto.AuthMapper;
import com.project.lapstore.core.auth.dto.request.LoginRequest;
import com.project.lapstore.core.auth.dto.response.LoginDetailResponse;
import com.project.lapstore.core.auth.dto.response.LoginSimpleResponse;
import com.project.lapstore.core.auth.dto.response.TokenReIssueResponse;
import com.project.lapstore.core.auth.service.AuthService;
import com.project.lapstore.core.user.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "인증 API")
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthApiController {

	private final AuthService authService;

	@NoAuth
	@PostMapping("/login")
	@Operation(summary = "로그인 API", description = "로그인을 한다")
	@ApiResponse(useReturnTypeSchema = true)
	public ResponseEntity<LoginSimpleResponse> login(
		@Valid @RequestBody LoginRequest request,
		HttpServletResponse httpServletResponse
	) {
		LoginDetailResponse loginDetailResponse = authService.login(request);
		LoginSimpleResponse loginSimpleResponse = LoginSimpleResponse.from(loginDetailResponse.accessToken());

		Cookie cookie = AuthMapper.toCookie(loginDetailResponse);
		httpServletResponse.addCookie(cookie);

		return ResponseEntity.ok(loginSimpleResponse);
	}

	@PostMapping("/logout")
	@Operation(summary = "로그아웃 API", description = "로그아웃을 한다")
	@ApiResponse(useReturnTypeSchema = true)
	public ResponseEntity<HttpStatus> logout(
		@Parameter(hidden = true) @JwtAuthorization User user
	) {
		authService.logout(user);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@NoAuth
	@PostMapping("/token")
	@Operation(summary = "토큰 재발급 API", description = "리프레쉬 토큰으로 요청하여 액세스 토큰을 재발급 받는다")
	@ApiResponse(useReturnTypeSchema = true)
	public ResponseEntity<TokenReIssueResponse> reIssueAccessToken(
		HttpServletRequest request
	) {
		String refreshTokenFromCookies = AuthMapper.extractRefreshTokenFromCookies(request);
		TokenReIssueResponse response = authService.createAccessTokenByRefreshToken(refreshTokenFromCookies);
		return ResponseEntity.ok(response);
	}
}
