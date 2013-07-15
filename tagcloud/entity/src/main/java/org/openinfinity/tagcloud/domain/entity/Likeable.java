package org.openinfinity.tagcloud.domain.entity;

import java.util.Set;

public interface Likeable {
	public Set<Like> getLikes();
	public void like(Profile profile);
}
