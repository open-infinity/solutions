package org.openinfinity.tagcloud.domain.service.testdata;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Score;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.service.ProfileService;
import org.openinfinity.tagcloud.domain.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TestDataGenerator {

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

	public static final Profile TEST_PROFILE = new Profile("test profile");

	public void generate() {
		if (!profileService.contains(TEST_PROFILE))
			profileService.create(TEST_PROFILE);
		
		add("Kampin kauppakeskus", 60.168852, 24.932203, new int[]{7,6,8,9}, "mall",
				"shopping centre","I love this place!","Amazing restaurants and shops in this area!"
				,"another tag","and another one ..","Nice Hotel right in the corner");
		add("Vanha kirkkopuisto", 60.16613, 24.939774, new int[]{4,5,2,9}, "park", "outdoor");
		add("Esplanadin puisto", 60.167198, 24.947627, new int[]{3,7,3,6}, "park", "outdoor");

	}

	private void add(String targetName, double latitude, double longitude,
			int[] scores, String... tags) {
		Target target = targetService.create(new Target(targetName, longitude,
				latitude));
		for (int s : scores) {
			Score score = new Score(s);
			targetService.addScoreToTarget(score, target);
		}
		for (String tagName : tags) {
			targetService
					.addTagToTarget(new Tag(tagName), target, TEST_PROFILE);
		}

	}

}
