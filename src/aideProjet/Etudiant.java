package aideProjet;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Etudiant implements Serializable {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String prenom;
	@Column
	private String nom;
	@Column(nullable = false)
	private Integer nbAbsence;
	
	@ManyToOne
	private Formation formation;
	
	private static final long serialVersionUID = 1L;
	
	public Etudiant() {
		super();
	}
	
	public Etudiant(String prenom, String nom, Formation formation) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.nbAbsence = nbAbsence;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	
	public Integer getNbAbsence() {
		return nbAbsence;
	}
	
	public void setNbAbsence(Integer new_Nbabsence) {
		this.nbAbsence = new_Nbabsence;
	}
	
	public void addAbsence(){
		this.setNbAbsence(this.getNbAbsence() + 1);
	}

	
}
