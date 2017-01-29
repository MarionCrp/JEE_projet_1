package aideProjet;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class EtudiantDAO {


	public static Etudiant retrieveById(int id) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		Etudiant etu = em.find(Etudiant.class, id);
	    // Close the entity manager
	    em.close();

		return etu;
	}


	public static Etudiant create(String prenom, String nom, Formation Formation) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();

		// create new etudiant
		Etudiant etudiant = new Etudiant();
		etudiant.setPrenom(prenom);
		etudiant.setNom(nom);
		etudiant.setFormation(Formation);
		etudiant.setNbAbsence(0);
		em.persist(etudiant);

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();

		return etudiant;
	}

	public static Etudiant update(Etudiant etudiant) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		em.getTransaction().begin();

		em.merge(etudiant);

		// Commit
		em.getTransaction().commit();
		// Close the entity manager
		em.close();

		return etudiant;
	}

	public static Etudiant addAbsences(int id, int absencesAAjouter) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Find
		Etudiant etudiant = em.find(Etudiant.class, id);

		em.getTransaction().begin();

		//
		int absencesInitiales = etudiant.getNbAbsence();
		etudiant.setNbAbsence(absencesInitiales + absencesAAjouter);

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();

		return etudiant;
	}


	public static void remove(Etudiant etudiant) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();

		// Retrouver l'entité persistante et ses liens avec d'autres entités en vue de la suppression
		etudiant = em.find(Etudiant.class, etudiant.getId());
		em.remove(etudiant);

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();

		// if EclipseLink cache enable -->
		// GestionFactory.factory.getCache().evictAll();
	}

	public static void remove(int id) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();

		//
		em.createQuery("DELETE FROM Etudiant AS e WHERE e.id = :id")
        .setParameter("id", id)
        .executeUpdate();

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();

		// if EclipseLink cache enable -->
		// GestionFactory.factory.getCache().evictAll();
	}

	public static int removeAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();

		// RemoveAll
		int deletedCount = em.createQuery("DELETE FROM Etudiant").executeUpdate();

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();

		return deletedCount;
	}

	// Retourne l'ensemble des etudiants
	public static List<Etudiant> getAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Recherche
		Query q = em.createQuery("SELECT e FROM Etudiant e");

		@SuppressWarnings("unchecked")
		List<Etudiant> listEtudiant = q.getResultList();

		return listEtudiant;
	}

	// Retourne l'ensemble des etudiants d'un Formation donné
	public static List<Etudiant> getAllByAbsences() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Recherche
		Query q = em.createQuery("SELECT e FROM Etudiant e WHERE e.nbAbsences > 0");

		@SuppressWarnings("unchecked")
		List<Etudiant> listEtudiant = q.getResultList();

		return listEtudiant;
	}

	public static Long count(){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Comptage
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Etudiant.class)));
		return em.createQuery(cq).getSingleResult();

	}

}
