package org.openinfinity.tagcloud.utils;

import java.util.ArrayList;
import java.util.List;

import org.openinfinity.tagcloud.domain.entity.Target;

public class Utils {
	
	public static double earthAverageMeridionalRadius = 6367449;
	public static double earthEquatorialRadius = 6378137;
	
	public static <T> List<T> createList(T... objects) {
		List<T> list = new ArrayList<T>();
		for(T object : objects) {
			list.add(object);
		}
		return list;
	}
	
	//haversine formula @ http://www.movable-type.co.uk/scripts/latlong.html
	public static double calcDistanceGCS(double lon1, double lat1, double lon2, double lat2) {
		double dLon = degToRad(lon2-lon1);
		double dLat = degToRad(lat2-lat1);
		lat1 = degToRad(lat1);
		lat2 = degToRad(lat2);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return earthEquatorialRadius * c;
	}
	
	public static double calcDistanceLCS(double dx, double dy) {
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	//Note! works only with small distances
	public static double[] calcLocation(double originLongitude, double originLatitude, double distanceToEast, double distanceToNorth) {
		double[] newLocation = new double[2];
		originLongitude = degToRad(originLongitude);
		originLatitude = degToRad(originLatitude);
		double metersPerDegreeLongitude = (Math.PI/180)*earthEquatorialRadius*Math.cos(Math.atan(0.99664719*Math.tan(originLatitude)));
		double metersPerDegreeLatitude = 111132.954-559.822*Math.cos(2*originLatitude)+1.175*Math.cos(4*originLatitude);
		
		double newLongitude = radToDeg(originLongitude) + distanceToEast/metersPerDegreeLongitude;
		if(newLongitude < -180) newLongitude += 360;
		if(newLongitude > 180) newLongitude -= 360;
		double newLatitude = radToDeg(originLatitude) + distanceToNorth/metersPerDegreeLatitude;
		if(newLatitude < -90) newLatitude = -90;
		if(newLatitude > 90) newLatitude = 90;
		
		newLocation[0] = newLongitude;
		newLocation[1] = newLatitude;
		return newLocation;
	}
	
	
	
	public static double degToRad(double deg) {
		return 2*Math.PI*(deg/360);
	}

	public static double radToDeg(double rad) {
		return 360*rad/(2*Math.PI);
	}

	
	
}
