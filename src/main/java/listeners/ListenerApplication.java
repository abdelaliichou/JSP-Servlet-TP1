package listeners;

import facade.FacadeParisStaticImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ListenerApplication implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // au moment de deployment de notre application, listener cree notre facade et la stocker dans la variable d'application
        sce.getServletContext().setAttribute("ParisFacade", new FacadeParisStaticImpl());
    }
}
