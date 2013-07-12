package org.openinfinity.tagcloud.domain.service.testdata;

import com.google.gson.*;
import org.apache.log4j.Logger;
import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Score;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.service.ProfileService;
import org.openinfinity.tagcloud.domain.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class TestDataGenerator {

    private static final Logger LOGGER = Logger.getLogger(TestDataGenerator.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	TargetService targetService;

	@Autowired
	ProfileService profileService;

	// public void generate() {
	// try {
	// BufferedReader reader = new BufferedReader(new
	// InputStreamReader(applicationContext.getResource("testData.txt").getInputStream()));
	// String line;
	// while ((line = reader.readLine()) != null) {
	// String[] elements = line.split(",");
	// if(elements.length < 3) continue;
	// Target target = new Target(elements[0].trim(),
	// Double.parseDouble(elements[2]), Double.parseDouble(elements[1]));
	// targetService.create(target);
	// Profile profile = new Profile("test user");
	// profileService.create(profile);
	// for(int i=3; i<elements.length; i++) {
	// targetService.addTagToTarget(new Tag(elements[i].trim().toLowerCase()),
	// target, profile);
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace(System.out);
	// }
	// }
	//
	//

	public static Profile testProfile;

	/*public void generate() {
		if (!profileService.contains(TEST_PROFILE))
			profileService.create(TEST_PROFILE);
		
		createTarget("Kampin kauppakeskus", 60.168852, 24.932203, new int[]{7,6,8,9}, "mall",
				"shopping centre","I love this place!","Amazing restaurants and shops in this area!"
				,"another tag","and another one ..","Nice Hotel right in the corner");
		createTarget("Vanha kirkkopuisto", 60.16613, 24.939774, new int[]{4,5,2,9}, "park", "outdoor");
		createTarget("Esplanadin puisto", 60.167198, 24.947627, new int[]{3,7,3,6}, "park", "outdoor");

	}

    */

    public List<Target> generate() {
        testProfile = new Profile("testFacebookId");
        testProfile = profileService.create(testProfile);

        List<Target> createdTargets = new ArrayList<Target>();

        createdTargets.add(createTarget("Kampin kauppakeskus", 60.168852, 24.932203, new int[]{7, 6, 8, 9}, "mall",
                "shopping centre", "I love this place!", "Amazing restaurants and shops in this area!"
                , "another tag", "and another one ..", "Nice Hotel right in the corner"));
        createdTargets.add(createTarget("Vanha kirkkopuisto", 60.16613, 24.939774, new int[]{4, 5, 2, 9}, "park", "outdoor"));
        createdTargets.add(createTarget("Esplanadin puisto", 60.167198, 24.947627, new int[]{3, 7, 3, 6}, "park", "outdoor"));



        String requestURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBPtTi_78oAV7GyAT0WJL6KckNfgKJthZE&location=60.169866,24.940926&radius=10000&sensor=false&language=fi";
        try {
            URL wikiRequest = new URL(requestURL);
            Scanner scanner = new Scanner(wikiRequest.openStream());
            String response = scanner.useDelimiter("\\Z").next();

            JsonElement jelement = new JsonParser().parse(response);
            JsonArray results = jelement.getAsJsonObject().getAsJsonArray("results");
            for(int i=0; i<results.size(); i++){
                JsonObject result = results.get(i).getAsJsonObject();
                double latitude = result.getAsJsonObject("geometry").getAsJsonObject("location").get("lat").getAsDouble();
                double longitude = result.getAsJsonObject("geometry").getAsJsonObject("location").get("lng").getAsDouble();
                String name = result.get("name").getAsString();
                double score = -1;
                if(result.get("rating") != null) score = result.get("rating").getAsDouble();
                List<String> tags = new ArrayList<String>();
                JsonArray tagArray = result.getAsJsonArray("types");
                for(int j=0; j<tagArray.size(); j++){
                    String tagName = tagArray.get(j).getAsString();
                    tagName = tagName.replace('_',' ');
                    tags.add(tagName);
                }
                if(score >= 0)
                    createdTargets.add(createTarget(name, latitude, longitude, new int[]{(int) score}, tags));
                else
                    createdTargets.add(createTarget(name, latitude, longitude, new int[]{}, tags));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            return createdTargets;
        }


    }




    private Target createTarget(String targetName, double latitude, double longitude,
                                int[] scores, String... tags) {
        Target target = targetService.create(new Target(targetName, longitude,
                latitude));
        for (int s : scores) {
            targetService.scoreTarget(s, target, testProfile.getFacebookId());
        }
        for (String tagName : tags) {
            targetService.addTagToTarget(tagName, target, testProfile.getFacebookId());
        }

        return targetService.loadById(target.getId());
    }

    private Target createTarget(String targetName, double latitude, double longitude,
                              int[] scores, List<String> tags) {
        Target target = targetService.create(new Target(targetName, longitude,
                latitude));
        for (int s : scores) {
            targetService.scoreTarget(s, target, testProfile.getFacebookId());
        }
        for (String tag : tags) {
            targetService.addTagToTarget(tag, target, testProfile.getFacebookId());
        }
        return targetService.loadById(target.getId());
    }



}
