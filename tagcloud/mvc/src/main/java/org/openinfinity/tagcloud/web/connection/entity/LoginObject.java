package org.openinfinity.tagcloud.web.connection.entity;

public class LoginObject<T> {
	
	private boolean logged_in;
	private String session_id;
	private T user_object;
	
	public boolean isLogged_in() {
		return logged_in;
	}
	public void setLogged_in(boolean logged_in) {
		this.logged_in = logged_in;
	}
	
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public T getUser_object() {
		return user_object;
	}
	public void setUser_object(T user_object) {
		this.user_object = user_object;
	}
	
	
}
