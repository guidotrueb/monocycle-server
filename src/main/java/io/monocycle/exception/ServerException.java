package io.monocycle.exception;

public class ServerException extends RuntimeException {

	private static final long serialVersionUID = -3559049787084588498L;

	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerException(String message) {
		super(message);
	}

}
