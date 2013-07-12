package org.openinfinity.tagcloud.web.connection.config;

import org.openinfinity.tagcloud.web.connection.LoggingPolicy;
import org.openinfinity.tagcloud.web.connection.entity.ConnectionCredential;

public interface Config {

	 ConnectionCredential buildDefaultConnectionCredential();
	 LoggingPolicy getDefaultLoggingPolicy();
}
