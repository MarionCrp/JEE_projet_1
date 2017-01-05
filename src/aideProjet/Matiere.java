package aideProjet;

public class Matiere {
	
	private Integer id;
	private String intitule;
	private Integer coefficient;
	
	public Matiere() {
		super();
	}
	
	public Matiere(Integer id, String intitule, Integer coefficient) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.coefficient = coefficient;
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

	public Integer getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}
}
