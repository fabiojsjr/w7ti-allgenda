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

<style>

	.input{
	
		cursor : pointer;
 	 	background-color: #FFFFFF;
 	 	width : 30%;
	}
	
	#divUpdateInt{	
	    position: relative;    
	    width: 100%;
	    height: 50px;	
	}
	
	#btnSendReport{
	    position: absolute;
	    top: 5px;
	    left: 35%;
	}
	
	#formRelatorioReuniao{
		margin-top: 30px;
		margin-left: 30px;
	}

</style>

<form class="stdform" id="formRelatorioReuniao" style="width: 900px !important;">
	<div class="maincontent" >
		<div class="row-fluid">
			<div class="span7" style="padding: 0px; margin: 0px; width: 100%">
				<div class='widgetbox personal-information'>
					<h4 class="widgettitle">Relatório reunião</h4>
				</div>
				<div class='widgetcontent'>
					<div class="control-group-int">
						<label class="control-label input">Data inicio:</label>
						<input type="text" class="datepicker" readonly="readonly"  maxlength="10" id="dataInicio" name="dataInicio"/>
					</div>
					<div class="control-group-int">
						<label class="control-label input">Data final:</label>
						<input type="text" class="datepicker" readonly="readonly"  maxlength="10" id="dataFinal" name="dataFinal"/>
					</div>
					<div class="control-group-int" style="text-align: center">						
						<div id="divUpdateInt">
							<button class="btn btn-primary" id="btnSendReport">Enviar</button>
						</div>						
					</div>
				</div>			
			</div>
		</div>
	</div>		
</form>
		
<script src="/allgenda/js/timepicki.js"></script>

<script>

	$(document).ready(function(){
		
		let formRelatorioReuniao = document.getElementById('formRelatorioReuniao');
		
		
		$(".datepicker").mask("99/99/9999");
		activateDatePickerID($(".datepicker"), 'EN');	
		
		$('#btnSendReport').click(function(e){
			
			e.preventDefault();				
			
			formRelatorioReuniao.command.value = "relatorioReuniao";
			formRelatorioReuniao.action.value = "criarRelatorioReuniao";

			$.ajax({
				type : 'POST',
				url : createQueryString(formRelatorioReuniao),
				dataType : 'html',
				contentType : "application/x-www-form-urlencoded;charset=ISO-8859-1",
				processData : false,
				success : function(data) {
					$('#erro').html(data);
					
					setTimeout( function() {
						$('#fancybox-close').trigger("click");
						$('#popup_ok').trigger("click");
					}, 1000 );
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



