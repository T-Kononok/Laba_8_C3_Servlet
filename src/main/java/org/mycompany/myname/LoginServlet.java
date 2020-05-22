package org.mycompany.myname;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class LoginServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;
    private int sessionTimeout = 10*60;

    public void init() throws ServletException {
        super.init();
        String value = getServletConfig().getInitParameter("SESSION_TIMEOUT");
        if (value!=null) {
            sessionTimeout = Integer.parseInt(value);
        }
    }
    // Метод будет вызван при обращении к сервлету HTTP-методом GET
    // т.е. когда пользователь просто открывает адрес в браузере
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = (String)request.getSession().getAttribute("name");
        String errorMessage = (String)request.getSession().getAttribute("error");
        String previousSessionId = null;
        if (name==null) {
            if (request.getCookies()!=null)
                for (Cookie aCookie: request.getCookies()) {
                    if (aCookie.getName().equals("sessionId")) {
                        previousSessionId = aCookie.getValue();
                        break;
                    }
                }
            if (previousSessionId!=null) {
                for (ChatUser aUser: activeUsers.values()) {
                    if
                    (aUser.getSessionId().equals(previousSessionId)) {
                        name = aUser.getName();
                        aUser.setSessionId(request.getSession().getId());
                    }
                }
            }
        }
        if (name!=null && !name.equals("")) {
            errorMessage = processLogonAttempt(name, request, response);
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.println("<html>" +
                    "<head>" +
                        "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>" +
                        "<title>Чатик: вход</title>" +
                    "</head>"+
                    "<body>");
        if (errorMessage!=null) {
            pw.println("<p><font color='red'>" + errorMessage + "</font></p>");
        }
        pw.println("<form action='/login' method='post'>" +
                "<p align='center'> Имя: " +
                "<input type='text' name='name' value=''>" +
                "<input type='submit' value='Войти'>");
        pw.println("</form></body></html>");
        request.getSession().setAttribute("error", null);
    }
    // Метод будет вызван при обращении к сервлету HTTP-методом POST
// т.е. когда пользователь отправляет сервлету данные
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("name");
        String errorMessage;
        if (name == null || name.equals("")) {
            errorMessage = "Имя пользователя не может быть пустым!";
        } else {
            errorMessage = processLogonAttempt(name, request, response);
        }
        if (errorMessage!=null) {
            request.getSession().setAttribute("name", null);
            request.getSession().setAttribute("error", errorMessage);
            response.sendRedirect(response.encodeRedirectURL("/login"));
        }
    }
    // Возвращает текстовое описание возникшей ошибки или null
    private String processLogonAttempt(String name, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String sessionId = request.getSession().getId();
        ChatUser aUser = activeUsers.get(name);
        if (aUser==null) {
            aUser = new ChatUser(name, Calendar.getInstance().getTimeInMillis(), sessionId);
            synchronized (activeUsers) {
                activeUsers.put(aUser.getName(), aUser);
            }
        }
        if (aUser.getSessionId().equals(sessionId) ||
                aUser.getLastInteractionTime()<(Calendar.getInstance().getTimeInMillis()-
                        sessionTimeout*1000)) {
            request.getSession().setAttribute("name", name);
            aUser.setLastInteractionTime(Calendar.getInstance().getTimeInMillis());
            Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
            sessionIdCookie.setMaxAge(60*60*24*365);
            response.addCookie(sessionIdCookie);
            response.sendRedirect(response.encodeRedirectURL("view.htm"));
            return null;
        } else {
            return "<p align='center'>Извините, но имя <strong>" + name + "</strong> уже кем-то занято. Пожалуйста выберите другое имя!";
        }
    }
}
