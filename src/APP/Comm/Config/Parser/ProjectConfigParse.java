package APP.Comm.Config.Parser;

// import java packages
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.digester.Digester;

import APP.Comm.Util.HLogger;

public class ProjectConfigParse {

	public HashMap parse() {
		Digester digester = new Digester();
		digester.setValidating(false);

		digester.addRuleSet(new SysConfigRuleSet());
		HashMap sysParams = null;
		InputStream fi = null;
		try {
			fi = this.getClass().getClassLoader()
					.getResourceAsStream("project-config.xml");
			sysParams = (HashMap) digester.parse(fi);
			// fi.close();
		} catch (Exception e) {
			HLogger.debug(e);
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
				HLogger.Error(e);
			}
		}
		return sysParams;
	}

	public static void main(String args[]) {
		ProjectConfigParse parse = new ProjectConfigParse();
		System.out.println(parse.parse());
	}

}