package com.project.lapstore.core.auth.domain;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptImpl implements EncryptHelper {

	@Override
	public String encrypt(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}

	@Override
	public boolean isMatch(String plainPassword, String hashedPassword) {
		try {
			return BCrypt.checkpw(plainPassword, hashedPassword);
		} catch (Exception e) {
			return false;
		}
	}
}
