package org.openinfinity.tagcloud.domain.service.testdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.sound.sampled.Line;

import org.openinfinity.tagcloud.domain.entity.Profile;
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
	
//	public void generate() {
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(applicationContext.getResource("testData.txt").getInputStream()));
//			String line;
//			while ((line = reader.readLine()) != null) {
//				String[] elements = line.split(",");
//				if(elements.length < 3) continue;
//				Target target = new Target(elements[0].trim(), Double.parseDouble(elements[2]), Double.parseDouble(elements[1]));
//				targetService.create(target);
//				Profile profile = new Profile("test user");
//				profileService.create(profile);
//				for(int i=3; i<elements.length; i++) {
//					targetService.addTagToTarget(new Tag(elements[i].trim().toLowerCase()), target, profile);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace(System.out);
//		} 
//	}
//	
//
	
	public static final Profile TEST_PROFILE = new Profile("test profile");
	
	public void generate() {
		if(!profileService.contains(TEST_PROFILE))
			profileService.create(TEST_PROFILE);
		
		add("Kampin kauppakeskus",60.168852,24.932203,"mall","shopping centre");
		add("Vanha kirkkopuisto", 60.16613,24.939774, "park", "outdoor");
		add("Esplanadin puisto", 60.167198,24.947627, "park", "outdoor");
		
		
	}
	
	private void add(String targetName, double latitude, double longitude, String... tags) {
		Target target = targetService.create(new Target(targetName, longitude, latitude));
		for(String tagName : tags) {
			targetService.addTagToTarget(new Tag(tagName), target, TEST_PROFILE);
		}
	}
	
}
