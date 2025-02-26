<%@page import="com.tetrasoft.data.finance.SalaReuniaoEntity"%>
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
	boolean master = false;
	if( !usuarioLogado.semPermissaoAcesso(SalaReuniaoEntity.ID_FUNCAO, "excluir") ) {		
		master = true;
	}
	
	String [] fields = new String[]
		{
				
			"nome",
			"capacidade"
		};	
	
	String filtroNome = request.getParameter("filtroNome");	
	String filtroCapacidade = request.getParameter("capacidade");
	String aux = request.getParameter("pagina");
	String actionCell = "<a title='Editar' style='color: #CE; border-color: #CE; width: 16px; margin-bottom: 0px;margin-right:5px;' class='marginIcon btn btn-primary btn-circle grouped_elements' href='salaReuniaoEdit.jsp?id=#ID'><i class='iconfa-edit'></i></a>";
	String calendarioSala = "<a title='Calendário' style='width: 14px; height: 14px; color: #CE; border-color: #CE; width: 16px;margin-bottom: 0px;margin-right:5px;' class='marginIcon btn btn-primary btn-circle callCalendario' href='painel.jsp?id=#ID'><i style='margin-top:0px!important' class='icon-calendar'></i></a>";	
	String reuniaoList = "<a title='Reuniões' style='width: 14px; height: 14px; color: #CE; border-color: #CE; width: 16px;margin-bottom: 0px;margin-right:5px;' class='marginIcon btn btn-primary btn-circle' href='reunioesList.jsp?v=" +  System.currentTimeMillis()  +  "&urlGoogleCalendar=#URLGOOGLE"  +  "&id=#ID' target='_blank'><i style='margin-top:0px!important' class='icon-tasks'></i></a>";
	
		   
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
	
	
	String orderBy = (request.getParameter("sSortDir_0") == null ? " asc ": request.getParameter("sSortDir_0"));
	
	Connection conn = null;
	
	try{
		
		SalaReuniaoEntity entity = new SalaReuniaoEntity();
		conn = new UsuarioEntity().retrieveConnection();
		StringBuilder where = new StringBuilder(" 1=1 ");
	
		if(filtroNome != null && !filtroNome.equals(""))
			where.append(" and nome LIKE '%"+filtroNome+"%'");
		
		if(filtroCapacidade != null && !filtroCapacidade.equals(""))
			where.append(" and capacidade LIKE '%"+filtroCapacidade+"%'");
		
		
		long totalEntradas = entity.getCount("1=1", conn);
		long totalEntradasFiltradas = 0;

		totalEntradasFiltradas = entity.getCount(where.toString(), conn);
		where.append(" ORDER BY "+fields[filtro]+" "+orderBy+" LIMIT "+ ultimoIndex +" , " + qntPorPagina);
		ArrayList<SalaReuniaoEntity> salaReuniaoData = entity.getArray(where.toString(), conn);
		if(salaReuniaoData != null){
			int i = 0;
			JSONObject json = new JSONObject();
			json.put("sEcho", request.getParameter("sEcho"));
			json.put("iTotalRecords", totalEntradas);
			json.put("iTotalDisplayRecords", totalEntradasFiltradas);
			
			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
			for(SalaReuniaoEntity salaReuniao : salaReuniaoData) {				
				
				try{
					String color = "#2C3E50";
					if( salaReuniao.getAtivo() == 0 ) color = "red";
					
					String linkEdit = "<a class='grouped_elements editBtn' style='color: " + color + "' href='salaReuniaoEdit.jsp?id=" + salaReuniao.getIdSalaReuniao() + "'>";
					ArrayList<String> innerArray = new ArrayList<String>();						
					innerArray.add(linkEdit + salaReuniao.getNome() + "</a>");	
					innerArray.add(linkEdit + salaReuniao.getCapacidade() + "</a>");
					innerArray.add(linkEdit + salaReuniao.getNumero() + "</a>");					
					
					if( usuarioLogado.semPermissaoAcesso(907, "alterar") ) {
						innerArray.add(linkEdit + (calendarioSala + reuniaoList)
								.replaceAll("#ID",salaReuniao.getIdSalaReuniao()+"")
								.replaceAll("#CE", color)
								.replaceAll("#URLGOOGLE", salaReuniao.getGoogleCalendarKey()+ "")
								
						);
						
					} else {
						innerArray.add(linkEdit + (actionCell + calendarioSala + reuniaoList)
								.replaceAll("#ID",salaReuniao.getIdSalaReuniao()+"")
								.replaceAll("#CE", color) 
								.replaceAll("#URLGOOGLE", salaReuniao.getGoogleCalendarKey()+ "")
						);
					}			
					
					array.add(innerArray);
					
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("ERRO ao buscar a sala de reunião: "+salaReuniao.getIdSalaReuniao());
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