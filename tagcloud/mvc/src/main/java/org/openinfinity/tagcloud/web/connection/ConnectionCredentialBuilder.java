package org.openinfinity.tagcloud.web.connection;

import org.openinfinity.tagcloud.web.connection.entity.ConnectionCredential;
import org.springframework.beans.factory.annotation.Value;

public class ConnectionCredentialBuilder {

	private ConnectionCredential credential;

	public ConnectionCredentialBuilder() {
		this.credential = new ConnectionCredential();
	}

	public ConnectionCredentialBuilder setClient_id(String client_id) {
		this.credential.setClient_id(client_id);
		return this;
	}

	public ConnectionCredentialBuilder setClient_secret(String client_secret) {
		this.credential.setClient_secret(client_secret);
		return this;
	}

	public ConnectionCredentialBuilder setApp_namespace(String app_namespace) {
		this.credential.setApp_namespace(app_namespace);
		return this;
	}

	public ConnectionCredentialBuilder setAuth_scope(String auth_scope) {
		this.credential.setAuth_scope(auth_scope);
		return this;
	}

	public ConnectionCredentialBuilder setAuth_redirect_link(
			String auth_redirect_link) {
		this.credential.setAuth_redirect_link(auth_redirect_link);
		return this;
	}

	public ConnectionCredentialBuilder setLogout_redirect_link(
			String logout_redirect_link) {
		this.credential.setLogout_redirect_link(logout_redirect_link);
		return this;
	}
	public ConnectionCredentialBuilder setDefault_redirect_path(
			String default_redirect_path) {
		this.credential.setDefault_redirect_path(default_redirect_path);
		return this;
	}

	public ConnectionCredential build() {
		return this.credential;
	}

}
