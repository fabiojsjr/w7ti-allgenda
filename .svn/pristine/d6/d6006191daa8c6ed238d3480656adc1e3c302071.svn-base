<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.JSONObject"%>
<%@ include file="../logado.jsp" %>
<%@include file="../idioma.jsp"%>
<%@include file="../methods.jsp"%>

<%
	boolean master = false;
	if( !usuarioLogado.semPermissaoAcesso(420, "excluir") ) {
		master = true;
	}
	// master = false;
	// usuarioLogado.setIdUsuario(2l);

	String [] fields 	= new String[]
		{
			"dataHora",
			"login",
			"tabela",
			"ipOrigem",
			"observacoes"
		};

	String filtroObs			= request.getParameter("filtroObs");
	String filtroNome			= request.getParameter("filtroNome");
	String filtroIdCampo 		= request.getParameter("filtroIdCampo");			
	String filtroIdUsuario 		= request.getParameter("filtroIdUsuario");			
	String filtroDataInicio		= request.getParameter("filtroDataInicio");
	String filtroDataFinal		= request.getParameter("filtroDataFinal");
	
	String aux			= request.getParameter("pagina");
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

	String orderBy		= request.getParameter("sSortDir_0");
	
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
	
	Connection conn = null;
	try{
		LogEntity entity = new LogEntity();
		conn = entity.retrieveConnection();
		
		String queryLog = "SELECT l.dataHora, u.login, l.tabela, l.ipOrigem, l.observacoes FROM log as l, usuario as u WHERE u.idUsuario = l.idUsuario ";
		String queryCount = "select count(*) from log as l, usuario as u WHERE u.idUsuario = l.idUsuario ";
		if(filtroDataInicio != null && !filtroDataInicio.equals("") ){
			queryLog   += " AND dataHora >= " + filtroDataInicio;
			queryCount += " AND dataHora >= " + filtroDataInicio;
		}
		if(filtroDataFinal != null && !filtroDataFinal.equals("")){
			queryLog   += " AND dataHora <= " + filtroDataFinal;
			queryCount += " AND dataHora <= " + filtroDataFinal;
		}
		if(filtroNome != null && !filtroNome.equals("")){
			queryLog   += " AND u.login LIKE '%" + filtroNome+"%'";
			queryCount += " AND u.login LIKE '%" + filtroNome+"%'";
		}
		if(filtroObs != null && !filtroObs.equals("")){
			queryLog   += " AND l.observacoes LIKE '%" + filtroObs+"%'";
			queryCount += " AND l.observacoes LIKE '%" + filtroObs+"%'";
		}
		
		if( !master ) {
			filtroIdUsuario = usuarioLogado.getIdUsuario().toString(); 
		}
		
		if( !filtroIdCampo.equals("") ) {
			filtroIdUsuario = "";
			
			queryLog = "SELECT l.dataHora, '', l.tabela, l.ipOrigem, l.observacoes FROM log as l WHERE l.observacoes LIKE 'Campanha respondida:%' AND l.idCampo = " + filtroIdCampo;
			queryCount = "select count(*) from log as l WHERE l.observacoes LIKE 'Campanha respondida:%' AND l.idCampo = " + filtroIdCampo;
		}
		
		if(filtroIdUsuario != null && !filtroIdUsuario.equals("")){
			queryLog   += " AND l.idUsuario = " + filtroIdUsuario;
			queryCount += " AND l.idUsuario = " + filtroIdUsuario;
		}
		
		
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = conn.createStatement();
		
		//System.out.println("QUERY = " + queryCount);
		long totalEntradas = entity.getCount("1=1", conn);

		rs = stmt.executeQuery(queryCount);
		int totalEntradasFiltradas = 0;
		while(rs.next()) {
			totalEntradasFiltradas = rs.getInt(1);
        }

		queryLog += "  ORDER BY "+fields[filtro]+" "+orderBy+" LIMIT "+ ultimoIndex +" , " + qntPorPagina;

		boolean isEmptyResultSet = true;
		rs = stmt.executeQuery(queryLog);
		
		int i = 0;
		
		JSONObject json = new JSONObject();
		json.put("sEcho", request.getParameter("sEcho"));
		json.put("iTotalRecords", totalEntradas);
		json.put("iTotalDisplayRecords", totalEntradasFiltradas);
		
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();

		while(rs.next()){
			try{
				ArrayList<String> innerArray = new ArrayList<String>();
				innerArray.add(sdf.format(rs.getDate(1)) + " " + rs.getTime(1));
				innerArray.add(rs.getString(2));
				innerArray.add(rs.getString(3));
				innerArray.add(rs.getString(4));
				innerArray.add(rs.getString(5));
				
				array.add(innerArray);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		json.put("aaData", array);
		out.print(json);
	    out.flush();
		
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();
	 	} catch( Exception ee ) {
	 		
		}
	}
%>