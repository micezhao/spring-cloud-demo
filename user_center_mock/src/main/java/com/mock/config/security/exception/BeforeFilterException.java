package com.mock.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class BeforeFilterException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9211684373673393066L;

	public BeforeFilterException(String msg) {
		super(msg);
	}

}
