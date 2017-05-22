package APP.Login.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {
	public static List<Map<String,String>> getClasses(String pack) {  
		  
        // 第一个class类的集合  
		List<Map<String,String>> classes = new ArrayList<Map<String,String>>();  
        // 是否循环迭代  
        boolean recursive = true;  
        // 获取包的名字 并进行替换  
        String packageName = pack;  
        String packageDirName = packageName.replace('.', '/');  
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(  
                    packageDirName);  
            // 循环迭代下去  
            while (dirs.hasMoreElements()) {  
                // 获取下一个元素  
                URL url = dirs.nextElement();  
                // 得到协议的名称  
                String protocol = url.getProtocol();  
                // 如果是以文件的形式保存在服务器上  
                if ("file".equals(protocol)) {  
                    System.err.println("file类型的扫描");  
                    // 获取包的物理路径  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    // 以文件的方式扫描整个包下的文件 并添加到集合中  
                    findAndAddClassesInPackageByFile(packageName, filePath,  
                            recursive, classes);  
                } else if ("jar".equals(protocol)) {  
                    // 如果是jar包文件  
                    // 定义一个JarFile  
                    System.err.println("jar类型的扫描");  
                    JarFile jar;  
                    try {  
                        // 获取jar  
                        jar = ((JarURLConnection)url.openConnection()).getJarFile();  
                        // 从此jar包 得到一个枚举类  
                        Enumeration<JarEntry> entries = jar.entries();  
                        // 同样的进行循环迭代  
                        while (entries.hasMoreElements()) {  
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
                            JarEntry entry = entries.nextElement();  
                            String name = entry.getName();  
                            // 如果是以/开头的  
                            if (name.charAt(0) == '/') {  
                                // 获取后面的字符串  
                                name = name.substring(1);  
                            }  
                            // 如果前半部分和定义的包名相同  
                            if (name.startsWith(packageDirName)) {  
                                int idx = name.lastIndexOf('/');  
                                // 如果以"/"结尾 是一个包  
                                if (idx != -1) {  
                                    // 获取包名 把"/"替换成"."  
                                    packageName = name.substring(0, idx)  
                                            .replace('/', '.');  
                                }  
                            }  
                        }  
                    } catch (IOException e) {  
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");  
                        e.printStackTrace();  
                    }  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
        return classes;  
    }  
	
	public static void findAndAddClassesInPackageByFile(String packageName,  
            String packagePath, final boolean recursive, List<Map<String,String>> classes) {  
        // 获取此包的目录 建立一个File  
        File dir = new File(packagePath);  
        // 如果不存在或者 也不是目录就直接返回  
        if (!dir.exists() || !dir.isDirectory()) {  
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");  
            return;  
        }  
        // 如果存在 就获取包下的所有文件 包括目录  
        File[] dirfiles = dir.listFiles(new FileFilter() {  
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
            public boolean accept(File file) {  
                return (recursive && file.isDirectory())  
                        || (file.getName().endsWith(".class"));  
            }  
        });  
        // 循环所有文件  
        for (File file : dirfiles) {  
            // 如果是目录 则继续扫描  
            if (file.isDirectory()) {  
                findAndAddClassesInPackageByFile(packageName + "."  
                        + file.getName(), file.getAbsolutePath(), recursive,  
                        classes);  
            } else {  
            	Map<String,String> map = new HashMap<String,String>();
                // 如果是java类文件 去掉后面的.class 只留下类名  
                String className = file.getName().substring(0,  
                        file.getName().length() - 6);  
                map.put("class", className);
                // 添加到集合中去  
               //classes.add(Class.forName(packageName + '.' + className));  
				 //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净  
                String className2 = "";
                String isOnlyRead = "";
                try {
					Field field[] = Class.forName(packageName + "."+className).getDeclaredFields();
					for(int i=0;i<field.length;i++){
						if(field[i].getName().equals("className")){
							className2 = (String) field[i].get(Class.forName(packageName + "."+className).newInstance());
							map.put("className", className2);
						}else if(field[i].getName().equals("isOnlyRead")){
							isOnlyRead = (String) field[i].get(Class.forName(packageName + "."+className).newInstance());
							map.put("isOnlyRead", isOnlyRead);
						}
					}
					Method[] method = Class.forName(packageName + "."+className).getDeclaredMethods();
					for(int i=0;i<method.length;i++){
						if(method[i].getName().equals("getParamNum")){
							String count = String.valueOf(method[i].invoke(Class.forName(packageName + "."+className).newInstance()));
							map.put("count", count);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
                classes.add(map); 
            } 
            
        }  
    }  
	
	public static void main(String[] args) {
		String pack = "appointRegist";
		List<Map<String,String>> list = getClasses(pack);
		System.out.println(list.toString());
	}
}
