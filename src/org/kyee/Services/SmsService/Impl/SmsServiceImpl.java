package org.kyee.Services.SmsService.Impl;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.kyee.Services.SmsService.base.SmsPoint;
import org.kyee.Services.SmsService.service.ISmsService;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.HttpClientUtil;

public class SmsServiceImpl implements ISmsService {

	// 电信平台的用户短信点数实现，优先查询本地表
	/*
	 * 发送格式：http://110.84.128.78:8088/httpIntf/dealIntf?
	 * postData=<Request><Head><MethodName>IMS_USE_LOG</MethodName>
	 * <Spid>496405</Spid><Appid>911</Appid>
	 * <Passwd>2e37ab198e04caa71f8ef3867bd3a8f7c4fc7f2d</Passwd>
	 * </Head><Body><Ims>8659527340000</Ims><Key>88888888</Key></Body>
	 * </Request>
	 */
	/*
	 * 响应格式：<Response><Head><Result>0</Result><ResultDesc>成功</ResultDesc>
	 * </Head><Body> <Ims>8659527340000</Ims> <TotalPoint>300050000</TotalPoint>
	 * <UserPoint>64640</UserPoint> <RemainPoint>299985360</RemainPoint>
	 * </Body></Response>
	 */
	String platformAddr = "110.84.128.78:8088/httpIntf/dealIntf";
	/*String pointPostData = new String(""
			+ "<Request><Head><MethodName>IMS_USE_LOG</MethodName>"
			+ "<Spid>496405</Spid><Appid>911</Appid>"
			+ "<Passwd>2e37ab198e04caa71f8ef3867bd3a8f7c4fc7f2d</Passwd>"
			+ "</Head><Body><Ims>8659527340000</Ims>"
			+ "<Key>88888888</Key></Body></Request>");*/
	
	String pointPostData = new String(""
			+ "<Request><Head><MethodName>IMS_USE_LOG</MethodName>"
			+ "<Spid>496405</Spid><Appid>911</Appid>"
			+ "<Passwd>bcf6fe414971b8fafd6dcde04a1ad65c6ad100eb</Passwd>"
			+ "</Head><Body><Ims>8659527340000</Ims>"
			+ "<Key>88888888</Key></Body></Request>");   
	        // 8659527340000 计费号码
	        // 8659527340005到8659527340049

	public SmsPoint getUserSmsPoint() throws Exception {
		HLogger.info(" SmsServiceImpl: getUserSmsPoint start!");
		SmsPoint smsp = new SmsPoint();
		String content = "";
		
		// 发送http请求，路径和方法都为空
		content = HttpClientUtil.xmlHttpPost(platformAddr, pointPostData);
		//content = "<Response><Head><Result>-1</Result><ResultDesc>postData is empty!</ResultDesc></Head></Response>";
		//content = "<Response> <Head> <Result>0</Result> <ResultDesc>成功</ResultDesc> </Head> <Body> <Ims>8659527340000</Ims> <TotalPoint>300050000</TotalPoint> <UserPoint>64640</UserPoint> <RemainPoint>299985360</RemainPoint> </Body></Response>";
		
		Document doc = DocumentHelper.parseText(content);
		if (!doc.selectNodes("Response/Head/Result").isEmpty()
				&& !doc.selectSingleNode("Response/Head/Result").getText().equals("-1")) {
			smsp.setIms(doc.selectSingleNode("Response/Body/Ims").getText());
			smsp.setTotalPoint(doc.selectSingleNode("Response/Body/TotalPoint").getText());
			smsp.setUserPoint(doc.selectSingleNode("Response/Body/UserPoint").getText());
			smsp.setRemainPoint(doc.selectSingleNode("Response/Body/RemainPoint").getText());
		}else 
		{
			throw new BaseBllException("查询短信平台异常" + doc.selectSingleNode("Response/Head/ResultDesc").getText());
		}
		HLogger.info(" SmsServiceImpl: getUserSmsPoint end!");
		return smsp;
	}
}
