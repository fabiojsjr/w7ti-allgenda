<%@page import="java.util.List"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.tetrasoft.data.cliente.Calendario"%>
<%@page import="com.tetrasoft.data.cliente.TaskEntity"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%> 


<a id="dynCalendar" style="overflow: auto;" class="grouped_elements"></a>		

<script type="text/javascript" id="runscript">	
	<!-- CALEND�RIO -->
	var start = 0;
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();

	var eventoSelecionado;
	var acesso = false;
	
	function vinculaEventoClick(){
		
	$(document).on('click','.fc-agenda-slots tbody tr',function(e){		
			
			var linhaHora = $('#dynCalendar');
			$(linhaHora).attr("href","calendarioEdit.jsp?start=" + start + "&idCalendario=<%= idCalendario %>");
			
			var hora = $(this).find("th").text();
			if(hora.trim() == "")				
				hora = $(this).prev().find("th").text();				
			$(linhaHora).click();
			
			setTimeout(function(){				
				if($("#Solicitante").val() == ""){										
					//$("[name='horaTask']").val(hora.substring(0,hora.indexOf(':')) + ' : ' + hora.substring(hora.indexOf(':') + 1,hora.length));
					$("[name='horaTask']").val("");
					$("[name='dataTask']").val("");
				}
			},500);
				
		});	
	}
	
	function buscarTodosEventos(){			
		
		var search = window.location.search;
		var url = "";		
		var id = search.substring(search.indexOf("=") + 1,search.length);			
		
		if(id != "")
			url = "eventosServlet" + "?id=" + id;
		else
			url = "eventosServlet";
		
		$.get(url,function(data){
		
			$('#calendar').fullCalendar("removeEvents");		
			$('#calendar').fullCalendar("addEventSource", JSON.parse(data));
			$('#calendar').fullCalendar("refetchResources");
			$('#calendar').fullCalendar("rerenderEvents");
			
		});		
	}
	
	function ajusteTelaCalendarioCelular(){
		
		if(window.innerWidth <= 450){
			
			var labelDias = $("td.fc-header-center");	
			$(labelDias).addClass('labelDias');
			var containerTable = $("table.fc-header tbody");
			$(".fc-header-center").hide();
			$(".labelDias").show();
			$(containerTable).append(labelDias);
			
		}else{
			
			$(".labelDias").remove();
			$(".fc-header-center").show();			
			
		}
	}	
	
	function ajusteTelaCalendarioIpad(){
		
		var parametroUrl = window.location.search;			
	
		if(window.innerWidth >= 768 && window.innerWidth <= 1025){
			
			if(parametroUrl.indexOf("id") != -1){
				$(".breadcrumbs, .pageheader, #header, .topbar").hide();	
				$("html").addClass("backgroundColor");
				$("h4.widgettitle.cabecalho").addClass("text-decoration-link");
				$("#leftPanel").hide();
				$("h4.widgettitle.cabecalho").wrap("<a class='linkPainel' href='painel.jsp'></a>");
				
			}else{
				$(".breadcrumbs, .pageheader, #header, .topbar").show();		
				$("html").removeClass("backgroundColor");
				$("h4.widgettitle.cabecalho").removeClass("text-decoration-link");
			}
			
		}		
		
	}

	jQuery(document).ready(function() {				
		
		setupCalendar();	
		
		vinculaEventoClick();
		
		ajusteTelaCalendarioCelular();
		
		ajusteTelaCalendarioIpad();	
		
		setInterval( function() {
			buscarTodosEventos();
			}, 30000);		
	});

	
	function setupCalendar() {		
		var calendar = jQuery('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			
			<% if( idioma.equals("PT") ) { %>
					monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
					monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'], dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'], 
					dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b'], buttonText: {   prev: '&nbsp;&#9668;&nbsp;',    next: '&nbsp;&#9658;&nbsp;',  
					prevYear: '&nbsp;&lt;&lt;&nbsp;',   nextYear: '&nbsp;&gt;&gt;&nbsp;',   today: 'hoje',  month: 'm�s',   week: 'semana', day: 'dia'  }, 
					titleFormat: {  month: 'MMMM yyyy', week: "d [ yyyy]{ '&#8212;'[ MMM] d MMM yyyy}", day: 'dddd, d MMM, yyyy'    }, 
					columnFormat: { month: 'ddd',   week: 'ddd d/M',    day: 'dddd d/M' },  
					allDayText: 'dia todo', axisFormat: 'H:mm', timeFormat: {   '': 'H(:mm)',   agenda: 'H:mm{ - H:mm}' },
					buttonText: {
						prev: '&laquo;',
						next: '&raquo;',
						prevYear: '&nbsp;&lt;&lt;&nbsp;',
						nextYear: '&nbsp;&gt;&gt;&nbsp;',
						today: 'hoje',
						month: 'm�s',
						week: 'semana',
						day: 'dia',
					},
			<%	} %>
			
			eventClick: function(event, element) {					
				eventoSelecionado = event;
				acesso = true;				
        		$('#calendar').fullCalendar('updateEvent', event);        	
    		},
    		//evento de arrastar
			eventDrop: function(event, element) {
				
				var query = "calendarioPainel.jsp?change=true&id=" + event.name;				
				var data = new Date(event.start);				
				var dia = data.getDate();
				var ano = data.getFullYear()
				var mes = Number(data.getMonth());			
				
				if(dia.toString().length == 1)
					dia = "0" + dia;
				
				if(mes.toString().length == 1)
					mes = "0" + mes;
				
				query += "&data=" + ano + '-' + mes + '-' + dia;
				query = query.replace("|task","");					
				
				ChamaPaginaArray(query, "centroPainel", "activateCalendar(1)");
				
    		},
    		
			eventAfterAllRender : function(view){				
				
				$("a[href^='calendarioEdit.jsp']").fancybox(
						{
							titleShow: false,
							transitionIn : 'elastic',
							transitionOut : 'elastic',
							autoDimensions: true,
							overlayShow : true,
							autoScale: true,
							easingIn : 'easeOutBack',
							easingOut : 'easeInBack',
							width: 910,
							scrolling: 'no',
						});
				
				$('a.grouped_elements').fancybox(
					{
						titleShow: false,
						transitionIn : 'elastic',
						transitionOut : 'elastic',
						autoDimensions: true,
						overlayShow : true,
						autoScale: true,
						easingIn : 'easeOutBack',
						easingOut : 'easeInBack',
						width: 910,
						scrolling: 'no',
					});				
			},			
			defaultView: 'agendaWeek',
			weekends: false,
			minTime: 7,
			maxTime: 21,
			timeZone: 'America/Sao_Paulo',
			selectable: true,
			selectHelper: true,
			editable: true,	
			height: 550,
			select: function(start, end, allDay) {		
				
				var inicioDia = start.toString().replace(/\s/g,"");
				var numerosDaData = inicioDia.replace(/[^0-9]/g,"");
				var dia = numerosDaData.substring(0,2);	
				
			    $('#dynCalendar').attr("href","calendarioEdit.jsp?start=" + start + "&idCalendario=<%= idCalendario %>");
			    $('#dynCalendar').click();		    
				calendar.fullCalendar('unselect');	
				
				setTimeout(function(){
					if($("#Solicitante").val() == ""){
						var dataForm = $("[name='dataTask']").val();
						if($("[name='dataTask']").val() != ""){
							var dataFormSplit = dataForm.split("/");
							$("[name='dataTask']").val(dia + "/" + dataFormSplit[1] + "/" + dataFormSplit[2]);	
						}														
					}	
				},500);			
			},			
			events: [
				<%						
					ArrayList<String> list2 = null;
					String idSala =  request.getParameter("idSala");
					Calendario cal = new Calendario();	
					if(idSala == null || idSala.equals("")){
						list2 = cal.getTODO( usuarioLogado.getIdUsuario(), idCalendario, false );
					}else{
						list2 = cal.getTODO( usuarioLogado.getIdUsuario(), idCalendario, false, Long.parseLong(idSala));						
					}
					
					for( String s : list2 ) {												
						StringTokenizer st = new StringTokenizer( s, "|");					
						String data       = st.nextToken();						
						String id         = st.nextToken();						
						String tipoAlerta = st.nextToken();						
						if( tipoAlerta.equals("2") ) continue;
						String tipo       = st.nextToken();						
						String evento     = st.nextToken();						
						String resp       = st.nextToken();						
						String color      = "red";
						TaskEntity taskAtual = new TaskEntity();
						taskAtual.getThis(" idTask = " + id);						
						String dataFinal = "";						
						if(taskAtual.getIdTask() != null && taskAtual.getIdTask() != 0){
							dataFinal = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(taskAtual.getDataHoraPrazoFinal());
						}
						
						
						if( !usuarioLogado.getIdUsuario().toString().equals( resp ) ) {
							if( TaskEntity.DATE_FORMAT7.parse( data ).before( new Date() ) ) {
								//color = "#FF5555";
								color = "red";
							} else {
								//color = "darkorange";
								color = "red";
							}
						} else {
							if( TaskEntity.DATE_FORMAT7.parse( data ).before( new Date() ) ) {
								color = "red";
							}
						}
						
				%>
						{
							name : '<%= id %>|<%= tipo %>',
							title : '<%= evento %>',							
							start : '<%=data%>',
							end: '<%= dataFinal %>',
							allDay : false,
							color : '<%= color %>',
							url : 'calendarioEdit.jsp?id=<%= id %>&tipo=<%= tipo %>&idCalendario=<%= idCalendario %>'
							
						},
						
				<%	}	%>	
				
			]
		});
		
	}	
	
</script>

<style>
	.fc-header-title h2{
		font-size: 1.6em!important;
	}
	
	.backgroundColor{
		background: #FFF;
	}
		
	.widgettitle{
		margin-top:15px;
	}
	
	
	.maincontentinner{
		padding-top:10px!important;
	}
	
	.labelDias{
		text-align:left;
		display:block;
		widh: 100%;		
	}
	
	.fc-header-title h2{
		margin-top:0px;
	}
	
	a.fc-event:link, a.fc-event:hover{
		color: #FFF!important;		
	}
</style>

<%	if( request.getParameter("ajax") == null ) { %>		
			<div id='calendar' style="overflow: auto;"></div>		
<%	} else { %>
			
		<div id="dashboard-left" class="span8" style="width: 95%; margin-top: 20px">
			<!div class="alert alert-error">
	
			<div class="widgetcontent font1" style="text-align: justify; border-top: 2px solid #2C3E50">				
					<div id='calendar' style="overflow: auto;"></div>				
			</div>
		</div><!--span6-->
		
		  	
<%	} %>

