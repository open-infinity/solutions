package org.openinfinity.tagcloud.web.connection.config;

import org.openinfinity.tagcloud.web.connection.ConnectionCredentialBuilder;
import org.openinfinity.tagcloud.web.connection.entity.ConnectionCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultConfig implements ConnectionConfig {
	@Value("${facebook.client_id}")
	private String client_id;
	@Value("${facebook.client_secret}")
	private String client_secret;
	@Value("${facebook.namespace}")
	private String app_namespace;
	@Value("${facebook.auth_scope}")
	String auth_scope;
	@Value("${facebook.auth_redirect_link}")
	private String auth_redirect_link;
	@Value("${facebook.logout_redirect_link}")
	private String logout_redirect_link;
	@Value("${webapp.default_redirect_path}")
	private String default_redirect_path;
	
	@Override
	public ConnectionCredential buildDefaultConnectionCredential() {
		return new ConnectionCredentialBuilder().setClient_id(client_id)
				.setClient_secret(client_secret)
				.setApp_namespace(app_namespace)
				.setAuth_redirect_link(auth_redirect_link)
				.setAuth_scope(auth_scope)
				.setLogout_redirect_link(logout_redirect_link)
				.setDefault_redirect_path(default_redirect_path).build();
	}
	@Override
	public LoggingPolicy getDefaultLoggingPolicy() {
		return LoggingPolicy.CLEAR_ON_PUBLIC_CALL;
	}
}
