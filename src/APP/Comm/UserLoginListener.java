package APP.Comm;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import APP.Comm.BLL.AppUser;
import APP.Comm.Util.HLogger;

/**
 * 功能：实现HttpSessioListener接口，只包含Session的创建和失效。浏览器连接时，触发创建
 * 事件（调用创建方法），浏览器关闭或超时，触发销毁方法。 扩展：可以使用HttpSession特性统计在线用户 作者：李智博
 */
public class UserLoginListener implements HttpSessionListener {
	public void sessionCreated(HttpSessionEvent event) {
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		ServletContext ctx = event.getSession().getServletContext();
		// session中的激活用户（以hashMap的形式存在）
		HashMap map = (HashMap) ctx.getAttribute("ACTIVEUSERS");
		if (map == null) {
			map = new HashMap();
			ctx.setAttribute("ACTIVEUSERS", map);
		}
		AppUser user = null;
		user = (AppUser) ctx.getAttribute("USER");
		if (user == null) {
			return;
		}
		if (map.containsKey(user.getLoginName())) {
			map.remove("" + user.getLoginName());
		}
		HLogger.info("用户：" + user.getLoginName() + "注销!");
	}
}
