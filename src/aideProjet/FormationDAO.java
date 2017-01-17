package aideProjet;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class FormationDAO {

	public static Formation create(String intitule) {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		// create
		em.getTransaction().begin();

		// create new formation
		Formation formation = new Formation();
		formation.setIntitule(intitule);
		em.persist(formation);

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();
		
		return formation;
	}
	
	
	public static int removeAll() {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();
		
		// RemoveAll
		int deletedCount = em.createQuery("DELETE FROM Formation").executeUpdate();

		// Commit
		em.getTransaction().commit();
		
		// Close the entity manager
		em.close();
		
		return deletedCount;
	}
	
	public static Formation getById(int id){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		Formation formation = em.find(Formation.class, id);
		
		em.close();
		
		return formation;
	}
	
	public static Formation getByIntitule(String intitule) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT f FROM Formation f WHERE f.intitule = :intitule")
				.setParameter("intitule", intitule);
		
		List<Formation> list = q.getResultList();
		return list.get(0);
	}
	
	
	public static List<Formation> getAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT g FROM Formation g");

		@SuppressWarnings("unchecked")
		List<Formation> listFormation = q.getResultList();
		
		return listFormation;
	}
	
	public static Long count(){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Comptage
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Formation.class)));
		return em.createQuery(cq).getSingleResult();
	}
}
