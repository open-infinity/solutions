package org.openinfinity.tagcloud.domain.entity.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.utils.Utils;

@Data
@NoArgsConstructor
public class Recommendation implements Comparable<Recommendation> {
	private Target target;
	private List<Tag> requiredTags = new ArrayList<Tag>();
	private List<Tag> preferredTags = new ArrayList<Tag>();
	private List<NearbyTarget> nearbyTargetsList = new ArrayList<NearbyTarget>();
	private HashMap<String, NearbyTarget> nearbyTargetsMap = new HashMap<String, NearbyTarget>();
    private TagQuery query;
    private double recommendationScore;


    public Recommendation(TagQuery tagQuery, List<Tag> nearbyTags) {
		this.query = tagQuery;
        for (Tag tag : nearbyTags) {
			NearbyTarget nearbyTarget = new NearbyTarget();
			nearbyTarget.setTag(tag);
			nearbyTarget.setDistance(Double.MAX_VALUE);
			nearbyTargetsMap.put(tag.getText(), nearbyTarget);
		}
	}
	

	@Override
	public int compareTo(Recommendation o) {
		if(this.getRecommendationScore() > o.getRecommendationScore()) return -1;
		if(this.getRecommendationScore() < o.getRecommendationScore()) return 1;
		return 0;
	}
	
	public void updateNearbyTargetList() {
		for(String tag : nearbyTargetsMap.keySet()) {
			nearbyTargetsList.add(nearbyTargetsMap.get(tag));
		}
	}


}
