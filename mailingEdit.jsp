<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.tetrasoft.data.cliente.MailingEntity"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>

<style>
	.stdform label { width: 35%; }
	.maincontentinner { padding-top: 20px; padding-left: 0px; padding-right: 0px; padding-bottom: 0px; }
</style>

<%@ include file="logadoForce.jsp" %> 
<%@ include file="idioma.jsp" %> 
<%@ include file="methods.jsp" %>

<%	if (usuarioLogado == null ) { %> 
		<script type="text/javascript">location.href = "painel.jsp";</script>
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
		
		MailingEntity mailingToChange = new MailingEntity();
		conn = new UsuarioEntity().retrieveConnection();
		
		try{
			if( request.getParameter("id") == null || request.getParameter("id").equals("0") ) {
				novo = true;
			} else {
				if(!mailingToChange.getThis("idMailing = "+request.getParameter("id"), conn) ){
					throw new Exception("Mailing não  existe");
				}
			}
		}catch(Exception e){
			out.println("<script>$.fancybox.close();</script>");
			return;
		}
		
		if( usuarioLogado.semPermissaoAcesso(MailingEntity.ID_FUNCAO) ) { 
			out.println("<script>$.fancybox.close();</script>");
			return;
		}
		
		new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), mailingToChange.getTableName(), mailingToChange.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), mailingToChange.getIdMailing(), "Mailing: edit",conn);
%>
	
<form action="javascript:return false;" method="post" name="formMailing" id="formMailing" enctype="multipart/form-data" class="stdform" style="width: 900px !important;">
	<input type="hidden" name="sid" value="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "sid") %>"/>
	<input type="hidden" name="id" value="" />
	<input type="hidden" name="command" value="" />
	<input type="hidden" name="action" value="" />
	<input type="hidden" name="chaveSite" value="<%=System.currentTimeMillis() + "" + System.currentTimeMillis()*2%>"/>
	<input type="hidden" name="idMailing" value="<%=mailingToChange.getIdMailing() %>"/>
	
	<script type="text/javascript">var form = document.formMailing;</script>
	
	<h4 class="widgettitle"> <b><%= novo ? "Incluir" : "Editar" %> Mailing</b></h4>
	<div class="maincontent">
		<div class="maincontentinner">
			<div class="row-fluid">
				<div class="span4 profile-left">
					<div class="widgetbox admin-information">
						<h4 class="widgettitle">Status</h4>
						<div class="widgetcontent">
							<div id="uniform-undefined">
								<div><input type="checkbox" name="inativo" <%= mailingToChange.getAtivo() != 1 ? "checked" : ""%> style="margin-top: 0;"> Inativo</div>
							</div>
						</div>
					</div>
					
					<p>
						<button class="btn btn-primary" id="updateMailing">Salvar</button>
					</p>
				</div><!--span4-->
				
				<div class="span8">
					<div class="widgetbox personal-information">
						<h4 class="widgettitle">Dados Pessoais</h4>
						<div class="widgetcontent" id="formUpdateCadastroPersonalInfo">
							<p class="control-group">
								<label class="control-label">Nome:</label>
								<input type="text" name="nome" value="<%= mailingToChange.getNome() %>"/>
							</p>
							<p class="control-group">
								<label class="control-label">Mailing:</label>
								<input type="text" name="contato" value="<%= mailingToChange.getContato() %>"/>
							</p>
							<p class="control-group">
								<label class="control-label">Email:</label>
								<input type="text"  value="<%=mailingToChange.getEmail() %>" name="email" id="email"/>
							</p>
							<p class="control-group">
								<label class="control-label">Telefone:</label> 
								<input type="text" class="input-medium" id="telefone1" name="telefone1" value="<%= returnNotNull(mailingToChange.getTelefone1()) %>"/>
							</p>
							<p class="control-group">
								<label class="control-label">Celular:</label> 
								<input type="text" class="input-medium" id="telefone2" name="telefone2" value="<%= returnNotNull(mailingToChange.getTelefone2()) %>"/>
							</p>
							<p class="control-group">
								<label class="control-label">Endereço:</label>
								<input type="text" id="endereco" name="endereco" class="input-large" value="<%= returnNotNull(mailingToChange.getEndereco()) %>"/>
							</p>
							<p class="control-group">
								<label class="control-label">Cidade:</label>
								<input type="text" id="cidade" name="cidade" class="input-large" value="<%= returnNotNull(mailingToChange.getCidade()) %>"/>
							</p>
							<p class="control-group">
								<label class="control-label">Estado: </label>
								<input type="text" id="estado" name="estado" class="input-small" value="<%= returnNotNull(mailingToChange.getEstado()) %>"/>
							</p>		
							<p class="control-group">
								<label class="control-label">CEP:</label> 
								<input type="text" id="cep" name="cep" class="input-small" value="<%= returnNotNull(mailingToChange.getCep()) %>"/>
							</p>
							<p class="control-group">
								<label class="control-label">País:</label> 
								<input type="text" id="pais" name="pais" class="input-small" value="<%= returnNotNull(mailingToChange.getPais()) %>"/>
							</p>
							<div class="input-xlarge">&nbsp;</div>
						</div>
					</div>
				</div><!--span8-->
			</div><!--row-fluid-->
		</div><!--maincontentinner-->
	</div>
		
	<script>
		$('input[name=telefone1]').mask("(99) 9999-9999");
		$('input[name=telefone2]').mask("(99) 99999-9999");
		$('input[name=cep]').mask("99999-999");
	
	
		$("#updateMailing").click(function(e){
	
			if(!$(form).valid()) {
				warningAlert('Atenção', 'Digite os campos obrigatórios'), function() {};
				return;
			}
	
			form.command.value = "cliente";
			form.action.value = "saveMailing";
			
			$.ajax({
				type: 'POST',
				url: createQueryString(form),
				dataType: 'html',
		        contentType: "application/x-www-form-urlencoded;charset=ISO-8859-1",
		        processData: false,
				success: function(data){
					$('#erro').html(data);
				}
			});
		});
		
		$("#formMailing").validate({
			errorPlacement: function(error, element){
				error.insertAfter(element);
			},
			rules: {
				nome: "required",
				email: "required"
			},
			highlight : function(label) {
				$(label).closest('.control-group').removeClass('success');
				$(label).closest('.control-group').addClass('error');
			},
			success : function(label) {
				$(label).addClass('valid').closest('.control-group').addClass('success');
			},
			ignore: [] // para o accordion e itens ocultos
		});
		
		jQuery(document).ready(function(event){
			jQuery('.accordion-primary').accordion({heightStyle: "content"});
		});
	
	</script>

	<div class="clear"></div>

</form>

<%
	} catch( Exception e ) {
		e.printStackTrace();
	} finally {
		try {
			conn.close();			
		} catch( Exception e2 ) {
		}
	}
%>