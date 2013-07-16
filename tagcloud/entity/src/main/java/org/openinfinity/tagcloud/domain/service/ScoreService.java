package org.openinfinity.tagcloud.domain.service;

import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Score;
import org.openinfinity.tagcloud.domain.entity.Target;

public interface ScoreService extends AbstractCrudServiceInterface<Score> {

	void scoreTarget(int scoreStars, Target target, String facebookId);
    
    List<Score> getFriendsScores(List<String> friendFacebookIds, Target target);
    
    double getAverageFriendScore(List<String> friendFacebookIds, Target target);
    
    Score getOwnScore(Profile profile, Target target);
}