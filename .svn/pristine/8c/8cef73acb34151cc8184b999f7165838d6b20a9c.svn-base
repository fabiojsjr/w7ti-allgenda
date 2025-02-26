<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.tetrasoft.data.basico.EstadoEntity"%>
<%@page import="com.tetrasoft.data.usuario.PerfilAcessoEntity"%>
<%@page import="com.tetrasoft.app.sender.SenderInterface"%>
<%@page import="javax.swing.text.MaskFormatter"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tetrasoft.data.basico.MunicipioEntity"%>

<style>
	.stdform label { width: 35%; }
	.maincontentinner { padding-top: 20px; padding-left: 0px; padding-right: 0px; padding-bottom: 0px; }
	
	.inputNome{
    	width: 43%;
    }
    
    .fancy-close-aux{
		top: 1px!important;
		right: -2px!important;
	}
    
    .inputEstado{
    	width: 48%;
    }
    
    .genero{
    	margin-left: 0px!important;
    }
    
    .inputPerfil{
    	width: 47%;
    }
       
	@media screen and (min-width: 768px) and (max-width: 1024px) {
    	.inputNome{
    		width: 45%;
    	}
    	
    	.inputEstado{
    		width: 48%;
    	}
    	
    	.inputPerfil{
    		width: 48%;
    	}   	
	}	
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
		boolean novo    = false;
		boolean proprio = false;
		
		UsuarioEntity userToChange = new UsuarioEntity();
		conn = userToChange.retrieveConnection();
		
		try{
			if( request.getParameter("idUsuario").equals("0") ) {
				novo = true;
			} else {
				if(!userToChange.getThis("idUsuario = "+request.getParameter("idUsuario"), conn) ){
					throw new Exception("Usuário não  existe");
				}
			}
		}catch(Exception e){
			out.println("<script>$.fancybox.close();</script>");
			return;
		}
		
		if( usuarioLogado.semPermissaoAcesso(400) ) { // se não  for usuário master, pode alterar somente os seus dados
			userToChange.setIdUsuario( usuarioLogado.getIdUsuario() );
			userToChange.getThis(conn);
		}
		
		if( userToChange.getIdUsuario().toString().equals( usuarioLogado.getIdUsuario().toString() ) ) {
			proprio = true;
			new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), userToChange.getIdUsuario(), "Usuario visualizou seus próprios dados",conn);
		} else {
			new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), userToChange.getIdUsuario(), "Usuario visualizou dados de outros usuários",conn);
		}
	
%>	
<form action="javascript:return false;" method="post" name="formAdminUsuarioDadosPessoais" id="formAdminUsuarioDadosPessoais" enctype="multipart/form-data" class="stdform">
	<input type="hidden" name="sid" value="<%= SystemParameter.get(SenderInterface.SID, "systemProperties", "sid") %>"/>
	<input type="hidden" name="id" value="" />
	<input type="hidden" name="command" value="" />
	<input type="hidden" name="action" value="" />
	<input type="hidden" name="formMode" value=""/>
	<input type="hidden" name="chaveSite" value="<%=System.currentTimeMillis() + "" + System.currentTimeMillis()*2%>"/>
	<input type="hidden" name="idUsuarioAdmin" value="<%=userToChange.getIdUsuario() %>"/>
	
	<script type="text/javascript">var form = document.formAdminUsuarioDadosPessoais;</script>
	
	<h4 class="widgettitle"> <b><%= p.get("ALTERAR_USUARIO") %></b></h4>
	<div class="maincontent">
		<div class="maincontentinner">
			<div class="row-fluid">
				<div class="span5 profile-left">
				
					<%	if( !novo ) { %>
						<div class="widgetbox profile-photo">
						
							<div class="headtitle">
								<div class="btn-group">
									<span data-toggle="dropdown" class="btn dropdown-toggle"><%=p.get("ACOES") %> <span class="caret"></span></span>
									<ul class="dropdown-menu">
										<li><a href="#" id="changeProfilePhoto"><%=p.get("ENVIE_FOTO") %></a></li>
										<li><a href="#" id="removePhoto"><%=p.get("REMOVER_FOTO") %></a></li>
										<script>
											$("#changeProfilePhoto").click(function(){
												document.getElementById('uploadPhotoIframe').contentWindow.clickFoto();
											});
											$("#removePhoto").click(function(){
										 		document.getElementById('uploadPhotoIframe').contentWindow.removeFoto();
											});
									  	</script>
									</ul>
								</div>
								<h4 class="widgettitle"><%=p.get("FOTO_PERFIL") %></h4>
							</div>
							<div class="widgetcontent">
								<div class="profilethumb">
									<iframe src="uploadFoto.jsp?rename=perfil&idUsuario=<%=userToChange.getIdUsuario() %>&nc=<%=new Date().getTime() %>" style="border: 0 none; width: 249px; max-width:249px;" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="uploadPhotoIframe"></iframe>
								</div>
							</div>
							
						</div>
					<%	} %>
	
	
					<!-- ------------------------------------------------------------------------------------------------------------------------------------------------- -->	
					<!-- ACCORDION -->	
					<div class="accordion-primary" style='width: 99%;'>
						<h3><a href="#"><%=p.get("INFORMACOES_LOGIN") %></a></h3>
						<div>
							<p class="control-group">
								<label style="text-align:left" class="control-label" style="width: 120px;"><%=p.get("FORM_LOGIN") %>:</label>
								<input type="text" maxlength="60" value="<%=userToChange.getLogin() %>" name="login" class="noEspecialChacacter" size="20" style="width: 160px;"/>
							</p>
							<p class="control-group">
								<label style="text-align:left" class="control-label" style="width: 120px;"><%=p.get("EMAIL") %>:</label>
								<input type="text" maxlength="70" value="<%=userToChange.getEmail() %>" name="email" style="width: 160px;"/>
							</p>
							<p class="control-group">
								<label style="text-align:left" class="control-label" style="width: 120px;"><%=p.get("SENHA") %>:</label>
								<input type="password" name="senha" placeholder="*******" style="width: 160px;"/>
								<br>
								<label style="text-align:left" class="control-label" style="width: 120px;"><%= p.get("CONFIRMAR") %>:</label>
								<input type="password" name="senha2" placeholder="*******" style="width: 160px;"/>
							</p>
						</div>

					</div>
					<!-- / ACCORDION -->
					<!-- ------------------------------------------------------------------------------------------------------------------------------------------------- -->	
		
	
					<div class="widgetbox admin-information" <% if( proprio ) out.print(" style='display:none;'"); %>>
						<h4 class="widgettitle"><%=p.get("STATUS") %></h4>
						<div class="widgetcontent">
							<div id="uniform-undefined">
								<div><input type="checkbox" name="inativo" <%= userToChange.getAtivo() != 1 ? "checked" : ""%> style="margin-top: 0;"> <%=p.get("INATIVO") %></div>
							</div>
						</div>
					</div>
					
					<p class="containerBtnSend">
						<button class="btn btn-primary" id="updateProfileButton"><%= p.get("SALVAR") %></button>
					</p>
										
				</div><!--span4-->
				
				<div class="span7">
					<div class="widgetbox personal-information">
						<h4 class="widgettitle"><%=p.get("DADOS_PESSOAIS") %></h4>
						<div class="widgetcontent" id="formUpdateCadastroPersonalInfo">
							<p class="control-group">
								<label class="control-label"><%= p.get("NOME") %>:</label>
								<input type="text" name="nome" value="<%= userToChange.getNome() %>" class="inputNome"/>
							</p>
							<p class="control-group">
								<label class="control-label"><%= p.get("SEXO") %>:</label>
	                            <span class="formwrapper sexoText genero">
									<input type="radio" id="masculino" name="sexo" value="0" <%= (userToChange.getSexo() == 0) ? "checked" : "" %>/> <%=p.get("MASCULINO") %> &nbsp; &nbsp;
									<input type="radio" id="feminino"  name="sexo" value="1" <%= (userToChange.getSexo() == 1) ? "checked" : "" %>/> <%=p.get("FEMININO") %> 
								</span>
							</p>
							<p class="control-group" <% if( proprio ) out.print(" style='display:none;'"); %>>
								<label class="control-label"><%= p.get("PERFIL_ACESSO") %>:</label>
	                            <select name="idPerfil" class="chzn-select inputPerfil" >
	                            	<%
										PerfilAcessoEntity perfil = new PerfilAcessoEntity();
	                            		ArrayList arr2 = perfil.getAllArray(conn);
	                            		for( int k = 0; k < arr2.size(); k++ ) {
											perfil = (PerfilAcessoEntity)arr2.get(k);
									%>
											<option value="<%= perfil.getIdPerfilAcesso() %>" <%=userToChange.getIdPerfil().longValue() == perfil.getIdPerfilAcesso().longValue() ? "selected" : "" %>><%= perfil.getNome() %></option>
									<%	} %>
									
								</select>
							</p>	
							<p class="control-group">
								<label class="control-label"><%= p.get("DATA_NASCIMENTO") %>:</label>
								
								<input type="hidden" id="datepickerHidden" />
								<%	if( userToChange.getDataNascimento() == null ) { %>
									<input type="text"  class="datePickerInput" name="dataNascimento" id="to2" placeHolder="__/__/____" value="" style="width: 150px"/> 
								<%	} else { 
										String dateStr = userToChange.DATE_FORMAT1_BR.format( userToChange.getDataNascimento() );
								%>
										<input type="text"  name="dataNascimento" id="to2" placeHolder="__/__/____" value="<%= dateStr %>"  style="width: 150px"/> 
								<%	} %>
								
							</p>
							<p class="control-group">
								<label class="control-label"><%= p.get("TELEFONE") %>:</label> 
								<input type="text" class="input-medium" name="telefone" value="<%= returnNotNull(userToChange.getTelefone()) %>"/>
							</p>
							<p class="control-group">
								<label class="control-label"><%= p.get("CELULAR") %>:</label> 
								<input type="text"  class="input-medium"  id="celular" name="celular" value="<%= returnNotNull(userToChange.getCelular()) %>"/>
							</p>
							<p class="control-group">
								<label class="control-label"><%= p.get("CEP") %>:</label> 
								<input type="text" id="cep" name="cep" class="input-small" value="<%= returnNotNull(userToChange.getCep()) %>"/>
							</p>
							<p class="control-group">
								<label class="control-label"><%=p.get("ESTADO") %>: </label>
								<select name="estado" class="chzn-select inputEstado" id="state">
                            	<%
                            		EstadoEntity uf = new EstadoEntity();
                            		ArrayList auf = uf.getArray("idPais=55 ORDER BY abreviacao", conn);
                            		for( int i = 0; i < auf.size(); i++ ) {
                            			uf = (EstadoEntity)auf.get(i);
                            			
                            			if( i == 0 && userToChange.getEstado().equals("") ) {
                            				out.print("<option value='' selected>-- " + ((String)p.get("SELECIONE")).toLowerCase() + " --<option>");
                            			}
                            	%>
										<option value="<%= uf.getAbreviacao() %>" <%=userToChange.getEstado().equals( uf.getAbreviacao() ) ? "selected" : "" %>><%= uf.getAbreviacao() %> - <%= uf.getNome() %></option>
								<%	} %>
							</select>
							</p>		
							<p class="control-group">
								<label class="control-label"><%= p.get("MUNICIPIO") %>:</label> 
								<input style="width: 45%!important" type="text" id="city" name="municipio" value="<%=returnNotNull(userToChange.getMunicipio()) %>"/>
							</p>		
							<p class="control-group" style="display: none">
								<label class="control-label"><%= p.get("NUMERO") %>:</label>
								<input style="width: 45%!important" type="text" name="numero" class="input-small" value="<%= returnNotNull(userToChange.getNumero()) %>"/>							
							</p>
							<p class="control-group">
								<label class="control-label"><%= p.get("ENDERECO") %>:</label>
								<input style="width: 45%!important" type="text" id="street" name="endereco" class="input-large" value="<%= returnNotNull(userToChange.getEndereco()) %>"/>
							</p>
							<p class="control-group" style="display: none">
								<label class="control-label"><%= p.get("COMPLEMENTO") %>:</label> 
								<input type="text" name="complemento" value="<%=returnNotNull(userToChange.getComplemento())%>"/>
							</p>							
						</div>
					</div>
				</div><!--span8-->
			</div><!--row-fluid-->
		</div><!--maincontentinner-->
	</div>
		
	<script>
	$('input[name=cep]').mask("99999-999");
	$('input[name=celular]').mask("(99) 99999-9999");
	$('input[name=telefone]').mask("(99) 9999-9999");
	$('input[name=dataNascimento]').mask("99/99/9999");
	
	function ajustesTelaAdminUsuario(){		
				
		var tamanhoTela = $(".rightpanel").width();		
		
		if(tamanhoTela <= 600){									
			
			$("#fancybox-wrap").removeAttr("style");
			$("#fancybox-wrap").attr("style","height: auto; width: 100vw; padding:0px; display: block; top: 0px; left: 0px;");
			
			$("#fancybox-content").removeAttr("style");
			$("#fancybox-content").attr("style","border-width: 10px; width: 95%; height: auto;");
			
			$("#fancybox-close").addClass("fancy-close-aux");
			$("#fancybox-bg-n").remove();
			$("#fancybox-bg-ne").remove();
			$("#fancybox-bg-e").remove();
			$("#fancybox-bg-se").remove();
			$("#fancybox-bg-s").remove();
			
			var btnSalvar = $(".containerBtnSend").clone();
			$(".containerBtnSend").remove();
			$("#formUpdateCadastroPersonalInfo").append(btnSalvar);
		}	
		
	}	
	
	$(document).on("input",".noEspecialChacacter",function(e){
		
		if(e.target.value.match(/[`´^çÇáÁàâãÀéèÉÈíìÍÌóòÓÒõúùÚÙñÑ~@&]/g)){
			var valor = e.target.value;
			var ultimoCaracter = valor.substring(0, valor.length);
			e.target.value = e.target.value.substring(0,valor.length -1);			
		}		
		
	});	
	
	function validarCampos(){
		
		
		if($("[name='login']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Login");
			throw "Preenchimento do campo Login";
		}
		
		if($("[name='email']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo E-mail");
			throw "Preenchimento do campo E-mail";
		}
		
		if($("[name='senha']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Senha");
			throw "Preenchimento do campo Senha";
		}
		
		if($("[name='senha2']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Confirmar");
			throw "Preenchimento do campo Confirmar";
		}
		
		if($("[name='senha']").val().trim() != $("[name='senha2']").val().trim() ){
			warningAlert("Mensagem","A senha deve ser igual a confirmação");
			throw "Preenchimento do campo Confirmar";			
		}
		
		if($("[name='nome']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Nome");
			throw "Preenchimento do campo Nome";
		}
		
		if($("[name='dataNascimento']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Data de Nascimento");
			throw "Preenchimento do campo Nome";
		}
		
		if($("[name='cep']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Cep");
			throw "Preenchimento do campo Cep";
		}
		
		if($("[name='estado']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Estado");
			throw "Preenchimento do campo Cep";
		}
		
		if($("[name='municipio']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Município");
			throw "Preenchimento do campo Cep";
		}
		
		if($("[name='endereco']").val().trim().length < 1){
			warningAlert("Mensagem","Preenchimento do campo Endereço");
			throw "Preenchimento do campo Endereço";
		}	
	}
	
	$(".inputEstado option:eq(1)").remove();
	
	$(document).on('click','#updateProfileButton',function(e){
		
		e.preventDefault();
		validarCampos();
		saveDadosPessoaisAdmin(form);
		
	});	
	
	$.validator.addMethod("checkAccordion", function (value, element) {
		if( value == null || value == '' ) {
		    expandAccordion();
			return false;
		} else {
	        return true;
		}
    });
	/*$.validator.addMethod("checkPassword", function (value, element) {
		var password = $("input[name=senha]").val();
		var confirmacao = $("input[name=senha2]").val();
		if(password == null && confirmacao == null) return true;
		if(password == '' && confirmacao == '') return true;
		
		if( password != confirmacao ) {
			<% if( !proprio ) out.print("expandAccordion();"); %>
		}
        return password == confirmacao;
    });
	$.validator.addMethod("checkTelephone", function (value, element) {
		var tel = $('input[name=telefone]').val();
		if(tel == '' ) return false;
		return true;
   	});
	$.validator.addMethod("checkCellphone", function (value, element) {
		var cel = $('input[name=celular]').val();
		if(cel == '' ) return false;
		return true;
   	});*/
	
	activateDatePickerID($("#to2"), 'PT');
	
	/*$("#formAdminUsuarioDadosPessoais").validate({
		errorPlacement: function(error, element){
			if(element.attr("name") == "telefone"){
				error.insertAfter("input[name=telefone]");
			}else{
				error.insertAfter(element);
			}
		},
		rules: {
			nome: "required",
			login: "required",
			cep: "required",
			endereco: "required",
			estado: "required",
			municipio: "required",
			email: "required",			
			senha: { checkPassword : true },
			senha2: { checkPassword : true }
		},messages :{
			nome : "Preencha o Nome",
			login : "Preencha o Login",
			cep : "Preencha o Cep",
			endereco: "Preencha o Endereço",
			estado: "Preencha o Estado",
			municipio: "Preencha o Município",
			email: "Preencha o E-mail",			
			senha: "Verifique a Senha",
			senha2: "Verifique a Senha"
		},
		highlight : function(label) {
			$(label).closest('.control-group').removeClass('success');
			$(label).closest('.control-group').addClass('error');
		},
		success : function(label) {
			$(label).addClass('valid').closest('.control-group').addClass('success');
		},
		ignore: [] // para o accordion e itens ocultos
	});*/
	
	$(document).ready(function(event){
		$('.accordion-primary').accordion({heightStyle: "content"});
		setTimeout(function(){
			ajustesTelaAdminUsuario();
		},700);
		
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