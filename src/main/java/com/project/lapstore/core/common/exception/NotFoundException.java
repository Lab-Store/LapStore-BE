package com.project.lapstore.core.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

	private final String code;

	public NotFoundException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
	}
}
