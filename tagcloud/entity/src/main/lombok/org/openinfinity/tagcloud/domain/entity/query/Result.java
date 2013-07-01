package org.openinfinity.tagcloud.domain.entity.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;

@Data
@NoArgsConstructor
public class Result implements Comparable<Result> {
	private Target target;
	private List<Tag> requiredTags = new ArrayList<Tag>();
	private List<Tag> preferredTags = new ArrayList<Tag>();
	private List<NearbyTarget> nearbyTargetsList = new ArrayList<NearbyTarget>();
	private HashMap<String, NearbyTarget> nearbyTargetsMap = new HashMap<String, NearbyTarget>();

	public Result(List<Tag> nearbyTags) {
		for (Tag tag : nearbyTags) {
			NearbyTarget nearbyTarget = new NearbyTarget();
			nearbyTarget.setTag(tag);
			nearbyTarget.setDistance(Double.MAX_VALUE);
			nearbyTargetsMap.put(tag.getText(), nearbyTarget);
		}
	}
	
	public double getPriority() {
		return 0;
	}

	@Override
	public int compareTo(Result o) {
		if(this.getPriority() > o.getPriority()) return -1;
		if(this.getPriority() < o.getPriority()) return 1;
		return 0;
	}
	
	public void updateNearbyTargetList() {
		for(String tag : nearbyTargetsMap.keySet()) {
			nearbyTargetsList.add(nearbyTargetsMap.get(tag));
		}
	}
	
}
