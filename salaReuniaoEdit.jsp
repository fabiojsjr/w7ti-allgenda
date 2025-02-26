<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.tetrasoft.data.finance.SalaReuniaoEntity"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>

<style>
	.stdform label { width: 20%; }
	.maincontentinner { padding-top: 20px; padding-left: 0px; padding-right: 0px; padding-bottom: 0px; }
	#lable-status{ padding: 0 0 10px 0; margin-bottom: 20px; }	
</style>

<%@ include file="logadoForce.jsp" %> 
<%@ include file="idioma.jsp" %> 
<%@ include file="methods.jsp" %>

<%	if (usuarioLogado == null ) { %> 
		<script type="text/javascript">location.href = "painel.jsp"</script>
<%	
		return;
	}

	out.println("<div id='erro'>");
	if(request.getParameter("info") != null){
		out.println("<script>infoAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("info")) == null ? request.getParameter("info") : p.get(request.getParameter("info")))+"'), function() {});</script>");
	}else if(request.getParameter("warning") != null){
		out.println("<script>warningAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("warning")) == null ? request.getParameter("warning") : p.get(request.getParameter("warning")))+"'), function() {});</script>");
	}
	out.println("</div>");
	
	Connection conn = null;
	try {
		boolean novo = false;
		String idSalaReuniao = "";
		SalaReuniaoEntity salaReuniaoToChange = new SalaReuniaoEntity();
		conn = new UsuarioEntity().retrieveConnection();
		
		try{
			if( request.getParameter("id") == null || request.getParameter("id").equals("0") ) {
				novo = true;
				idSalaReuniao = System.currentTimeMillis()+"";
			} else {
				if(!salaReuniaoToChange.getThis("idSalaReuniao = "+request.getParameter("id"), conn) ){
					throw new Exception("Sala de reuni&atilde;o n&atilde;o existe");
				}
				idSalaReuniao = salaReuniaoToChange.getIdSalaReuniao().toString();
			}
		}catch(Exception e){
			out.println("<script>$.fancybox.close();</script>");
			return;
		}
		
		if( usuarioLogado.semPermissaoAcesso(SalaReuniaoEntity.ID_FUNCAO) ) { 
			out.println("<script>$.fancybox.close();</script>");
			return;
		}
		
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), 0l, "Sala de reuni&atilde;o: Edit");
%>
	
<form action="javascript:return false;" method="post" name="formClient" id="formClient" accept-charset="UTF-8" enctype="multipart/form-data" class="stdform form-width-reuniao">
	<!-- O que seria cada atributo desetes -->
	<input type="hidden" name="sid" value="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "sid") %>"/>
	<input type="hidden" name="id" value="<%=salaReuniaoToChange.getIdSalaReuniao() %>" />
	<input type="hidden" name="command" value="" />
	<input type="hidden" name="action" value="" />
	<input type="hidden" name="chaveSite" value="<%=System.currentTimeMillis() + "" + System.currentTimeMillis()*2%>"/>
	<input type="hidden" name="idSalaReuniao" value="<%=idSalaReuniao %>"/>
	
	<script type="text/javascript">var form = document.formClient;</script>
	
	<h4 class="widgettitle"> <b><%= novo ? "Incluir" : "Editar" %>  Sala de reuni&atilde;o</b></h4>
	<div class="maincontent">
		<div class="maincontentinner">
			<div class="row-fluid">
				<div class="span4 profile-left">
					<div class="widgetbox admin-information">
						<h4 class="widgettitle">Status</h4>
						<div class="widgetcontent">
							<div id="uniform-undefined">
								<div id="lable-status">
								Ativo :<br>
										<label>Sim <input type="radio" name="ativo" value="1" CHECKED <%= salaReuniaoToChange.getAtivo() != null && salaReuniaoToChange.getAtivo() == 1 ? " CHECKED" : "" %> /></label>
										<label>N&atilde;o <input type="radio" name="ativo" value="0" <%= salaReuniaoToChange.getAtivo() != null && salaReuniaoToChange.getAtivo() == 0 ? " CHECKED" : "" %> /></label>
								</div>
							</div>
						</div>
					</div>
					
					<p>
						<button class="btn btn-primary" id="updateCliente">Salvar</button>
					</p>
				</div><!--span4-->
				
				<div class="span8">
					<div class="widgetbox personal-information">
						<h4 class="widgettitle">Dados da sala</h4>
						<div class="widgetcontent" id="formUpdateCadastroPersonalInfo">							
							<div  class=" control-group"  >
								<label class="control-label">Nome:</label>
								<input type="text" name="nome" value="<%= returnNotNull(salaReuniaoToChange.getNome()) %>" maxlength="252"/>
							</div>
							<div  class=" control-group"  >
								<label class="control-label">N&uacute;mero:</label>
								<input type="text" name="numero" class="somenteNumero" value="<%= returnNotNull(salaReuniaoToChange.getNumero()) %>" maxlength="252"/>
							</div>
							<div  class=" control-group"  >
								<label class="control-label">Capacidade:</label>
								<input type="text" name="capacidade" class="somenteNumero" value="<%= returnNotNull(salaReuniaoToChange.getCapacidade()) %>" maxlength="252"/>
							</div>
							<div  class=" control-group">
								<label class="control-label">Google client id:</label>
								<input type="text" name="googleClientKey" value="<%= returnNotNull(salaReuniaoToChange.getGoogleClientKey()) %>"/>
							</div>
							<div  class=" control-group">
								<label class="control-label">Google api key:</label>
								<input type="text" name="googleApiKey" value="<%= returnNotNull(salaReuniaoToChange.getGoogleApiKey()) %>"/>
							</div> 
							<div  class=" control-group">
								<label class="control-label">Google calendar id:</label>
								<input type="text" name="googleCalendarKey" value="<%= returnNotNull(salaReuniaoToChange.getGoogleCalendarKey()) %>"/>
							</div>
							<div class="input-xlarge">&nbsp;</div>
						</div>
					</div>
				</div><!--span8-->
			</div><!--row-fluid-->
		</div><!--maincontentinner-->
	</div>
	
	<script>	
		
		$(".somenteNumero").keyup(function(e){			
			if(e.target.value.match(/\D/g)){
				var valor = e.target.value;
				var ultimoCaracter = valor.substring(0, valor.length);
				e.target.value = e.target.value.substring(0,valor.length -1);			
			}			
			
		});
		
		$("#updateCliente").click(
				function(e) {

					if (!$(form).valid()) {
						throw "Digite os campos obrigat&oacute;rios";
						return;
						//warningAlert('Mensagem','Digite os campos obrigat&oacute;rios'),function() {};return;
					}

					form.command.value = "salaReuniao";
					form.action.value = "saveSalaReuniao";
					var uri = createQueryString(form);
					var uriEncode = encodeURI(uri);

					$.ajax({
						type : 'POST',
						url : uriEncode,
						dataType : 'html',
						contentType : "application/x-www-form-urlencoded;charset=UTF-8",
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
		

			$("#formClient").validate(
					{
						errorPlacement : function(error, element) {
							error.insertAfter(element);
						},
						rules : {
							nome : "required",
							capacidade : "required"
			
						},
						highlight : function(label) {
							$(label).closest('.control-group').removeClass(
									'success');
							$(label).closest('.control-group').addClass('error');
						},
						success : function(label) {
							$(label).addClass('valid').closest('.control-group')
									.addClass('success');
						},
						ignore : []
					// para o accordion e itens ocultos
					});
			
			jQuery(document).ready(function(event) {
				jQuery('.accordion-primary').accordion({
					heightStyle : "content"
				});				
				
			});		
	
	
		
	</script>
</form>

<style>
	.form-width-reuniao{
		width: 900px;
	}
	
	@media screen and (max-width: 1024px) and (min-width: 760px){
	  .form-width-reuniao {
	    width: 668px;
	  }
	}


</style>

<%
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();			
		} catch( Exception e2 ) {
			System.out.println(e2.getMessage());
		}
	}
%>