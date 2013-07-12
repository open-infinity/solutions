package org.openinfinity.tagcloud.domain.entity;

import org.openinfinity.tagcloud.domain.entity.Tag;

import java.util.List;

public class TagQuery {
    private final List<Tag> required;
    private final List<Tag> preferred;
    private final List<Tag> nearby;
    private final double longitude;
    private final double latitude;
    private final double radius;

    public TagQuery(List<Tag> required, List<Tag> preferred, List<Tag> nearby, double longitude, double latitude, double radius) {
        this.required = required;
        this.preferred = preferred;
        this.nearby = nearby;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    public List<Tag> getRequired() {
        return required;
    }

    public List<Tag> getPreferred() {
        return preferred;
    }

    public List<Tag> getNearby() {
        return nearby;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getRadius() {
        return radius;
    }
}
