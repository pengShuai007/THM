package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DataBase.Helper.IDataBase;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;

import com.util.OSSTools.OSSUtil;

/**
 * 
 * @author
 * 
 */
public class UploadUtil {
	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 修改者：罗代华
	 * 日期：2014年4月30日下午1:40:44
	 * 修改日期：2014年11月24日18:28:18
	 * 描述：上传文件
	 * @param HttpContext context当前环境的context
	 * @param String fileName上传文件的名字（fileName+时间）
	 * @param String tempPath服务器上的相对路径必须以Resources\\开头
	 * @param IDataBase appDb当前环境的DB
	 * @return map 返回from表单的所有参数
	 * </pre>
	 * @throws Exception 
	 */
	public static Map<String, String> fileupload(HttpContext context,String fileName
			,String tempPath,IDataBase appDb)
			throws Exception {
		//设置文件的最大值  3M
		final long maxsize = 3 * 1024 * 1024; 
		//设定图片的格式范围
		final String[] allowedExt = new String[] { "jpg", "jpeg","bmp","png","pgn","icn","tif","JPG", "JPEG","BMP","PNG","PGN","ICN","TIF" };
		
		
		if(!tempPath.startsWith("Resources\\Upload")){
			throw new BaseBllException("UploadUtil function fileupload 传入相对路径错误，相对路径必须以“Resources\\Upload”开头。所有图片资源文件必须保存在Resources目录下");
		}
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		Map<String, String> upmap = new HashMap<String, String>();
//		edit add start OSS上传  KYEEAPP-1027  罗代华 2014年11月19日22:12:20
		String savepath = context.getServletContext().getRealPath("/")+tempPath;
//		edit add end OSS上传  KYEEAPP-1027  罗代华 2014年11月19日22:12:20
		String HOSPITAL = null;
		response.setContentType("text/html");
		// 设置字符编码为UTF-8, 这样支持汉字显示
		response.setCharacterEncoding("UTF-8");

		// 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		// dfif.setSizeThreshold(4096);//
		// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘
		// dfif.setRepository(new
		// File(request.getServletContext().getRealPath("/")
		// + "ImagesUpload"));// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录

		// 用以上工厂实例化上传组件
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		// 设置最大上传尺寸
		sfu.setSizeMax(maxsize);
		@SuppressWarnings("unused")
		PrintWriter out = null;
		try{
			out = response.getWriter();
		}catch(Exception e){
			throw new BaseBllException(e);
		}
		// 从request得到 所有 上传域的列表
		List<?> fileList = null;
		try {
			fileList = sfu.parseRequest(request);
		} catch (FileUploadException e) {// 处理文件尺寸过大异常
			if (e instanceof SizeLimitExceededException) {
			    
			    throw new BaseBllException("上传文件超过"+maxsize+"M");
			}
			throw new BaseBllException(e);
		}
		// 没有文件上传
		if (fileList == null || fileList.size() == 0) {

		    throw new BaseBllException("没有上传文件");
		}
		// 得到所有上传的文件
		Iterator<?> fileItr = fileList.iterator();
		String u_name = null;
		// 循环处理所有文件
		//Edit begin shiqi 2014年11月21日11:11:45  任务号:KYEEAPP-1021  修改原因：同时上传两张图片，文件名很可能相同，故而覆盖
		int n=0;//设置参数相加，更改文件名
		//Edit end shiqi 2014年11月21日11:11:45  任务号:KYEEAPP-1021  
		while (fileItr.hasNext()) {
			FileItem fileItem = null;
			String path = null;
			long size = 0;
			// 得到当前文件
			fileItem = (FileItem) fileItr.next();
			// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
			if (fileItem == null || fileItem.isFormField()) {
				// 如果item是正常的表单域
				String name = fileItem.getFieldName();
				String value = fileItem.getString();
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");				
				upmap.put(name, value);
				if ("hospitalId".equals(name)) {
					HOSPITAL = value;
				}
				if ("hospitalIdp".equals(name)) {
					HOSPITAL = value;
				}
				//System.out.println("参数名称： " + name + "参数值: " + value);
				continue;
			}
			String uploadPath;
			if(HOSPITAL==null){
				 uploadPath = savepath ;// 选定上传的目录此处为当前目录
			}else{
				uploadPath = savepath + HOSPITAL + "\\";// 选定上传的目录此处为当前目录
			}
			

			if (!new File(uploadPath).isDirectory())// 选定上传的目录此处为当前目录，没有则创建
				new File(uploadPath).mkdirs();
			System.out.println("uploadPath=" + uploadPath);
			// 得到文件的完整路径
			path = fileItem.getName();
			// 得到文件的大小
			size = fileItem.getSize();
			if ("".equals(path) || size == 0) {
				upmap.put(fileItem.getFieldName(), null);
				continue;
			}

			// 得到去除路径的文件名
			String t_name = path.substring(path.lastIndexOf("\\") + 1);
			// 得到文件的扩展名(无扩展名时将得到全名)
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
			// 拒绝接受规定文件格式之外的文件类型
			int allowFlag = 0;
			int allowedExtCount = allowedExt.length;
			for (; allowFlag < allowedExtCount; allowFlag++) {
				if (allowedExt[allowFlag].equals(t_ext))
					break;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String time=formatter.format(new Date());
			// 根据系统时间生成上传后保存的文件名
			//Edit begin shiqi 2014年11月21日11:11:45  任务号:KYEEAPP-1021  修改原因：同时上传两张图片，文件名很可能相同，故而覆盖
			String prefix = fileName+time+(n++);//添加自增参数，区别文件名
			//Edit end shiqi 2014年11月21日11:11:45  任务号:KYEEAPP-1021 
			u_name = uploadPath + prefix + "." + t_ext;
			upmap.put(fileItem.getFieldName(), u_name);
			String OSSPASS = PubSystemParams.getParams("OSSPASS",appDb);//是否启用OSS上传			
			if("0".equals(OSSPASS)){
				fileItem.write(new File(u_name));//上传服务器
			}else{
				String temp=u_name.replaceAll("\\\\", "/");
				OSSUtil.OSSFileUpload(temp, appDb,fileItem.getInputStream());//上传OSS
			}
		}
		return upmap;
	}

	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年6月4日下午1:33:22
	 * 描述：文件下载
	 * </pre>
	 * @throws IOException 
	 */
	public static void downloadfile(HttpContext context) throws IOException {
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		//设置请求编码
		response.setContentType("text/html;charset=utf-8");
		
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		String filePath = request.getParameter("versionUrl");
		String fileName = request.getParameter("versionName");
		//获得文件路径，与文件名
		System.out.println(fileName);
		try {
			long fileLength = new File(filePath).length();
			bis = new BufferedInputStream(new FileInputStream(filePath));
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.setContentType("application/x-msdownload;");
			//告诉浏览器为下载链接
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				HLogger.error(e);
				context.write(JsonUtil.exceptionJson("系统找不到指定的文件！下载失败！"));
			}else{
				e.printStackTrace();
			}
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	/**
	 * 
	 * <pre>
	 * CopyRight(c) 2014-2015
	 * 创建人：刘健
	 * 日期：2014年6月6日上午11:42:01
	 * 描述：文件上传不重写文件名称
	 *</pre>
	 */
	public static Map<String, String> fileuploadNooverride(HttpContext context,
			long maxsize, String[] allowedExt, String savepath)
			throws IOException {
		
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		Map<String, String> upmap = new HashMap<String, String>();

		String HOSPITAL = null;
		// final long MAX_SIZE = 3 * 1024 * 1024;// 设置上传文件最大为 3M
		// 允许上传的文件格式的列表
		// final String[] allowedExt = new String[] { "jpg", "jpeg","txt",
		// };
		response.setContentType("text/html");
		// 设置字符编码为UTF-8, 这样支持汉字显示
		response.setCharacterEncoding("UTF-8");

		// 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		// dfif.setSizeThreshold(4096);//
		// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘
		// dfif.setRepository(new
		// File(request.getServletContext().getRealPath("/")
		// + "ImagesUpload"));// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录

		// 用以上工厂实例化上传组件
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		// 设置最大上传尺寸
		sfu.setSizeMax(maxsize);

		PrintWriter out = response.getWriter();
		// 从request得到 所有 上传域的列表
		List<?> fileList = null;
		try {
			fileList = sfu.parseRequest(request);
		} catch (FileUploadException e) {// 处理文件尺寸过大异常
			if (e instanceof SizeLimitExceededException) {

			    throw new IOException("上传文件超过"+maxsize+"KB");
			}
			e.printStackTrace();
		}
		// 没有文件上传
		if (fileList == null || fileList.size() == 0) {

		    throw new IOException("没有上传文件");
		}
		// 得到所有上传的文件
		Iterator<?> fileItr = fileList.iterator();
		String u_name = null;
		// 循环处理所有文件
		while (fileItr.hasNext()) {
			FileItem fileItem = null;
			String path = null;
			long size = 0;
			// 得到当前文件
			fileItem = (FileItem) fileItr.next();
			// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
			if (fileItem == null || fileItem.isFormField()) {
				// 如果item是正常的表单域
				String name = fileItem.getFieldName();
				String value = fileItem.getString();
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				upmap.put(name, value);
				if ("hospitalId".equals(name)) {
					HOSPITAL = value;
				}
				System.out.println("参数名称： " + name + "参数值: " + value);
				continue;
			}
			String uploadPath;
			if(HOSPITAL==null){
				 uploadPath = savepath + "\\";// 选定上传的目录此处为当前目录
			}else{
				uploadPath = savepath + HOSPITAL + "\\";// 选定上传的目录此处为当前目录
			}
			

			if (!new File(uploadPath).isDirectory())// 选定上传的目录此处为当前目录，没有则创建
				new File(uploadPath).mkdirs();
			System.out.println("uploadPath=" + uploadPath);
			// 得到文件的完整路径
			path = fileItem.getName();
			// 得到文件的大小
			size = fileItem.getSize();
			if ("".equals(path) || size == 0) {
				/*
				 * out.println("请选择上传文件<p />");
				 * out.println("<a href=\"upload.html\" target=\"_top\">返回</a>"
				 * );
				 */
				upmap.put(fileItem.getFieldName(), null);
				continue;
			}

			// 得到去除路径的文件名
			String t_name = path.substring(path.lastIndexOf("\\") + 1);
			// 得到文件的扩展名(无扩展名时将得到全名)
			String l_name=t_name.substring(0,t_name.lastIndexOf("."));
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
			// 拒绝接受规定文件格式之外的文件类型
			int allowFlag = 0;
			int allowedExtCount = allowedExt.length;
			for (; allowFlag < allowedExtCount; allowFlag++) {
				if (allowedExt[allowFlag].equals(t_ext))
					break;
			}
			// if (allowFlag == allowedExtCount) {
			// out.println("请上传以下类型的文件<p />");
			// for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)
			// out.println("*." + allowedExt[allowFlag]
			// + "&nbsp;&nbsp;&nbsp;");
			// out.println("<p /><a href=\"upload.html\" target=\"_top\">返回</a>");
			// return null;
			// }

			//long now = System.currentTimeMillis();
			// 根据系统时间生成上传后保存的文件名
			//String prefix = String.valueOf(now);
			// 保存的最终文件完整路径,保存在web根目录下的ImagesUploaded目录下
			// String u_name = request.getServletContext().getRealPath("/") +
			// "deptUpload/"
			// + prefix + "." + t_ext;
			// u_name = savepath + prefix + "." + t_ext;
			u_name = uploadPath + l_name + "." + t_ext;
			upmap.put(fileItem.getFieldName(), u_name);
			try {
				// 保存文件
				fileItem.write(new File(u_name));
				// out.println("文件上传成功. 已保存为: " + prefix + "." + t_ext
				// + " &nbsp;&nbsp;文件大小: " + size + "字节<p />");
				// out.println("<a href=\"upload.html\" target=\"_top\">继续上传</a>");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return upmap;
	} 
	
	/**
	 * 
	 * <pre>
	 * 任务： KYEEAPP-1021
	 * 描述： 端的上传图片
	 * 作者：shiqi 
	 * 时间：2014年11月25日下午2:01:47
	 * @param context
	 * @param fileName
	 * @param tempPath
	 * @param appDb
	 * @return
	 * @throws BaseBllException
	 * returnType：Map<String,String>
	 * </pre>
	 */
	public static Map<String, String> fileuploadT(HttpContext context,String fileName
        ,String tempPath,IDataBase appDb)
        throws BaseBllException {
    //设置文件的最大值  50k
    final long maxsize = 3*1024*1024; 
    //设定图片的格式范围
    final String[] allowedExt = new String[] { "jpg", "jpeg","bmp","png","pgn","icn","tif","JPG", "JPEG","BMP","PNG","PGN","ICN","TIF" };
    
    
    if(!tempPath.startsWith("Resources\\")){
        throw new BaseBllException("传入相对路径错误，相对路径必须以“Resources\\”开头。所有图片资源文件必须保存在Resources目录下");
    }
    HttpServletRequest request = context.getRequest();
    HttpServletResponse response = context.getResponse();
    Map<String, String> upmap = new HashMap<String, String>();
//  edit add start OSS上传  KYEEAPP-1027  罗代华 2014年11月19日22:12:20
    String savepath = context.getServletContext().getRealPath("/")+tempPath;
//  edit add end OSS上传  KYEEAPP-1027  罗代华 2014年11月19日22:12:20
    String HOSPITAL = null;
    response.setContentType("text/html");
    // 设置字符编码为UTF-8, 这样支持汉字显示
    response.setCharacterEncoding("UTF-8");

    // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
    DiskFileItemFactory dfif = new DiskFileItemFactory();
    // dfif.setSizeThreshold(4096);//
    // 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘
    // dfif.setRepository(new
    // File(request.getServletContext().getRealPath("/")
    // + "ImagesUpload"));// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录

    // 用以上工厂实例化上传组件
    ServletFileUpload sfu = new ServletFileUpload(dfif);
    // 设置最大上传尺寸
    sfu.setSizeMax(maxsize);
    @SuppressWarnings("unused")
    PrintWriter out = null;
    try{
        out = response.getWriter();
    }catch(Exception e){
        throw new BaseBllException(e);
    }
    // 从request得到 所有 上传域的列表
    List<?> fileList = null;
    try {
        fileList = sfu.parseRequest(request);
    } catch (FileUploadException e) {// 处理文件尺寸过大异常
        if (e instanceof SizeLimitExceededException) {
            
            throw new BaseBllException("上传文件超过"+maxsize+"M");
        }
        throw new BaseBllException(e);
    }
    // 没有文件上传
    if (fileList == null || fileList.size() == 0) {

        throw new BaseBllException("没有上传文件");
    }
    // 得到所有上传的文件
    Iterator<?> fileItr = fileList.iterator();
    String u_name = null;
    // 循环处理所有文件
    //Edit begin shiqi 2014年11月21日11:11:45  任务号:KYEEAPP-1021  修改原因：同时上传两张图片，文件名很可能相同，故而覆盖
    int n=0;//设置参数相加，更改文件名
    //Edit end shiqi 2014年11月21日11:11:45  任务号:KYEEAPP-1021  
    while (fileItr.hasNext()) {
        FileItem fileItem = null;
        String path = null;
        long size = 0;
        // 得到当前文件
        fileItem = (FileItem) fileItr.next();
        // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
        if (fileItem == null || fileItem.isFormField()) {
            // 如果item是正常的表单域
            String name = fileItem.getFieldName();
            String value = fileItem.getString();
            try{
                value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
            }catch (Exception e){
                throw new BaseBllException(e);
            }
            upmap.put(name, value);
            if ("hospitalId".equals(name)) {
                HOSPITAL = value;
            }
            if ("hospitalIdp".equals(name)) {
                HOSPITAL = value;
            }
            //System.out.println("参数名称： " + name + "参数值: " + value);
            continue;
        }
        String uploadPath;
        if(HOSPITAL==null){
             uploadPath = savepath;// 选定上传的目录此处为当前目录
        }else{
            uploadPath = savepath + HOSPITAL + "\\";// 选定上传的目录此处为当前目录
        }
        

        if (!new File(uploadPath).isDirectory())// 选定上传的目录此处为当前目录，没有则创建
            new File(uploadPath).mkdirs();
        System.out.println("uploadPath=" + uploadPath);
        // 得到文件的完整路径
        path = fileItem.getName();
        // 得到文件的大小
        size = fileItem.getSize();
        if ("".equals(path) || size == 0) {
            upmap.put(fileItem.getFieldName(), null);
            continue;
        }

        // 得到去除路径的文件名
        String t_name = path.substring(path.lastIndexOf("\\") + 1);
        // 得到文件的扩展名(无扩展名时将得到全名)
        String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
        // 拒绝接受规定文件格式之外的文件类型
        int allowFlag = 0;
        int allowedExtCount = allowedExt.length;
        for (; allowFlag < allowedExtCount; allowFlag++) {
            if (allowedExt[allowFlag].equals(t_ext))
                break;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        String time=formatter.format(new Date());
        // 根据系统时间生成上传后保存的文件名
        //Edit begin shiqi 2014年11月21日11:11:45  任务号:KYEEAPP-1021  修改原因：同时上传两张图片，文件名很可能相同，故而覆盖
        String prefix = fileName+time+(n++);//添加自增参数，区别文件名
        //Edit end shiqi 2014年11月21日11:11:45  任务号:KYEEAPP-1021 
        u_name = uploadPath + prefix + "." + t_ext;
        upmap.put(fileItem.getFieldName(), u_name);
        try {
            // 保存文件
            fileItem.write(new File(u_name));
        } catch (Exception e) {
            throw new BaseBllException(e);
        }

    }
    return upmap;
}

}
