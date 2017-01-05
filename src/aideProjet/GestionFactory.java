package aideProjet;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GestionFactory {

	/////// SIMULATION DE LA PERSISTANCE DES ETUDIANTS ET DES ABSENCES
	
	// CHARGER en premier à l'execution du projet (car constante : static final)
	private static final HashMap<Integer, Etudiant> LISTE_ID_ETUDIANTS = intializeListEtudiants();
	private static final HashMap<Integer, Integer> LISTE_ID_ABSENCES = intializelistEtudiantAbsence();

	// Initialisation des étudiants
	private static HashMap<Integer, Etudiant> intializeListEtudiants() {

		// Création des étudiants
		Etudiant kevin = new Etudiant(0, "K�vin", "Coissard");
		Etudiant elodie = new Etudiant(1, "Elodie", "Goy");
		Etudiant david = new Etudiant(2, "David", "Cotte");
		Etudiant milena = new Etudiant(3, "Mil�na", "Charles");
		
		Etudiant jeremie = new Etudiant(4, "J�r�mie", "Guillot");
		Etudiant martin = new Etudiant(5, "Martin", "Bolot");
		Etudiant yoann = new Etudiant(6, "Yoann", "Merle");
		Etudiant jean = new Etudiant(7, "Jean", "Debard");
		
		ArrayList<Etudiant> etudiants_aspe = new ArrayList<>(Arrays.asList(jeremie, martin, yoann, jean));
		ArrayList<Etudiant> etudiants_simo = new ArrayList<>(Arrays.asList(kevin, elodie, david, milena));
		
		Formation simo = new Formation(0, "SIMO", etudiants_simo);
		Formation aspe = new Formation(1, "ASPE", etudiants_aspe);

		// Création du hasmap (association clé/valeur)
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

		// Création du hasmap (association clé/valeur)
		// Association etudiant id -> absences
		HashMap<Integer, Integer> listEtudiantAbsenceTemp = new HashMap<>();
		
		// Le nombre d'absences est généré aléatoirement
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

	// Retourne un étudiant en fonction de son id 
	public static Etudiant getEtudiantById(int id) {
		return LISTE_ID_ETUDIANTS.get(id);
	}

	// Retourne le nombre d'absences d'un etudiant en fonction de son id 
	public static Integer getAbsencesByEtudiantId(int id) {
		return LISTE_ID_ABSENCES.get(id);
	}

}