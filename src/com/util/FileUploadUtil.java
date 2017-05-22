package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.base.ext.SYS_USER_EXT;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;
import APP.Comm.Util.JsonUtil;

/**
 * <pre>
 * 任务：
 * 描述：文件上传\下载 通用
 * 作者：yanbaoan 
 * 时间：2016年2月26日下午2:51:13
 * 类名: FileUploadUtil
 * </pre>
 */
public class FileUploadUtil {

	/**
	 * <pre>
	 * 任务： 
	 * 描述： 文件上传，通用文件，路径和大小可配置,可查看上传进度
	 * 作者：yanbaoan 
	 * 时间：2016年2月26日下午1:42:00
	 * @param context
	 * @param path
	 * @throws BaseBllException
	 * returnType：void
	 * </pre>
	 */
	public static void upload(HttpContext context) throws BaseBllException {
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		String key = Integer.toString(request.hashCode());
		request.getSession().setAttribute("key", key);

		HttpSession session = request.getSession();
		String loginUser = "unknown";
		if (null != session.getAttribute("appuser")) {
			SYS_USER_EXT user = (SYS_USER_EXT) session.getAttribute("appuser");
			loginUser = user.getUSER_CODE();
		}

		// 文件上传路径,从配置文件中读取
		String filePath = "reportlets";
		if (null != context.getServletContext().getInitParameter(
				"reportFilePath")) {
			filePath = context.getServletContext().getInitParameter(
					"reportFilePath");
		} else {
			HLogger.error("upload file path is not config!");
		}

		// 最大上传限制 默认10m, 从配置文件中读取
		long fileSizeMax = 1024 * 1024 * 10;
		if (null != context.getServletContext().getInitParameter(
				"maxUploadSize")) {
			fileSizeMax = Long.parseLong(context.getServletContext()
					.getInitParameter("maxUploadSize"));
		} else {
			HLogger.error("upload max size is not config!");
		}

		String realPath = context.getServletContext().getRealPath("/WEB-INF");
		String savePath = realPath + File.separator + filePath + File.separator
				+ loginUser;
		String reportFilePath = File.separator + loginUser;

		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			HLogger.info(savePath + "目录不存在，需要创建");
			// 创建目录
			file.mkdir();
		}
		// 消息提示
		String message = "";
		// 输入流
		InputStream in = null;
		// 输出流
		FileOutputStream out = null;
		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(fileSizeMax);
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			// 设置进度监听器
			upload.setProgressListener(new Progress(request, key));
			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				return;
			}
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					HLogger.info(name + "=" + value);
					// 如果fileitem中封装的是上传文件
				} else {
					// 得到上传的文件名称，
					String filename = item.getName();
					HLogger.info(filename);
					reportFilePath = reportFilePath + File.separator + filename;
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename
							.substring(filename.lastIndexOf(File.separator) + 1);
					// 获取item中的上传文件的输入流
					in = item.getInputStream();
					// 创建一个文件输出流
					out = new FileOutputStream(savePath + File.separator + filename);
					// 创建一个缓冲区
					byte buffer[] = new byte[2048];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\"
						// + filename)当中
						out.write(buffer, 0, len);
					}
					// 删除处理文件上传时生成的临时文件
					item.delete();
					message = "文件上传成功！";
				}
			}
		} catch (Exception e) {
			message = "文件上传失败！";
			e.printStackTrace();
			HLogger.error("文件上传失败");
		} finally {
			// 关闭输入流
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					HLogger.error(e.toString());
				}
			}

			// 关闭输出流
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					HLogger.error(e.toString());
				}
			}

		}
		responseMessage(response, message, reportFilePath);
	}

	/**
	 * <pre>
	 * 任务： 
	 * 描述： 上传进度
	 * 作者：yanbaoan 
	 * 时间：2016年2月26日下午2:06:12
	 * @param context
	 * @throws BaseBllException
	 * returnType：void
	 * </pre>
	 */
	public static void progress(HttpContext context) throws BaseBllException {
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			HttpSession session = request.getSession();
			String key = (String) request.getSession().getAttribute("key");
			// 获取上传进度百分比
			String status = (String) session.getAttribute(key);
			// 设置到响应
			out.println(status);
		} catch (IOException e) {
			HLogger.error(e.getMessage());
		}

	}

	/**
	 * 
	 * <pre>
	 * 任务： 
	 * 描述： 设置返回值
	 * 作者：yanbaoan 
	 * 时间：2016年2月26日下午1:39:31
	 * @param response
	 * @param message
	 * @param reportFilePath
	 * returnType：void
	 * </pre>
	 */
	private static void responseMessage(HttpServletResponse response,
			String message, String reportFilePath) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=" + "UTF-8");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(reportFilePath);
			writer.flush();
		} catch (Exception e) {
			HLogger.error(e.toString());
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException ioe) {
					HLogger.error(ioe.toString());
				}
			}
		}
	}

	/**
	 * <pre>
	 * 任务： 
	 * 描述： 文件下载，通用
	 * 作者：yanbaoan 
	 * 时间：2016年2月26日上午11:12:14
	 * @param context
	 * @param path
	 * @throws Exception
	 * returnType：void
	 * </pre>
	 */
	public static void download(HttpContext context, String path)
			throws BaseBllException {
		HttpServletResponse response = context.getResponse();
		response.setCharacterEncoding("UTF-8");

		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		// 获得文件路径，与文件名
		try {
			File file = new File(path);
			long fileLength = file.length();
			bis = new BufferedInputStream(new FileInputStream(path));
			// 告诉浏览器为下载链接
			response.setContentType("application/x-msdownload;");
			String fileName = file.getName();
			HLogger.info(fileName);
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
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
			} else {
				HLogger.error(e.getMessage());
			}
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					HLogger.error(e.getMessage());
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					HLogger.error(e.getMessage());
				}
		}
	}

	/**
	 * 
	 * <pre>
	 * 任务： 
	 * 描述： 文件删除
	 * 作者：yanbaoan 
	 * 时间：2016年2月29日下午3:37:53
	 * @param path
	 * returnType：void
	 * </pre>
	 */
	public static void remove(String path) {
		File file = new File(path);
		if ((file.exists()) && (file.isFile())) {
			file.delete();
		}
	}

}
