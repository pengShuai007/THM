package APP.Login.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Encryption {
	static String path1  = Encryption.class.getClassLoader().getResource("/").getPath();
	
	public static String getEncryption(String str) throws IOException, ScriptException, NoSuchMethodException{
		String path = path1.substring(0,path1.length()-16)+"AppLoginJs";
		//System.out.println("*************"+path);
		ScriptEngineManager sem=new ScriptEngineManager(); 
	    ScriptEngine engine=sem.getEngineByName("javascript"); 
	    StringBuffer sb = new StringBuffer();
	    File f1 = new File(path+"/test.js");
	    BufferedReader reader1 = new BufferedReader(new FileReader(f1));
	    String tempString1 = null;
	    while ((tempString1 = reader1.readLine()) != null) {
	    	sb.append(tempString1);
        }
	    File f2 = new File(path+"/Barrett.js");
	    BufferedReader reader2 = new BufferedReader(new FileReader(f2));
	    String tempString2 = null;
	    while ((tempString2 = reader2.readLine()) != null) {
	    	sb.append(tempString2);
        }
	    File f3 = new File(path+"/BigInt.js");
	    BufferedReader reader3 = new BufferedReader(new FileReader(f3));
	    String tempString3 = null;
	    while ((tempString3 = reader3.readLine()) != null) {
	    	sb.append(tempString3);
        }
	    File f4 = new File(path+"/MD5.js");
	    BufferedReader reader4 = new BufferedReader(new FileReader(f4));
	    String tempString4 = null;
	    while ((tempString4 = reader4.readLine()) != null) {
	    	sb.append(tempString4);
        }
	    File f5 = new File(path+"/RSA.js");
	    BufferedReader reader5 = new BufferedReader(new FileReader(f5));
	    String tempString5 = null;
	    while ((tempString5 = reader5.readLine()) != null) {
	    	sb.append(tempString5);
        }
	    engine.eval(sb.toString());
	    String MD5_password = "";
	    if(engine instanceof Invocable) {    
	    	Invocable invoke = (Invocable)engine;
	    	MD5_password= (String)invoke.invokeFunction("getRsaResult", str);
	    }
	    return MD5_password;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, IOException, ScriptException {
		System.out.println(getEncryption("18292835973"));
	}
}
