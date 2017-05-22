package APP.Comm.Util;
import java.io.IOException;
import java.util.Properties;


import com.aliyun.api.DefaultAliyunClient;


/**
 * @author 任国华
 * desc:监控云端RDS数据库性能，还可实现用程序实现对MySQL数据库的备份。
 * time：2015-01-29
 *
 */
public class AliYunClientUtil {
		private static String serverUrl;			//阿里云地址
		private static String accessKeyId;			//阿里云访问凭证id
		private static String accessKeySecret;		//阿里云访问凭证密码
		public static DefaultAliyunClient client;
		public static DefaultAliyunClient getClient() {
			return client;
		}
		public static void setClient(DefaultAliyunClient client) {
			AliYunClientUtil.client = client;
		}
		static {
			Properties p = new Properties();
			try {
				p.load(AliYunClientUtil.class.getClassLoader().getResourceAsStream(
							"org/kyee/Services/RDSStatusService/base/aliYuncs.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serverUrl = p.getProperty("serverUrl");
			accessKeyId = p.getProperty("accessKeyId");
			accessKeySecret = p.getProperty("accessKeySecret");
			//生成阿里云客户端，其他地方使用的话直接调用即可
			client = new DefaultAliyunClient(serverUrl, accessKeyId,accessKeySecret);
		}

		public String getServerUrl() {
			return serverUrl;
		}

		public void setServerUrl(String serverUrl) {
			this.serverUrl = serverUrl;
		}

		public String getAccessKeyId() {
			return accessKeyId;
		}

		public void setAccessKeyId(String accessKeyId) {
			this.accessKeyId = accessKeyId;
		}

		public String getAccessKeySecret() {
			return accessKeySecret;
		}

		public void setAccessKeySecret(String accessKeySecret) {
			this.accessKeySecret = accessKeySecret;
		}
		
		//程序创建云端数据库的测试类
//		public int createDBInstanceTest(){
//			int retFlag = 0;
//			CreateDBInstanceRequest request = new CreateDBInstanceRequest();
//			request.setRegionId("cn-hangzhou");
//			request.setEngine("mysql");
//			request.setEngineVersion("5.6");//5.5.19
//			request.setdBInstanceClass("rds.mys2.small");
//			request.setdBInstanceStorage(100L);
//			request.setdBInstanceNetType("Internet");
//			request.setdBInstanceDescription("wahaha");
//			request.setSecurityIPList("10.10.10.10");
//			request.setPayType("Postpaid");
//			request.setClientToken(UUID.randomUUID().toString());
//			try {
//				CreateDBInstanceResponse result = client.execute(request);
//				if(result.isSuccess()){
//					retFlag = 1;
//				}else{
//					retFlag = 0;
//				}
//			} catch (ApiException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return retFlag;
//		}
//		
		//主测试方法
//		public static void main(String args[]){
//			AliYunClientUtil util = new AliYunClientUtil();
//			try {
//				System.out.println(util.describeDBInstancesTest());
//			} catch (ApiException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

}
