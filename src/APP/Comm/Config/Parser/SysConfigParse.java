package APP.Comm.Config.Parser;

// import java packages
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.digester.Digester;

import APP.Comm.Util.HLogger;

/**
 * comments:ï¿½ï¿½È¡ÏµÍ³ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ï¢
 * 
 * sjl modify 2013-10-10ï¿½ï¿½ï¿½ï¿½1:04:10
 */
public class SysConfigParse {

	public Properties parseProperties() {
		Properties properties = new Properties();
		HashMap sysParams = null;
		InputStream fi = null;
		try {
			ProjectConfigParse projectParse = new ProjectConfigParse();
			sysParams = projectParse.parse();
			String filePath = sysParams.get("PROJECTHOME") + "";
			String fileName = filePath + File.separator + "config"
					+ File.separator + "webservice.properties";
			fi = new FileInputStream(fileName);
			properties.load(fi);
			return properties;
		} catch (Exception e) {
			HLogger.error(e);
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				HLogger.error(e);
			}
		}
		return properties;
	}

	public HashMap parse() {
		Digester digester = new Digester();
		digester.setValidating(false);

		digester.addRuleSet(new SysConfigRuleSet());
		HashMap sysParams = null;
		InputStream fi = null;
		try {
			// fi = this.getClass().getClassLoader()
			// .getResourceAsStream("sys-config.xml");
			ProjectConfigParse projectParse = new ProjectConfigParse();
			sysParams = projectParse.parse();
			String filePath = sysParams.get("PROJECTHOME") + "";
			String fileName = filePath + File.separatorChar + "config"
					+ File.separatorChar + "sys-config.xml";
			fi = new FileInputStream(fileName);
			sysParams = (HashMap) digester.parse(fi);
			// fi.close();
		} catch (Exception e) {
			HLogger.error(e);
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				HLogger.error(e);
			}
		}
		return sysParams;
	}

	// 测试main
	public static void main(String args[]) {
		SysConfigParse parse = new SysConfigParse();
		System.out.println(parse.parse());
	}
}