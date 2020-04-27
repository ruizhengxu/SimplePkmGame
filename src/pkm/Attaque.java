package pkm;

import javafx.scene.control.TextArea;

public abstract class Attaque {
	
	private String nom;
	private String type;
	private int puissance;
	private int precision;
	private int nombreRepetition;
	private int repetitionRestantes;
	
	/*CONSTRUCTEURS*/
	public Attaque(String nom, String type, int puissance, int precision, int nombreRepetition) {
		this.nom = nom;
		this.type = type;
		this.puissance = puissance;
		this.precision = precision;
		this.nombreRepetition = nombreRepetition;
		this.repetitionRestantes = nombreRepetition;
	}
	
	
	/*GETTERS*/
	public String getNom() {
		return nom;
	}

	public int getPuissance() {
		return puissance;
	}

	public int getPrecision() {
		return precision;
	}

	public int getNombreRepetition() {
		return nombreRepetition;
	}

	public int getRepetitionRestantes() {
		return repetitionRestantes;
	}
	
	public String getType() {
		return this.type;
	}
	
	
	public void resetNombreRepetitions() {
		this.repetitionRestantes = nombreRepetition;
	}
	
	public void baisserNombreRepetitions() {
		this.repetitionRestantes -= 1;
		if(this.repetitionRestantes < 0) {
			this.repetitionRestantes = 0;
		}
	}
	
	public abstract void utiliserAttaque(Pokemon attaquant, Pokemon victime, TextArea ta);
	
	public abstract boolean isCompatible(Pokemon pokemon);
	
	public abstract Attaque genererMemeAttaque(boolean generer);

	/*METHODE toString()*/
	public String toString() {
		return(this.nom + " : " + this.puissance + ", " + this.precision + ", " + this.repetitionRestantes + "/" + this.nombreRepetition);
	}
	
	
}
