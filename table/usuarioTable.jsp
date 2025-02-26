<%@page import="com.tetrasoft.data.usuario.PerfilAcessoEntity"%>
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
			"login",
			"nome",
			"idPerfil",
			"ativo"
		};

	String filtroNome 	= request.getParameter("filtroNome");	
	String filtroIdUsuario = request.getParameter("filtroIdUsuario");
	String filtroLogin 	= request.getParameter("filtroLogin");			
	//String filtroEmail 	= request.getParameter("filtroEmail");
	String filtroAtivo	= request.getParameter("filtroAtivo");
	String aux			= request.getParameter("pagina");
	String actionCell 	= "<a title='"+p.get("EDITAR")+"' style='width: 16px; margin-bottom: 0px;margin-right:5px;' class='btn btn-primary btn-circle grouped_elements' href='adminUsuarioDadosPessoais.jsp?idUsuario=#IDUSUARIO'><i class='iconfa-edit'></i></a>";
	String actionCellExcluir = "<a title='"+p.get("ATIVAR") + " / " + p.get("INATIVAR")+"' style='width: 16px; margin-bottom: 0px;margin-right:5px;' class='btn btn-primary btn-circle' href='javascript:inativarUsuario2(#IDUSUARIO, formX, adminUsuarioTable)'><i class='iconfa-remove'></i></a>";
	
		
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
	
		UsuarioEntity entity = new UsuarioEntity();
		conn = entity.retrieveConnection();
		
		String where = "1=1";
			
		if(filtroNome != null && !filtroNome.equals("")){
			where += " and nome LIKE '%"+filtroNome+"%'";
		}
		/*if(filtroEmail != null && !filtroEmail.equals("")){
			where += " and email LIKE '%"+filtroEmail+"%'";
		}*/
		if(filtroLogin != null && !filtroLogin.equals("")){
			where += " and login LIKE '"+filtroLogin+"%'";
		}
		if(filtroAtivo != null && !filtroAtivo.equals("") && !filtroAtivo.equals("blank")){
			where += " and ativo ="+filtroAtivo;
		}
		if(filtroIdUsuario != null && !filtroIdUsuario.equals("")){
			where += " and idUsuario = "+filtroIdUsuario;
		}
		
		long totalEntradas = entity.getCount("1=1", conn);
		
		long totalEntradasFiltradas = entity.getCount(where, conn);

		where = where +"  ORDER BY "+fields[filtro]+" "+orderBy+" LIMIT "+ ultimoIndex +" , " + qntPorPagina;
	
		ArrayList<UsuarioEntity> a = entity.getArray(where, conn);
		if(a!=null){
			int i = 0;
			String login = "";
			String nome = "";
			String perfil = "";
			String ativo = "";
			
			JSONObject json = new JSONObject();
			json.put("sEcho", request.getParameter("sEcho"));
			json.put("iTotalRecords", totalEntradas);
			json.put("iTotalDisplayRecords", totalEntradasFiltradas);
			
			PerfilAcessoEntity perfilAcesso = new PerfilAcessoEntity();
			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
			
			for(UsuarioEntity usuario : a) {
				try{
					String f  = "";
					String f_ = "";
					if( usuario.getAtivo() == 0 ) {
						f  = "<font color='red'>";
						f_ = "</font>";
					}
					
					perfilAcesso.setIdPerfilAcesso( usuario.getIdPerfil() );
					perfilAcesso.getThis(conn);
					
					login = usuario.getLogin();
					nome = usuario.getNome() == null || usuario.getNome().equals("") ? "Sem Nome" : usuario.getNome();
					perfil = perfilAcesso.getNome();
					ativo = UsuarioEntity.ATIVO.get(usuario.getAtivo());
					
					ArrayList<String> innerArray = new ArrayList<String>();
					innerArray.add(f + login+"<input type='hidden' name='userId' value='"+usuario.getIdUsuario()+"'/>" + f_);
					innerArray.add(f + nome + f_);
					innerArray.add(f + perfil + f_);
					innerArray.add(f + ativo + f_);
					//innerArray.add(actionCell.replace("#IDUSUARIO", usuario.getIdUsuario().toString()));
					
					String botoesAcao = "";
					
					if(!usuarioLogado.semPermissaoAcesso(400, "alterar") )						
						botoesAcao = actionCell.replace("#IDUSUARIO", usuario.getIdUsuario().toString());											
					
					if(!usuarioLogado.semPermissaoAcesso(400, "excluir") )
						botoesAcao += actionCellExcluir.replace("#IDUSUARIO", usuario.getIdUsuario().toString());					
					
					innerArray.add(botoesAcao);
					
					array.add(innerArray);
					
				}catch(Exception e){
					System.out.println("ERRO AO PEGAR O USUARIO: "+usuario.getIdUsuario());
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