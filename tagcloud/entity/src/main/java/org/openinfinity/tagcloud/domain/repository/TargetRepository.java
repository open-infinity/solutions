package org.openinfinity.tagcloud.domain.repository;

import java.util.Collection;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.entity.query.CoordinateBounds;

public interface TargetRepository  extends AbstractCrudRepositoryInterface<Target> {

	List<Target> loadByCoordinates(double longitude, double latitude, double radius);

	Collection<Target> loadByTag(Tag tag);

    List<Target> loadByCoordinates(CoordinateBounds b, double radius);

	List<Target> searchLike(String input);
	
	List<Target> searchFromTags(String tag);
	
	List<Target> searchAll();
}