package aideProjet;

public class Etudiant {
	
	private Integer id;
	private String prenom;
	private String nom;
	private Formation formation;
	private Integer nbAbsence;
	
	public Etudiant() {
		super();
	}
	
	public Etudiant(Integer id, String prenom, String nom, Integer nbAbsence) {
		super();
		this.id = id;
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
