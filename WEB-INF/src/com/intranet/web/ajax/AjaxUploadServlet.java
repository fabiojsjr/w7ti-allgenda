package com.intranet.web.ajax;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
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
import com.tetrasoft.common.web.IntranetBaseCommand;

public class AjaxUploadServlet extends HttpServlet implements Servlet {
	
	public AjaxUploadServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserSession sessionDefault = (UserSession) request.getSession().getAttribute("UserSession_Allgenda");
		
		if (sessionDefault==null) throw new ServletException("SESSION NULL");
		if (sessionDefault.getAttribute(IntranetBaseCommand.LOGIN_TAG)==null) {
			throw new ServletException("USU�RIO N�O AUTENTICADO");
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
            rootPath = SystemParameter.get(SenderInterface.SID, "systemProperties", "shopFilePath");
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

						// Create new File object
						uploadedFile = new File(filePath, myFileName);
						if( uploadedFile.exists() )
							uploadedFile.delete();

						// Write the uploaded file to the system
						fileItem.write(uploadedFile);
						
						
						File destino = new File(filePath + "zoom/");
						if (!destino.exists()) destino.mkdir();
						
						destino = new File(filePath + "thumb/");
						if (!destino.exists()) destino.mkdir();

						File origem = new File(filePath + myFileName);
			            destino = new File(filePath + "zoom/" + myFileName); 
			            copiar(origem, destino);
			            
			            destino = new File(filePath + "thumb/" + myFileName); 
			            copiar(origem, destino);

			            redimensionarImagem(uploadedFile, new File(filePath + "zoom/" + myFileName), 1024, 1280);
			            redimensionarImagem(uploadedFile, new File(filePath + "thumb/" + myFileName), 130, 130);
			            
			            /*
			            
						//rotina para galeria de zooom dos produtos esta deixando as fotos pretas 
						Thumbnail thumb = new Thumbnail();
						thumb.converter(uploadedFile.getPath(), uploadedFile.getPath() , 308, 247);
						thumb.converter(uploadedFile.getPath() , filePath + "/thumb/" + myFileName , 116, 93);
						thumb.converter(uploadedFile.getPath() , filePath + "/zoom/" + myFileName, 1280, 1024);*/
						
						// dados extras do sistema
						String renameTo = request.getParameter("ajaxUploadRenameTo");
						String excecao  = request.getParameter("ajaxUploadExcecao");
						
						if( renameTo == null ) renameTo = "";
						if( excecao  == null ) excecao  = "";
						
						if( !renameTo.equals("") ) {
							String ext = myFileName.substring( myFileName.lastIndexOf("."), myFileName.length() );
							File newFile = new File(filePath, renameTo + ext);
							if( newFile.exists() )
								newFile.delete();
							
							uploadedFile.renameTo(newFile);
							uploadedFile = new File( filePath + renameTo + ext.toLowerCase() );
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
	
	
	
	
	private static void redimensionarImagem(File fileImgOriginal,
	        File fileImgRedimensionada, int altura, int largura)
	        throws Exception {
	 
	    // Primeiramente, vamos verificar se a imagem original existe e se ela
	    // pode ser lida para que possamos redimensiona-la e salva-la em disco.
	    if (!fileImgOriginal.exists()) {
	        throw new Exception("A imagem que voc� quer redimensionar n�o existe");
	    }
	 
	    // Vamos ver se a imagem pode ser lida
	    if (!fileImgOriginal.canRead()) {
	        throw new Exception("A imagem que voc� quer redimensionar n�o pode ser lida");
	    }
	 
	    // Se passou pelas verifica��es acima, vamos ler a imagem e obter um
	    // objeto do tipo Image
	    Image imagem = ImageIO.read(fileImgOriginal);
	 
	    // Agora, vamos obter um objeto do tipo imagem novamente, s� que
	    // reduzindo seu tamanho para o tamanho que queremos.
	    Image thumbs = imagem.getScaledInstance(largura, altura,BufferedImage.SCALE_SMOOTH);
	 
	    // J� temos nosso thumbs criado em mem�ria. Precisamos salvar esse
	    // thumbs em disco. Para isso, usaremos o objeto BufferedImage
	    BufferedImage buffer = new BufferedImage(largura, altura,BufferedImage.TYPE_INT_RGB);
	         
	    // Desenha a imagem, que foi redimensionada acima, no buffer.
	    buffer.createGraphics().drawImage(thumbs, 0, 0, null);
	     
	    // Salva a imagem no disco!
	    ImageIO.write(buffer, getExtension(fileImgRedimensionada),fileImgRedimensionada);
	         
	    // Faz o "flush" do buffer
	    buffer.flush();
	}
	
	private static String getExtension(File file) {
	    String nomeArq = file.getName();
	    String ext = nomeArq.substring(nomeArq.lastIndexOf('.') + 1);
	    return ext;
	}
	
	static void copiar(File fonte, File destino) throws IOException{
        FileInputStream in = new FileInputStream(fonte);
        OutputStream out = new FileOutputStream(destino);
    
        byte[] buf = new byte[1024];
        int len;
        while((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserSession sessionAllgenda = (UserSession) request.getSession().getAttribute("UserSession_Allgenda");

		if (sessionAllgenda==null) throw new ServletException("SESSION NULL");
		if (sessionAllgenda.getAttribute(IntranetBaseCommand.LOGIN_TAG)==null) {
			throw new ServletException("N�O AUTENTICADO");
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
	                    rootPath = SystemParameter.get(SenderInterface.SID, "systemProperties", "shopFilePath");
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

		out.println("*"+percentComplete+":" + bytesSec + "?" + arq);
		out.close();
	}
}
