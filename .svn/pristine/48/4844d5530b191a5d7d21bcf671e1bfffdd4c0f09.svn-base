<?xml version='1.0' encoding='ISO-8859-1'?>

<%@ page language="java" contentType="text/xml; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.data.cliente.TaskEntity"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.File"%>

<%
	String sala = "";
	if( request.getParameter("sala") != null ) {
		sala = "where idSala = " + request.getParameter("sala") ;
	}

	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime( new Date() );
//	cal.add( Calendar.DATE, dias*-1 );

    StringBuffer retorno = new StringBuffer("");

    Connection conn = null;
    Statement  dbSt = null;
    ResultSet  dbRs = null;

    try {
    		TaskEntity	 task = new TaskEntity();
//        UserComplementoEntity	userComp  = new UserComplementoEntity();
//        TimeSheetEntity 			timesheet = new TimeSheetEntity();

        conn = task.retrieveConnection();
        dbSt = conn.createStatement();
        
        Date dataLimite = cal.getTime();
        
        StringBuffer xmlFile  = new StringBuffer();
//        xmlFile.append("<?xml version='1.0' encoding='ISO-8859-1'?>\n");
        xmlFile.append("<lista sala=\"" + request.getParameter("sala") + "\">\n");
        
//        ArrayList a = task.getArraySelect("select * from task " + sala

        ArrayList a = task.getArraySelect("select * from task " + sala + " AND NOW() BETWEEN dataHoraPrazo AND dataHoraPrazoFinal ORDER BY dataHoraPrazo ASC LIMIT 1" );

        if ( a.size() > 0 ) {
	        	for( int i = 0; i < a.size(); i++ ) {
	        		task = (TaskEntity)a.get(i);
	        		xmlFile.append("   <task>\n");
	        		xmlFile.append("      <titulo>REUNIÃO ATUAL</titulo>\n");
	        		xmlFile.append("      <id>" 		+ task.getIdTask() + "</id>\n");
	        		xmlFile.append("      <idSala>" 	+ task.getIdSala() + "</idSala>\n");
	        		xmlFile.append("      <nomeSala>" 	+ task.getIdTask() + "</nomeSala>\n");
	        		xmlFile.append("      <solicitante>" 	+ task.getSolicitante() + "</solicitante>\n");
	        		xmlFile.append("      <participantes>" 	+ task.getParticipantes() + "</participantes>\n");
	        		xmlFile.append("      <nomesParticipantes>" 	+ task.getNomesParticipantes() + "</nomesParticipantes>\n");
	        		xmlFile.append("      <descricao>" 	+ task.getDescricao() + "</descricao>\n");
	        		xmlFile.append("      <data>" 	+ task.getDataHoraPrazo() + "</data>\n");
	        		xmlFile.append("      <horaInicio>" 	+ task.getDataHoraPrazo() + "</horaInicio>\n");
	        		xmlFile.append("      <horaFinal>" 	+ task.getDataHoraPrazoFinal() + "</horaFinal>\n");
	        		xmlFile.append("   </task>\n");
	        	}
       	} else {
	        	xmlFile.append("   <task>\n");
	        	xmlFile.append("      <titulo>NENHUMA REUNIÃO AGEDADA</titulo>\n");
	        	xmlFile.append("      <id></id>\n");
	        	xmlFile.append("      <idSala> </idSala>\n");
	        	xmlFile.append("      <nomeSala> </nomeSala>\n");
	        	xmlFile.append("      <solicitante> </solicitante>\n");
	        	xmlFile.append("      <participantes> </participantes>\n");
	        	xmlFile.append("      <nomesParticipantes> </nomesParticipantes>\n");
	        	xmlFile.append("      <descricao> </descricao>\n");
	        	xmlFile.append("      <data> </data>\n");
	        	xmlFile.append("      <horaInicio> </horaInicio>\n");
	        	xmlFile.append("      <horaFinal> </horaFinal>\n");
	        	xmlFile.append("   </task>\n");
        }
        
        
        
        a = task.getArraySelect("select * from task " + sala + " AND dataHoraPrazo >= NOW() ORDER BY dataHoraPrazo ASC LIMIT 6");
        
        int count = 0 ;
        if ( a.size() > 0 ) {
	        	for( int i = 0; i < a.size(); i++ ) {
	        		
	        		count =  i ;
	        		
	        		task = (TaskEntity)a.get(i);
	        		xmlFile.append("   <task>\n");
	        		xmlFile.append("      <titulo>PRÓXIMA REUNIÃO</titulo>\n");
	        		xmlFile.append("      <id>" 		+ task.getIdTask() + "</id>\n");
	        		xmlFile.append("      <idSala>" 	+ task.getIdSala() + "</idSala>\n");
	        		xmlFile.append("      <nomeSala>" 	+ task.getIdTask() + "</nomeSala>\n");
	        		xmlFile.append("      <solicitante>" 	+ task.getSolicitante() + "</solicitante>\n");
	        		xmlFile.append("      <participantes>" 	+ task.getParticipantes() + "</participantes>\n");
	        		xmlFile.append("      <nomesParticipantes>" 	+ task.getNomesParticipantes() + "</nomesParticipantes>\n");
	        		xmlFile.append("      <descricao>" 	+ task.getDescricao() + "</descricao>\n");
	        		xmlFile.append("      <data>" 	+ task.getDataHoraPrazo() + "</data>\n");
	        		xmlFile.append("      <horaInicio>" 	+ task.getDataHoraPrazo() + "</horaInicio>\n");
	        		xmlFile.append("      <horaFinal>" 	+ task.getDataHoraPrazoFinal() + "</horaFinal>\n");
	        		xmlFile.append("   </task>\n");
	        	}
        }
        
        
    		for( int j = count; j < 6; j++ ) {
        
     	xmlFile.append("   <task>\n");
	    	xmlFile.append("      <titulo>NENHUMA REUNIÃO AGEDADA</titulo>\n");
	    	xmlFile.append("      <id></id>\n");
	    	xmlFile.append("      <idSala> </idSala>\n");
	    	xmlFile.append("      <nomeSala> </nomeSala>\n");
	    	xmlFile.append("      <solicitante> </solicitante>\n");
	    	xmlFile.append("      <participantes> </participantes>\n");
	    	xmlFile.append("      <nomesParticipantes> </nomesParticipantes>\n");
	    	xmlFile.append("      <descricao> </descricao>\n");
	    	xmlFile.append("      <data> </data>\n");
	    	xmlFile.append("      <horaInicio> </horaInicio>\n");
	    	xmlFile.append("      <horaFinal> </horaFinal>\n");
	    	xmlFile.append("   </task>\n");
        }
        
        
       	xmlFile.append("</lista>");
       	
       	out.print( xmlFile.toString() );
    		
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
    	try {
    		conn.close();
    		dbSt.close();
    		dbRs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
%>
