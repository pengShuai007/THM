package APP.Comm.Config.Parser;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class SysConfigRuleSet extends RuleSetBase {
	String pattern = "appSystem";

	public SysConfigRuleSet() {
		this("");
	}

	public SysConfigRuleSet(String namespaceURI) {
		super();
		this.namespaceURI = namespaceURI;
	}

	public void addRuleInstances(Digester digester) {

		digester.addObjectCreate(pattern, "java.util.HashMap");
		
		digester.addObjectCreate("appSystem/param", "APP.Comm.Config.Param");

		digester.addSetProperties("appSystem/param");
//		
		digester.addCallMethod("appSystem/param/key", "setKey", 0);
		
		digester.addCallMethod("appSystem/param/value", "setValue", 0);
		
		digester.addRule("appSystem/param", new SysConfigRule());

	}
}