package com.util;

import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

/**
 * 
 * <pre>
 * 任务：
 * 描述：进度监听及计算
 * 作者：yanbaoan 
 * 时间：2016年2月24日下午6:49:11
 * 类名: Progress 
 * </pre>
 */
public class Progress implements ProgressListener {

	private HttpSession session;

	private long length = 0;

	private long currentLength = 0;

	private boolean isComplete = false;

	private double megaBytes = -1;

	private String key;

	public Progress(HttpServletRequest request, String key) {
		session = request.getSession();
		this.key = key;
	}

	@Override
	public void update(long pBytesRead, long pContentLength, int items) {
		this.currentLength = pBytesRead;
		double mBytes = pBytesRead / 1000000;
		double total = pContentLength / 1000000;
		if (megaBytes == mBytes) {
			return;
		}
		megaBytes = mBytes;
		if (pContentLength == -1) {
		} else {
			double read = (mBytes / total);
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			session.setAttribute(key, nf.format(read));
		}

	}

	public long getLength() {
		return length;
	}

	public long getCurrentLength() {
		return currentLength;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

}
