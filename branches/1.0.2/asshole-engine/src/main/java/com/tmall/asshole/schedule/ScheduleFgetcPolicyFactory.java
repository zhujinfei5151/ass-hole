package com.tmall.asshole.schedule;

import com.tmall.asshole.schedule.fgetcplolicy.BasicScheduleFgetcPolicy;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class ScheduleFgetcPolicyFactory {

	public static IScheduleFgetcPolicy create(String algorithmType) {
        	return new BasicScheduleFgetcPolicy();
	}
	
	

}
