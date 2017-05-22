package com.kyee.cloud.SqlQuery.comm;

public class SqlQueryConstant {

	public enum DBTypeEnum{
		/**
		 * APP只读数据库
		 */
		APPReadOnlyDB("1"),
		/**
		 * 消息队列库
		 */
		MessageQueueDB("2"),
		/**
		 * 分级诊疗数据库
		 */
		HierdiagnosisDB("3"),
		/**
		 * 运维数据库
		 */
		COPDB("4"),
		/**
		 * 短信服务数据库
		 */
		SMSDB("5"),
		/**
		 * 分级诊疗后台管理数据库
		 */
		HierdiagnosisManageDB("6"),
		/**
		 * 病友圈数据库
		 */
		SufferersCircleDB("7");
		
		private String value;
		private DBTypeEnum(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
}
