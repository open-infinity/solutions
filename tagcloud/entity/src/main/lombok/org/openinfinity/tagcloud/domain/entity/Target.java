package org.openinfinity.tagcloud.domain.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.openinfinity.core.annotation.NotScript;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Target implements TextEntity {

	@Id
	private String id;

	@NonNull
	@NotScript
	@Size(min=2, max=50)
	private String text;

	private Set<Tag> tags = new HashSet<Tag>();

	private List<Score> scores = new ArrayList<Score>();
	
	private double score = 0;

	private List<Comment> comments = new ArrayList<Comment>();
	
	@GeoSpatialIndexed(bits=30, collection="target")
	private double[] location = new double[2];
	
	public Target(String text, double longitude, double latitude) {
		super();
		this.text = text;
		setLocation(longitude, latitude);
	}
	
	
	public void setLocation(double longitude, double latitude) {
		location[0] = longitude;
		location[1] = latitude;
	}

	/*
	 * removes existing score from the same user, adds a new one and updates the average score
	 * returns users old score or null if it does not exist
	 */
	public Score addScore(Score score) {
		Score oldScore = null;
		
		ListIterator<Score> iterator = scores.listIterator();
		while (iterator.hasNext()) {
			Score existingScore = iterator.next();
		
			if(existingScore.getProfile().getId().equals(score.getProfile().getId())) {
				oldScore = existingScore;
				iterator.remove();
				break;
			}
		}
		scores.add(score);
		calcScore();
		return oldScore;
	}
	
	@Override
	public String toString() {
		return "Target, id="+id+", text="+text;
	}



	private void calcScore() {
		int size = scores.size();
		int summ = 0;
		for(Score s : scores){
			summ += s.getStars();
		}
		score = 1.0*summ/size; 
	}



}