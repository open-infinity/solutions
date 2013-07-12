package org.openinfinity.tagcloud.domain.entity.query;

public class CoordinateBounds {
    double nELng;
    double nELat;
    double sWLng;
    double sWLat;

    public CoordinateBounds(double nELng, double nELat, double sWLng, double sWLat) {
        this.nELng = nELng;
        this.nELat = nELat;
        this.sWLng = sWLng;
        this.sWLat = sWLat;
    }

    public double getnELng() {
        return nELng;
    }

    public void setnELng(double nELng) {
        this.nELng = nELng;
    }

    public double getnELat() {
        return nELat;
    }

    public void setnELat(double nELat) {
        this.nELat = nELat;
    }

    public double getsWLng() {
        return sWLng;
    }

    public void setsWLng(double sWLng) {
        this.sWLng = sWLng;
    }

    public double getsWLat() {
        return sWLat;
    }

    public void setsWLat(double sWLat) {
        this.sWLat = sWLat;
    }
}
