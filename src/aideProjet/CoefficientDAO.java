package aideProjet;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class CoefficientDAO {

	public static Coefficient create(Matiere matiere, Formation formation, int valeur) {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		// create
		em.getTransaction().begin();

		// create new coefficient
		Coefficient coefficient = new Coefficient();
		coefficient.setFormation(formation);
		coefficient.setMatiere(matiere);
		coefficient.setValeur(valeur);
		
		em.persist(coefficient);

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();
		
		return coefficient;
	}
	
	public static Coefficient update(Coefficient coefficient) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();

		em.merge(coefficient);

		// Commit
		em.getTransaction().commit();
		// Close the entity manager
		em.close();

		return coefficient;
	}
	
	
	public static int removeAll() {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();
		
		// RemoveAll
		int deletedCount = em.createQuery("DELETE FROM Coefficient").executeUpdate();

		// Commit
		em.getTransaction().commit();
		
		// Close the entity manager
		em.close();
		
		return deletedCount;
	}
	
	public static Coefficient getById(int id){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		Coefficient coefficient = em.find(Coefficient.class, id);
		em.close();
		
		return coefficient;
	}
	
	public static Coefficient getByMatiereAndFormation(Matiere matiere, Formation formation) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT f FROM Coefficient f WHERE f.formation = :formation AND f.matiere = :matiere")
				.setParameter("formation", formation)
				.setParameter("matiere", matiere);
		
		List<Coefficient> list = q.getResultList();
		return list.get(0);
	}
	
	public static List<Coefficient> getCoefficientByFormation(Formation formation){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT g FROM Coefficient g WHERE g.formation = :formation")
				.setParameter("formation", formation);
		
		@SuppressWarnings("unchecked")

		List<Coefficient> listCoefficient = q.getResultList();
		return listCoefficient;
	}
	
	
	public static List<Coefficient> getAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT g FROM Coefficient g");

		@SuppressWarnings("unchecked")
		List<Coefficient> listCoefficient = q.getResultList();
		
		return listCoefficient;
	}
	
	public static Long count(){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Comptage
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Coefficient.class)));
		return em.createQuery(cq).getSingleResult();
	}
}
