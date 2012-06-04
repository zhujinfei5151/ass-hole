package com.tmall.asshole.engine.model.mock;

public class Submit {
	
	public boolean execute(Integer hsfResult){
        System.err.println("submit~~~~~~~~~~~~~~~");		
        System.out.println("检查零售商的预付款是否足够");
        System.out.println("调用广浩的资金接口检查");
        if(hsfResult==1){
        	//冻结资金 调用
        	System.out.println("资金扣款成功");
        	return true;
        }
    	System.out.println("资金扣款失败");
        return false;
	}
}
