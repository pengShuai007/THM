package APP.Comm.DotNet;

import javax.servlet.ServletContext;

/**
 * comments:模拟DotNet下的Application类
 * 
 * sjl modify 2013-10-11上午10:55:57
 */
public class Application {
	public static ServletContext servletContext=null;
	public static void add(String key,Object value){
		servletContext.setAttribute(key, value);
	}
	
	public static Object get(String key){
		return servletContext.getAttribute(key);
	}
	
	
}
