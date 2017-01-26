package aideProjet;

import java.util.ArrayList;
import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Formation implements Serializable {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String intitule;
	
	@OneToMany(mappedBy="formation", fetch=FetchType.LAZY)
	List<Etudiant> etudiants;
	
	@OneToMany(mappedBy = "formation")
	private Set<Coefficient> coefficients = new HashSet<Coefficient>();
	
	private static final long serialVersionUID = 1L;
	
	public Formation() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public List getEtudiants() {
		return etudiants;
	}

	public void inscriptionEtudiants(ArrayList<Etudiant> etudiants) {
		for(Etudiant etudiant : etudiants){
			etudiant.setFormation(this);
		}
	}
}
