package org.openinfinity.tagcloud.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Settings {
	public static final Settings NO_USER_SETTINGS = new Settings(1, 1, 1, 1, 0, 0);
	
    private double distanceScoreWeight = 1;
    private double preferredScoreWeight = 1;
    private double nearScoreWeight = 1;
    private double avgScoreWeight = 1;
    private double ownScoreWeight = 1;
    private double friendScoreWeight = 1;

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
