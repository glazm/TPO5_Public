import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.concurrent.locks.Lock;

public class PrepareResultServlet extends HttpServlet {

    public void serviceRequest(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        ServletContext context = getServletContext();

        String getRequestServlet = context.getInitParameter("getRequestServlet");
        RequestDispatcher disp = context.getRequestDispatcher(getRequestServlet);
        disp.include(request,response);


        HttpSession session = request.getSession();
        Lock mainLock = (Lock) session.getAttribute("Lock");
        mainLock.unlock();
        String type = (String) session.getAttribute("rodzaj");


        PrintWriter out = response.getWriter();

        out.println("<center><h2>Aplikacja WEB</center></h2><hr>");
        out.println("<form method=\"post\">");
        out.println("Rodzaj samochodu<br>");
        out.println("<input type=\"text\" size=\"30\" name=\"rodzaj\"><br>");
        out.println("<br><input type=\"submit\" value=\"Wyszukaj\">");
        out.println("</form>");

        out.println("<table cellpadding=\"3\" border=\"1\"><tr><th>Nazwa</th>");
        out.println("<th>Rocznik</th>");
        out.println("<th>Pochodzenie</th></tr>");
        out.println("<tr><td>Dane1</td>");
        out.println("<td>Dane1</td>");
        out.println("<td>Dane1</td></tr>");
        out.println("<tr><td>Dane1</td>");
        out.println("<td>Dane1</td>");
        out.println("<td>Dane1</td></tr>");
        out.println("</table>");


        if(type!= null) out.println("<h2>"+type+"</h2>");

    }

    public void doGet(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        serviceRequest(request,response);
    }
    public void doPost(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        serviceRequest(request,response);
    }
}
