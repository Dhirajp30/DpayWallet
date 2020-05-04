package com.Dpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WalletException extends RuntimeException {

	private static final long serialVersionUID = -4005267182579342970L;

	public WalletException() {

	}

	public WalletException(String message) {
		super(message);

	}

	public WalletException(Throwable cause) {
		super(cause);

	}

	public WalletException(String message, Throwable cause) {
		super(message, cause);

	}

	public WalletException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}