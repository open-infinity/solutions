package org.openinfinity.tagcloud.web.connection.exception;

public class NullActiveConnectionException extends Exception{

	private static final long serialVersionUID = 1L;
	private final static String default_str = "ActiveConnection cannot be Null ";

	public NullActiveConnectionException() {
		super(default_str);
	}

	public NullActiveConnectionException(String message) {
		super(default_str + "[" + message + " ]");
	}

	public NullActiveConnectionException(String message, Throwable cause) {
		super(default_str + "[" + message + " ]", cause);
	}

	public NullActiveConnectionException(Throwable cause) {
		super(default_str, cause);
	}
}
