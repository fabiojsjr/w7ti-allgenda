package com.tetrasoft.web.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.technique.engine.app.SystemParameter;
import com.tetrasoft.data.usuario.UsuarioEntity;
import com.tetrasoft.util.File;

public class ImagemServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Connection dbCon = null;
		Statement dbSt = null;
		ResultSet dbRs = null;

		try {
			byte[] bytearray = new byte[4096];
			int size=0;
			InputStream sImage =null;

			String id    = request.getParameter("idNoticia");
			String campo = "imagem";
			String query = "SELECT " + campo + " FROM noticia WHERE idNoticia = " + id;
			
			dbCon = new UsuarioEntity().retrieveConnection();
			if( dbSt == null ) dbSt = dbCon.createStatement();
			dbRs = dbSt.executeQuery(query);
			dbRs.next();

			sImage = dbRs.getBinaryStream(campo);
			if(sImage==null){
				if(query.contains("imagemTopo") || query.contains("imagemRodape")) return;
				query= query.replaceAll(campo,"imagem");				
				query= query.replaceAll(campo,"imagem");				
				dbRs = dbSt.executeQuery(query);
				dbRs.next();
				sImage = dbRs.getBinaryStream("imagem");
			}
			
			try{
				response.reset();
				response.setContentType("image/jpeg");
				response.setHeader("Cache-Control", "no-cache");
				response.addHeader("Content-Disposition","filename=getimage.jpeg");
				
				while((size=sImage.read(bytearray))!= -1 ) {
					response.getOutputStream().write(bytearray,0,size);
				}
				
				response.flushBuffer();
				sImage.close();
			}catch (Exception e) {
				response.reset();
				response.setContentType("image/jpeg");
				response.setHeader("Cache-Control", "no-cache");
				response.addHeader("Content-Disposition","filename=getimage.jpeg");
				
				String path = SystemParameter.get("Allgenda", "systemProperties", "shopFilePath");
				File f = new File(path+"images/nao_disponivel2.jpg");
				FileInputStream is = new FileInputStream(f);
				long length = f.length();
				bytearray = new byte[(int)length];
			    while((size=is.read(bytearray)) != -1) {
			    	response.getOutputStream().write(bytearray,0,size);
			    }

			    response.flushBuffer();
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dbSt != null)  dbSt.close();
				if (dbRs != null)  dbRs.close();
				if (dbCon != null) dbCon.close();
			} catch (Exception e2) {
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
