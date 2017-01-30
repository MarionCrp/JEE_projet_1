package aideProjet;

import javax.persistence.EntityManager;

import java.util.*;

public class Services {
	public static Float calculeMoyenne(Etudiant etudiant){
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();
		
		// On récupère la liste des notes d'une formation
		Formation formation = etudiant.getFormation();
		List<Coefficient> list_coeff = CoefficientDAO.getActiveCoefficientByFormation(formation);
		float moyenne = 0;
		if(list_coeff.size() > 0){
			float total_point = 0;
			int total_coeff = 0;
			for(Coefficient coeff : list_coeff){
				Matiere mat = coeff.getMatiere();
				Float note_etu = NoteDAO.getByEtudiantAndMatiere(etudiant, mat).getResultat();
				int valeur_coeff = coeff.getValeur();
				total_point += ((float)note_etu * valeur_coeff);
				total_coeff += coeff.getValeur();
			}
			
			moyenne = total_point / total_coeff;
		}
		em.close();
		return (float) (Math.round(moyenne*100.0)/100.0);
	}
	
	public static Float calculeMoyenneGenerale(List<Etudiant> etudiants){
		if(etudiants.size() > 0){
			float somme_moyenne = 0;
			for(Etudiant etudiant : etudiants){
				somme_moyenne += calculeMoyenne(etudiant);
			}
			return (float) (Math.round(somme_moyenne/etudiants.size()*100)/100.0);
		} else {
			return (float) 0;
		}
		
	}
}
