<%@page import="java.util.List"%>
<%@page import="com.tetrasoft.data.finance.SalaReuniaoEntity"%>
<%@page import="com.tetrasoft.data.usuario.PerfilAcessoEntity"%>
<%@page import="com.tetrasoft.data.cliente.Calendario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page import="com.tetrasoft.data.cliente.TaskEntity"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>

<%@ include file="logado.jsp" %> 
<%@ include file="idioma.jsp" %> 
<%@ include file="methods.jsp" %>

<%
	String idRelatorio =  request.getParameter("file");		
%>


<style>

	.input{
	
		cursor : pointer;
 	 	background-color: #FFFFFF;
 	 	width : 30%;
	}	
	
	#formRelatorioReuniao{
		margin-top: 30px;
		margin-left: 30px;
	}
	
	.containerRelatorioElements{		
		position: relative;
    	overflow: auto;
    	height: 60px;
	}
	
	.img-excel{
			    
	    width: 40px;
	    height: 40px;	    
	    position: absolute;
	    bottom: 0px;
	    left: calc(50% - 118px);
	}
	
	.maincontent{
		margin-top: 30px;
		margin-left: 60px;
	}
	
</style>
<input type="hidden" name="idRelatorio" id="idRelatorio" value="<%=idRelatorio %>"/>
<input type="hidden" name="msgErro" id="msgErro" value="<%=request.getParameter("msg")%>"/>

	<div class="maincontent" >
		<div class="row-fluid">
			<div class="span8">
				<div class='widgetbox personal-information'>
					<h4 class="widgettitle">Relatório reunião</h4>
				</div>
				<div class='widgetcontent'>
					<form class="stdform" id="formRelatorioReuniao">
						<input type="hidden" name="command"/>
						<input type="hidden" name="action"/>
						<input type="hidden" name="sid" value="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "sid") %>"/>	
						<input type="hidden" name="chaveSite" value="<%=System.currentTimeMillis() + "" + System.currentTimeMillis()*2%>"/>							
						<div class="control-group">
							<label class="control-label input">Data inicio:</label>
							<input type="text" class="datepicker" readonly="readonly"  maxlength="10" id="dataInicio" name="dataInicio"/>
						</div>
						<div class="control-group">
							<label class="control-label input">Data final:</label>
							<input type="text" class="datepicker" readonly="readonly"  maxlength="10" id="dataFinal" name="dataFinal"/>
						</div>	
						
						<div class="control-group-containerRelatorio">	
							<div class="containerRelatorio">
								<a href="" id="relatorioPath" target="_blank " style="color:#000; position: absolute; left: calc(50% - 180px);"></a>
								<img src=""></img>							
							</div>						
							<div id="divUpdateInt" class="btnUpdateRelatorio">							
								<button class="btn btn-primary" id="btnSendReport">Enviar</button>
							</div>																																	
						</div>
					</form>					
				</div>			
			</div>
		</div>
	</div>		

		
<script src="/allgenda/js/timepicki.js"></script>

<script>

	$(document).ready(function(){		
		
		let form = document.getElementById('formRelatorioReuniao');	
				
		$(".datepicker").mask("99/99/9999");
		activateDatePickerID($(".datepicker"), 'EN');		
		
		$('#btnSendReport').click(function(e){
			
			e.preventDefault();	
			
			
			form.command.value = "relatorioReuniao";
			form.action.value = "criarRelatorioReuniao";

			$.ajax({
				type : 'POST',
				url : createQueryString(formRelatorioReuniao),
				dataType : 'html',
				contentType : "application/x-www-form-urlencoded;charset=ISO-8859-1",
				processData : false,
				success : function(data) {
					
					let resposta = $(data).html("</div>");			
					
					let mensaGemErro = resposta[4].value;										
					
					if(mensaGemErro == 'null'){
						
						let relatorio = resposta[2].value;
						
						let linkRelatorio = $('#relatorioPath');						
						
						$(linkRelatorio).attr("href",relatorio);
						$(linkRelatorio).html(relatorio);	
						$(".containerRelatorio img").attr("src","images/excel.png").addClass("img-excel");
						$(".containerRelatorio").addClass("containerRelatorioElements");
					}else{
						
						warningAlert("Mensagem",mensaGemErro);
					}						
				}
			});			
			
		});
		
		
		$(formRelatorioReuniao).validate({
			rules : {
				dataInicio : "required",
				dataFinal : "required"			
			},
			messages :{
				dataTask : "Preencha a Data inicio",
				horaTask : "Preencha a Data final"				
			},highlight : function(label) {
				$(label).closest('.control-group-int').removeClass('success');
				$(label).closest('.control-group-int').addClass('error');
			},
			success : function(label) {
				$(label).addClass('valid').closest('.control-group-int').addClass('success');
			}
		});
	});	

</script>



