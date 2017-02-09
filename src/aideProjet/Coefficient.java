package aideProjet;

import java.io.Serializable;

import javax.persistence.*;

@Entity
// Coefficient est une matière, affectée à une formation (qui possède donc une valeur de coefficient différent selon les formations)
public class Coefficient implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "matiere_id")
	private Matiere matiere;
	
	@ManyToOne
	@JoinColumn(name = "formaton_id")
	private Formation formation;
	
	@Column
	private Integer valeur;
	
	@Column
	private boolean actif;
	
private static final long serialVersionUID = 1L;
	
	public Coefficient() {
		super();
	}
	
	public Coefficient(Matiere matiere, Formation formation, int valeur, boolean actif) {
		super();
		this.matiere = matiere;
		this.formation = formation;
		this.valeur = valeur;
		this.actif = actif;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Formation getFormation() {
		return formation;
	}
	
	public void setFormation(Formation formation) {
		this.formation = formation;
	}
	
	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		if(valeur > 0){
			this.valeur = valeur;
		}
		
	}
	
	public boolean getActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}
}
