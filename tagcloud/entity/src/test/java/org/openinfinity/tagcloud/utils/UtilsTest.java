package org.openinfinity.tagcloud.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test 
	public void testLocationCalculationUtils() {
		for(int i=0; i<1000; i++) {
			double lon1 = Math.random()*360-180;
			double lat1 = Math.random()*170-85;
			double dx = Math.random()*10000 - 5000;
			double dy = Math.random()*10000 - 5000;
			double[] loc = Utils.calcLocation(lon1, lat1, dx, dy);
			double val1 = Utils.calcDistanceGCS(lon1, lat1, loc[0], loc[1]);
			double val2 = Utils.calcDistanceLCS(dx, dy);
			double dif = Math.abs((val1-val2)/val2);
			assertEquals(true, dif<0.01);
		}	
	}
	

}
