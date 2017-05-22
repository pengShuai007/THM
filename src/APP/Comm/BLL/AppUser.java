package APP.Comm.BLL;

/**
 * 缓存用户登录信息的对象
 * test
 */
public class AppUser {
	// 用户表主键
	private long userExtId;

	public long getUserExtId() {
		return userExtId;
	}

	public void setUserExtId(long userExtId) {
		this.userExtId = userExtId;
	}

	// 用户ID
	private long UserId;
	// 用户登录名
	private String LoginName;
	// 用户姓名
	private String _UserName;
	// 密码
	private String _PassWord;
	// 用户职位
	private String _PositionId;
	// 验证码
	private String _CheckCode;
	// 部门id
	private String _DeptCode;
	// 部门名称
	private String _DeptName;
	// 身份
	private String _UserIdEntity;

	/**
	 * 用户ID
	 * 
	 * LOGID=1 引起后端调用分歧，统一使用loginName作为用户唯一标示 修改：施建龙 时间：2013年1月15日10:14:38
	 */
	private void setUserId(long value) {
		this.UserId = value;
	}

	private long getUserId() {
		return this.UserId;
	}

	/**
	 * 用户登录名
	 */

	public final void setLoginName(String value) {
		LoginName = value;
	}

	public final String getLoginName() {
		return LoginName;
	}

	/**
	 * 用户姓名
	 */
	public final void setUserName(String value) {
		_UserName = value;
	}

	public final String getUserName() {
		return _UserName;
	}

	/**
	 * 密码
	 */
	public final void setPassWord(String value) {
		_PassWord = value;
	}

	public final String getPassWord() {
		return _PassWord;
	}

	/**
	 * 用户职位
	 */
	public final void setPositionId(String value) {
		this._PositionId = value;
	}

	public final String getPositionId() {
		return this._PositionId;
	}

	/**
	 * 部门id
	 */
	public final void setDeptCode(String value) {
		this._DeptCode = value;
	}

	public final String getDeptCode() {
		return this._DeptCode;
	}

	/**
	 * 部门名称
	 */
	public final void setDeptName(String value) {
		this._DeptName = value;
	}

	public final String getDeptName() {
		return this._DeptName;
	}

	public final void setCheckCode(String value) {
		_CheckCode = value;
	}

	public final String getCheckCode() {
		return this._CheckCode;
	}

	/**
	 * 身份
	 */
	public final void setUserIdEntity(String value) {
		_UserIdEntity = value;
	}

	public final String getUserIdEntity() {
		return _UserIdEntity;
	}
}