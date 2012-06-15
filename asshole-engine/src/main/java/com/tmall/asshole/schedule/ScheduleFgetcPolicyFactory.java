package com.tmall.asshole.schedule;

import com.tmall.asshole.schedule.fgetcplolicy.BasicScheduleFgetcPolicy;

public class ScheduleFgetcPolicyFactory {

	public static IScheduleFgetcPolicy create(String algorithmType) {
        	return new BasicScheduleFgetcPolicy();
	}
	
	

}
