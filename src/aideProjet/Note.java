package aideProjet;

public class Note {
	
	private Integer id;
	private Float resultat;
	private Matiere matiere;
	private Etudiant etudiant;
	
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
