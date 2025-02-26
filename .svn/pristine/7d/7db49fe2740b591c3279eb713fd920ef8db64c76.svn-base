<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<div id="fancyRecover"></div>

<a id="grow2"></a>
<script type="text/javascript">
	jQuery(document).ready(function(){
		// growl notification
		<% if( mensagensNaoLidas > 0 ) { %>
			jQuery('#grow2').show(function(){
				jQuery.jGrowl("<a href=\"javascript:abre('miolo', 'mensagemLista.jsp?caixa=inbox')\"><%= p.get("VOCE_TEM_NOVAS_MENSAGENS").toString().replace("#QUANTIDADE_NOVAS_MENSAGENS", String.valueOf(mensagensNaoLidas)) %></a>");
			});
		<%	} %>
	});
</script>

	</form>
	</body>
</html>




	