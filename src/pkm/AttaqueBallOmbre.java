package pkm;

public class AttaqueBallOmbre extends AttaqueSpeciale {

	public AttaqueBallOmbre() {
		super("Balle Ombre", "Spectre", 90, 100, 10);
	}
	
	/*
	 * Cette méthode est un Override de la méthode genererMemeAttaque de la classe AttaqueSpeciale
	 * car c'est une classe qui hérite de AttaqueSpeciale, donc nous avons juste à modifier le code
	 * de la méthode, et Override permettra aussi de gérer si l'attaque est physique ou spéciale
	 * par exemple pour attaqueCoupDeTete qui est physique, un Override fait qu'il hérite directement
	 * de la classe AttaquePhysique et pas AttaqueSpeciale
	 */
	@Override
	public AttaqueBallOmbre genererMemeAttaque(boolean generer) {
		if(! generer) {
			return null;
		}
		else {
			return new AttaqueBallOmbre();
		}
	}
}
