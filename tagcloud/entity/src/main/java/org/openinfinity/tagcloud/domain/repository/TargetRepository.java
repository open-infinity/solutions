package org.openinfinity.tagcloud.domain.repository;

import java.math.BigInteger;
import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Target;

public interface TargetRepository  extends AbstractCrudRepositoryInterface<Target, BigInteger> {

	Collection<Target> loadByCoordinates(double longitude, double latitude, double radius);

}