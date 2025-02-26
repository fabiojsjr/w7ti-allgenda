package com.intranet.web.ajax;

import org.apache.commons.fileupload.ProgressListener;

public class AjaxUploadServletListener implements ProgressListener {
	private volatile long	bytesRead	= 0L, contentLength = 0L, item = 0L, bytesSec=0L, ultBytes=0L;
	
	private String arquivo="";
	private boolean isOver=false;
	
	public AjaxUploadServletListener() {
		super();
	}

	public void update(long aBytesRead, long aContentLength, int anItem) {
		bytesRead = aBytesRead;
		contentLength = aContentLength;
		item = anItem;
	}

	public long getBytesRead() {
		return bytesRead;
	}

	public long getContentLength() {
		return contentLength;
	}

	public long getItem() {
		return item;
	}

	public String getArquivo() {
		return arquivo;
	}
	
	public void setArquivo(String arquivo) {
		this.arquivo=arquivo;
	}
	
	public long getBytesSec() {
		bytesSec=(bytesRead-ultBytes)/1024;
		ultBytes = bytesRead;
		return bytesSec;
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	
}
