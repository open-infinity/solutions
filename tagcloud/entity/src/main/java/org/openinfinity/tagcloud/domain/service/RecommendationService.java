package org.openinfinity.tagcloud.domain.service;

import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.query.Recommendation;

public interface RecommendationService {
    public void updateRecommendationScore(Recommendation recommendation, Profile user, List<String> friendFacebookIds);
}
