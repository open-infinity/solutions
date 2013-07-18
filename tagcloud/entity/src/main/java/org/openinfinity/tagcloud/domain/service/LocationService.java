package org.openinfinity.tagcloud.domain.service;

import java.util.List;

import org.openinfinity.tagcloud.domain.entity.query.TargetQuery;

public interface LocationService {
	List<TargetQuery> getLocations(String term);
}
