package APP.Comm.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import APP.Comm.BLL.BaseBLL;
import APP.Comm.BLL.BaseBllException;
import APP.Comm.DotNet.HttpContext;

/**
 * 说明：文件上传下载的工具类;继承过BaseBLL 作者：左仁帅 时间：2013年1月24日 16:17:57
 */
public class FileUtil extends BaseBLL {
	/**
	 * 说明：执行该方法不需要委托执行； 上传文件到子系统DeptReckon
	 * depteckonResouceLocation=FileUitl.DepteckonResouceLocation的返回值 作者：左仁帅
	 * 时间：2013年1月24日 16:31:18
	 * 
	 * @param fileDataName
	 *            页面input -file -name
	 * @param depteckonResouceLocation
	 *            FileUitl.DepteckonResouceLocation的返回值
	 * @param lastName
	 *            文件后缀名，例如xls
	 * @param context
	 *            HttpContext
	 * @param isNeedDelete
	 *            session 结束时候，是否需要删除该文件
	 * @param filePath
	 *            输出文件的绝对路径
	 * @return 文件名称检查有效性 true|false
	 */
	// HttpContext context,
	public final boolean UploadDepteckonFile(String fileDataName,
			String depteckonResouceLocation, String lastName,
			boolean isNeedDelete, RefObject<String> filePath,
			HttpContext context) {
		// filePath.argvalue = "";
		// context.Response.ContentType = "text/html";
		// HttpPostedFile postedFile = context.Request.Files[fileDataName];
		// //获取上传信息对象
		// if (postedFile == null)
		// {
		// throw new BaseBllException("input -file -name 文件流不存在！");
		// }
		// String filename = postedFile.FileName; //获取上传的文件路径
		// if (DotNetToJavaStringHelper.isNullOrEmpty(filename) ||
		// filename.split("[.]", -1).getLength() < 2)
		// {
		// return false;
		// }
		// else if (filename.split("[.]", -1)[1].indexOf(lastName) < 0)
		// {
		// return false;
		// }
		// else
		// {
		// if (DotNetToJavaStringHelper.isNullOrEmpty(depteckonResouceLocation))
		// {
		// depteckonResouceLocation = HttpContext.Current.Server.MapPath("");
		// }
		// if (depteckonResouceLocation.endsWith("\\"))
		// {
		// depteckonResouceLocation += "DEPTRECKON\\";
		// }
		// else
		// {
		// depteckonResouceLocation += "\\DEPTRECKON\\";
		// }
		// if (!Directory.Exists(depteckonResouceLocation))
		// {
		// Directory.CreateDirectory(depteckonResouceLocation);
		// }
		// depteckonResouceLocation = depteckonResouceLocation + new
		// java.util.Date().ToString("yyyyMMddhhmmssfff") + "." + lastName;
		// postedFile.SaveAs(depteckonResouceLocation);
		// filePath.argvalue = depteckonResouceLocation;
		// //预删除
		// if (isNeedDelete)
		// {
		// java.util.List<String> paths =
		// (java.util.List<String>)context.Session["paths"];
		// if (paths == null)
		// {
		// paths = new java.util.ArrayList<String>();
		// }
		// paths.add(depteckonResouceLocation);
		// }
		// return true;
		// }
		return true;
	}

	/**
	 * 说明：执行该方法不需要委托执行； 把文件上传到指定目录 作者：左仁帅 时间：2013年1月24日 16:31:18
	 * 
	 * @param fileDataName
	 *            页面input -file -name
	 * @param path
	 *            文件保存的绝对路径，比如C:\img\a.jpg
	 * @param context
	 *            HttpContext
	 * @param isNeedDelete
	 *            session 结束时候，是否需要删除该文件
	 * @return 文件名称检查有效性 true|false
	 */
	// HttpContext context,
	public final boolean UploadFile(String fileDataName, String path,
			boolean isNeedDelete, HttpContext context) {
		// context.Response.ContentType = "text/html";
		// HttpPostedFile postedFile = context.Request.Files[fileDataName];
		// //获取上传信息对象
		// if (postedFile == null)
		// {
		// throw new BaseBllException("input -file -name 文件流不存在！");
		// }
		// postedFile.SaveAs(path);
		// //预删除
		// if (isNeedDelete)
		// {
		// java.util.List<String> paths =
		// (java.util.List<String>)context.Session["paths"];
		// if (paths == null)
		// {
		// paths = new java.util.ArrayList<String>();
		// }
		// paths.add(path);
		// }
		return true;
	}

	/**
	 * 说明：执行该方法不需要委托执行； 下载文件 作者：左仁帅 时间：2013年1月24日 19:22:02
	 * 
	 * @param filePath
	 *            文件的绝对路径
	 * @param fileName
	 *            文件的名称(带后缀名)
	 * @param context
	 *            HttpContext
	 * @throws BaseBllException
	 */
	// , HttpContext context
	public final void DownFile(String filePath, String fileName,
			HttpContext context) throws BaseBllException {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(filePath);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1)
					.toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(
					filePath));
			byte[] buffer = new byte[1024];
//			fis.read(buffer);
//			fis.close();
			// 清空response
			context.getResponse().reset();
			// 设置response的Header
			filename=context.getResponse().encodeURL(new 
            		String(filename.getBytes(),"ISO8859-1"));//将文件名进行转码，防止中文出错
			context.getResponse().setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
			context.getResponse().addHeader("Content-Length",
					"" + file.length());
			OutputStream toClient = new BufferedOutputStream(context
					.getResponse().getOutputStream());
			context.getResponse().setContentType("application/octet-stream");
//			toClient.write(buffer);
			int len;
            while((len=fis.read(buffer)) >0){
            	toClient.write(buffer);
            }
			toClient.flush();
			fis.close();
			toClient.close();
		} catch (IOException ex) {
			throw new BaseBllException(ex);
			// ex.printStackTrace();
		}
	}

	/**
	 * 说明：执行该方法需要BLLContainer的DoProcess进行委托执行 根据系统代码 RESOURCE_LOCATION，获取资源根目录，
	 * 例如：D:\FilePathTemp 作者：左仁帅 时间：2013年1月23日 17:48:27
	 * 
	 * @param hrpDb
	 * @return
	 * @throws BaseBllException
	 */
//	public final String DepteckonResouceLocation() throws BaseBllException {
//		StringBuilder sql = new StringBuilder(
//				" select COMMENTS from SYS_CODE_ITEM where CODE_NO='RESOURCE_LOCATION' ");
//		Object obj = super.getAppDB().QueryObject(sql.toString(), null);
//		return (obj == null ? "" : obj.toString());
//	}

	/**
	 * 说明：根据图片的绝对路径，输出流到context 作者：左仁帅 时间：2013年3月22日 10:01:28
	 * 
	 * @param path
	 *            图片的绝对路径
	 * @param context
	 */
	// , HttpContext context
	public final void OutStreamImg(String path, HttpContext context) {
		// try
		// {
		// Bitmap image = new Bitmap(path);
		// MemoryStream ms = new MemoryStream();
		// if (path.lastIndexOf('.') <= 0)
		// {
		// return;
		// }
		// String lastName = path.substring(path.lastIndexOf('.') + 1);
		// if (lastName.toUpperCase().equals("BMP"))
		// {
		// image.Save(ms, ImageFormat.Bmp);
		// }
		// else if (lastName.toUpperCase().equals("EMF"))
		// {
		// image.Save(ms, ImageFormat.Emf);
		// }
		// else if (lastName.toUpperCase().equals("EXIF"))
		// {
		// image.Save(ms, ImageFormat.Exif);
		// }
		// else if (lastName.toUpperCase().equals("GIF"))
		// {
		// image.Save(ms, ImageFormat.Gif);
		// }
		// else if (lastName.toUpperCase().equals("ICON"))
		// {
		// image.Save(ms, ImageFormat.Icon);
		// }
		// else if (lastName.toUpperCase().equals("JPEG") ||
		// lastName.toUpperCase().equals("JPG"))
		// {
		// image.Save(ms, ImageFormat.Jpeg);
		// }
		// else if (lastName.toUpperCase().equals("PNG"))
		// {
		// image.Save(ms, ImageFormat.Png);
		// }
		// context.Response.ClearContent();
		// context.Response.BinaryWrite(ms.toArray());
		// }
		// catch (RuntimeException e)
		// {
		// HLogger.Error(e);
		// }
	}

	public static String[] readDirFilesName(String filePath, boolean isAll)
			throws BaseBllException {
		String[] rs = null;
		List<File> fs = readDirFiles(filePath, isAll);
		if (fs != null) {
			rs = new String[fs.size()];
			int i = 0;
			for (File file : fs) {
				rs[i] = file.getName();
				i++;
			}
		} else {
			rs = new String[] {};
		}

		return rs;
	}

	/**
	 * 取指定目录下指定后缀名的文件
	 * 
	 * @param filePath
	 * @param isAll
	 * @param filterName
	 * @return
	 * @throws BaseBllException
	 */
	public static List<File> readDirFilesFilter(String filePath, boolean isAll,
			String filterName) throws BaseBllException {
		List<File> fs = readDirFiles(filePath, isAll);
		List<File> rs = new ArrayList<File>();
		for (File file : fs) {
			String fName = file.getName();
			if (fName != null && fName.length() > 0) {
				int i = fName.lastIndexOf(".");
				if (i > -1 && i < fName.length() && fName.equals(filterName)) {
					rs.add(file);
				}
			}
		}
		return rs;
	}

	/**
	 * 取指定目录下的文件
	 * 
	 * @param filepath
	 * @param isAll
	 *            是否读取子目录的文件
	 * @return
	 * @throws BaseBllException
	 */
	public static List<File> readDirFiles(String filepath, boolean isAll)
			throws BaseBllException {
		List<File> fs = new ArrayList<File>();
		try {
			File file = new File(filepath);
			if (!isAll && file.isDirectory()) {
				fs.addAll(Arrays.asList(file.listFiles()));
				return fs;
			}
			if (!file.isDirectory()) {
				fs.add(file);
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						fs.add(readfile);
					} else if (readfile.isDirectory()) {
						readDirFiles(filepath + "\\" + filelist[i], isAll);
					}
				}
			}
		} catch (Exception e) {
			// System.out.println("readfile()   Exception:" + e.getMessage());
			throw new BaseBllException(e);
		}
		return fs;
	}
	
	/**
	 * <pre>
	 * 任务： KYEEAPPC-1415
	 * 描述： 日志文件压缩
	 * 作者：罗代华 
	 * 时间：2015年1月27日下午9:23:03
	 * @param source 文件源
	 * @param targetPath 压缩文件保存路径
	 * 		
	 * @return 压缩文件保存路径
	 * @throws IOException
	 * @throws BaseBllException
	 * returnType：String
	 * </pre>
	*/
	public String doZIPFile(String source,String targetPath) throws BaseBllException{
    	File sFile = new File(source);//源文件夹
    	String tFileName = sFile.getName()+".zip";
    	File targetFile  = new File(targetPath);//目的文件夹
    	if(!targetFile.exists()){
    		targetFile.mkdirs();
    	}
    	if(!sFile.exists()){
    		throw new BaseBllException("需要压缩的文件不存在，请检查！");
    	}
    	FileInputStream fis = null;  
	    FileOutputStream outputStream = null;  
	    ZipOutputStream  out = null; 
		try {
			outputStream = new FileOutputStream(new File(targetPath+"\\"+tFileName));
			out = new ZipOutputStream (outputStream);
			fis = new FileInputStream(sFile); 
			out.putNextEntry(new ZipEntry(sFile.getName()));
			out.setEncoding("gbk");
            //进行写操作  
			byte[] buf = new byte[1024];  
		    int num;  
		    while ((num = fis.read(buf)) != -1) {  
		    	out.write(buf, 0, num);  
		   }  
		} catch (Exception e) {
			HLogger.error(e.getMessage());
			throw new BaseBllException(e.getMessage());
		}finally{
			try{
				if (out != null) { 
					out.close(); 
				}
				if (outputStream != null)  
				   outputStream.close();  
				if (fis != null)  
				   fis.close();
			}catch(Exception e){
				HLogger.info(e);
				throw new BaseBllException(e);
			}
		}
        return targetPath+"\\"+tFileName;
    }
	/**
	 * <pre>
	 * 任务： KYEEAPPC-1486
	 * 描述：文件复制 
	 * 作者：罗代华 
	 * 时间：2015年2月8日下午2:44:40
	 * @param source 源文件路径
	 * @param targetPath 复制文件路径
	 * @return 复制文件路径全路径（包含文件名）
	 * @throws BaseBllException
	 * returnType：String
	 * </pre>
	*/
	public String doCopyFile(String source,String targetPath) throws BaseBllException{
    	File sFile = new File(source);//源文件夹
    	String tFileName = sFile.getName();
    	File targetFile  = new File(targetPath);//目的文件夹
    	if(!targetFile.exists()){
    		targetFile.mkdirs();
    	}
    	if(!sFile.exists()){
    		throw new BaseBllException("需要复制的文件不存在，请检查！");
    	}
    	FileInputStream fis = null;  
	    FileOutputStream outputStream = null;
	    try{
	    	fis = new FileInputStream(sFile);
	    	outputStream = new FileOutputStream(new File(targetPath+"\\"+tFileName));
	    	byte[] buff = new byte[2048];
	    	int temp=0;  
	        while((temp=fis.read(buff,0,2048))!=-1){  
	        	outputStream.write(buff,0,temp);  
	        }  
	        fis.close();  
        	outputStream.close();
        	return targetPath+"\\"+tFileName;
	    }catch(Exception e){
	    	HLogger.error(e);
	    	throw new BaseBllException(e);
	    }finally{
	    	try{
		    	if(fis!=null){
		    		fis.close(); 
		    	}if(outputStream!=null)
	        	outputStream.close();
	    	}catch(Exception e){
		    	HLogger.error(e);
	    		throw new BaseBllException(e);
	    	}
	    }
    }
}