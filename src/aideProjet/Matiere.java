package aideProjet;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Matiere implements Serializable {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String intitule;

	
	private static final long serialVersionUID = 1L;
	
	public Matiere() {
		super();
	}
	
	public Matiere(String intitule) {
		super();
		this.intitule = intitule;
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
}
