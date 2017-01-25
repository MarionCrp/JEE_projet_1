package aideProjet;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Coefficient implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Matiere matiere;
	
	@OneToOne
	private Formation formation;
	
	@Column
	private Integer valeur;
	
private static final long serialVersionUID = 1L;
	
	public Coefficient() {
		super();
	}
	
	public Coefficient(Matiere matiere, Formation formation, int valeur) {
		super();
		this.matiere = matiere;
		this.formation = formation;
		this.valeur = valeur;
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

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		this.valeur = valeur;
	}
}
