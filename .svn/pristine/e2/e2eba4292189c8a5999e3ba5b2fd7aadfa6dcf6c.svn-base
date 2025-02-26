<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.tetrasoft.data.usuario.LogEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tetrasoft.data.basico.PaisEntity"%>
<%@page import="com.tetrasoft.data.basico.EstadoEntity"%>
<%@page import="com.tetrasoft.data.basico.MunicipioEntity"%>

<%@ include file="logadoForce.jsp" %>
<%@ include file="idioma.jsp" %> 
<%@ include file="methods.jsp" %>

<%	if (usuarioLogado == null || usuarioLogado.semPermissaoAcesso(400) ) { %> 
		<script type="text/javascript">location.href = "painel.jsp";</script>
<%	
		return;
	}

	boolean isNovo = true;
	UsuarioEntity user = new UsuarioEntity();
	
	String idUsuario = request.getParameter("idUsuario");
	
	out.println("<div id='erros'>");
	if(request.getParameter("info") != null){
		out.println("<script>infoAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("info")) == null ? request.getParameter("info") : p.get(request.getParameter("info")))+"'), function() {});</script>");
	}else if(request.getParameter("warning") != null){
		out.println("<script>warningAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("warning")) == null ? request.getParameter("warning") : p.get(request.getParameter("warning")))+"'), function() {});</script>");
	}
	out.println("</div>");
	
	new LogEntity( LogEntity.TIPO_SITE, user.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), new Long(idUsuario), "Usuario: editou dados pessoais");
	if(request.getParameter("idUsuario") != null && !request.getParameter("idUsuario").equals("")) {
		isNovo = false;
		user.getThis("idUsuario = "+request.getParameter("idUsuario"));
	}
%>

<input type="hidden" name="idUsuario" value="<%= user.getIdUsuario() %>"/>
<script>$('#formX').removeData('validator');</script>

<style>
#ui-datepicker-div{
    z-index: 1102 !important;
}
</style>

<%
	out.println("<div id='erro'>");
	if(request.getParameter("info") != null){
		out.println("<script>infoAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("info")) == null ? request.getParameter("info") : p.get(request.getParameter("info")))+"'), function() {});</script>");
	}else if(request.getParameter("warning") != null){
		out.println("<script>warningAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("warning")) == null ? request.getParameter("warning") : p.get(request.getParameter("warning")))+"'), function() {});</script>");
	}
	out.println("</div>");
	
	new LogEntity( LogEntity.TIPO_SITE, usuarioLogado.getIdUsuario(), usuarioLogado.getTableName(), usuarioLogado.get_IDFieldName(), request.getRemoteAddr(), request.getLocalName(), user.getIdUsuario(), "Usuário: editar");
%>

<ul class="breadcrumbs">
	<li><a href="painel.jsp"><i class="iconfa-home"></i></a> <span class="separator"></span></li>
	<li><%= p.get("CONFIGURACOES") %><span class="separator"></span></li>
	<li><b><%= p.get("DADOS_PESSOAIS") %></b></li>
</ul>

<div class="pageheader">
	<div class="pageicon"><span class="iconfa-laptop"></span></div>
	<div class="pagetitle">
		<h5><%=p.get("CONFIGURE_DADOS_PESSOAIS_E_FOTOS") %></h5>
		<h1><%=p.get("EDITAR_PERFIL") %></h1>
	</div>
</div><!--pageheader-->
		
<div class="clear20"></div>

<div class="maincontent">
	<div class="maincontentinner">
		<div class="row-fluid">
			<div class="span4 profile-left">
			<div class="widgetbox profile-photo">
				<div class="headtitle">
					<div class="btn-group">
						<button data-toggle="dropdown" class="btn dropdown-toggle">Foto<span class="caret"></span></button>
							<ul class="dropdown-menu">
								<li><a href="#" id="changeProfilePhoto">Enviar Foto</a></li>
								<li><a href="#" id="removePhoto">Remover foto</a></li>
									<script type="text/javascript">
										$("#changeProfilePhoto").click(function(){
											document.getElementById('uploadPhotoIframe').contentWindow.clickFoto(); 
										});
									
										$("#removePhoto").click(function(){ 
								 			document.getElementById('uploadPhotoIframe').contentWindow.removeFoto(); 
										});
						  			</script> 
							</ul>
					</div>
			</div>		
		<h4 class="widgettitle">Foto</h4>
			<div class="widgetcontent">
				<div class="profilethumb">
						<iframe src="uploadFoto.jsp?idCliente=<%= user.getIdUsuario() %>&nc=<%=new Date().getTime() %>" style="border: 0 none; width: 249px; max-width:249px;" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="uploadPhotoIframe"></iframe>
				</div>
			</div>

		</div>
				<div class="widgetbox login-information">
					<h4 class="widgettitle"><%=p.get("INFORMACOES_LOGIN") %></h4>
					<div class="widgetcontent" id="formUpdateCadastroLoginInfo">
						<p>
							<label class="control-label"><%=p.get("FORM_LOGIN") %>:</label>
							<input <%= isNovo? "" : "disabled" %> name="login" value="<%=user.getLogin() %>"/>
						</p>
						<p>
							<label class="control-label"><%=p.get("EMAIL") %>:</label>
							<input name="email" <%= isNovo? "" : "disabled" %> value="<%=user.getEmail() %>"/>
						</p>
						<p class="control-group">
							<label class="control-label"><%=p.get("SENHA") %>:</label>
							<input type="password" name="senha" placeholder="*******"/>
							<br>
							<br>
							<label class="control-label"><%= p.get("CONFIRMAR") %>:</label>
							<input type="password" name="senha2" placeholder="*******"/>

						</p>
					</div>
				</div>							
			</div><!--span4-->
			
			<div class="span8">
				<div class="widgetbox personal-information">
					<h4 class="widgettitle"><%=p.get("DADOS_PESSOAIS") %></h4>
					<div class="widgetcontent" id="formUpdateCadastroPersonalInfo">
						<p class="control-group">
							<label class="control-label"><%= p.get("NOME") %>:</label>
							<input type="text" name="nome" value="<%= user.getNome() %>"/>
						</p>
						<p class="control-group">
							<label class="control-label"><%= p.get("SEXO") %>:</label>
                            <span class="formwrapper sexoText">
								<input type="radio" id="masculino" name="sexo" value="0" <%= (user.getSexo() == 0) ? "checked" : "" %>/>
								<span class="masculinoText"><%=p.get("MASCULINO") %> &nbsp; &nbsp; </span>
								<input type="radio" id="feminino" name="sexo" value="1" <%= (user.getSexo() == 1) ? "checked" : "" %>/>
								<span class="femininoText"><%=p.get("FEMININO") %> </span>
							</span>
						</p>
						<p class="control-group" style="display: none">
							<label class="control-label"><%= p.get("ESTADO_CIVIL") %>:</label>
                            <select name="estadoCivil" class="chzn-select">
								<option value="0" <%=user.getEstadoCivil() == 0 ? "selected" : "" %>><%=p.get("SOLTEIRO") %></option>
								<option value="1" <%=user.getEstadoCivil() == 1 ? "selected" : "" %>><%=p.get("CASADO") %></option>
								<option value="2" <%=user.getEstadoCivil() == 2 ? "selected" : "" %>><%=p.get("DIVORCIADO") %></option>
								<option value="3" <%=user.getEstadoCivil() == 3 ? "selected" : "" %>><%=p.get("VIUVO") %></option>
							</select>
						</p>
						<p class="control-group">
							<label class="control-label"><%= p.get("DATA_NASCIMENTO") %>:</label>
							<input type="hidden" id="datepickerHidden" />
							<%	if( user.getDataNascimento() == null ) { %>
								<input type="text"  class="datePickerInput" name="dataNascimento" id="to" placeHolder="__/__/____" value=""/>
							<%	} else { %>
								<input type="text"  class="datePickerInput" name="dataNascimento" id="to" placeHolder="__/__/____" value="<%= usuarioLogado.DATE_FORMAT1_BR.format((Date)returnNotNull(user.getDataNascimento()))%>" />
							<%	} %>
						</p>
						<p class="control-group">
							<label class="control-label"><%= p.get("TELEFONE") %>:</label> 
							<input type="text" class="fone" id="te" name="telefone" value="<%= returnNotNull(user.getTelefone()) %>"/>
						</p>
						<p class="control-group">
							<label class="control-label"><%= p.get("CELULAR") %>:</label> 
							<input type="text" class="fone" id="t" name="celular" value="<%= returnNotNull(user.getCelular()) %>"/>
						</p>
						<p class="control-group">
							<label class="control-label"><%= p.get("CEP") %>:</label> 
							<input type="text" id="c" name="cep" class="input-small" onkeyup = "javascript:busca();" value="<%= returnNotNull(user.getCep()).toString().length() == 8 ? returnNotNull(user.getCep()).toString().substring(0,5) + "-" +returnNotNull(user.getCep()).toString().substring(5,8) :  returnNotNull(user.getCep()).toString()%>"/>
						</p>
						<p class="control-group">
							<label class="control-label"><%= p.get("NUMERO") %>:</label>
							<input type="text" name="numero" class="input-small" value="<%= returnNotNull(user.getNumero()) %>"/>							
						</p>
						<p class="control-group">
							<label class="control-label"><%= p.get("ENDERECO") %>:</label>
							<input type="text" id="street" name="endereco" value="<%= returnNotNull(user.getEndereco()) %>"/>
						</p>
						<p class="control-group">
							<label class="control-label"><%= p.get("COMPLEMENTO") %>:</label> 
							<input type="text" name="complemento" value="<%=returnNotNull(user.getComplemento())%>"/>
						</p>	
						<p class="control-group">
							<label class="control-label"><%=p.get("ESTADO") %>: </label>
                            <select name="estado" id="est" class="chzn-select">
                            	<%
                            		EstadoEntity uf = new EstadoEntity();
                            		ArrayList auf = uf.getArray("idPais = 1 ORDER BY abreviacao");
                            		for( int i = 0; i < auf.size(); i++ ) {
                            			uf = (EstadoEntity)auf.get(i);
                            			
                            			if( i == 0 && user.getEstado().equals("") ) {
                            				out.print("<option value='' selected>-- " + ((String)p.get("SELECIONE")).toLowerCase() + " --<option>");
                            			}
                            	%>
										<option value="<%= uf.getAbreviacao() %>" <%=user.getEstado().equals( uf.getAbreviacao() ) ? "selected" : "" %>><%= uf.getAbreviacao() %> - <%= uf.getNome() %> </option>
								<%	} %>
							</select>
						</p>		
						<p class="control-group">
							<label class="control-label"><%= p.get("MUNICIPIO") %>:</label> 
							<input type="text" id="mun" name="municipio" value="<%=returnNotNull(user.getMunicipio()) %>"/>
						</p>		
						<input type="hidden" name="emailOk" value=""/>
						<input type="hidden" name="cpfOk" value=""/>
					</div>
				</div>
				<p>
					<button class="btn btn-primary" id="updateProfileButton"><%= p.get("SALVAR") %></button>
				</p>
			</div><!--span8-->
		</div><!--row-fluid-->
	</div><!--maincontentinner-->
</div>
	
<script>

function busca() {
	
	var cepSize = document.getElementById("c").value.length;
	var cepValue = document.getElementById("c").value;
	
	/*
	if(cepSize > 7) {
		
		if($.trim($("#c").val().replace("-","")) != ""){
			//Recebe os dados do webservice cep.republicavirtual.com.br 
			$.getScript("http://cep.republicavirtual.com.br/web_cep.php?formato=javascript&cep="+$("#c").val(), function(){
	            if(resultadoCEP["resultado"] == 1){
				//seta os valores para os inputs pelos ids 
	                $("#street").val(unescape(resultadoCEP["tipo_logradouro"])+" "+unescape(resultadoCEP["logradouro"]));
	                $("#district").val(unescape(resultadoCEP["bairro"]));
 	                $("#mun").val(unescape(resultadoCEP["cidade"]));
 	                $("#est").val(unescape(resultadoCEP["uf"]));
	            }
	        });
	    }
	}
	*/
	
}

jQuery(function($) {
    //Inicio Mascara Telefone

	$('input[name=dataNascimento]').mask("99/99/9999");
	$('input[name=telefone]').mask("999-999-9999");
	$('input[name=celular]').mask("999-999-9999");

	//Fim Mascara Telefone
});
   

	$("input[name=estado]").keypress(function(e){
		limitText(this,2, e);
	});
	
	$("input[name=numero]").keypress(function(e){
		limitText(this,10, e);
	});
	
	$("input[name=cep]").keypress(function(e){
		onlyNumber(e);
		limitText(this,5,e);
	});
	
	$("input[name=nome],input[name=endereco]]").keypress(function(e){
		limitText(this,200,e);
	});
	
	$("#datepicker").keypress(function(e){
		 formatoData(this, e);
	});
	
	$("input[name=datePickerInput]").keyup(function(e){
		formatoData(this, e);
	});
	
	$("#updateProfileButton").click(function(e){
		e.preventDefault();
		saveDadosPessoais();
	});
	$.validator.addMethod("checkPassword", function (value, element) {
			var password = $("input[name=senha]").val();
			var confirmacao = $("input[name=senha2]").val();
			if(password == null && confirmacao == null) return true;
			if(password == '' && confirmacao == '') return true;
	        return password == confirmacao;
    });
	$.validator.addMethod("checkTelephone", function (value, element) {
		var tel = $('input[name=telefone]').val();
		if(tel == null ) return t;
		if(tel == '' ) return false;
		return true;
   	});
	$.validator.addMethod("checkCellphone", function (value, element) {
		var cel = $('input[name=celular]').val();
		if(cel == null ) return false;
		if(cel == '' ) return false;
		return true;
   	});
	activateDatePickerID($("#datepicker"), 'EN');
	$("#formX").validate({
		groups: {
			telefone: "telefone",
			celular: "celular",
			sexo: "masculino feminino",
			senha: "senha senha2"
		},
		errorPlacement: function(error, element){
			if(element.attr("name") == "telefone"){
				error.insertAfter("input[name=telefone]");
			}else if(element.attr("name") == "celular"){
				error.insertAfter("input[name=celular]");
			}else if(element.attr("name") == "sexo"){
				error.insertAfter(".sexoText");
			}else if(element.attr("name") == "senha" || element.attr("name") == "senha2"){
				error.insertAfter("input[name=senha2]");
			}else{
				error.insertAfter(element);
			}
		},
		rules: {
			nome: "required",
			sexo: "required",
			estadoCivil: "required",
			dataNascimento: {
				required : true
			},
			telefone: {
				checkTelephone : true
			},
			
			cep: "required",
			endereco: "required",
			numero: "required",
			estado: "required",
			municipio: "required",
			senha: {
				checkPassword : true
			},
			senha2: {
				checkPassword : true
			}
		},
		messages: {
			nome: "<%=p.get("INFORME_NOME")%>",
			sexo: "<%=p.get("INFORME_SEXO")%>",
			estadoCivil: "<%=p.get("INFORME_ESTADO_CIVIL")%>",
			dataNascimento:{
				required: "<%=p.get("INFORME_DATA_NASCIMENTO")%>"
			} ,
			telefone: {
				checkTelephone: "<%=p.get("INFORME_TELEFONE")%>"
			},
			celular: {
				checkCellphone: "<%=p.get("INFORME_CELULAR")%>"
			},
			
			cep: "<%=p.get("INFORME_CEP")%>",
			endereco: "<%=p.get("INFORME_ENDERECO")%>",
			estado: "<%=p.get("INFORME_ESTADO")%>",
			numero: "<%=p.get("INFORME_NUMERO")%>",
			municipio: "<%=p.get("INFORME_MUNICIPIO")%>",
			senha: {
				checkPassword: "<%=p.get("SENHA_IGUAL_CONFIRMACAO")%>"
			},
			senha2: {
				checkPassword: "<%=p.get("SENHA_IGUAL_CONFIRMACAO")%>"
			}
		},
		highlight : function(label) {
			$(label).closest('.control-group').removeClass('success');
			$(label).closest('.control-group').addClass('error');
		},
		success : function(label) {
			$(label).addClass('valid').closest('.control-group').addClass('success');

		}
	});
	
</script>
<div class="clear"></div>