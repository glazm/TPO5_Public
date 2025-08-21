import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;

public class GetRequestServlet extends HttpServlet {
    private ServletContext context;
    private String searchDataServlet;

    public void init(){
        context = getServletContext();

        searchDataServlet = context.getInitParameter("searchDataServlet");
    }

    public void serviceRequest(HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        request.setCharacterEncoding("ISO-8859-2");

        HttpSession session = request.getSession();


        response.setCharacterEncoding("ISO-8859-2");
        PrintWriter out = response.getWriter();

        out.println("<center><h2>Aplikacja WEB</center></h2><hr>");
        out.println("<form method=\"post\">");
        out.println("Rodzaj samochodu<br>");
        out.println("<input type=\"text\" size=\"30\" name=\"rodzaj\"><br>");
        out.println("<br><input type=\"submit\" value=\"Wyszukaj\">");
        out.println("</form>");

        String param = request.getParameter("rodzaj");
        if(param == null) return;

        if(param!=null) {
            out.println("Wyszukujesz " + param);
            session.setAttribute("rodzaj", param);
        }

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
