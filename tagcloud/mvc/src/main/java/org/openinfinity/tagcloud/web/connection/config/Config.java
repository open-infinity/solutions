package org.openinfinity.tagcloud.web.connection.config;

import org.openinfinity.tagcloud.web.connection.ConnectionCredential;
import org.openinfinity.tagcloud.web.connection.LoggingPolicy;

public interface Config {

	 ConnectionCredential buildDefaultConnectionCredential();
	 LoggingPolicy getDefaultLoggingPolicy();
}
