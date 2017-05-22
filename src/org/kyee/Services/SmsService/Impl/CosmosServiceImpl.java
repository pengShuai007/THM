package org.kyee.Services.SmsService.Impl;

import java.util.Date;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.kyee.Services.SmsService.base.CosmosNumber;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.MD5Pass;

public class CosmosServiceImpl {
	
	private String platformAddr = "http://101.251.222.2/getbalance"; // 宇宙之讯短信平台接口
	private String ecpid = "030001000630"; // 企业ID
	private String bcpid = "030001000630"; // 请求查询约的cpid
	private String msgtype = "1"; // 信息类型1：短信，2：彩信,默认为1
	private String timestamp;
	private String passWord = "834375df4daf91baac4d28e6d2c22af0"; //md5加密的密码
	private String reserve = "0"; // 保留字段,默认为0

	public String getEcpid() {
		return ecpid;
	}

	public void setEcpid(String ecpid) {
		this.ecpid = ecpid;
	}

	public String getBcpid() {
		return bcpid;
	}

	public void setBcpid(String bcpid) {
		this.bcpid = bcpid;
	}

	/**
	 * 任务号：
	 * 描述：获取请求包数据
	 * 作者：liuxingping
	 * 时间：2015年5月29日下午3:26:24
	 * @return
	 */
	public String httpPostXml(String url, String postData) throws Exception {
		String response = ""; // 请求响应结果
		HttpClient httpClient = new HttpClient(); //建立Http客服端对象
		PostMethod postMethod = new PostMethod(url); //创建post请求方法
		try {
			postMethod.setRequestEntity(new StringRequestEntity(postData.toString(),
					"text/html", "utf-8")); //设置请求实体
			httpClient.executeMethod(postMethod);
			int statusCode = 0; //请求状态
			statusCode = httpClient.executeMethod(postMethod);
			if ((statusCode == 301) || (statusCode == 302)) { //重定向
				Header locationHeader = postMethod
						.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					response = httpPostXml(location, postData);
				} else {
					HLogger.error("Location field value is null");
				}
			} else {
				try {
					response = postMethod.getResponseBodyAsString(); //获取返回包
				} catch (Exception e) {
					HLogger.error(e);
				}
				postMethod.releaseConnection();
			}
		} catch (Exception e) {
			HLogger.error(e);
		}
		return response;
	}
	
	/**
	 * 任务号：
	 * 描述：处理响应数据
	 * 作者：liuxingping
	 * 时间：2015年6月1日上午8:32:10
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String handleResponse(String data) throws Exception {
		String result = "";
		result = data.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		return result;
	}
	/**
	 * 任务号：
	 * 描述：获取宇宙之讯的剩余短信条数
	 * 作者：liuxingping
	 * 时间：2015年5月29日下午2:52:44
	 * @return
	 * @throws Exception
	 */
	public  CosmosNumber getUserCosmosNumber() throws Exception {
		//拼接请求包
		StringBuffer postStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		postStr.append("<BalanceRequest>");
		postStr.append("<ecpid>" + ecpid + "</ecpid>");
		postStr.append("<bcpid>" + bcpid + "</bcpid>");
		postStr.append("<msgtype>" + msgtype + "</msgtype>");
		timestamp = new Date().getTime() + "";
		postStr.append("<timestamp>" + timestamp + "</timestamp>");
		String signature = MD5Pass.Md5Encrypt(passWord + timestamp); //生成密钥
		postStr.append("<signature>" + signature + "</signature>");
		postStr.append("<reserve>" + reserve + "</reserve>");
		postStr.append("</BalanceRequest>");
		//发送post请求
		String response = this.httpPostXml(platformAddr,postStr.toString());
		//处理响应返回数据
		String context = this.handleResponse(response);
		Document document = DocumentHelper.parseText(context);
		String balance = "";
		if ("0".equals(document.selectSingleNode("BalanceResponse/desc").getText())) {
			balance = document.selectSingleNode("BalanceResponse/balance").getText();
		} else {
			throw new Exception("调用宇宙之讯平台失败：" + document.selectSingleNode("BalanceResponse/desc").getText());
		}
		CosmosNumber cosmosNumber = new CosmosNumber();
		cosmosNumber.setECPID(ecpid);
		cosmosNumber.setBCPID(bcpid);
		cosmosNumber.setREMAIN_NUMBER(balance); //设置剩余条数
		return cosmosNumber;
	}
	
	public static void main(String[] args) {
		try {
			CosmosServiceImpl cosmosServiceImpl = new CosmosServiceImpl();
			CosmosNumber cosmosNumber = cosmosServiceImpl.getUserCosmosNumber();
			System.out.print("调用"+cosmosNumber.getECPID());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
