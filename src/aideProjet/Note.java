package aideProjet;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Note implements Serializable {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Etudiant etudiant;
	
	@OneToOne
	private Matiere matiere;
	
	@Column
	private float resultat;
	
	private static final long serialVersionUID = 1L;
	
	public Note() {
		super();
	}
	
	public Note(Integer id, Float resultat, Matiere matiere, Etudiant etudiant) {
		super();
		this.id = id;
		this.resultat = resultat;
		this.matiere = matiere;
		this.etudiant = etudiant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getResultat() {
		return resultat;
	}

	public void setResultat(Float resultat) {
		this.resultat = resultat;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
	
	public Etudiant Etudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	
}
