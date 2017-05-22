/**
 * 
 */
package APP.Comm.Handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import APP.Comm.DotNet.HttpContext;
import APP.Comm.Util.HLogger;


public class PhotoFilter implements Filter {
	final String[] allowType = new String[] { "jpg", "jpeg", "bmp", "png",
			"pgn", "icn", "tif", "JPG", "JPEG", "BMP", "PNG", "PGN", "ICN",
			"TIF" };

	@SuppressWarnings("unused")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HLogger.info("PhotoFilter doFilter Start!");
		HLogger.info("photoFilter 开始执行");
		HttpServletRequest _request = (HttpServletRequest) request;
		HttpServletResponse _response = (HttpServletResponse) response;
		ServletContext servletContext = null;
		try {
			_request.setCharacterEncoding("UTF-8");
			_response.setCharacterEncoding("UTF-8");// _request.getParameterNames()
			HttpContext _context = new HttpContext(_request, _response);
			String url = _request.getRequestURL() + "";
			String loc = _request.getParameter("loc");
			List<String> list = Arrays.asList(allowType);
			chain.doFilter(request, response);
			HLogger.info("PhotoFilter doFilter end!");
			HLogger.info("dofilter 不满足过滤条件，沿用以前的访问方式");
			return;
		} catch (Exception ex) {
			HLogger.error(ex);
			throw new ServletException(ex);
		} 
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
