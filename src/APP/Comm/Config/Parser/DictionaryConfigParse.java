package APP.Comm.Config.Parser;

// import java packages
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.digester.Digester;

import APP.Comm.Util.HLogger;


public class DictionaryConfigParse {

	
	
	public HashMap parse() {
		Digester digester = new Digester();
		digester.setValidating(false);
		
		digester.addRuleSet(new DictionaryConfigRuleSet());
		HashMap sysParams=null;
		InputStream fi=null;
		try {
//			fi = this.getClass().getClassLoader()
//			.getResourceAsStream("sys-config.xml");
			ProjectConfigParse projectParse=new ProjectConfigParse();
			sysParams=projectParse.parse();
			String filePath = sysParams.get("PROJECTHOME") + "";
			String fileName=filePath + File.separator +"config"+ File.separator+"dictionary.xml";
			fi = new FileInputStream(fileName);
			sysParams=(HashMap)digester.parse(fi);
//			fi.close();
		} catch (Exception e) {
			HLogger.debug(e);		
		}finally{
			try {
				fi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				HLogger.Error(e);
			}
		}
		return sysParams;
	}

	public static void main(String args[]) {
		DictionaryConfigParse parse=new DictionaryConfigParse();
		parse.parse();
	}
	
}