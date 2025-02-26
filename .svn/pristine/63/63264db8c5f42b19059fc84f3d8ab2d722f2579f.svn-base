<%@page import="com.tetrasoft.data.cliente.MensagemEntity"%>
<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@page import="com.tetrasoft.data.usuario.UsuarioEntity"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.JSONObject"%>

<%@ include file="../logadoForce.jsp" %>
<%@include file="../idioma.jsp"%>
<%@include file="../methods.jsp"%>

<%
	String [] fields 	= new String[]
		{
			"dataHora",
			"",
			"assunto",
			""
		};

	String filtroNome 		= request.getParameter("filtroNome");	
	String filtroDataInicio = request.getParameter("filtroDataInicio");
	String filtroDataFinal 	= request.getParameter("filtroDataFinal");			
	String filtroStatus 	= request.getParameter("filtroStatus");
	String filtroTipo2 	    = request.getParameter("filtroTipo");
	String filtroTipoCaixa	= request.getParameter("filtroTipoCaixa");
	String aux				= request.getParameter("pagina");
	String actionCell		= "<a title='"+p.get("LER")+"' href=\"javascript:abre('miolo', 'mensagemLer.jsp?idMensagem=#IDMENSAGEM')\" class=\"btn btn-info btn-info\"> <i class=\"iconfa-inbox\"></i>&nbsp;&nbsp;" + p.get("LER") + "</a>";
//	String actionCell 		= "<a title='"+p.get("EDITAR")+"' style='width: 16px; margin-bottom: 0px;margin-right:5px;' class='btn btn-primary btn-circle grouped_elements' href='adminUsuarioDadosPessoais.jsp?idUsuario=#IDUSUARIO'><i class='iconfa-edit'></i></a>";
	
	int ultimoIndex;
	try{
		ultimoIndex 	= Integer.valueOf(request.getParameter("iDisplayStart"));
	}catch(Exception e){
		ultimoIndex 	=	0;
	}
	int qntPorPagina;
	try{
		qntPorPagina 	= Integer.valueOf(request.getParameter("iDisplayLength"));
	}catch(Exception e){
		qntPorPagina	= 10;
	}
	int filtro;
	try{
		filtro			= Integer.valueOf(request.getParameter("iSortCol_0"));
	}catch(Exception e){
		filtro			= 0;
	}
	String orderBy		= (request.getParameter("sSortDir_0") == null ? " asc ": request.getParameter("sSortDir_0"));
	
	Connection conn = null;
	try{
	
		UsuarioEntity user = new UsuarioEntity();
		MensagemEntity entity = new MensagemEntity();
		conn = user.retrieveConnection();
		
		String where = "status = " + filtroStatus + " AND tipo = " + filtroTipo2;
		
		if( filtroTipoCaixa.equals("inbox") ) {
			where += " AND (idDestinatario = " + usuarioLogado.getIdUsuario() + " OR idDestinatario = 0 )";
		} else {
			where += " AND idRemetente = " + usuarioLogado.getIdUsuario();
		}
		
		if(filtroNome != null && !filtroNome.equals("")){
			where += " and assunto LIKE '%"+filtroNome+"%'";
		}
		
		try{
			filtroDataInicio = new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("dd/MM/yyyy").parse(filtroDataInicio))+"000000";
		}catch(Exception e){
			filtroDataInicio = "";
		}
		try{
			filtroDataFinal = new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("dd/MM/yyyy").parse(filtroDataFinal))+"235959";
		}catch(Exception e){
			filtroDataFinal = "";
		}
		if(filtroDataInicio != null && !filtroDataInicio.equals("") ){
			where += " AND dataHora >= " + filtroDataInicio;
		}
		if(filtroDataFinal != null && !filtroDataFinal.equals("")){
			where += " AND dataHora <= " + filtroDataFinal;
		}
		
		
		long totalEntradas = entity.getCount("1=1", conn);
		
		long totalEntradasFiltradas = entity.getCount(where, conn);

		where = where +"  ORDER BY "+fields[filtro]+" "+orderBy+" LIMIT "+ ultimoIndex +" , " + qntPorPagina;
	
		ArrayList<MensagemEntity> a = entity.getArray(where, conn);
		if(a!=null){
			int i = 0;
			String data 	 = "";
			String remetente = "";
			String assunto 	 = "";
			
			JSONObject json = new JSONObject();
			json.put("sEcho", request.getParameter("sEcho"));
			json.put("iTotalRecords", totalEntradas);
			json.put("iTotalDisplayRecords", totalEntradasFiltradas);
			
			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
			
			for(MensagemEntity mensagem : a) {
				try{
					String f  = "";
					String f_ = "";
					/*
					if( usuario.getAtivo() == 0 ) {
						f  = "<font color='red'>";
						f_ = "</font>";
					}
					*/
					
					user.setIdUsuario( filtroTipoCaixa.equals("inbox") ? mensagem.getIdRemetente() : mensagem.getIdDestinatario() );
					if( !user.getThis(conn) ) {
						user.setNome("Mensagem do Sistema");
					}
					
					data = mensagem.DATE_FORMAT1_BR2.format( mensagem.getDataHora() );
					remetente = user.getNome();
					assunto = mensagem.getAssunto();
					
					ArrayList<String> innerArray = new ArrayList<String>();
					innerArray.add(f + data + "<input type='hidden' name='userId' value='"+mensagem.getIdMensagem()+"'/>" + f_);
					innerArray.add(f + remetente + f_);
					innerArray.add(f + assunto + f_);
					innerArray.add(actionCell.replace("#IDMENSAGEM", mensagem.getIdMensagem().toString()));
					array.add(innerArray);
					
				}catch(Exception e){
					System.out.println("ERRO AO PEGAR A MENSAGEM: "+mensagem.getIdMensagem());
				}
			}
			
			json.put("aaData", array);
			out.print(json);
		    out.flush();
		}
		
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
	 	} catch( Exception ee ) {
	 		
		}
	}
%>