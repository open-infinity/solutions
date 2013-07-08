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
	
	public void generate() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(applicationContext.getResource("testData.txt").getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] elements = line.split(",");
				if(elements.length < 3) continue;
				Target target = new Target(elements[0].trim(), Double.parseDouble(elements[2]), Double.parseDouble(elements[1]));
				targetService.create(target);
				Profile profile = new Profile("test user");
				profileService.create(profile);
				for(int i=3; i<elements.length; i++) {
					targetService.addTagToTarget(new Tag(elements[i].trim().toLowerCase()), target, profile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} 
		
	}
	
}
