package aideProjet;

import java.util.ArrayList;
import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

public class Formation implements Serializable {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String intitule;
	
	@OneToMany(mappedBy="formation", fetch=FetchType.LAZY)
	ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
	
	private static final long serialVersionUID = 1L;
	
	public Formation() {
		super();
	}
	
	public Formation(Integer id, String intitule, ArrayList<Etudiant> etudiants) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.etudiants = etudiants;
		inscriptionEtudiants(etudiants);
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

	public ArrayList getEtudiants() {
		return etudiants;
	}

	public void inscriptionEtudiants(ArrayList<Etudiant> etudiants) {
		for(Etudiant etudiant : etudiants){
			etudiant.setFormation(this);
		}
	}
}
