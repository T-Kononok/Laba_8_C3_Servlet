package org.mycompany.myname;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageListServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.println("<html>" +
                "<head>" +
                    "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>" +
                "<meta http-equiv='refresh' content='2'>" +
                "</head>");
        pw.println("<body>");
        for (ChatMessage aMessage : messages) {
            if (aMessage.getMessage().charAt(0)=='@') {
                if (request.getSession().getAttribute("name").equals
                        (aMessage.getMessage().substring(1,aMessage.getMessage().lastIndexOf('@')))){
                    String messageString = new String(aMessage.getMessage().replace(
                            aMessage.getMessage().substring(0, aMessage.getMessage().lastIndexOf('@') + 1),
                            ""));
                    pw.println("<div><pre>      Личное сообщение от <strong>" + aMessage.getAuthor().getName() + "</strong>:<br>      " +
                            messageString + "</pre></div>");
                }
                if (request.getSession().getAttribute("name").equals(aMessage.getAuthor().getName())){
                    String messageString = new String(aMessage.getMessage().replace(
                            aMessage.getMessage().substring(0, aMessage.getMessage().lastIndexOf('@') + 1),
                            ""));
                    pw.println("<div><pre>      Личное сообщение для <strong>" +
                            aMessage.getMessage().substring(1,aMessage.getMessage().lastIndexOf('@')) +
                            "</strong>:<br>      " +
                            messageString + "</pre></div>");
                }
            }
            else
                pw.println("<div><pre>      <strong>" + aMessage.getAuthor().getName() + "</strong>:<br>      " +
                        aMessage.getMessage() + "</pre></div>");
        }
        pw.println("</body></html>");
    }
}

