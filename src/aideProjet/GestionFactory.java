package aideProjet;

import java.util.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GestionFactory {
	
	// Nom de l'unité de persistence 
	// Permet le lien avec le fichier persistence.xml présent dans le dossier WebContent/WEB-INF/classes/META-INF
	// Ce fichier contient les propriétés de connexion à la base de données
	private static final String PERSISTENCE_UNIT_NAME = "Projet_JPA";
	
	// Factory pour la création d'EntityManager (gestion des transactions)
	public static EntityManagerFactory factory;
	
	
	// Creation de la factory
	public static void open() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);;
	}
	
	// Fermeture de la factory
	public static void close() {
		factory.close();
	}	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////
	
	
	

	/////// SIMULATION DE LA PERSISTANCE DES ETUDIANTS ET DES ABSENCES
	
	// CHARGER en premier Ã  l'execution du projet (car constante : static final)
	private static final HashMap<Integer, Etudiant> LISTE_ID_ETUDIANTS = intializeListEtudiants();
	private static final HashMap<Integer, Integer> LISTE_ID_ABSENCES = intializelistEtudiantAbsence();

	// Initialisation des Ã©tudiants
	private static HashMap<Integer, Etudiant> intializeListEtudiants() {

		// CrÃ©ation des Ã©tudiants
		Etudiant kevin = new Etudiant(0, "Kévin", "Coissard", 0);
		Etudiant elodie = new Etudiant(1, "Elodie", "Goy", 0);
		Etudiant david = new Etudiant(2, "David", "Cotte", 0);
		Etudiant milena = new Etudiant(3, "Miléna", "Charles", 0);
		
		Etudiant jeremie = new Etudiant(4, "Jérémie", "Guillot", 5);
		Etudiant martin = new Etudiant(5, "Martin", "Bolot", 0);
		Etudiant yoann = new Etudiant(6, "Yoann", "Merle", 1);
		Etudiant jean = new Etudiant(7, "Jean", "Debard", 2);
		
		ArrayList<Etudiant> etudiants_aspe = new ArrayList<>(Arrays.asList(jeremie, martin, yoann, jean));
		ArrayList<Etudiant> etudiants_simo = new ArrayList<>(Arrays.asList(kevin, elodie, david, milena));
		
		ArrayList<Formation> formations = new ArrayList<>();

		Formation simo = new Formation(0, "SIMO", etudiants_simo);
		Formation aspe = new Formation(1, "ASPE", etudiants_aspe);
		
		formations.add(simo);
		formations.add(aspe);

		// CrÃ©ation du hasmap (association clÃ©/valeur)
		// Association id -> etudiant
		HashMap<Integer, Etudiant> listEtudiantsTemp = new HashMap<>();
		listEtudiantsTemp.put(kevin.getId(), kevin);
		listEtudiantsTemp.put(elodie.getId(), elodie);
		listEtudiantsTemp.put(yoann.getId(), yoann);
		listEtudiantsTemp.put(martin.getId(), martin);
		
		//
		return listEtudiantsTemp;
	}

	// Initialisation des absences
	private static final HashMap<Integer, Integer> intializelistEtudiantAbsence() {

		// CrÃ©ation du hasmap (association clÃ©/valeur)
		// Association etudiant id -> absences
		HashMap<Integer, Integer> listEtudiantAbsenceTemp = new HashMap<>();
		
		// Le nombre d'absences est gÃ©nÃ©rÃ© alÃ©atoirement
		Random rand = new Random();
		for (Etudiant etudiant : LISTE_ID_ETUDIANTS.values()) {
			listEtudiantAbsenceTemp.put(etudiant.getId(), rand.nextInt(10));
		}

		//
		return listEtudiantAbsenceTemp;
	}

	
	/////// METHODES A UTILISER
	// Retourne l'ensemble des etudiants
	public static Collection<Etudiant> getEtudiants() {
		return LISTE_ID_ETUDIANTS.values();
	}

	// Retourne un Ã©tudiant en fonction de son id 
	public static Etudiant getEtudiantById(int id) {
		return LISTE_ID_ETUDIANTS.get(id);
	}

	// Retourne le nombre d'absences d'un etudiant en fonction de son id 
	public static Integer getAbsencesByEtudiantId(int id) {
		return LISTE_ID_ABSENCES.get(id);
	}
	
}