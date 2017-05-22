package APP.Comm.Config.Parser;

import java.util.HashMap;

import org.apache.commons.digester.Rule;

import APP.Comm.Config.Param;


public class SysConfigRule extends Rule {

	public void end(String namespace, String name) throws Exception {
		Object current = digester.peek(0);
		Object parent = digester.peek(1);
		if (current instanceof Param) {
			((HashMap) parent).put(((Param)current).getKey().toUpperCase(),((Param)current).getValue());
		}
	}
}
