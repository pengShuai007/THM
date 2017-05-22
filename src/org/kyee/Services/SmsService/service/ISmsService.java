package org.kyee.Services.SmsService.service;

import org.kyee.Services.SmsService.base.SmsPoint;

public interface ISmsService {
	//获取用户剩余点数
	public  SmsPoint getUserSmsPoint() throws Exception;
}
