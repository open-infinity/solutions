package org.openinfinity.tagcloud.domain.repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.entity.query.CoordinateBounds;
import org.springframework.data.mongodb.core.geo.GeoResults;

public interface TargetRepository  extends AbstractCrudRepositoryInterface<Target> {

	List<Target> loadByCoordinates(double longitude, double latitude, double radius);

	Collection<Target> loadByTag(Tag tag);

    List<Target> loadByCoordinates(CoordinateBounds b, double radius);
}