package org.openinfinity.tagcloud.domain.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openinfinity.tagcloud.domain.entity.query.TargetQuery;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@Service
public class LocationServiceImpl implements LocationService {

	@Override
	public List<TargetQuery> getLocations(String term) {
		List<TargetQuery> locs = new ArrayList<TargetQuery>();
		
        String requestURL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+term+"&types=geocode&language=fi&sensor=true&key=AIzaSyBPtTi_78oAV7GyAT0WJL6KckNfgKJthZE";
        try {
            URL wikiRequest = new URL(requestURL);
            Scanner scanner = new Scanner(wikiRequest.openStream());
            String response = scanner.useDelimiter("\\Z").next();

            JsonElement jelement = new JsonParser().parse(response);
            JsonArray results = jelement.getAsJsonObject().getAsJsonArray("predictions");
            for(int i=0; i<results.size(); i++){
                JsonObject result = results.get(i).getAsJsonObject();
                String label = result.get("description").getAsString();
                String id = result.get("reference").getAsString();
                locs.add(new TargetQuery(label, TargetQuery.Category.Location, id));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            return locs;
        }


	}
	
}
