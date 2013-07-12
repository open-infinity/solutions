package org.openinfinity.tagcloud.web.connection.entity;


public class ConnectionCredential {

    private String client_id;
    private String client_secret;
    private String app_namespace;
    private String auth_scope;
    private String auth_redirect_link;
    private String logout_redirect_link;
    private String default_redirect_path;

    public ConnectionCredential() {
    	init();
    }
    public void init(){
    	this.client_id = "";
        this.client_secret = "";
        this.app_namespace = "";
        this.auth_scope = "";
        this.auth_redirect_link = "";
    }

    public String getDefault_redirect_path() {
		return default_redirect_path;
	}

	public void setDefault_redirect_path(String default_redirect_path) {
		this.default_redirect_path = default_redirect_path;
	}

	public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getApp_namespace() {
        return app_namespace;
    }

    public void setApp_namespace(String app_namespace) {
        this.app_namespace = app_namespace;
    }

    public String getAuth_scope() {
        return auth_scope;
    }

    public void setAuth_scope(String auth_scope) {
        this.auth_scope = auth_scope;
    }

    public String getAuth_redirect_link() {
        return auth_redirect_link;
    }

    public void setAuth_redirect_link(String auth_redirect_link) {
        this.auth_redirect_link = auth_redirect_link;
    }

    public String getLogout_redirect_link() {
        return logout_redirect_link;
    }

    public void setLogout_redirect_link(String logout_redirect_link) {
        this.logout_redirect_link = logout_redirect_link;
    }
}
