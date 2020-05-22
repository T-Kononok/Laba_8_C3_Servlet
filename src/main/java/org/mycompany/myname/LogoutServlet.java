package org.mycompany.myname;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class LogoutServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = (String) request.getSession().getAttribute("name");
        if (name!=null) {
            ChatUser aUser = activeUsers.get(name);
            if (aUser.getSessionId().equals(request.getSession().getId())) {
                synchronized (activeUsers) {
                    activeUsers.remove(name);
                }
                request.getSession().setAttribute("name", null);
                response.addCookie(new Cookie("sessionId", null));
                response.sendRedirect(response.encodeRedirectURL("/login"));
            } else {
                response.sendRedirect(response.encodeRedirectURL("view.htm"));
            }
        } else {
            response.sendRedirect(response.encodeRedirectURL("view.htm"));
        }
    }
}

