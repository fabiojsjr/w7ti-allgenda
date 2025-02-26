<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tetrasoft.data.usuario.UsuarioEntity"%>
<%@page import="com.technique.engine.web.UserSession"%>
<%@page import="com.technique.engine.app.SystemParameter"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>

<%! 
	static String idioma; 
	static String locale;
	
	public void updateLocale(HttpSession session){
		if(idioma == null){
			locale = "en_US";
		}else if(idioma.equals("PT")){
			locale = "pt_BR";
		}else if(idioma.equals("ES")){
			locale = "es_ES";
		}else{
			locale = "en_US";
		}
		
		session.setAttribute("locale", locale);
	}
%>

<%
	if (session.getAttribute("UserSession_Allgenda") != null){
		UserSession u = ((UserSession) session.getAttribute("UserSession_Allgenda"));
		if(u.getAttributeString("idiomaLogin")!=null){
			idioma = u.getAttributeString("idioma");
			u.setAttribute("idiomaLogin", null);
			u.setAttribute("idioma", idioma);
			session.setAttribute("idioma", idioma);
			updateLocale(session);
		}
	}

	idioma = (String)session.getAttribute("idioma");

	if( idioma == null ) 	idioma = "PT";
	if( idioma.equals("") ) idioma = "PT";
	
	if( session.getAttribute("first_time") == null ) {
		session.setAttribute("first_time", "false");
	
		String ip = request.getRemoteAddr();
		//out.print("IP="+ ip);
		//CountryLookup country = new CountryLookup();
		//if( !country.brasil( request.getRemoteAddr() ) ) {
		//	idioma = "PT";
		//	session.setAttribute("idioma", idioma);
		//}
	
		//out.print("x="+ request.getLocale().getLanguage());
		//out.print("y="+ request.getLocale().getCountry());
		//out.print("z="+ request.getLocale().getDisplayCountry());
		if( request.getLocale().getLanguage().equals("PT") ) {
			idioma = "PT";
			session.setAttribute("idioma", idioma);
		}
	}

	
	if( request.getParameter("changeIdioma") != null ) {
		idioma = request.getParameter("changeIdioma");
		session.setAttribute("idioma", idioma);
		updateLocale(session);
		
		// salva no banco a preferéncia
		if (session.getAttribute("UserSession_Allgenda") != null){
			UserSession userSession2 = (UserSession) session.getAttribute("UserSession_Allgenda");
			UsuarioEntity usuarioLogado2 = (UsuarioEntity) userSession2.getAttribute("loginAllgenda");		
			if (usuarioLogado2 != null) {
				usuarioLogado2.setIdiomaPadrao(idioma);
				usuarioLogado2.update();
			}
		}
	}
	
	Properties p = new Properties();
	String pathFisico = SystemParameter.get("Allgenda", "systemProperties", "filePath");
	FileInputStream fis;
	try{
		fis = new FileInputStream( pathFisico + "lang/" + idioma + "/geral.properties");
	}catch(Exception e){
		fis = new FileInputStream( pathFisico + "lang/EN/geral.properties");
	}
	p.load( fis );
	
//	SimpleDateFormat sdf = new SimpleDateFormat( (idioma.equals("PT") ? "dd/MM" : "dd/MM") + "/yyyy");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

%>

