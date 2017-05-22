
package com.kyee.cloud.operator.bll;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import model.base.ext.SYS_USER_EXT;
import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.AppDataTable;
import APP.Comm.Util.DotNetToJavaStringHelper;
import APP.Comm.Util.HLogger;

import com.kyee.cloud.operator.dal.UserManagementDalC;

/**
 * <pre>
 * 作者:刘健
 * 日期:2014年3月6日 下午4:58:37
 * 描述:用户忘记密码后修改密码
 * </pre>
 */
public class UserManagementBllC extends BaseBLL{
	//操作数据库
	private UserManagementDalC userDal=new UserManagementDalC();

	public AppDataTable GetUserMenuPerm() throws BaseBllException {
		HLogger.info("UserManagementBllC Function GetUserMenuPerm Start!");
		String userCode = super.getParameterValue("userCode");
		AppDataTable result = userDal.GetUserMenuPerm(super.getBaseDB(),userCode);
		HLogger.info("UserManagementBllC Function GetUserMenuPerm End!");
		return result;
	}
	
	public Map<String,String> getUserHosp() throws BaseBllException {
		HLogger.info("UserManagementBllC Function GetUserMenuPerm Start!");
		Map<String,String> result = new HashMap<String,String>();
		SYS_USER_EXT appuser = (SYS_USER_EXT) super.getContext().getSessionAttribute("appuser");
		String userCode = appuser.getUSER_CODE();
		String areaIds = "";
		String hospIds = "";
		AppDataTable userAreaIds = userDal.gerUserAreaIds(super.getBaseDB(),userCode);
		if (userAreaIds.getCount() > 0) {
			for (int i=0; i<userAreaIds.DataTable.getRows().size(); i++) {
				String areaId = userAreaIds.DataTable.getRows().get(i).getStringColumn("AREA_ID");
				String allChildAreaId = userDal.getChildAreaById(super.getBaseDB(), areaId);
				if (i == 0) {
					areaIds = allChildAreaId;
				} else {
					areaIds += "," + allChildAreaId;
				}
			}
			hospIds = userDal.gerUserHospIds(super.getBaseDB(),areaIds);
		}
		result.put("areaIds", areaIds);
		result.put("hospIds", hospIds);
		HLogger.info("UserManagementBllC Function GetUserMenuPerm End!");
		return result;
	}
	
	/**
	 * 任务号：
	 * 描述：
	 * 作者：liuxingping
	 * 时间：2015年5月14日下午2:01:46
	 * @return
	 * @throws BaseBllException
	 * @throws UnsupportedEncodingException
	 */
	public AppDataTable queryProvince() throws BaseBllException, UnsupportedEncodingException {
        HLogger.info("UserManagementBllC Function queryProvince begin!");
//        AppDataTable result = userDal.queryProvince(getAppDB());
        AppDataTable result = null;
        String hospIds = "";
        Map<String,String> userAreaHosp = this.getUserHosp();
        if ("".equals(userAreaHosp.get("areaIds"))) {
        } else {
        	hospIds = userAreaHosp.get("hospIds");
        	if (DotNetToJavaStringHelper.isNullOrEmpty(hospIds)) {
        		hospIds = "''";
        	}
        }
        HLogger.info("UserManagementBllC Function queryProvince end!");
        return result;
    }
	
    /**
     * 任务号：
     * 描述：
     * 作者：liuxingping
     * 时间：2015年5月14日下午2:01:50
     * @return
     * @throws BaseBllException
     * @throws UnsupportedEncodingException
     */
    public AppDataTable queryCity() throws BaseBllException, UnsupportedEncodingException {
        HLogger.info("UserManagementBllC Function queryCity begin!");
        AppDataTable result = null;
        String provinceID=getParameterValue("PROVINCE_ID"); //省份ID
        String hospIds = "";
        Map<String,String> userAreaHosp = this.getUserHosp();
        if ("".equals(userAreaHosp.get("areaIds"))) {
        } else {
        	hospIds = userAreaHosp.get("hospIds");
        	if (DotNetToJavaStringHelper.isNullOrEmpty(hospIds)) {
        		hospIds = "''";
        	}
        }
//        AppDataTable result = null;
//        Map<String,String> userAreaHosp = this.getUserHosp();
//        if ("".equals(userAreaHosp.get("areaIds"))) {
//        	result = userDal.queryCity(getAppDB(),provinceID);
//        } else {
//        	result = userDal.queryCity(getAppDB(),provinceID);
//        }
        HLogger.info("UserManagementBllC Function queryCity end!");
        return result;
    }
    
    /**
     * 任务号：
     * 描述：
     * 作者：liuxingping
     * 时间：2015年5月14日下午2:01:52
     * @return
     * @throws BaseBllException
     * @throws UnsupportedEncodingException
     */
    public AppDataTable queryHospital() throws BaseBllException, UnsupportedEncodingException {
        HLogger.info("UserManagementBllC Function queryHospital begin!");
        String provinceName=getParameterValue("PROVINCE_NAME"); //省份名称
        String cityName=getParameterValue("CITY_NAME"); //城市名称
        AppDataTable result = null;
        String hospIds = "";
        Map<String,String> userAreaHosp = this.getUserHosp();
        if ("".equals(userAreaHosp.get("areaIds"))) {
        	result = userDal.queryHospital(getBaseDB(),provinceName,cityName);
        } else {
        	hospIds = userAreaHosp.get("hospIds");
        	if (DotNetToJavaStringHelper.isNullOrEmpty(hospIds)) {
        		hospIds = "''";
        	}
        	result = userDal.queryHospital(getBaseDB(),provinceName,cityName,hospIds);
        }
//        AppDataTable result = userDal.queryHospital(getBaseDB(),provinceName,cityName);
        HLogger.info("UserManagementBllC Function queryHospital end!");
        return result;
    }
    
}
