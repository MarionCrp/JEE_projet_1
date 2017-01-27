package aideProjet;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class NoteDAO {

	public static Note create(Etudiant etudiant, Matiere matiere, float resultat) {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		// create
		em.getTransaction().begin();

		// create new note
		Note note = new Note();
		note.setEtudiant(etudiant);
		note.setMatiere(matiere);
		note.setResultat(resultat);
		
		em.persist(note);

		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();
		
		return note;
	}
	
	public static Note update(Note note) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		em.getTransaction().begin();

		em.merge(note);

		// Commit
		em.getTransaction().commit();
		// Close the entity manager
		em.close();

		return note;
	}
	
	
	public static int removeAll() {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		//
		em.getTransaction().begin();
		
		// RemoveAll
		int deletedCount = em.createQuery("DELETE FROM Note").executeUpdate();

		// Commit
		em.getTransaction().commit();
		
		// Close the entity manager
		em.close();
		
		return deletedCount;
	}
	
	public static Note getById(int id){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		Note note = em.find(Note.class, id);
		
		em.close();
		
		return note;
	}
	
	public static Note getByEtudiantAndMatiere(Etudiant etudiant, Matiere matiere) {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		// Recherche 
		Query q = em.createQuery("SELECT f FROM Note f WHERE f.etudiant = :etudiant AND f.matiere = :matiere")
				.setParameter("etudiant", etudiant)
				.setParameter("matiere", matiere);
		
		List<Note> list = q.getResultList();
		try{
			Note note = list.get(0);
		} catch (Exception ex) {
			return null;
		}
		return list.get(0);
	}	
	
	public static List<Note> getAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT g FROM Note g");

		@SuppressWarnings("unchecked")
		List<Note> listNote = q.getResultList();
		
		return listNote;
	}
	
	public static Long count(){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Comptage
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Note.class)));
		return em.createQuery(cq).getSingleResult();
	}
}
