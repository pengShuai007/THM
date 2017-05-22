package APP.Comm.BLL;

import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.Util.HLogger;

/**
 * 系统Handler的基类 所有自定义Handler必须扩展自本类，同时通过覆盖ProcessHrpRequest接入系统
 * 用户扩展的ProcessHrpRequest可以不捕获异常，BaseHttpHandler会记录所有异常信息 施建龙 2012-12-27 模块号:无
 * 说明：系统中最上层的Hanler基类，所有自定义的.ashx都必须继承该类 Copyright: Copyright (c) 2012
 * ////Company: KYEE author: 宋建康 ////@version: 1.0
 */
public class BLLHandler {
	private String moduleName;

	/**
	 * bll的委托执行对象
	 */
	public BLLContainer BLLContainer;
	/**
	 * 用户登录信息对象
	 */
	public AppUser AppUser;
	/**
	 * 请求的方法
	 */
	public String op;

	private IDataBase _baseDb;
	private IDataBase _appRWDb;
	private IDataBase _appRDDb;
	private IDataBase _appCDb;
	private IDataBase _appHDDb;
	private IDataBase _appHPPDb;
	private IDataBase _appMEDb;
	private IDataBase _appHDCDb;
	private IDataBase _appHPLUSYWDb;
	private IDataBase _appHPLUSYeWuDb;
	private IDataBase _appHPLUSMCDb;
	private IDataBase _patientDB;
	/**
	 * 初始化数据库操作对象 施建龙 2013年9月22日9:22:04
	 */
//	public BLLHandler() {
//		try {
//			this._appDb = DBFactory.CreateAppDB();
//			this._hisDb = DBFactory.CreateHisDB();
//			this.BLLContainer = new BLLContainer(this._baseDb,this._appDb, this._hisDb);
//			this.BLLContainer.moduleName = moduleName;
//		} catch (BaseBllException e) {
//			HLogger.error(e);
//		}
//	}

	/**
	 * 说明：实际业务处理函数，各业务模块必须实现本方法，并在此实现具体的业务逻辑 本函数以及后续BLL层、DAL层的所有业务函数不得捕捉异常，
	 * 并且在需要中断业务、回滚数据库操作时必须向上抛出异常
	 */
	public void ProcessHrpRequest() {
		throw new RuntimeException("必须实现方法：ProcessHrpRequest");
	}

	/**
	 * 说明： 1、创建当前类的实例 2、调用本方法，完成业务处理 施建龙 2013年9月22日9:23:15
	 */
	public final void ProcessRequest() {
		try {
			// 生成BLL层基类，严禁在此直接调用本模块的BLL层函数，通过基类委托统一调用
			this.BLLContainer = new BLLContainer(this._baseDb);
			this.BLLContainer.moduleName = moduleName;
			ProcessHrpRequest();
		} catch (Throwable e) {
			if (e instanceof BaseBllException) {
				HLogger.Error(e);
			}else if (e instanceof LogException) {
				// 记录插入日志到数据库的时候，产生的异常 
				HLogger.Error(e);
			}else {
				// 系统级别异常处理
				HLogger.Error(e);

			}
		}
	}

	/**
	 * 说明： 给BaseBLL变量赋值
	 * 
	 * @param baseBll
	 *            业务层对象
	 */
	public final void SetParamsToBLL(Object baseBll) {
		BaseBLL baseBLL = (BaseBLL) baseBll;
		// baseBLL.setAppUser("GUEST");
//		baseBLL.setAppDB(this._appDb);
//		AbstractDataBase appDb = (AbstractDataBase) baseBLL.getAppDB();
//		appDb.appUser = this.AppUser;
	}
}