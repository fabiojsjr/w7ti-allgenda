<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@page import="com.tetrasoft.data.cliente.MailingEntity"%>
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
	boolean master = false;
	if( !usuarioLogado.semPermissaoAcesso(MailingEntity.ID_FUNCAO, "excluir") ) {
		master = true;
	}
	
	String [] fields 	= new String[]
		{
			"nome",
			"email",
			"telefone1",
			"contato",
			"ativo"
		};
	
	String filtroNome 		= request.getParameter("filtroNome");	
	String filtroEmail 		= request.getParameter("filtroEmail");	
	String filtroTelefone	= request.getParameter("filtroTelefone");	
	String filtroContato 	= request.getParameter("filtroContato");	
	String filtroAtivo 		= request.getParameter("filtroAtivo");	
	String aux				= request.getParameter("pagina");
	String actionCell 		= "<a title='#TE' style='color: #CE; border-color: #CE; width: 16px; margin-bottom: 0px;margin-right:5px;' class='btn btn-primary btn-circle grouped_elements' href='mailingEdit.jsp?id=#ID'><i class='iconfa-edit'></i></a>";
		   
	int ultimoIndex;
	
	try{
		ultimoIndex = Integer.valueOf(request.getParameter("iDisplayStart"));
	}catch(Exception e){
		ultimoIndex = 0;
	}
	int qntPorPagina;
	try{
		qntPorPagina = Integer.valueOf(request.getParameter("iDisplayLength"));
	}catch(Exception e){
		qntPorPagina = 10;
	}
	int filtro;
	try{
		filtro	= Integer.valueOf(request.getParameter("iSortCol_0"));
	}catch(Exception e){
		filtro	= 0;
	}
	String orderBy		= (request.getParameter("sSortDir_0") == null ? " asc ": request.getParameter("sSortDir_0"));
	
	Connection conn = null;
	try{
		MailingEntity entity = new MailingEntity();
		conn = new UsuarioEntity().retrieveConnection();
		StringBuilder where = new StringBuilder(" 1=1 ");
			
		if( filtroNome 	   != null && !filtroNome.equals("") )		where.append(" and nome LIKE '%"+filtroNome+"%'");
		if( filtroEmail    != null && !filtroEmail.equals("") )		where.append(" and email LIKE '%"+filtroEmail+"%'");
		if( filtroTelefone != null && !filtroTelefone.equals("") )	where.append(" and ( telefone1 LIKE '%"+filtroTelefone+"%' OR telefone2 LIKE '%"+filtroTelefone+"%' ) ");
		if( filtroContato  != null && !filtroContato.equals("") )	where.append(" and contato LIKE '%"+filtroContato+"%'");
		if( filtroAtivo    != null && !filtroAtivo.equals("") )   	where.append(" and ativo = "+filtroAtivo);
		
		long totalEntradas = entity.getCount("1=1", conn);
		long totalEntradasFiltradas = 0;

		totalEntradasFiltradas = entity.getCount(where.toString(), conn);
		where.append(" ORDER BY "+fields[filtro]+" "+orderBy+" LIMIT "+ ultimoIndex +" , " + qntPorPagina);
		ArrayList<MailingEntity> mailingData = entity.getArray(where.toString(), conn);
		
		if(mailingData != null){
			int i = 0;
			JSONObject json = new JSONObject();
			json.put("sEcho", request.getParameter("sEcho"));
			json.put("iTotalRecords", totalEntradas);
			json.put("iTotalDisplayRecords", totalEntradasFiltradas);
			
			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();

			for(MailingEntity mailing :mailingData) {
				try{
					String color = "#2C3E50";
					if( mailing.getAtivo() == 0 ) color = "red";
					
					String linkEdit = "<a class='grouped_elements' style='color: " + color + "' href='mailingEdit.jsp?id=" + mailing.getIdMailing() + "'>";
					String status   = mailing.getAtivo() == 1 ? (String)p.get("ATIVO") : (String)p.get("INATIVO");
					
					ArrayList<String> innerArray = new ArrayList<String>();
					innerArray.add(linkEdit + mailing.getNome() + "</a>");
					innerArray.add(linkEdit + mailing.getEmail() + "<input type='hidden' name='userId' value='"+mailing.getIdMailing()+"'/></a>");
					innerArray.add(linkEdit + mailing.getTelefone1() + "</a>"); 
					innerArray.add(linkEdit + mailing.getContato() + "</a>");
					innerArray.add(linkEdit + status + "</a>");
					innerArray.add(linkEdit + (actionCell)
							.replaceAll("#ID",mailing.getIdMailing()+"")
							.replaceAll("#CE", color) 
					);
					array.add(innerArray);
					
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("ERRO AO PEGAR O MAILING: "+mailing.getIdMailing());
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