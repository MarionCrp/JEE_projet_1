package notes_absence.controler;

import java.io.IOException;
import java.util.Collection;
import java.util.*;

import aideProjet.*;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	private String urlModifEtudiant;
	private String urlModifList;
	private String urlMatiere;
	
	private HttpSession maSession;

	// INIT
	public void init() throws ServletException {
		urlList = getServletConfig().getInitParameter("urlList");
		urlDetail = getServletConfig().getInitParameter("urlDetail");
		urlModifEtudiant = getServletConfig().getInitParameter("urlModifEtudiant");
		urlModifList = getServletConfig().getInitParameter("urlModifList");
		urlMatiere = getServletConfig().getInitParameter("urlMatiere");

		GestionFactory.open();
		generateDataInDb();
	}
	
	@Override
	public void destroy() {
		super.destroy();

		// Fermeture de la factory
		GestionFactory.close();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		maSession = request.getSession();

		// On r�cup�re la m�thode d'envoi de la requ�te
		String methode = request.getMethod().toLowerCase();

		// On r�cup�re l'action � ex�cuter
		String action = request.getPathInfo();
		String method = request.getMethod().toLowerCase();
		if (action == null) {
			action = "/detail";
		}

		// Ex�cution action
		if (method.equals("get") && action.equals("/list")) {
			maSession.setAttribute("previous_page", action);
			doList(request, response);

		} else if (method.equals("get") && action.equals("/detail")) {
			maSession.setAttribute("previous_page", action);
			doDetail(request, response);

		} else if (method.equals("post") && action.equals("/modifEtudiant")) {
			doModifEtudiant(request, response);

		} else if (method.equals("post") && action.equals("/modifList")) {
			doModifList(request, response);

		} else if (method.equals("get") && action.equals("/matiere")) {
			maSession.setAttribute("previous_page", action);
			doMatiere(request, response);

		} else if (method.equals("get") && action.equals("/ajoutAbsence")) {

			doAjoutAbsence(request, response);
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
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();
		
		Collection<Etudiant> etudiants;
		
		String formation_param = request.getParameter("formation");
		/*if( formation_param == null || formation_param.isEmpty()){
			etudiants = EtudiantDAO.getAll();
		} else {
			Formation choosen_formation = FormationDAO.getById(Integer.parseInt(formation_param));
			if(choosen_formation == null) etudiants = EtudiantDAO.getAll();
			else{
				etudiants = choosen_formation.getEtudiants();
			}
		}*/
		etudiants = EtudiantDAO.getAll();

		List<Formation> formations = FormationDAO.getAll();
		
		// Si une formation est choisie on l'affecte en tant que param�tre de requ�te. Sinon on lui met comme valeur -1.
		Integer choosen_formation_id;
		if(request.getParameterMap().containsKey("formation")){
			choosen_formation_id = Integer.parseInt(request.getParameter("formation"));
		} else {
			choosen_formation_id = -1;
		}
		
		request.setAttribute("choosen_formation_id", choosen_formation_id);
		request.setAttribute("formations", formations);
		request.setAttribute("etudiants", etudiants);
		

		loadJSP(urlList, request, response);
		
		em.close();
	}
	
	private void doMatiere(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();
		
		Collection<Etudiant> etudiants = EtudiantDAO.getAll();
		request.setAttribute("etudiants", etudiants);
		loadJSP(urlMatiere, request, response);
		em.close();
	}

	//
	private void doDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();

		Etudiant etudiant = new Etudiant();
		etudiant = EtudiantDAO.retrieveById(Integer.parseInt(request.getParameter("id")));
		List<Formation> formations = FormationDAO.getAll();
		request.setAttribute("etudiant", etudiant);
		request.setAttribute("formations", formations);
		if (etudiant == null) {
			 etudiant = new Etudiant();
			 doList(request, response);
		 } else {
			 loadJSP(urlDetail, request, response);
		 }
		
		em.close();
	}
	
	private void doModifEtudiant(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		// On r�cup�re l'�tudiant en base de donn�e.
		Etudiant new_etu = updateEtudiant(request, Integer.parseInt(request.getParameter("id")));
		if(new_etu == null){
			doList(request, response);
		} else {
			doDetail(request, response);
		}
	}
	
	private void doModifList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// On r�cup�re l'�tudiant en base de donn�e.
		String[] ids = request.getParameterValues("id");
		for(String id : ids){
			Etudiant etu = updateEtudiant(request, Integer.parseInt(id));
		}

		doList(request, response);
		
		}
	
	private void doAjoutAbsence(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String referer = request.getHeader("Referer");

		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();	
		
		Etudiant etudiant = new Etudiant();
		etudiant = EtudiantDAO.retrieveById(Integer.parseInt(request.getParameter("id")));
		etudiant.addAbsence();
		if (maSession.getAttribute("previous_page").equals("/matiere")) {
			doMatiere(request, response);
		} else if (maSession.getAttribute("previous_page").equals("/detail")) {
			doDetail(request, response);
		} else {
			doList(request, response);
		}
		
		em.close();
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
	
	private Etudiant updateEtudiant( HttpServletRequest request, int id){
		Etudiant etudiant = EtudiantDAO.retrieveById(id);
		// On affecte les donn�es envoy�es par le formulaire � l'�tudiant
		System.out.println(request.getParameter("nom"));
		if(request.getParameter("nom") != null){
			etudiant.setNom(request.getParameter("nom"));
		}
		if(request.getParameter("prenom") != null){
			etudiant.setPrenom(request.getParameter("prenom"));
		}
		if(request.getParameter("formation") != null){
			Formation formation = FormationDAO.getById(Integer.parseInt(request.getParameter("formation")));
			etudiant.setFormation(formation);
		}
		if(request.getParameter("absence[" + id + "]") != null){
			int nb_absence = Integer.parseInt(request.getParameter("absence[" + id + "]"));
			etudiant.setNbAbsence(nb_absence);
		}
		
		// On modifie l'�tudiant en base de donn�es.
		return EtudiantDAO.update(etudiant);
	}
	
	private void generateDataInDb(){
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();
		Formation simo = new Formation();
		Formation aspe = new Formation();
		Formation big_data = new Formation();
		if(FormationDAO.getAll().size() == 0){
			simo = FormationDAO.create("SIMO");
			aspe = FormationDAO.create("ASPE");
			big_data = FormationDAO.create("BIG DATA");
		} else {
			simo = FormationDAO.getByIntitule("SIMO");
			aspe = FormationDAO.getByIntitule("ASPE");
			big_data = FormationDAO.getByIntitule("BIG DATA");
		}
		
		if(EtudiantDAO.getAll().size() == 0){
			// Création des étudiants
			Etudiant kevin = EtudiantDAO.create("K�vin", "Coissard", simo);
			Etudiant elodie = EtudiantDAO.create("Elodie", "Goy", simo);
			Etudiant david = EtudiantDAO.create("David", "Cotte", simo);
			Etudiant milena = EtudiantDAO.create("Mil�na", "Charles", simo);
			
			Etudiant jeremie = EtudiantDAO.create("J�r�mie", "Guillot", aspe);
			Etudiant martin = EtudiantDAO.create("Martin", "Bolot", aspe);
			Etudiant yoann = EtudiantDAO.create("Yoann", "Merle", aspe);
			Etudiant jean = EtudiantDAO.create("Jean", "Debard", aspe);

			Etudiant amandine = EtudiantDAO.create("Amandine", "Henriet", big_data);
		}
		
		em.close();
	}
}
