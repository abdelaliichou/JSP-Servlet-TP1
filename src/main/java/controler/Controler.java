package controler;

import facade.FacadeParis;
import facade.exceptions.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.Match;
import modele.Pari;
import modele.Utilisateur;

import java.io.IOException;
import java.util.Collection;

// pour dire qur n'import quelle url qui est de type /tp1/.. sera capter par cette classe

@WebServlet(name = "controler", urlPatterns = "/pel/*")
public class Controler extends HttpServlet {

    private static final String HOME = "home";
    private static final String MENU = "menu";
    private static final String MESPARIS = "mesparis";
    private static final String CONNEXION = "connexion";
    private static final String PARISOUVERT = "parisouvert";
    private static final String PARIER = "parier";
    private transient FacadeParis facadeParis;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // ici on a just charger notre local variable "facadeParis" par ce qu'on a charger dans attributs
        // dans la class StartupListener, on ete capable de faire directement comme la suite =>
        // this.facadeParis = new FacadeParisStaticImpl();

        // on a fait ce code la pour ne pas le re-fait 2 fois, une dans DOPOST et l'autre dans DOGET,
        this.facadeParis = (FacadeParis) getServletContext().getAttribute("ParisFacade");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String[] path = req.getRequestURI().split("/");
        String cle = path[path.length - 1];
        String destination = "/WEB-INF/pages/Home.jsp";


        if (path.length > 2) {
            if (HOME.equals(cle)) {
                destination = "/WEB-INF/pages/Home.jsp";

            } else if ("logout".equals(cle)) {
                this.disconnect(req);
                destination = "/WEB-INF/pages/Home.jsp";

            } else if (MENU.equals(cle) && req.getSession().getAttribute("user") != null) {
                destination = "/WEB-INF/pages/Menu.jsp";

            } else if (PARISOUVERT.equals(cle) && req.getSession().getAttribute("user") != null) {
                Collection<Match> matchs = facadeParis.getMatchsPasCommences();
                req.setAttribute("matchs", matchs);
                destination = "/WEB-INF/pages/Parisouvert.jsp";

            } else if (PARIER.equals(cle) && req.getSession().getAttribute("user") != null) {
                String id = req.getParameter("id");
                destination = "/WEB-INF/pages/Parier.jsp";
                String error = "";

                if (id != null) {
                    Match match = facadeParis.getMatch(Long.parseLong(id));
                    req.getSession().setAttribute("match", match);

                } else {
                    String verdict = req.getParameter("verdict");
                    String montant = req.getParameter("montant");

                    try {
                        facadeParis.parier(((Utilisateur) req.getSession().getAttribute("user")).getLogin(), ((Match) req.getSession().getAttribute("match")).getIdMatch(), verdict, Double.parseDouble(montant));
                        destination = "/WEB-INF/pages/Menu.jsp";

                    } catch (MontantNegatifOuNulException e) {
                        error = "Vous devez saisir un montant positif pour votre pari !";

                    } catch (ResultatImpossibleException e) {
                        error = "Ce résultat n'est pas possible !";

                    } catch (MatchClosException e) {
                        error = "Ce match est clos, vous ne pouvez plus parier !";

                    }
                }
                req.setAttribute("error", error);

            } else if (MESPARIS.equals(cle) && req.getSession().getAttribute("user") != null) {
                Collection<Pari> paris = facadeParis.getMesParis(((Utilisateur) req.getSession().getAttribute("user")).getLogin());
                req.setAttribute("paris", paris);
                req.setAttribute("error", "");
                destination = "/WEB-INF/pages/Mesparis.jsp";

            } else if ("annuler".equals(cle) && req.getSession().getAttribute("user") != null) {
                String id = req.getParameter("id");

                try {
                    facadeParis.annulerPari(((Utilisateur) req.getSession().getAttribute("user")).getLogin(), Long.parseLong(id));
                } catch (OperationNonAuthoriseeException e) {
                    req.setAttribute("error", "Vous ne pouves pas annuler ce pari !");
                }
                Collection<Pari> paris = facadeParis.getMesParis(((Utilisateur) req.getSession().getAttribute("user")).getLogin());
                req.setAttribute("paris", paris);
                destination = "/WEB-INF/pages/Mesparis.jsp";

            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                destination = "/WEB-INF/pages/Notfound.jsp";

            }
            req.getRequestDispatcher(destination).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] path = req.getRequestURI().split("/");
        if (CONNEXION.equals(path[2])) {
            // this two parameters exists in the jsp file in the name field => " name=Pseudo " and " name=Password "
            String pseudo = req.getParameter("pseudo");
            String email = req.getParameter("password");
            boolean isError = false;
            String msgError = "";

            try {
                Utilisateur user = this.facadeParis.connexion(pseudo, email);
                req.getSession().setAttribute("user", user);
            }
            catch (UtilisateurDejaConnecteException e) {
                isError = true;
                msgError = "Utilisateur déjà connecté";
            }
            catch (InformationsSaisiesIncoherentesException e) {
                isError = true;
                msgError = "Le pseudo est obligatoire de taile min de 2, le mot de passe est obligatoire de taille 2 min";
            }

            if (isError) {
                req.setAttribute("errrequestor", msgError);
                req.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/pages/Menu.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/Notfound.jsp").forward(req, resp);
        }
    }

    private void disconnect(HttpServletRequest request) {
        HttpSession session = request.getSession();
        this.facadeParis.deconnexion(((Utilisateur) session.getAttribute("user")).getLogin());
        session.removeAttribute("user");
        // detruire tous l'envirenement de session de cet utilisateur
        session.invalidate();
    }
}
