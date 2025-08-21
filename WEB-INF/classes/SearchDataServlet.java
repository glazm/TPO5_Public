import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SearchDataServlet extends HttpServlet {

    private ServletContext context;
    private Command command;
    private String prepareResultServlet;
    private String getRequestServlet;
    private Object lock = new Object();

    public void init(){
        context = getServletContext();

        prepareResultServlet = context.getInitParameter("prepareResultServlet");
        getRequestServlet = context.getInitParameter("getRequestServlet");
        String findCars = context.getInitParameter("findCars");

        try{
            Class newFindCars = Class.forName(findCars);
            command = (Command) newFindCars.newInstance();
        }catch (Exception e){
            throw new MyExceptions("Can't create class "+findCars);
        }

    }
    public void serviceRequest(HttpServletRequest request,
                               HttpServletResponse response)
                                throws ServletException, IOException
    {
        response.setContentType("text/html");

        RequestDispatcher disp = context.getRequestDispatcher(getRequestServlet);
        disp.include(request,response);

        HttpSession session = request.getSession();

        String type = (String) session.getAttribute("rodzaj");

        if(type == null) return;

        command.setParameter("rodzaj", type);

        Lock mainLock = new ReentrantLock();
        mainLock.lock();

        command.execute();

        List results = (List) command.getResults();

        session.setAttribute("Results", results);
        session.setAttribute("Lock", mainLock);

        disp = context.getRequestDispatcher(prepareResultServlet);
        disp.forward(request,response);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        serviceRequest(request,response);
    }
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        serviceRequest(request,response);
    }
}
