// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 01/12/2003 13:18:09
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   AbstractController.java

package com.technique.engine.web;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Referenced classes of package com.technique.engine.web:
//            UserSession, MultiPartProcessor, AbstractCommand, CommandWrapper,
//            RendererXSLtoHTML

public abstract class AbstractController extends HttpServlet
{

    public AbstractController()
    {
    }

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }

    public void destroy()
    {
    }

    private AbstractCommand getCommand(UserSession session)
        throws ExceptionWarning
    {
        String command = session.getCommandName();
        SystemParameter.log(session.getSID(), TechCommonKeys.LOG_DEBUG, "next command: " + command);
        return SystemParameter.getCommand(session.getSID(), command);
    }

    private HttpSession getSession(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if(session == null)
            session = request.getSession(true);
        return session;
    }

    private String getSID(HttpServletRequest request)
    {
        return request.getParameter("sid");
    }

    private UserSession getUserSessionMultipart(HttpSession session, HttpServletRequest request)
        throws ExceptionWarning
    {
        return MultiPartProcessor.getInstance().parse(session, request, -1L, 4096);
    }

    private UserSession getUserSession(HttpSession session, HttpServletRequest request)
        throws ExceptionWarning
    {
        String ctype = request.getContentType();
        if(ctype != null && ctype.startsWith("multipart/form-data"))
            return getUserSessionMultipart(session, request);
        String sid = getSID(request);
        try
        {
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "Server Date: " + new Date());
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "contentType: " + request.getContentType());
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "authType: " + request.getAuthType());
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "characterEncoding: " + request.getCharacterEncoding());
            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "contentLength: " + request.getContentLength());
            String s;
            for(Enumeration enu = request.getHeaderNames(); enu.hasMoreElements(); SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "Header [" + s + "]=" + request.getHeader(s)))
                s = enu.nextElement().toString();

            SystemParameter.log(sid, TechCommonKeys.LOG_DEBUG, "remote (user/adabg/host): " + request.getRemoteUser() + "/" + request.getRemoteAddr() + "/" + request.getRemoteHost());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        UserSession usrSession = (UserSession)session.getAttribute("UserSession_" + sid);
        if(usrSession == null)
            usrSession = new UserSession(session.getId(), sid);
        String key;
        for(Enumeration enu = request.getParameterNames(); enu.hasMoreElements(); usrSession.setFormAttribute(key, request.getParameterValues(key), true))
            key = (String)enu.nextElement();

        return usrSession;
    }

    public void saveUserSession(HttpSession session, UserSession usrSession)
    {
        usrSession.clearTmp();
        session.setAttribute("UserSession_" + usrSession.getSID(), usrSession);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        CommandWrapper wrapper = null;
        UserSession usrSession = null;
        try
        {
            try
            {
                HttpSession session = getSession(request);
                usrSession = getUserSession(session, request);
                beforeGetCommand(usrSession, request, response);
                AbstractCommand command = getCommand(usrSession);
                beforeExecuteCommand(command, usrSession, request, response);
                wrapper = command.execute(usrSession);
                setCookies(usrSession, response);
                saveUserSession(session, usrSession);
                afterExecuteCommand(command, usrSession, request, response, wrapper);
                SystemParameter.log(usrSession.getSID(), TechCommonKeys.LOG_DEBUG, "next page: " + wrapper.getNextPage());
                dispatch(wrapper, response);
            }
            catch(ExceptionInfo ei)
            {
                onInfoErrors(ei, response.getWriter(), request, response);
            }
            catch(ExceptionWarning ew)
            {
                onWarningErrors(ew, response.getWriter(), request, response);
            }
            catch(ExceptionFatal ef)
            {
                onFatalErrors(ef, response.getWriter(), request, response);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(response.getWriter());
        }
        wrapper = null;
        usrSession = null;
    }

    private void setCookies(UserSession usrSession, HttpServletResponse response)
    {
        try
        {
            Hashtable cookies = usrSession.getCookies();
            if(cookies != null)
            {
                Cookie cookie;
                for(Enumeration enu = cookies.elements(); enu.hasMoreElements(); response.addCookie(cookie))
                    cookie = (Cookie)enu.nextElement();

            }
        }
        catch(Exception exception) { }
    }

    protected abstract void beforeGetCommand(UserSession usersession, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ExceptionFatal, ExceptionWarning, ExceptionInfo;

    protected abstract void beforeExecuteCommand(AbstractCommand abstractcommand, UserSession usersession, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ExceptionFatal, ExceptionWarning, ExceptionInfo;

    protected abstract void afterExecuteCommand(AbstractCommand abstractcommand, UserSession usersession, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, CommandWrapper commandwrapper)
        throws ExceptionFatal, ExceptionWarning, ExceptionInfo;

    protected abstract void onFatalErrors(ExceptionFatal exceptionfatal, PrintWriter printwriter, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception;

    protected abstract void onWarningErrors(ExceptionWarning exceptionwarning, PrintWriter printwriter, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception;

    protected abstract void onInfoErrors(ExceptionInfo exceptioninfo, PrintWriter printwriter, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws Exception;

    protected void dispatch(CommandWrapper wrapper, HttpServletResponse response)
        throws ExceptionFatal, ExceptionWarning
    {
        try
        {
        	if(!wrapper.getNextPage().contains("jsp")){
	            if(!wrapper.hasAttachment())
	            {
	                response.setContentType(wrapper.getContentType());
	                response.getWriter().write(RendererXSLtoHTML.getInstance().transform(wrapper.getSID(), wrapper.getXMLData(), wrapper.getNextPage()));
	            } else
	            {
	                dispatchAttachment(wrapper, response);
	            }
        	}
        }
        catch(Exception e)
        {
//            e.printStackTrace();
            throw new ExceptionFatal("Error in dispatch method!", e);
        }
    }

    protected void dispatchAttachment(CommandWrapper wrapper, HttpServletResponse response)
        throws Exception
    {
        response.setContentType(wrapper.getContentType());
        if(wrapper.getAttachmentFileName() != null && !"".equals(wrapper.getAttachmentFileName()))
            response.setHeader("Content-Disposition", "attachment; filename=\"" + wrapper.getAttachmentFileName() + "\"");
        response.getOutputStream().write(wrapper.getAttachmentContent());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        processRequest(request, response);
    }

    public String getServletInfo()
    {
        return "TechEngine systems entry point";
    }

    static
    {
        System.out.println("Welcome to TechEngine 1.2.0a - build 10/2017");
        System.out.println("============================================");
        System.out.println("total memory:" + Runtime.getRuntime().totalMemory() + " bytes");
        System.out.println(new Date());
        System.out.println("developed by: Fabio J Silva Jr - fabiojsjr@gmail.com");
    }
}