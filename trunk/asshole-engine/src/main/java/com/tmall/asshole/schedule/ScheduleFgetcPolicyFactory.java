package com.tmall.asshole.schedule;

public class ScheduleFgetcPolicyFactory {

	public static IScheduleFgetcPolicy create(String algorithmType) {
        	return new BasicScheduleFgetcPolicy();
	}
	
	

}
