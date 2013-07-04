package org.openinfinity.tagcloud.web.connection.exception;

public class InvalidConnectionCredentialException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String default_str = "Check your ConnectionCredential fields ";

	public InvalidConnectionCredentialException() {
		super(default_str);
	}

	public InvalidConnectionCredentialException(String message) {
		super(default_str + "[" + message + " ]");
	}

	public InvalidConnectionCredentialException(String message, Throwable cause) {
		super(default_str + "[" + message + " ]", cause);
	}

	public InvalidConnectionCredentialException(Throwable cause) {
		super(default_str, cause);
	}
}
