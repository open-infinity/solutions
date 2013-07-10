package org.openinfinity.tagcloud.domain.entity.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private TagQuery query;
    private double recommendationScore;

    private static double preferredScoreWeight = 1;
    private static double nearScoreWeight = 1;
    private static double avgScoreWeight = 1;
    private static double ownScoreWeight = 0;
    private static double friendScoreWeight = 0;


    public Result(TagQuery tagQuery, List<Tag> nearbyTags) {
		this.query = tagQuery;
        for (Tag tag : nearbyTags) {
			NearbyTarget nearbyTarget = new NearbyTarget();
			nearbyTarget.setTag(tag);
			nearbyTarget.setDistance(Double.MAX_VALUE);
			nearbyTargetsMap.put(tag.getText(), nearbyTarget);
		}
	}
	
	public double updateRecommendationScore() {
        double preferredScore = calcPreferredScore();
        double nearScore = calcNearScore();
        double avgScore = target.getScore()/10.0;
        double ownScore = 1; //fix
        double friendScore = 1; //fix

        recommendationScore = (
                preferredScore*preferredScoreWeight +
                nearScore*nearScoreWeight +
                avgScore*avgScoreWeight +
                ownScore*ownScoreWeight +
                friendScore*friendScoreWeight
        )/(preferredScoreWeight+nearScoreWeight+avgScoreWeight+ownScoreWeight+friendScoreWeight);

        return recommendationScore;
	}

    private double calcPreferredScore() {
        if(query.getPreferred().size() > 0)
            return 1.0*preferredTags.size()/query.getPreferred().size();
        else return 1;
    }

    private double calcNearScore() {
        if(query.getNearby().size() == 0) return 1;

        double score = 0;
        for(NearbyTarget nearbyTarget : nearbyTargetsList){
            score += NearbyTarget.MAX_DISTANCE - nearbyTarget.getDistance();
        }
        score /= NearbyTarget.MAX_DISTANCE;
        score /= nearbyTargetsList.size();

        return score;
    }

	@Override
	public int compareTo(Result o) {
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
