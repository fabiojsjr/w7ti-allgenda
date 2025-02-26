<html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="com.tetrasoft.data.cliente.TaskEntity"%>
<%@page import="com.tetrasoft.data.finance.SalaReuniaoEntity"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>

<head>
	<meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="refresh" content="50">
    
	 <link rel="stylesheet" href="css/reset.css"/>	
	 <style>
	 
	 		@font-face {
		     font-family: RobotoRegular;
		     src: url('fonts/roboto/Roboto-Regular-webfont.html');
			}

            body, td, th{

                font-family: RobotoRegular, "Helvetica Neue", Helvetica, sans-serif;;                           
            }

            .wrapper{
                box-shadow: 0px 5px 10px rgba(0,0,0,.5);                               
            }

            .container{
                width: 100%;
                padding: 1%;                
            }

            .container-main{
                margin: 0;
                padding: 0;

            }
            img{
                display: block;
                max-width: 100%;
            }

            .logo{  
                width: 230px;
                margin-top: -30px;             
                float: right;                              
            }

            .container-head, .list-meeting-main, .list-second-meeting{                
                overflow: auto;
            }

            .list-second-meeting{
                color: #000;
                min-height: 25vh;
            }
            
            .reuniaoDescricao2{
            	line-height: 24px;
            }

            .list-second-meeting{
                line-height: 1.4rem;
            }

            .meeting-second-data{
                font-weight: bold;
            }

            .list-meeting-main{
                line-height: 2rem;
                overflow: hidden;
            }

            .container-head{
                padding: 1%;
            }

            .container-head-left{

                float: left;
                width: 50%;
            }

            .container-head-left h3{
                font-size: 2.5rem;
            }     

            .meeting-main{
                float: left;
                width: 50%;
                color: #FFF;
                font-size: 1.5rem;
                height: 50vh;
            }    

            .meeting-main-head{
                overflow: auto;
            }   

            .meeting-main-head > p:first-child{
                float:left;
            } 

            .meeting-data{
                float:right;
                overflow: hidden;
            }

            .meeting-main-content{
                margin-top: 5%;  
                font-size: 1.2rem;                          
            }      

            .meeting-main:nth-child(1){
                width: 55%;
                background-color: #2c3e50;
                padding: 2%;
            }

            .meeting-main:nth-child(2){
                width: 45%;
                background-color: #425262;
                padding: 2%;
            }   

            .second-meeting{
                float: left;
                width: 20%;
                padding: 2%;
                min-height: inherit;
            }

            .second-meeting:nth-child(1){
                background-color: rgb(137,137,139);
            }

            .second-meeting:nth-child(2){
                background-color: rgb(147,147,148);
            }

            .second-meeting:nth-child(3){
                background-color: rgba(169,169,171);
            }

            .second-meeting:nth-child(4){
                background-color: rgba(180,180,182);
            }

            .second-meeting:nth-child(5){
                background-color: rgba(190,190,192);
            }

            @media(max-width:478px){
                .container-head-left{
                    float:left;
                    
                }        
            }     

            @media(max-width:550px){
                .meeting-main,.second-meeting{
                    float: none;
                    width: auto!important;
                }      

                .logo{
                    float:left;
                    width: 230px;
                    margin-top: 2px;
                }         
            }  

            @media(max-width:730px){
                .meeting-data{
                    float: left;
                    width: 100%;
                }        
            }
                                
        </style>

</head>

<%@ include file="idioma.jsp" %>
<%@ include file="methods.jsp" %>

<body id="body" onload="toggleFullScreen()"> 

<%	
	int idFunc = SalaReuniaoEntity.ID_FUNCAO;
	
	try {
		
		SimpleDateFormat dataFormatTeste = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		List<TaskEntity> reuniaoAtual = null;
		List<TaskEntity> proximasReunioes = null;
		
		
		SimpleDateFormat formatData2 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatData = new SimpleDateFormat("HH:mm");
		String idSalaReuniao = request.getParameter("id");
		String urlGoogleCalendar = request.getParameter("urlGoogleCalendar");
		List<TaskEntity> listReunioes = new ArrayList<TaskEntity>();
		SalaReuniaoEntity salaReuniao = new SalaReuniaoEntity();
		TaskEntity task = new TaskEntity();
		
		//verifica se o id da sala est� na url
		if(idSalaReuniao != null && !idSalaReuniao.equals("")){
			
			//busca a sala de reuniao
			salaReuniao.getThis(" idSalaReuniao =" + idSalaReuniao);	
			
			//recupera a reuni�o que est� ocorrendo no momento
			reuniaoAtual = task.get(" idSala=" + idSalaReuniao + " AND NOW() BETWEEN dataHoraPrazo AND dataHoraPrazoFinal ORDER BY dataHoraPrazo ASC LIMIT 1");
			
			//se estiver uma reuniao ocorrendo no momento pega as outras reunioes seguidas dessa
			if(reuniaoAtual != null && reuniaoAtual.size() > 0)
				proximasReunioes = task.get(" idSala=" + idSalaReuniao + " AND dataHoraPrazo > '" +  reuniaoAtual.get(0).getDataHoraPrazo() + "' ORDER BY dataHoraPrazo ASC LIMIT 6");
			//caso n�o tenha nenhuma reuni�o acontecendo no momento, pega as reuni�es depois do horario atual
			else
				proximasReunioes = task.get(" idSala=" + idSalaReuniao + " AND dataHoraPrazo >= NOW() ORDER BY dataHoraPrazo ASC LIMIT 6");
			
			//verifica se tem reuni�o no momento atual
			//se tivet sta a reuniao na lista, se n�o tiver sena a primeira reuni�o como null
			if(reuniaoAtual != null && reuniaoAtual.size() > 0)
				listReunioes.add(reuniaoAtual.get(0));
			else
				listReunioes.add(null);
			
			
			//coloca todas as reuni�es no array de reuni�es
			if(proximasReunioes.size() > 0){
				
				for(TaskEntity ob : proximasReunioes){
					listReunioes.add(ob);
				}				
			}		
		}		
%>
 <div class="container">
          <div class="wrapper">
              <div class="container-head">
                  <div class="container-head-left">
                     <h3><%=salaReuniao.getNome()%></h3>
                   	 <p style="display:none" class="idSalaReuniao"><%=salaReuniao.getIdSalaReuniao()%></p>
					 <small>Reuni&otilde;es marcadas.</small>
                  </div>
                  <a href="painel.jsp">
                  	<img src="images/logo/logo2.png" class="logo" alt="logo"/>
                  </a>                               
              </div>
              <div class="container-main">
                  <div class="list-meeting-main">
                      <div class="meeting-main">
                          <div class="meeting-main-head">
                              <p>Reuni�o atual</p>
                              <%if(listReunioes != null && listReunioes.size() > 0 && listReunioes.get(0) != null){%>
								<div class="meeting-data">
									<p><%= formatData2.format(listReunioes.get(0).getDataHoraPrazo())%></p>
									<p><%= formatData.format(listReunioes.get(0).getDataHoraPrazo())%> - <%= formatData.format(listReunioes.get(0).getDataHoraPrazoFinal())%></p>
								</div>						 																		
							  <%}%>         
						   </div>           
                          <div class="meeting-main-content">
                          	<%if(listReunioes != null && listReunioes.size() > 0 && listReunioes.get(0) != null){%>						
								<p><%= listReunioes.get(0).getSolicitante() == null ? "" : "Solicitante: " + listReunioes.get(0).getSolicitante() %></p>
								<p><%= listReunioes.get(0).getNomesParticipantes() == null ? "" : "Participantes: " %></p>	
								<p><%= listReunioes.get(0).getNomesParticipantes() == null ? "" : task.separarNomes(listReunioes.get(0).getNomesParticipantes())%></p>
								<p><%= listReunioes.get(0).getDescricao() == null ? "" : listReunioes.get(0).getDescricao()%></p>						  												
							<%}else{%>
								<p>Nenhuma reuni&atilde;o marcada.</p>
							<%}%>
						</div>                 
                      </div>
                      <div class="meeting-main">
                              <div class="meeting-main-head">
                                  <p>Pr�xima reuni�o</p>
                                  <%if(listReunioes != null && listReunioes.size() > 1 && listReunioes.get(1) != null){%>
	                                  <div class="meeting-data">                                      
											<div>
												<p><%= formatData2.format(listReunioes.get(1).getDataHoraPrazo())%></p>
												<p><%= formatData.format(listReunioes.get(1).getDataHoraPrazo())%> - <%= formatData.format(listReunioes.get(1).getDataHoraPrazoFinal())%></p>
											</div>
									  </div>																		
								<%}%>                                                                  
                              </div>
                              <div class="meeting-main-content">
                                  <%if(listReunioes != null && listReunioes.size() > 1 && listReunioes.get(1) != null){%>						
									<p><%= listReunioes.get(1).getSolicitante() == null ? "" : "Solicitante: "  + listReunioes.get(1).getSolicitante()%></p>
									<p><%= listReunioes.get(1).getNomesParticipantes() == null ? "" : "Participantes: " %></p>		
									<%= listReunioes.get(1).getNomesParticipantes() == null ? "" : task.separarNomes(listReunioes.get(1).getNomesParticipantes())%>
									<p><%= listReunioes.get(1).getDescricao() == null ? "" : listReunioes.get(1).getDescricao()%></p>										
								<%}else{%>	
									<p>Nenhuma reuni&atilde;o marcada.</p>
								<%}%>		
                              </div>
                          </div>
                  	</div>
                  <div class="list-second-meeting">
                      <div class="second-meeting">                                                       
                       <%if(listReunioes != null && listReunioes.size() > 2 && listReunioes.get(2) != null){%>
                          <div class="meeting-second-data">	
							<p><%= formatData2.format(listReunioes.get(2).getDataHoraPrazo())%></p>
							<p><%= formatData.format(listReunioes.get(2).getDataHoraPrazo())%> - <%= formatData.format(listReunioes.get(2).getDataHoraPrazoFinal())%></p>	
					  	  </div>
						  <div class="meeting-second-content">
	                      	<p><%= listReunioes.get(2).getSolicitante() == null ? "" : "Solicitante: " + listReunioes.get(2).getSolicitante()%></p>
	                      </div>       																				
						<%}else{%>
							<p>Nenhuma reuni&atilde;o marcada.</p>
						<%}%>                     
                      </div> 
                      <div class="second-meeting">
                      	<%if(listReunioes != null && listReunioes.size() > 3 && listReunioes.get(3) != null){%>
                          	<div class="meeting-second-data">                      	
							<p><%= formatData2.format(listReunioes.get(3).getDataHoraPrazo())%></p>										
							<p><%= formatData.format(listReunioes.get(3).getDataHoraPrazo())%> - <%= formatData.format(listReunioes.get(3).getDataHoraPrazoFinal())%></p>
						</div>	
						<div class="meeting-second-content">
                          		<p><%= listReunioes.get(3).getSolicitante() == null ? "" : "Solicitante: " + listReunioes.get(3).getSolicitante()%></p>
                          	</div>  																				
					<%}else{%>
						<p class="reuniaoDescricao2">Nenhuma reuni&atilde;o marcada.</p>
					<%}%>                          
                      </div>      
                      <div class="second-meeting">                                                      
                       <%if(listReunioes != null && listReunioes.size() > 4 && listReunioes.get(4) != null){%>
                           <div class="meeting-second-data">
							<p><%= formatData2.format(listReunioes.get(4).getDataHoraPrazo())%></p>										
							<p><%= formatData.format(listReunioes.get(4).getDataHoraPrazo())%> - <%= formatData.format(listReunioes.get(4).getDataHoraPrazoFinal())%></p>										
						</div>
						<div class="meeting-second-content">
                          		<p><%= listReunioes.get(4).getSolicitante() == null ? "" : "Solicitante: " +  listReunioes.get(4).getSolicitante()%></p>    
                          	</div>																			
						<%}else{%>
							<p class="reuniaoDescricao2">Nenhuma reuni&atilde;o marcada.</p>
						<%}%>                           
                      </div>      
                      <div class="second-meeting">
                      	<%if(listReunioes != null && listReunioes.size() > 5 && listReunioes.get(5) != null){%>
                          	<div class="meeting-second-data">                      	
							<p><%= formatData2.format(listReunioes.get(5).getDataHoraPrazo())%></p>										
							<p><%= formatData.format(listReunioes.get(5).getDataHoraPrazo())%> - <%= formatData.format(listReunioes.get(5).getDataHoraPrazoFinal())%></p>
						</div>	
						 <div class="meeting-second-content">
                               <p><%= listReunioes.get(5).getSolicitante() == null ? "" : "Solicitante: " +  listReunioes.get(5).getSolicitante()%></p>
                           </div>   																							
					<%}else{%>
						<p class="reuniaoDescricao2">Nenhuma reuni&atilde;o marcada.</p>
					<%}%>                   
                      </div>      
                      <div class="second-meeting">
                      	<%if(listReunioes != null && listReunioes.size() > 6 && listReunioes.get(6) != null){%>
                          	<div class="meeting-second-data">                      	
								<p><%= formatData2.format(listReunioes.get(6).getDataHoraPrazo())%></p>										
								<p><%= formatData.format(listReunioes.get(6).getDataHoraPrazo())%> - <%= formatData.format(listReunioes.get(6).getDataHoraPrazoFinal())%></p>
							</div>
							<div class="meeting-second-content">
                              	<p><%= listReunioes.get(6).getSolicitante() == null ? "" : "Solicitante: " + listReunioes.get(6).getSolicitante()%></p>
                          	</div>																										
					<%}else{%>
						<p>Nenhuma reuni&atilde;o marcada.</p>
					<%}%>          
                      </div>                         
                  </div>
              </div> 
          </div>         
      </div>
        
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/moment.js"></script>
<script>
	
	var queryURL = window.location.href.substring(window.location.href.indexOf('urlGoogleCalendar'),window.location.href.indexOf('id')-1);
	var querySplit = queryURL.split("=");	
	var urlCalendar = querySplit[1];

	var API_KEY = '<%=salaReuniao.getGoogleApiKey()%>';
	var CLIENT_ID = '<%=salaReuniao.getGoogleClientKey()%>';
	var CALENDAR_ID = '<%=salaReuniao.getGoogleCalendarKey()%>';
	var SCOPES = 'https://www.googleapis.com/auth/calendar';
	var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"];		
		
	function handleClientLoad() {
		if(urlCalendar != 'null' && urlCalendar != 'undefined')
	   	 	gapi.load('client:auth2', initClient);
	}	
	
	
	function initClient() {
	    gapi.client.init({
	      apiKey: API_KEY,
	      clientId: CLIENT_ID,
	      discoveryDocs: DISCOVERY_DOCS,
	      scope: SCOPES
	    }).then(function (response) {          
	      
	    	//verifica se esta  autorizado pela api    
	      if(!gapi.auth2.getAuthInstance().isSignedIn.Ab)
	    	  handleAuthClick();
	     
	    }, function(error) {
	      console.log(JSON.stringify(error, null, 2));
	    });
	  } 
	  
	function listGoogleCalendarEvents() {
	    gapi.client.init({
	        apiKey: API_KEY,
	        clientId: CLIENT_ID,
	        discoveryDocs: DISCOVERY_DOCS,
	        scope: SCOPES
	    }).then(function () {
	    
	    var request = gapi.client.calendar.events.list({
	        'calendarId': CALENDAR_ID                
	    });     
	
	    request.execute(function(events) {
	         
	        if (typeof(Storage) !== "undefined")        	  
	        	localStorage.setItem("listGoogleCalendar", JSON.stringify(events.items));            	
	    	else
	    	  alert("Erro ao buscar os dados do Google Calendar");        	
	    });      
	
		}, function(error) {
		  console.log(JSON.stringify(error, null, 2));
		});
	} 
  
  //chama a tela de autoriza��o
  function handleAuthClick(event) {
    gapi.auth2.getAuthInstance().signIn();
  }
  
  //desloga as credenciais
  function handleSignoutClick(event) {
    gapi.auth2.getAuthInstance().signOut();
  }   
  
</script>

<script>	
	
	//grava os eventos no banco de dados
	//com.tetrasoft.web.finance.EventosServlet
	function saveEventosGoogle(){
		
		var eventos;	
		
		if(urlCalendar != 'null' && urlCalendar != 'undefined'){
			
			setTimeout(function(){
				
				//js/google-calendar-api.js
				listGoogleCalendarEvents();
				
				var myData = {};
				
				eventos = localStorage.getItem("listGoogleCalendar");
				
				if(eventos != null && eventos != 'undefined')
					eventos = JSON.parse(eventos);
				
				localStorage.removeItem("listGoogleCalendar");
				
				$(eventos).each(function(i,e){
					debugger;
					myData.dataInicial = formatDataGoogleCalendar(e.start.dateTime);
					myData.dataFinal = formatDataGoogleCalendar(e.end.dateTime);			
					myData.idGoogle = e.id;
					myData.sala = <%=idSalaReuniao%>;
					myData.descricao = e.summary;
					myData.solicitante = e.creator.email;	
					//myData.descricao = e.description;
					
					$.ajax({
						url: 'eventosServlet',
						type: 'POST',
						data: myData,
						success: function(response){
							console.log(response);
						},error: function(error,xhr,throwable){
							console.log(error);
						}
					});			
				});			
				
			},2000);			
		}					
	}
	
	//formata a data retornada da API google calendar
	function formatDataGoogleCalendar(data){
		
		var dataInicialSplit = data.split('T');
		var horaInicialSplit = dataInicialSplit[1].split("-"); 
		var data = dataInicialSplit[0];
		var hora = horaInicialSplit[0]; 
		
		return data + 'T' + hora;		
	}
	
	$(document).ready(function(){		
		
		saveEventosGoogle();		
	});
	
//	//recarrega a p�gina a cada 30 segundos
//	setTimeout( function() {window.location = window.location.href;},3000000);
	
//	openFullscreen() ;
//	
//	var elem = document.documentElement;
//	function openFullscreen() {
//		
//		alert('teste');
//		  if (elem.requestFullscreen) {
//			  alert('teste1');
//		    elem.requestFullscreen();
//		  } else if (elem.mozRequestFullScreen) { /* Firefox */
//			  alert('teste2');
//		    elem.mozRequestFullScreen();
//		  } else if (elem.webkitRequestFullscreen) { /* Chrome, Safari & Opera */
//			  alert('teste3');
//		    elem.webkitRequestFullscreen();
//		  } else if (elem.msRequestFullscreen) { /* IE/Edge */
//			  alert('teste4');
//		    elem.msRequestFullscreen();
//		  }
//	}
//	
	
	function toggleFullScreen() {
		
		
		  if ((document.fullScreenElement && document.fullScreenElement !== null) ||    
		   (!document.mozFullScreen && !document.webkitIsFullScreen)) {
		    if (document.documentElement.requestFullScreen) {  
		      document.documentElement.requestFullScreen();  
		    } else if (document.documentElement.mozRequestFullScreen) {  
		      document.documentElement.mozRequestFullScreen();  
		    } else if (document.documentElement.webkitRequestFullScreen) {  
		      document.documentElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);  
		    }  
		  } else {  
		    if (document.cancelFullScreen) {  
		      document.cancelFullScreen();  
		    } else if (document.mozCancelFullScreen) {  
		      document.mozCancelFullScreen();  
		    } else if (document.webkitCancelFullScreen) {  
		      document.webkitCancelFullScreen();  
		    }  
		  }  
		} 
	
	
	
</script>
<script async defer src="https://apis.google.com/js/api.js"
 onload="this.onload=function(){};handleClientLoad()"
 onreadystatechange="if (this.readyState === 'complete') this.onload()">
</script>
<%
} catch( Exception e ) {
	e.printStackTrace();
} 
%>
</body>
</html>