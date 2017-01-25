package aideProjet;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class MatiereDAO {

	public static Matiere create(String intitule) {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		// create
		em.getTransaction().begin();

		// create new matiere
		Matiere matiere = new Matiere();
		matiere.setIntitule(intitule);
		
		em.persist(matiere);

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();
		
		return matiere;
	}
	
	
	public static int removeAll() {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();
		
		// RemoveAll
		int deletedCount = em.createQuery("DELETE FROM Matiere").executeUpdate();

		// Commit
		em.getTransaction().commit();
		
		// Close the entity manager
		em.close();
		
		return deletedCount;
	}
	
	public static Matiere getById(int id){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		Matiere matiere = em.find(Matiere.class, id);
		
		em.close();
		
		return matiere;
	}
	
	public static Matiere getByIntitule(String intitule) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT f FROM Matiere f WHERE f.intitule = :intitule")
				.setParameter("intitule", intitule);
		
		List<Matiere> list = q.getResultList();
		return list.get(0);
	}
	
	
	public static List<Matiere> getAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT g FROM Matiere g");

		@SuppressWarnings("unchecked")
		List<Matiere> listMatiere = q.getResultList();
		
		return listMatiere;
	}
	
	public static Long count(){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Comptage
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Matiere.class)));
		return em.createQuery(cq).getSingleResult();
	}
}
