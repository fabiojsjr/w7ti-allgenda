<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%@page import="com.tetrasoft.data.basico.EstadoEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tetrasoft.data.basico.MunicipioEntity"%>
<%@page import="com.tetrasoft.data.usuario.UsuarioEntity"%>
<%@ include file="idioma.jsp" %>

<% if(request.getParameter("msgClean") != null ) {
	if(request.getParameter("protocolo")!=""&&request.getParameter("protocolo")!=null){
		out.println("Protocolo:" + request.getParameter("protocolo"));
	} 
	if(p.get(request.getParameter("msgClean")) != null){
		out.println(p.get(request.getParameter("msgClean")));
	}else{
		out.println(request.getParameter("msgClean"));
	}
} 

if(request.getParameter("msg") != null ) { %>
	<% if(!request.getParameter("msg").equals("")){ %>
		<div class="alert alert-<%= request.getParameter("type") != null ? request.getParameter("type") : "error"%>">
			<a class="close" data-dismiss="alert">é</a>
			<span>
			<%
			if(p.get(request.getParameter("msg")) != null){
				out.println(p.get(request.getParameter("msg")));
			}else{
				out.println(request.getParameter("msg"));
			}
			%>
			</span>
		</div>
		<script>setTimeout('$("#erro").html("");', 3000);</script>
	<%	} %>
<%	} %>

<% 
if(request.getParameter("info") != null ) {
	if(!request.getParameter("info").equals("")){
		String s = "<script>infoAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("info")) == null ? request.getParameter("info") : p.get(request.getParameter("info")))+"'), function() {"+(request.getParameter("abre") != null ? "abre('miolo', '"+request.getParameter("abre")+"')" : request.getParameter("toPainel") != null ? "window.location = 'painel.jsp';" : "")+"});</script>";
		s = s.replace("step", "&step");
		out.println(s);
	}
} 

if(request.getParameter("infoCallback") != null ) {
	if(!request.getParameter("infoCallback").equals("")){
		String s = "<script>infoAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("infoCallback")) == null ? request.getParameter("infoCallback") : p.get(request.getParameter("infoCallback")))+"'), function() {"+request.getParameter("callback")+"});</script>";
		out.println(s);
	}
} 

if(request.getParameter("warning") != null ) {
	if(!request.getParameter("warning").equals("")){
		String s = "<script>warningAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("warning")) == null ? request.getParameter("warning") : p.get(request.getParameter("warning")))+"'), function() {});</script>";
		out.println(s);
	}
} 

if(request.getParameter("warningCallback") != null ) {
	if(!request.getParameter("warningCallback").equals("")){
		String s = "<script>warningAlert('"+p.get("MENSAGEM")+"', $.trim('"+(p.get(request.getParameter("warningCallback")) == null ? request.getParameter("warningCallback") : p.get(request.getParameter("warningCallback")))+"'), function() {"+request.getParameter("callback")+"});</script>";
		out.println(s);
	}
} 

if(request.getParameter("searchProduto") != null){
	out.println("<script>buscar('adminProdutosPesquisar.jsp',document.buscaProduto)</script>");
}


if(request.getParameter("searchComentario") != null){
	out.println("<script>buscar('adminProdutosComentarios.jsp',document.buscaComentario)</script>");
}

if(request.getParameter("updateLoja") != null){
	out.println("<script>setTimeout(\"abre('lojaCentro', '"+request.getParameter("updateLoja")+"')\", 1500);</script>");
}

if(request.getParameter("listEventos") != null){
	out.println(request.getParameter("listEventos"));
}

	
if(request.getParameter("toStep") != null){
	%>
	<script>
		$(".defaultWizard").smartWizard("goToStepForce", <%=request.getParameter("nextStep")%>);
		<%
		if(request.getParameter("tipo") != null && ((request.getParameter("tipo").equals("diagnosticoPrecoce") && request.getParameter("changeIdDiagnosticoPrecoce") == null) || (request.getParameter("tipo").equals("projeto") && request.getParameter("changeIdProjeto") == null))){
		%>
			$("#resumo").attr("src", $("#resumo").attr("src"));
		<%
		}
		%>
	</script>
	<%
} 

if(request.getParameter("redirect") != null ) { %>
	<br/><center><img src='/allgenda/images/carregando.gif' /></center>
	<script>
		setTimeout( "abre('miolo', '<%= request.getParameter("redirect") %>');", 1500);
	</script>
<%
}

if(request.getParameter("showBack") != null ) { %>
	<script>
		window.location = '/allgenda/painel.jsp';
	</script>
<%	} %>

<% 
if(request.getParameter("closeFancy") != null){
	out.println("<script>$.fancybox.close();</script>");
}

String redirFancy = request.getParameter("redirectFancy");
if( redirFancy != null ) {
	if( redirFancy.contains("inventarioEdit.jsp") ) {
		out.println(
			"<meta http-equiv='refresh' content='0;url=/allgenda/painel.jsp?redirect=inventarioEdit.jsp' />" +
			"<script>eval(window.location = '/allgenda/painel.jsp?redirect=inventarioEdit.jsp')</script>"
		);
		
	} else {
		String noc = System.currentTimeMillis()+"";
%>
	  	<a href="/allgenda/painel.jsp?redirect=<%= redirFancy %>" id="popup_box_<%= noc %>"></a>
	  	<a href="/allgenda/<%= redirFancy %>" class="grouped_elements" id="popup_box2_<%= noc %>"></a>
		<script>
		    setTimeout("document.getElementById('popup_box_<%= noc %>').click();", 1000);
		</script>
<%
	}
}

if(request.getParameter("infoBancaria") != null){
	out.println("<script>updateBancoStatus('"+request.getParameter("infoBancaria")+"');</script>");
}

try{
	if (request.getParameter("action") != null) {
		String action = request.getParameter("action");
		
		if (action.equals("logado")) {
			out.print("<input type='hidden' value='logado' name='loginOk'/>");
			
			String redir = request.getParameter("redirect");
			if( redir != null ) {
				if( redir.toLowerCase().contains("idioma") ) {
					response.sendRedirect("/allgenda/painel.jsp?changeIdioma=EN&redirect=" + redir);
				} else {
					String change = request.getParameter("changeIdioma");
					if( change != null ) {
						response.sendRedirect("/allgenda/painel.jsp?redirect=" + redir + "&changeIdioma=" + change);
					} else {
						response.sendRedirect("/allgenda/painel.jsp?redirect=" + redir);
					}
				}
			} else {
				response.sendRedirect("/allgenda/painel.jsp");
			}
		} 
		
		if (action.equals("logadoSite")) {
			out.print("<input type='hidden' value='logado' name='loginOk'/>");
		} 
		
		if (action.equals("cadastrado")) {
			out.println("<script>eval(window.location = '/allgenda/painel.jsp')</script>");
		} 
		if(action.equals("voucherUtilizado")) {
			out.println("<script>eval(window.location = '/allgenda/painel.jsp?info="+request.getParameter("infoMessage")+"')</script>");
		}
		
		if (action.equals("pais")){
			String pais = request.getParameter("pais");
			%>
			<select name="estado" class="input_check" id="estado" onchange="javascript:ajaxEstado(this.value)" class="input_check">
				<option value=""> -- <%= ((String)p.get("SELECIONE")).toLowerCase() %> -- </option>
				<%
				ArrayList a = new ArrayList();
				EstadoEntity estado = new EstadoEntity();
				a = estado.getArray("1=1 and idPais = " + pais + " ORDER BY nome");
				for (int i = 0; i < a.size(); i ++){
					estado = (EstadoEntity) a.get(i);
					%>
					<option value="<%=estado.getIdEstado()%>" ><%= estado.getNome() %></option>
					<%
				}
				%>
			</select>
			<%
		}

		if (action.equals("estado")){
			String estado = request.getParameter("estado");
			%>
			<select name="idMunicipio" class="input_check" id="cidade">
				<option value="" > -- <%= ((String)p.get("SELECIONE")).toLowerCase() %> -- </option>
				<%
				ArrayList a = new ArrayList();
				MunicipioEntity municipio = new MunicipioEntity();
				a = municipio.getArray("1=1 and idEstado = " + estado + " ORDER BY descricao");
				for (int i = 0; i < a.size(); i ++){
					municipio = (MunicipioEntity) a.get(i);
					%>
					<option value="<%=municipio.getIdMunicipio() %>" ><%= municipio.getDescricao() %></option>
					<%
				}
				%>
			</select>
			<%
		}
		if (action.equals("returnInElement")){
			out.print("<script>eval($('"+request.getParameter("returnElement")+"').text('"+(p.get(request.getParameter("returnMessage")) == null ? request.getParameter("returnMessage") : (p.get(request.getParameter("returnMessage")) ))+"'))</script>");
		}
		
		if (action.equals(UsuarioEntity.EMAIL_USADO)){
			out.print("<script>eval($('#respostaEmailText').text('"+p.get("EMAIL_CADASTRADO")+"'))</script>");
		}
		
		if (action.equals(UsuarioEntity.CPF_USADO)){
			out.print("<script>eval($('#respostaCPFText').text('"+p.get("CPF_JA_CADASTRADO")+"'))</script>");
		}
		
		if (action.equals(UsuarioEntity.INDICACAO)){
			out.print("<script>eval($('#respostaIndicacaoText').text('"+p.get("PATROCINADOR_INVALIDO")+"'))</script>");
		}
		
		if (action.equals(UsuarioEntity.LOGIN_DISPONIVEL)){
			%>
			<img src="images/task_check.png"/><input type="hidden" name="confirmed"/>
			<%
		} else if (action.equals(UsuarioEntity.LOGIN_INDISPONIVEL)){
			out.print("<script>eval($('#respostaLoginText').text('"+p.get("INDICACAO_INDISPONIVEL")+"'))</script>");
		}

		if (action.equals("senhaMasterOk")){
			%>
			<img src="images/task_check.png"/><input type="hidden" name="senhaMasterOk"/>
			<%
		} else if (action.equals("senhaMasterErro")){
			%>
			<img src="images/task_remove.png"/>
			<%
		}
		
		if (action.equals("transferenciaOk")){
			%>
			<div class="erro" id="erro" style="display:block"><div id="defaultError" class="defaultError">Transferéncia realizada com sucesso!</div></div>
			<%
		}
		
		if (action.equals("lojaCadastro")){
			out.println("<script>top.document.location.href='/allgenda/loja/index.jsp';</script>");   
		}	
		
		if (action.equals("cadastroBinarioOk")){
			out.println("<script>$('#cadastroBox').hide();</script>");
		}
		
		if (action.equals("cadastroBinarioFail")){
			out.println("<script>$('#cadastroBox').show('slow');</script>");
		}
		
		if(action.equals("enderecoModifiado")){
			out.println("<script>setTimeout(function(){$.get('confirmacaoEndereco.jsp', function(data) {$.fancybox(data);});},1000)</script>");
		}
	}
	
} catch (Exception e){
	e.printStackTrace();
}
%>
