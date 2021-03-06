package notes_absence.controler;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

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

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * Servlet implementation class Controler
 */
@WebServlet("/Controler")
public class Controler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Flash flash_error = new Flash("danger");
	private static Flash flash_success = new Flash("success");
	private static final String[] ACCEPTED_PARAMS = { "formation", "matiere", "etudiant", "coefficient" };

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controler() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String urlList;
	private String urlDetail;
	private String urlMatiere;

	private HttpSession maSession;

	/******************************************************** INIT ******************************************************/
	public void init() throws ServletException {
		urlList = getServletConfig().getInitParameter("urlList");
		urlDetail = getServletConfig().getInitParameter("urlDetail");
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
	/******************************************************** doGET ******************************************************/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		maSession = request.getSession();
		flash_error.destroyAllMessages();
		flash_success.destroyAllMessages();

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

		} else if (method.equals("post") && action.equals("/modifList")) {
			doModifList(request, response);

		} else if (method.equals("get") && action.equals("/detail")) {
			maSession.setAttribute("previous_page", action);
			doDetail(request, response);

		} else if (method.equals("post") && action.equals("/modifEtudiant")) {
			doModifEtudiant(request, response);

		} else if (method.equals("get") && action.equals("/ajoutAbsence")) {
			doAjoutAbsence(request, response);

		} else if (method.equals("get") && action.equals("/supprimerAbsence")) {
			doSupprimerAbsence(request, response);

		} else if (action.equals("/matiere")) {
			doMatiere(request, response);

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

	/******************************************************** LIST ******************************************************/

	private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();

		List<Formation> formations = FormationDAO.getAll();

		// Si une formation est choisie on l'affecte en tant que param�tre de
		// requ�te. Sinon on lui met comme valeur -1.
		Integer choosen_formation_id;
		Float moyenne_generale;

		List<Etudiant> etudiants;
		// On test si le param�tre formation est pr�sent dans l'url (s'il est �
		// -1 c'est que l'utilisateur � choisi "tous les �tudiants"))
		if ((request.getParameterMap().containsKey("formation") && !request.getParameter("formation").equals("-1"))) {
			try {
				choosen_formation_id = Integer.parseInt(request.getParameter("formation"));
				Formation choosen_formation = FormationDAO.getById(choosen_formation_id);
				etudiants = choosen_formation.getEtudiants();

			} catch (Exception ex) {
				flash_error.addMessage("La formation demand�e n'existe pas");
				etudiants = EtudiantDAO.getAll();
				choosen_formation_id = -1;
			}
		} else {
			etudiants = EtudiantDAO.getAll();
			choosen_formation_id = -1;
		}

		// On enregistre la page pr�c�dente (pour la page d'�dition d'un �l�ve)
		moyenne_generale = Services.calculeMoyenneGenerale(etudiants);
		String previous_matiere_filtered_link = (String) maSession.getAttribute("previous_matiere_filtered_link");
		if (previous_matiere_filtered_link == null)
			previous_matiere_filtered_link = "matiere";

		// On enregistre la formation en session pour rediriger l'utilisateur
		// sur la bonne page lors de son retour sur la liste.
		maSession.setAttribute("choosen_formation_id", choosen_formation_id);
		maSession.setAttribute("previous_matiere_filtered_link", previous_matiere_filtered_link);
		maSession.setAttribute("previous_link", get_current_url(request));
		maSession.setAttribute("previous_list_link", get_current_url(request));
		request.setAttribute("choosen_formation_id", choosen_formation_id);
		request.setAttribute("formations", formations);
		request.setAttribute("etudiants", etudiants);
		request.setAttribute("flash_error", flash_error);
		request.setAttribute("flash_success", flash_success);
		request.setAttribute("moyenne_generale", moyenne_generale);
		loadJSP(urlList, request, response);

		em.close();
	}

	/********************************************************
	 * MODIF LIST
	 ******************************************************/
	private void doModifList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// On r�cup�re les ids des �tudiants via le post et on les modifie en
		// bouclant sur chacun

		String[] ids = request.getParameterValues("id");

		for (String id : ids) {
			try {
				Etudiant etu = updateEtudiant(request, Integer.parseInt(id));
			} catch (Exception ex) {
				flash_error.addMessage("Une erreur � �t� rencontr�e lors de l'�dition des absences");
			}
		}
		// On retourne la formation choisie si c'est le cas :
		if (request.getParameterMap().containsKey("formation") && request.getParameter("formation") != "-1") {
			request.setAttribute("formation", request.getParameter("formation"));
		}

		if (flash_error.hasNoMessage()) {
			flash_success.addMessage("Les absences ont �t� modifi�es avec succ�s");
		}
		doList(request, response);
	}

	/******************************************************** MATIERE ******************************************************/

	private void doMatiere(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();

		/***** Initialisation des variables ****/
		List<Formation> formations = FormationDAO.getAll();
		Integer choosen_formation_id;
		Integer choosen_coefficient_id;
		List<Coefficient> coefficients = new ArrayList<Coefficient>();
		List<Etudiant> etudiants = new ArrayList<Etudiant>();
		Coefficient coefficient = new Coefficient();
		HashMap<Etudiant, Float> liste_notes = new HashMap<Etudiant, Float>();

		/***** Teste des donn�es r�cup�r�es dans le get *****/
		// -1 si invalide.
		if (request.getParameterMap().containsKey("formation")) {
			try {
				choosen_formation_id = Integer.parseInt(request.getParameter("formation"));
			} catch (NumberFormatException ex) {
				flash_error.addMessage("La formation demand�e n'a pas �t� trouv�e");
				choosen_formation_id = -1;
			}
		} else {
			choosen_formation_id = -1;
		}

		if (request.getParameterMap().containsKey("coefficient")) {
			try {
				choosen_coefficient_id = Integer.parseInt(request.getParameter("coefficient"));
			} catch (NumberFormatException ex) {
				flash_error.addMessage("La mati�re demand�e n'a pas �t� trouv�e");
				choosen_coefficient_id = -1;
			}
		} else {
			choosen_coefficient_id = -1;
		}

		/***** Modification des notes ****/
		if (request.getParameterMap().containsKey("updateMatiere")) {
			String[] ids = request.getParameterValues("id");
			for (String id : ids) {
				Note note = updateNote(request, Integer.parseInt(id));
				Etudiant etu = updateEtudiant(request, Integer.parseInt(id));
			}
		}

		/****
		 * On fait les traitements sur la base de donn�es en fonction des
		 * param�tres r�cup�r�s
		 */
		if (choosen_formation_id != -1) {
			Formation choosen_formation = FormationDAO.getById(choosen_formation_id);
			if (choosen_formation == null) {
				choosen_formation_id = -1;
				flash_error.addMessage("La formation demand�e n'a pas �t� trouv�e");
			} else {
				coefficients = CoefficientDAO.getCoefficientByFormation(choosen_formation);
				etudiants = choosen_formation.getEtudiants();
				if (choosen_coefficient_id != -1) {
					coefficient = CoefficientDAO.getById(choosen_coefficient_id);
					if (coefficient == null) {
						// On invalide le coefficient car l'id demand�e ne
						// correspond pas � un objet
						choosen_coefficient_id = -1;
						flash_error.addMessage("La mati�re demand�e n'a pas �t� trouv�e");
						coefficient = new Coefficient(); // Cependant le JSP
															// attends un
															// coefficient, on
															// le r�initialise
															// donc.
					} else {
						for (Etudiant etudiant : etudiants) {
							Note noteEtudiant = NoteDAO.getByEtudiantAndMatiere(etudiant, coefficient.getMatiere());
							Float resultat = noteEtudiant == null ? 0 : noteEtudiant.getResultat();
							liste_notes.put(etudiant, resultat);
						}
					}
				}
			}
		}

		/**** Modification du coefficient ****/
		if (request.getParameterMap().containsKey("updateMatiere")) {
			int id = Integer.parseInt(request.getParameter("coefficient_id"));
			// Si la valeur du coefficient envoy� n'est pas correcte, il prend
			// une valeur par d�fault qui g�n�rera une erreur.
			int valeur = -1;
			if (request.getParameterMap().containsKey("coefficient_value")
					&& !request.getParameter("coefficient_value").isEmpty()) {
				valeur = Integer.parseInt(request.getParameter("coefficient_value"));
			}

			boolean actif;
			if (request.getParameterMap().containsKey("coefficient_active")
					&& request.getParameter("coefficient_active").equals("on")) {
				actif = true;
			} else {
				actif = false;
			}
			updateCoefficient(request, id, valeur, actif);
			coefficient = CoefficientDAO.getById(id);
		}

		// On enregistre la formation en session pour rediriger l'utilisateur
		// sur la bonne page lors de son retour sur la liste
		// (si il d�cide de consulter la fiche de l'�l�ve par exemple.
		maSession.setAttribute("choosen_formation_id", choosen_formation_id);
		maSession.setAttribute("previous_matiere_filtered_link", get_current_url(request));
		maSession.setAttribute("previous_link", get_current_url(request));

		request.setAttribute("choosen_formation_id", choosen_formation_id);
		request.setAttribute("choosen_coefficient_id", choosen_coefficient_id);
		request.setAttribute("formations", formations);
		request.setAttribute("coefficients", coefficients);
		request.setAttribute("coefficient", coefficient);
		request.setAttribute("etudiants", etudiants);
		request.setAttribute("liste_notes", liste_notes);
		request.setAttribute("flash_error", flash_error);
		request.setAttribute("flash_success", flash_success);

		loadJSP(urlMatiere, request, response);

		em.close();
	}

	/********************************************************
	 * UPDATE VALEUR COEFFICIENT
	 ******************************************************/
	private void updateCoefficient(HttpServletRequest request, int id, int valeur, boolean actif) {
		// On r�cup�re le coefficient en base de donn�e.
		if (valeur > 0) {
			Coefficient coeff = CoefficientDAO.getById(id);
			coeff.setValeur(valeur);
			coeff.setActif(actif);
			try {
				CoefficientDAO.update(coeff);
				flash_success.addMessage("La mati�re a bien �t� mise � jour");
			} catch (Exception ex) {
				flash_error.addMessage("Une erreur a �t� rencontr�e dans la mise � jour de la mati�re");
			}
		} else {
			flash_error.addMessage("La valeur du coefficient n'est pas correcte");
		}
	}

	/********************************************************
	 * UPDATE NOTE
	 ******************************************************/
	private Note updateNote(HttpServletRequest request, int id) {
		float valeur_note = -1; // Par d�faut est initialis� avec une valeur
								// incorrecte.

		// On r�cup�re la valeur de la note et l'�tudiant concern�
		if (request.getParameterMap().containsKey("note[" + id + "]")
				&& !request.getParameter("note[" + id + "]").isEmpty()) {
			valeur_note = Float.parseFloat(request.getParameter("note[" + id + "]"));
			;
		} else {
			flash_error.addMessage("Une note doit �tre un nombre d�cimal sup�rieur ou �gal � 0");
		}

		Etudiant etu = EtudiantDAO.retrieveById(id);

		// On r�cup�re la mati�re
		Coefficient coeff = CoefficientDAO.getById(Integer.parseInt(request.getParameter("coefficient")));
		Matiere mat = coeff.getMatiere();
		if (mat != null && valeur_note >= 0 && valeur_note <= 20) {
			Note note = new Note();
			note = NoteDAO.getByEtudiantAndMatiere(etu, mat);
			if (note == null) {
				note = NoteDAO.create(etu, mat, valeur_note);
				return note;
			} else {
				note.setResultat(valeur_note);
				return NoteDAO.update(note);
			}
		} else {
			if (valeur_note > 20 || valeur_note < 0) {
				flash_error.addMessage("Une note doit �tre compris entre 0 et 20");
			} else {
				flash_error.addMessage("Une erreur a �t� rencontr�e lors de l'�dition d'une note");
			}
			return null;

		}
	}

	/******************************************************** DETAILS ******************************************************/
	private void doDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();

		Etudiant etudiant = new Etudiant();
		etudiant = EtudiantDAO.retrieveById(Integer.parseInt(request.getParameter("id")));

		if (etudiant == null) {
			flash_error.addMessage("Cet �tudiant n'existe pas");
			// doList(request, response);
			response.sendRedirect("list");
		} else {

			List<Formation> formations = FormationDAO.getAll();

			String choosen_formation_id = "";
			if (maSession.getAttribute("choosen_formation_id") != null) {
				choosen_formation_id = maSession.getAttribute("choosen_formation_id").toString();
			}

			List<Coefficient> active_coefficients = CoefficientDAO
					.getActiveCoefficientByFormation(etudiant.getFormation());

			// Pour rediriger vers la formation choisie sur une page pr�c�dente.

			request.setAttribute("etudiant", etudiant);
			request.setAttribute("formations", formations);
			request.setAttribute("choosen_formation_id", choosen_formation_id);
			request.setAttribute("active_coefficients", active_coefficients);
			request.setAttribute("flash_error", flash_error);
			request.setAttribute("flash_success", flash_success);

			loadJSP(urlDetail, request, response);
		}
		em.close();
	}

	/********************************************************
	 * MODIF ETUDIANT
	 ******************************************************/
	private void doModifEtudiant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// On r�cup�re l'�tudiant en base de donn�e.
		try {
			Etudiant new_etu = updateEtudiant(request, Integer.parseInt(request.getParameter("id")));
			flash_success.addMessage("La fiche de l'�l�ve a �t� modifi�e avec succ�s");
			doDetail(request, response);

		} catch (Exception ex) {
			flash_error.addMessage("Une erreur est survenue lors de l'�dition de la fiche de l'�l�ve");
			doList(request, response);
		}
	}

	/********************************************************
	 * DO AJOUT ABSENCE
	 ******************************************************/
	private void doAjoutAbsence(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		modifAbsence(request, response, "ajouter");
	}

	/********************************************************
	 * DO SUPPRIMER ABSENCE
	 ******************************************************/
	private void doSupprimerAbsence(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		modifAbsence(request, response, "supprimer");
	}

	/*******************************
	 * UPDATE NOMBRE ABSENCE VIA BOUTONS + /- (m�thodes ci dessus)
	 *************************************/
	private void modifAbsence(HttpServletRequest request, HttpServletResponse response, String modif_type)
			throws ServletException, IOException {
		String referer = request.getHeader("Referer");
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();

		Etudiant etudiant = new Etudiant();
		etudiant = EtudiantDAO.retrieveById(Integer.parseInt(request.getParameter("id")));
		if (modif_type.equals("ajouter")) {
			etudiant.addAbsence();
		} else {
			etudiant.supprAbsence();
		}

		EtudiantDAO.update(etudiant);
		if (maSession.getAttribute("previous_page").equals("/detail")) {
			response.sendRedirect("detail?id=" + etudiant.getId());
		} else if (maSession.getAttribute("previous_page").equals("/list")) {
			response.sendRedirect((String) maSession.getAttribute("previous_list_link"));
		}

		em.close();
	}

	/**
	 * /******************************************************** LOAD JSP
	 * ******************************************************
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
		// RequestDispatcher rheader =
		// getServletContext().getRequestDispatcher(\"header\");

		// rheader.include(request, response);
		rd.forward(request, response);
	}

	/********************************************************
	 * UPDATE ETUDIANT
	 ******************************************************/
	private Etudiant updateEtudiant(HttpServletRequest request, int id) {
		Etudiant etudiant = EtudiantDAO.retrieveById(id);
		// On affecte les donn�es envoy�es par le formulaire � l'�tudiant si il
		// r�pond au crit�re ( longueur < 20 et sans chiffre
		if (request.getParameterMap().containsKey("nom")) {
			if (!request.getParameter("nom").isEmpty()) {
				if (request.getParameter("nom").length() < 20) {
					if (request.getParameter("nom").matches(".*\\d+.*")) {
						flash_error.addMessage("Le nom ne peut contenir de chiffre");
					} else {
						etudiant.setNom(request.getParameter("nom"));
					}
				} else {
					flash_error.addMessage("Le nom entr� est trop long");
				}

			} else {
				flash_error.addMessage("Le nom ne peut �tre vide");
			}
		}

		if (request.getParameterMap().containsKey("prenom")) {
			if (!request.getParameter("prenom").isEmpty()) {
				if (request.getParameter("prenom").length() < 20) {
					if (request.getParameter("prenom").matches(".*\\d+.*")) {
						flash_error.addMessage("Le pr�nom ne peut contenir de chiffre");
					} else {
						etudiant.setPrenom(request.getParameter("prenom"));
					}
				} else {
					flash_error.addMessage("Le prenom entr� est trop long");
				}

			} else {
				flash_error.addMessage("Le pr�nom ne peut �tre vide");
			}
		}

		int nb_absence = -1;
		String[] absences_params = { "absence[" + id + "]", "absence" };
		for (String absences_param : absences_params) {
			if (request.getParameterMap().containsKey(absences_param)) {
				if (request.getParameter(absences_param).isEmpty()) {
					flash_error.addMessage("Un champs 'absence' renseign� n'est pas correct");
				} else {
					try {
						nb_absence = Integer.parseInt(request.getParameter(absences_param));
						if (nb_absence >= 0) {
							etudiant.setNbAbsence(nb_absence);
						} else {
							flash_error.addMessage("Le nombre d'absence ne peut �tre n�gatif!");
						}
					} catch (Exception ex) {
						flash_error.addMessage("Une erreur est survenue lors de la modification des absences");
					}
				}
			}
		}

		// On modifie l'�tudiant en base de donn�es.
		return EtudiantDAO.update(etudiant);
	}

	private String get_current_url(HttpServletRequest request) {
		// getPathInfo().substring(1) r�cup�re la m�thode de l'url.
		// request.getQueryString, ses param�tres.
		// Cet url permet de pouvoir revenir sur la page pr�filtr�e des
		// moyennes/notes.
		return request.getPathInfo().substring(1) + "?" + request.getQueryString();
	}

	/********************************************************
	 * GENERATE DATA IN DB
	 ******************************************************/
	private void generateDataInDb() {
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();
		Formation simo = new Formation();
		Formation aspe = new Formation();
		Formation big_data = new Formation();
		if (FormationDAO.getAll().size() == 0) {
			simo = FormationDAO.create("SIMO");
			aspe = FormationDAO.create("ASPE");
			big_data = FormationDAO.create("BIG DATA");
		} else {
			simo = FormationDAO.getByIntitule("SIMO");
			aspe = FormationDAO.getByIntitule("ASPE");
			big_data = FormationDAO.getByIntitule("BIG DATA");
		}

		if (EtudiantDAO.getAll().size() == 0) {
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

			if (MatiereDAO.getAll().size() == 0) {
				// Cr�ation des Matiere
				Matiere mat1 = MatiereDAO.create("SIMO-MI1-PROJET");
				Matiere mat2 = MatiereDAO.create("SIMO-MI1-DS");
				Matiere mat3 = MatiereDAO.create("SIGD-MI4-PROJET");
				Matiere mat4 = MatiereDAO.create("SIGD-MI4-DS");

				Coefficient coeff1 = CoefficientDAO.create(mat1, simo, 10, false);
				Coefficient coeff3 = CoefficientDAO.create(mat2, simo, 17, false);
				Coefficient coeff4 = CoefficientDAO.create(mat3, simo, 14, false);
				Coefficient coeff5 = CoefficientDAO.create(mat4, simo, 10, false);
				Coefficient coeff6 = CoefficientDAO.create(mat3, big_data, 11, false);
				Coefficient coeff7 = CoefficientDAO.create(mat4, big_data, 8, false);
			}
		}

		em.close();

	}
}
