<%@page import="com.tetrasoft.data.usuario.UsuarioEntity"%>
<%@page import="com.technique.engine.web.UserSession"%>

<%
	if (request.getParameter("idCidade") != null && !request.getParameter("idCidade").equals("")){
		session.setAttribute("idCidade", request.getParameter("idCidade"));
	} else if (session.getAttribute("idCidade") == null || session.getAttribute("idCidade").equals("")){
		session.setAttribute("idCidade", 5213); //sp
	}
	
	String filtroTipo = "";
	if (request.getParameter("tipo") != null ){
		filtroTipo = request.getParameter("tipo");
		if (filtroTipo.equals("0") || filtroTipo.equals("1") || filtroTipo.equals("2") || filtroTipo.equals("")) session.setAttribute("filtroTipo", filtroTipo);
	}
	if (session.getAttribute("filtroTipo") != null)	filtroTipo = (String) session.getAttribute("filtroTipo");
	
	UserSession userSession = null;
	UsuarioEntity usuarioLogado = null;
	boolean logado = false;
	
	if (session.getAttribute("UserSession_Allgenda") != null){
		userSession = (UserSession) session.getAttribute("UserSession_Allgenda");
		usuarioLogado = (UsuarioEntity) userSession.getAttribute("loginAllgenda");
		
		if( !usuarioLogado.getThis() ) {
			usuarioLogado = null;
			
			if(userSession!=null){
				userSession.removeAttribute("loginAllgenda");
				userSession.removeAttribute("senhaMaster");
			}
			
			session.removeAttribute("UserSession_Allgenda");
			session.invalidate();
			
			out.println("<script>top.document.location.href='index.jsp'</script>");   
		}
		if (usuarioLogado != null) logado = true;
	}
		
%>