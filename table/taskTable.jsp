<%@page import="com.tetrasoft.data.cliente.TaskEntity"%>
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
	if( !usuarioLogado.semPermissaoAcesso(TaskEntity.ID_FUNCAO, "excluir") ) {		
		master = true;
	}
	
	String [] fields = new String[]
		{				
			"descricao ",
			"nome ",
			"dataHoraPrazo ",
			"dataHoraPrazoFinal "
		};
	
	SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	String numColTable = request.getParameter("iSortCol_0");
	
	
	String filtroDescricao = request.getParameter("filtroDescricao");	
	String filtroSala = request.getParameter("filtroSala");
	String filtroDataInicial 	= returnNotNull(request.getParameter("filtroDataInicial")).toString();
	String filtroDataFinal	 	= returnNotNull(request.getParameter("filtroDataFinal")).toString();	
	
	String aux = request.getParameter("pagina");
	String actionCell = "<a title='Editar' style='color: #CE; border-color: #CE; width: 16px; margin-bottom: 0px;margin-right:5px;' class='marginIcon btn btn-primary btn-circle grouped_elements' href='calendarioEdit.jsp?id=#ID'><i class='iconfa-edit'></i></a>";
	String reuniaoExcluir = "<a title='Excluir' style='width: 14px; height: 14px; color: #CE; border-color: #CE; width: 16px;margin-bottom: 0px;margin-right:5px;' class='marginIcon btn btn-primary btn-circle btn-excluirReuniao' href='calendarioEdit.jsp?id=#ID'><i style='margin-top:0px!important' class='iconfa-remove'></i></a>";
			   
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
		
		TaskEntity entity = new TaskEntity();
		conn = new UsuarioEntity().retrieveConnection();
		StringBuilder where = new StringBuilder(" 1=1 ");
	
		if(filtroDescricao != null && !filtroDescricao.equals(""))
			where.append(" and descricao LIKE '%"+filtroDescricao+"%'");
		
		if(filtroSala!= null && !filtroSala.equals("")){
			where.append(" and sr.nome LIKE '%"+ filtroSala +"%'");			
		}		
		
		if( filtroDataInicial != null && !filtroDataInicial.equals("") && filtroDataInicial.length() == 10 ){			
			
			try {
				SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy-MM-dd");
				Date d = TaskEntity.DATE_FORMAT1.parse(filtroDataInicial);
				where.append(" AND dataHoraPrazo >= '" + sdfTemp.format(d) + " 00:00:00'" );
				where.append(" AND dataHoraPrazo <= '" + sdfTemp.format(d) + " 23:59:59'" );
			} catch (Exception e) {
				
				System.out.println(e.getMessage());
				throw new RuntimeException(e.getMessage());
				
			}
		}
		
		if( filtroDataFinal != null && !filtroDataFinal.equals("") && filtroDataFinal.length() == 10 ){
			try {
				SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date d = TaskEntity.DATE_FORMAT1.parse(filtroDataFinal);
				where.append(" AND dataHoraPrazoFinal <= '" + sdfTemp.format(d) + "'" );
			} catch (Exception e) {
				
				System.out.println(e.getMessage());
				throw new RuntimeException(e.getMessage());
				
			}
		}
		
		long totalEntradas = entity.getCount("1=1", conn);
		long totalEntradasFiltradas = 0;		
		
		//filtro na tabela task
		if(filtroSala.equals("") && !numColTable.equals("1")){			
			totalEntradasFiltradas = entity.getCount(where.toString(), conn);
			where.append(" ORDER BY "+fields[filtro]+" "+orderBy+" LIMIT "+ ultimoIndex +" , " + qntPorPagina);		
		//filtro na tabela salaReuniao	
		}else{	
			
			totalEntradasFiltradas = entity.getCount(where.toString(), conn);			
			where.append(" AND idSala = sr.idSalaReuniao ORDER BY " + "sr.nome" +" " + orderBy);
			
		}	
		
		ArrayList<TaskEntity> taskData = null;		
		
		if((filtroSala == null || filtroSala.equals("")) && !numColTable.equals("1"))
			taskData = entity.getArray(where.toString(), conn);
		else			
			taskData = entity.getArray(new String[]{" salaReuniao sr "}, where.toString(), ultimoIndex+1, qntPorPagina, conn);		
					
		
		if(taskData != null){
			int i = 0;
			JSONObject json = new JSONObject();
			json.put("sEcho", request.getParameter("sEcho"));
			json.put("iTotalRecords", totalEntradas);
			json.put("iTotalDisplayRecords", totalEntradasFiltradas);
			
			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		
			for(TaskEntity task : taskData) {
				try{
					String color = "#2C3E50";
					SalaReuniaoEntity salaReuniaoEntity = new SalaReuniaoEntity();
					salaReuniaoEntity.getThis(" idSalaReuniao =" + task.getIdSala());
					
					String linkEdit = "<a class='grouped_elements editBtn' style='color: " + color + "' href='calendarioEdit.jsp?id=" + task.getIdTask() + "'>";
					ArrayList<String> innerArray = new ArrayList<String>();						
					innerArray.add(linkEdit + task.getDescricao() + "</a>");
					innerArray.add(linkEdit + salaReuniaoEntity.getNome() + "</a>");
					innerArray.add(linkEdit + formataData.format(task.getDataHoraPrazo())  + "</a>");
					innerArray.add(linkEdit + formataData.format(task.getDataHoraPrazoFinal())  + "</a>");				
					
					String botoesAcao = "";
					
					if(!usuarioLogado.semPermissaoAcesso(909, "alterar"))						
						botoesAcao = (linkEdit + actionCell).replaceAll("#ID",task.getIdTask()+"").replaceAll("#CE", color);				
						
					if(!usuarioLogado.semPermissaoAcesso(909, "excluir") )
						botoesAcao += (linkEdit + reuniaoExcluir).replaceAll("#ID",task.getIdTask()+"").replaceAll("#CE", color);			
					
					innerArray.add(botoesAcao);
					
					array.add(innerArray);
					
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("ERRO ao buscar a sala Reuni�o: "+ task.getIdTask());
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