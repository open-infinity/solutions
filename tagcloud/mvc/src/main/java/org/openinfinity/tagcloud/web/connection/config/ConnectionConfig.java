package org.openinfinity.tagcloud.web.connection.config;

import org.openinfinity.tagcloud.web.connection.entity.ConnectionCredential;

public interface ConnectionConfig {

	 ConnectionCredential buildDefaultConnectionCredential();
	 LoggingPolicy getDefaultLoggingPolicy();
}
