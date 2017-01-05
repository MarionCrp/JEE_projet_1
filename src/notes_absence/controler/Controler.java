package notes_absence.controler;

import java.io.IOException;
import java.util.Collection;
import java.util.*;

import aideProjet.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controler
 */
@WebServlet("/Controler")
public class Controler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controler() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String urlList;
	private String urlDetail;

	// INIT
	public void init() throws ServletException {
		urlList = getServletConfig().getInitParameter("urlList");
		urlDetail = getServletConfig().getInitParameter("urlDetail");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// On r�cup�re la m�thode d'envoi de la requ�te
		String methode = request.getMethod().toLowerCase();

		// On r�cup�re l'action � ex�cuter
		String action = request.getPathInfo();
		if (action == null) {
			action = "/detail";
			System.out.println("action == null");
		}

		// Ex�cution action
		if (action.equals("/list")) {
			doList(request, response);

		} else if (action.equals("/detail")) {

			doDetail(request, response);

		} else {
			// ROOT PATH :
			doList(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void doList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Etudiant> etudiants = GestionFactory.getEtudiants();
		request.setAttribute("etudiants", etudiants);
		loadJSP(urlList, request, response);

	}

	//
	private void doDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Etudiant etudiant = new Etudiant();
		etudiant = GestionFactory.getEtudiantById(Integer.parseInt(request.getParameter("id")));

		 if (etudiant == null) {
			 etudiant = new Etudiant();
			 doList(request, response);
		 } else {
			 loadJSP(urlDetail, request, response);
		 }
	}

	/**
	 * Charge la JSP indiqu�e en param�tre
	 * 
	 * @param url
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadJSP(String url, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// L'interface RequestDispatcher permet de transf�rer le contr�le � une
		// autre servlet
		// Deux m�thodes possibles :
		// - forward() : donne le contr�le � une autre servlet. Annule le flux
		// de sortie de la servlet courante
		// - include() : inclus dynamiquement une autre servlet
		// + le contr�le est donn� � une autre servlet puis revient � la servlet
		// courante (sorte d'appel de fonction).
		// + Le flux de sortie n'est pas supprim� et les deux se cumulent

		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(url);
		//RequestDispatcher rheader = getServletContext().getRequestDispatcher(\"header\");

		//rheader.include(request, response);
		rd.forward(request, response);
	}
}
