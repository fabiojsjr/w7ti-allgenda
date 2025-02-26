package com.tetrasoft.web.ajax;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionWarning;
import com.technique.engine.web.UserSession;
import com.tetrasoft.app.sender.SenderInterface;
import com.tetrasoft.common.Thumbnail;
import com.tetrasoft.common.web.BaseCommand;

public class AjaxUploadServlet extends HttpServlet implements Servlet {
	
	public AjaxUploadServlet() {
		super();
	}
	
	final public static String[] EXTENSION = new String[]{"jpg","png","jpeg","gif","bmp","docx","doc","xlsx","xls","pdf","pptx","ppt","zip"}; 

	final public static String UPLOAD_IMAGEM_PRODUTO 	= "1";
	final public static String UPLOAD_EMAIL_MARKETING 	= "2";
	final public static String UPLOAD_IMAGEM_NOTICIA 	= "3";
	final public static String UPLOAD_IMAGEM_BANNER 	= "4";
	final public static String UPLOAD_CNAB 				= "5";
	final public static String UPLOAD_DOC 				= "6";
	final public static String UPLOAD_DOC_BOOKING		= "7";
	
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserSession sessionDefault = (UserSession) request.getSession().getAttribute("UserSession_Allgenda");
		
		if (sessionDefault==null) throw new ServletException("SESSION NULL");
		if (sessionDefault.getAttribute(BaseCommand.LOGIN_TAG)==null) {
			throw new ServletException("USUéRIO NéO AUTENTICADO");
		}

		// create file upload factory and upload servlet
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		// set file upload progress listener
		AjaxUploadServletListener listener = new AjaxUploadServletListener();

		HttpSession session = request.getSession();

		session.setAttribute("LISTENER-" + request.getParameter("idq"), listener);

		// upload servlet allows to set upload listener
		upload.setProgressListener(listener);

		List uploadedItems = null;
		FileItem fileItem = null;
		// Path to store file on local system
		String rootPath = "";
        try {
            rootPath = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath");
        } catch (ExceptionWarning e) {
        }
		String filePath = rootPath + request.getParameter("ajaxUploadPath");
		new File(filePath).mkdirs();

		try {
			// iterate over all uploaded files
			uploadedItems = upload.parseRequest(request);

			Iterator i = uploadedItems.iterator();

			while (i.hasNext()) {
				fileItem = (FileItem) i.next();

				if ( fileItem.isFormField() == false ) {
					if ( fileItem.getSize() > 0 ) {
						File uploadedFile = null;
						String myFullFileName = fileItem.getName(), myFileName = "", slashType = (myFullFileName.lastIndexOf("\\") > 0) ? "\\" : "/"; // Windows
																																						// or
						int startIndex = myFullFileName.lastIndexOf(slashType);

						// Ignore the path and get the filename
						myFileName = myFullFileName.substring(startIndex + 1, myFullFileName.length());

						listener.setArquivo(myFileName);
						listener.setOver(true);
						
						// dados extras do sistema - Extenséo
						String ext      = myFileName.substring( myFileName.lastIndexOf("."), myFileName.length() );
						
						// Create new File object
						uploadedFile = new File(filePath, myFileName);

						if( uploadedFile.exists() )
							uploadedFile.delete();
//						Write the uploaded file to the system
						fileItem.write(uploadedFile);
//						code acima coloca a img ou zip na pasta...
						
						// Write the uploaded file to the system
						fileItem.delete(); // teste
							
						// dados extras do sistema
						String renameTo = request.getParameter("ajaxUploadRenameTo");
						String excecao  = request.getParameter("ajaxUploadExcecao");
						String perfil   = request.getParameter("uploadDocumentos");
						
						if( renameTo == null ) renameTo = "";
						if( excecao  == null ) excecao  = "";
						if( perfil   == null ) perfil  = "";
						
						if( perfil.equals("3")){
							if(!(ext.toLowerCase().equals(".xls") || (ext.toLowerCase().equals(".xlsx")))){
								uploadedFile.delete();
							}
							
						}else if( !renameTo.equals("") ) {
							File newFile = new File(filePath, renameTo + ext.toLowerCase());
							if( newFile.exists())
								newFile.delete();
							
							for( String ext2 : EXTENSION ) {
								File tmpFile = new File(filePath, renameTo + ext2 );
								
								if( tmpFile.exists() ) {
									tmpFile.delete();
								}
							}
							
							
							/*
							if( perfil.equals("2")){
								File folder = new File(filePath);
								File[] files = folder.listFiles();
								if(!ext.toLowerCase().equals(".jpg") && !ext.toLowerCase().equals(".jpeg") && !ext.toLowerCase().equals(".gif") && !ext.toLowerCase().equals(".png") && !ext.toLowerCase().equals(".bmp")){
									uploadedFile.delete();
								}else if(files.length > 1){
									for(File file : folder.listFiles()){
										if(FilenameUtils.removeExtension(file.getName()).equals(renameTo))
											file.delete();
									}
								}
							}
							*/
							if(uploadedFile.exists()){
								uploadedFile.renameTo(newFile);
								uploadedFile = new File( filePath + renameTo + ext.toLowerCase() );
								if( renameTo.contains("perfil") ) {
									Thumbnail thumbnail = new Thumbnail();
									thumbnail.converter(uploadedFile.getAbsolutePath(), uploadedFile.getAbsolutePath(), 800, 600);
								}
							}
						}
						
						if( excecao.equals(UPLOAD_EMAIL_MARKETING)){
							File newFile = new File(filePath, new SimpleDateFormat("yyyyMMdd").format(new Date()) + uploadedFile.getName());
							if( newFile.exists() )
								newFile.delete();
							
							uploadedFile.renameTo(newFile);
							uploadedFile = new File( filePath + renameTo + ext.toLowerCase() );
							
						} else if( excecao.equals(UPLOAD_DOC)){
							// filePath = c:/servidor/tomcat/webapps/click/upload/usuario/1390915364695/doc/
							try {
//								out.println("ENVIADO COM SUCESSO = idOS " + request.getParameter("idOS") );
								
								/*
								UsuarioEntity user = new UsuarioEntity();
								String s = new String(filePath);
								s = s.replaceAll("/doc/", "");
								s = s.substring( s.lastIndexOf("/")+1 );
								s = s.replaceAll("/", "");

								user.setIdUsuario( new Long(s) );
								user.getThis();
								user.update();
								*/
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}	
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparTemp(File f){
		if(f.isDirectory()){
			File[] files = f.listFiles();
			for(int i = 0;i < files.length;i++){
				limparTemp(files[i]);
			}
		}
		f.delete();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserSession sessionAllgenda = (UserSession) request.getSession().getAttribute("UserSession_Allgenda");

		if (sessionAllgenda==null) throw new ServletException("SESSION NULL");
		if (sessionAllgenda.getAttribute(BaseCommand.LOGIN_TAG)==null) {
			throw new ServletException("NéO AUTENTICADO");
		}

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		AjaxUploadServletListener listener = null;
		long bytesRead = 0, contentLength = 0;
		long percentComplete=0L;
		long bytesSec=0L;
		String arq="";

		listener = (AjaxUploadServletListener) session.getAttribute("LISTENER-" + request.getParameter("idq"));

		if ( listener != null ) {
			// Get the meta information
			bytesRead = listener.getBytesRead();
			contentLength = listener.getContentLength();
			if (contentLength==0) {
				percentComplete=100L;
			} else {
				percentComplete = ( (100 * bytesRead) / contentLength);
			}
			bytesSec=listener.getBytesSec();
			if (bytesRead == contentLength) {
				percentComplete=1L;
				if ( listener.isOver() ) {
					percentComplete=100L;
					session.setAttribute("LISTENER-" + request.getParameter("idq"), null);

					String rootPath = "";
                    try {
	                    rootPath = SystemParameter.get(SenderInterface.SID, "systemProperties", "filePath");
                    } catch (ExceptionWarning e) {
                    }
					String filePath = rootPath + request.getParameter("ajaxUploadPath");
					File file = new File(filePath,listener.getArquivo());
					if (file.exists()) {
						percentComplete=100L;
						arq = request.getParameter("ajaxUploadPath") + listener.getArquivo();
						
					} else {
						String renameTo = request.getParameter("ajaxUploadRenameTo");
						if( renameTo == null ) renameTo = "";
						
//						String excecao  = request.getParameter("ajaxUploadExcecao");
//						if( excecao.equals(EXCECAO_PRODUTO) || excecao.equals(EXCECAO_LOGO_MARCA) || excecao.equals(EXCECAO_IMAGENSPUB) ||  
//							excecao.equals(EXCECAO_PDV) || excecao.equals(EXCECAO_RELEASES)){
//							renameTo="";
//						}
						if( !renameTo.equals("") ) {
							String ext = listener.getArquivo().substring( listener.getArquivo().lastIndexOf("."), listener.getArquivo().length() ).toLowerCase();
							File newFile = new File(filePath, renameTo + ext);
							arq = request.getParameter("ajaxUploadPath") + renameTo + ext;
							
							if( !newFile.exists() ) {
								arq = "[f]Falha";
							}
						}
					}
				}
			}
		} else {
			arq="[f]Falha";
		}
		
		/*
		if( percentComplete == 100l ) {
			try {
				if( arq.contains("/os/") ) {
//					arq: upload/os/1465314284286/5.xlsx
					String idOS = arq.substring( arq.indexOf("/os")+3, arq.lastIndexOf("/") );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/
		
		out.println("*"+percentComplete+":" + bytesSec + "?" + arq);
		out.close();
	}
}
