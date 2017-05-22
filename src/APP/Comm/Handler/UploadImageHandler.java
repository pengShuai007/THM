package APP.Comm.Handler;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;

import APP.Comm.BLL.BaseBllException;
import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.JsonUtil;

/**
 * UploadImageHandler 的摘要说明
 */
public class UploadImageHandler extends BaseServlet {
	/**
	 * handler 主入口
	 * 
	 * @param context
	 */
	@Override
	public void ProcessHrpRequest(HttpContext context) throws BaseBllException {
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (super.op)
		// ORIGINAL LINE: case "getCanUserSilverlight":
		if (context.getRequest().getParameter("op")
				.equals("getCanUserSilverlight")) {
			this.ProcessRequestExpend(context);
		} else {
			context.write(JsonUtil.errorMessageJsonString("op参数错误！"));
		}
	}

	public final void ProcessRequestExpend(HttpContext context)
			throws BaseBllException {

		// 关键代码，把图片存储在网站的ClientBin/images目录下
		context.getResponse().setContentType("text/plain");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String filename = sdf.format(new java.util.Date()) + ".png";
		// string image = context.Request.QueryString("image").ToString();
		// Stream s = context.Request.InputStream;
		// String img = context.getRequest().Form("No")+"";
		// img = img.replace(" ", "");
		// byte[] imagebyte = Convert.FromBase64String(img);
		// String expenseNo =
		// context.getSession().getAttribute("EXPENSE_NO")+"";
		// //string path = System.AppDomain.CurrentDomain.BaseDirectory;
		//
		//
		// String strSaveFileName =
		// context.Server.MapPath("~/Resources/BudgetImages/" + expenseNo +
		// "/");
		// if (!Directory.exists()(strSaveFileName))
		// {
		// Directory.CreateDirectory(strSaveFileName);
		// }
		// strSaveFileName = strSaveFileName + filename;
		// if (File.exists()(strSaveFileName))
		// {
		// File.Delete(strSaveFileName);
		// }
		// //C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced
		// by its Java equivalent:
		// // using (FileStream fs = File.Create(strSaveFileName))
		// FileStream fs = File.Create(strSaveFileName);
		// try
		// {
		// SaveImage(imagebyte, fs);
		// }
		// finally
		// {
		// fs.dispose();
		// }

	}

	private void SaveImage(byte[] img, FileInputStream fs)
			throws BaseBllException {
		// byte[] buffer = new byte[4096];
		// int bytesRead;
		// while ((bytesRead = stream.Read(buffer, 0, buffer.Length)) != 0)
		// {
		// fs.Write(buffer, 0, bytesRead);
		// }

		int len = (int) img.length;
		// fs.write(img, 0, len);
	}

	@Override
	public boolean getHandlerExtend(HttpContext context)
			throws BaseBllException {
		return true;
	}

//	public final boolean getIsReusable(HttpContext context)
//			throws BaseBllException {
//		return false;
//	}
}
