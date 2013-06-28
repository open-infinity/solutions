package org.openinfinity.tagcloud.domain.repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.springframework.data.mongodb.core.geo.GeoResults;

public interface TargetRepository  extends AbstractCrudRepositoryInterface<Target, BigInteger> {

	List<Target> loadByCoordinates(double longitude, double latitude, double radius);

	Collection<Target> loadByTag(Tag tag);

	

	
}