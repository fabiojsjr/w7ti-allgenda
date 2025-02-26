<%@page import="com.tetrasoft.data.usuario.PerfilAcessoEntity"%>
<%@page contentType="text/html; charset=ISO-8859-1"%>
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
			"nome"
		};

	String filtroNome 	= request.getParameter("filtroNome");	
	String aux			= request.getParameter("pagina");
	String actionCell 	= "<a title='" + p.get("ALTERAR") + "' style='width: 16px; margin-bottom: 0px;margin-right:5px;' class='btn btn-primary btn-circle grouped_elements' href='adminPerfilEdit.jsp?idPerfil=#IDPERFIL'><i class='iconfa-edit'></i></a>";
		
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
		PerfilAcessoEntity entity = new PerfilAcessoEntity();
		conn = entity.retrieveConnection();
		
		String where = "1=1";
			
		if(filtroNome != null && !filtroNome.equals("")){
			where += " and nome LIKE '%"+filtroNome+"%'";
		}
		
		long totalEntradas = entity.getCount("1=1", conn);
		
		long totalEntradasFiltradas = entity.getCount(where, conn);

		where = where +"  ORDER BY "+fields[filtro]+" "+orderBy+" LIMIT "+ ultimoIndex +" , " + qntPorPagina;
	
		ArrayList<PerfilAcessoEntity> a = entity.getArray(where, conn);
		if(a!=null){
			int i = 0;
			String nome = "";
			
			JSONObject json = new JSONObject();
			json.put("sEcho", request.getParameter("sEcho"));
			json.put("iTotalRecords", totalEntradas);
			json.put("iTotalDisplayRecords", totalEntradasFiltradas);
			
			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
			
			for(PerfilAcessoEntity perfil : a) {
				try{
					nome = perfil.getNome() == null || perfil.getNome().equals("") ? "Sem Nome" : perfil.getNome();
					
					ArrayList<String> innerArray = new ArrayList<String>();
					innerArray.add(nome);
					
					if( usuarioLogado.semPermissaoAcesso(410, "alterar") ) {
						innerArray.add("");
					} else {
						innerArray.add(actionCell.replace("#IDPERFIL", perfil.getIdPerfilAcesso().toString()));
					}
					
					array.add(innerArray);
					
				}catch(Exception e){
					System.out.println("ERRO AO PEGAR O PERFIL: "+entity.getIdPerfilAcesso());
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