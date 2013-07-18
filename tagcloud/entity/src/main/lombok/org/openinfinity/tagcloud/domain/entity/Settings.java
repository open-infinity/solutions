package org.openinfinity.tagcloud.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Settings {
	public static final Settings NO_USER_SETTINGS = new Settings(0, 1, 1, 1, 0, 0);
	
    private double distanceScoreWeight;
    private double preferredScoreWeight;
    private double nearScoreWeight;
    private double avgScoreWeight;
    private double ownScoreWeight;
    private double friendScoreWeight;

    public Settings(double distanceScoreWeight, double preferredScoreWeight,
			double nearScoreWeight, double avgScoreWeight,
			double ownScoreWeight, double friendScoreWeight) {
		super();
		this.distanceScoreWeight = distanceScoreWeight;
		this.preferredScoreWeight = preferredScoreWeight;
		this.nearScoreWeight = nearScoreWeight;
		this.avgScoreWeight = avgScoreWeight;
		this.ownScoreWeight = ownScoreWeight;
		this.friendScoreWeight = friendScoreWeight;
	}

    
}
