package pkm;

public class Joueur {

	private String pseudo;
	
	public Joueur(String psuedo) {
		this.pseudo = psuedo;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String toString() {
		return this.pseudo;
	}
	
}
