package org.openinfinity.tagcloud.domain.entity.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class TagQuery {
    private List<Tag> required = new ArrayList<Tag>();
    private List<Tag> preferred = new ArrayList<Tag>();
    private List<Tag> nearby = new ArrayList<Tag>();

    private double longitude;
    private double latitude;
    private double radius;

    private Profile user = null;
    private List<String> friendFacebookIds = new ArrayList<String>();
    
    public TagQuery(double longitude, double latitude, double radius) {
    	this.longitude = longitude;
    	this.latitude = latitude;
    	this.radius = radius;
    }

    public TagQuery requireTags(Collection<Tag> tags) {
		required.addAll(tags);
		return this;
	}
    
    public TagQuery preferTags(Collection<Tag> tags) {
		preferred.addAll(tags);
		return this;
	}
    
    public TagQuery nearbyTags(Collection<Tag> tags) {
		nearby.addAll(tags);
		return this;
	}
    
	public TagQuery friendFacebookIds(Collection<String> friendFacebookIds) {
		this.friendFacebookIds.addAll(friendFacebookIds);
		return this;
	}
    
	public TagQuery user(Profile user) {
		this.user = user;
		return this;
	}
	    
    
}
