package com.tetrasoft.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.technique.engine.web.UserSession;
import com.tetrasoft.data.cliente.MensagemEntity;
import com.tetrasoft.data.usuario.UsuarioEntity;

public class AjaxRequestServlet extends HttpServlet {
	
	private static final String GET_MENSAGENS		 = "mensagens"; 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
		String action = request.getParameter("action");
		JSONObject json = new JSONObject();
		UsuarioEntity usuarioLogado = null;
		response.setHeader("Content-Type", "application/json;charset=iso-8859-1");
		try{
			usuarioLogado = (UsuarioEntity) ((UserSession) request.getSession().getAttribute("UserSession_Allgenda")).getAttribute("loginAllgenda");	
			if(usuarioLogado == null) throw new Exception();
		}catch(Exception e){
			response.setHeader("Cache-Control", "no-cache"); 
			response.setContentType("text/html");
			
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>location.href = 'index.jsp';</script>");
			out.flush();
			System.out.println(usuarioLogado.getIdUsuario());
		}
		
		
		response.setHeader("Cache-Control", "no-cache"); 
		PrintWriter out = response.getWriter();
		if(action.equals(GET_MENSAGENS)){
			
			getMensagens(request, json, usuarioLogado);
			out.print(json);
			
		}
		response.setContentType("application/json");
		out.flush();
	}
	
	@SuppressWarnings("unchecked")
	private void getMensagens(HttpServletRequest request, JSONObject json, UsuarioEntity usuarioLogado) {
		
		SimpleDateFormat defaultTimeMinuteSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Connection conn = null;
		try{
			conn = new UsuarioEntity().retrieveConnection();
			
			ArrayList<MensagemEntity> mensagens = ((ArrayList<MensagemEntity>)new MensagemEntity().getArray("status = 0 AND idDestinatario = " + usuarioLogado.getIdUsuario() + " ORDER BY dataHora DESC", conn));
			json.put("naoLidas", mensagens.size());
			
			
			JSONArray array = new JSONArray();
			for(MensagemEntity mensagem : mensagens){
				JSONObject js = new JSONObject();
				
				js.put("idMensagem", mensagem.getIdMensagem());
				js.put("assunto", mensagem.getAssunto());
				js.put("remetente", mensagem.getRemetente(conn));
				js.put("data", defaultTimeMinuteSimpleDateFormat.format( mensagem.getDataHora() ));
				
				array.put(js);
			}
			json.put("mensagens", array);
		}catch( Exception e){
//			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
//				e.printStackTrace();
			}
		}
	}

	/*
	private void getDocumento(HttpServletRequest request, JSONObject json, UsuarioEntity logado) {
		
		String idEtapa = request.getParameter("idE");
		ArrayList<DocumentoEntity> documentos = null;
		try {
//			if(!new ProjetoUsuariosEntity().getThis("idUsuario = "+logado.getIdUsuario()+" and idProjeto = "+idProjeto)){
//				return;
//			}
			documentos = (ArrayList<DocumentoEntity>)new DocumentoEntity().getArray("idEtapa = "+idEtapa+" order by nomeDocumento");
			JSONArray array = new JSONArray();
			for(DocumentoEntity documento : documentos){
				JSONObject js = new JSONObject();
				js.put("id", documento.getIdDocumento());
				js.put("nome", documento.getNomeDocumento());
				array.put(js);
			}
			json.put("documentos", array);
		} catch (ExceptionWarning e) {
			e.printStackTrace();
		} catch (ExceptionInjection e) {
			e.printStackTrace();
		}
	}
	*/
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
//		String action = request.getParameter("action");
		JSONObject json = new JSONObject();
		
		response.setHeader("Cache-Control", "no-cache"); 
		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}
}
