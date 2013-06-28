package org.openinfinity.tagcloud.domain.entity.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;

@Data
@NoArgsConstructor
public class NearbyTarget {
	private Target target;
	private Tag tag;
	private double distance;
	
	public static final double MAX_DISTANCE = 1000;
}
