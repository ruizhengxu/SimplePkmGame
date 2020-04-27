package pkm;

import java.util.Random;

import javafx.scene.control.TextArea;

public class AttaqueSpeciale extends Attaque {
	
	public AttaqueSpeciale(String nom, String type, int puissance, int precision, int nombreRepetitions) {
		super(nom, type, puissance, precision, nombreRepetitions);
	}
	
	@Override
	public boolean isCompatible(Pokemon pokemon) {
		boolean compatible = true;
		if(null == pokemon) {
			compatible = false;
		}
		return compatible;
	}
	
	@Override
	public void utiliserAttaque(Pokemon attaquant, Pokemon victime, TextArea ta) {
		if(null == attaquant || null == victime) {
			System.out.println("Erreur paramètre");
		}
		else {
			if(this.getRepetitionRestantes() == 0) {
				ta.setText(attaquant.getNom() + " n'a plus de PP pour\nl'attaque " + this.getNom());
			}
			else {
				Random random = new Random();
				double precision = random.nextInt(100);
				if(this.getPrecision() < precision) {
					ta.appendText(attaquant.getNom() + " a raté son attaque");
				}
				else {
					double dammage = ( (((2*attaquant.getNiveau()/5)*this.getPuissance()*((double)attaquant.getAttaqueSpeciale()/(double)victime.getDefenseSpeciale()))/50)+2);
					double critical = 1;
					int chanceCritical = random.nextInt(100);
					if(chanceCritical > 85) {
						critical = 1.5;
					}
					double rangeMin = 0.75;
					double rangeMax = 1.00;
					double randomNumber = rangeMin + (rangeMax - rangeMin)*random.nextDouble();
					double typeBonus = 1;
					if(this.getType().equals(attaquant.getType1()) || (this.getType().equals(attaquant.getType2()))) {
						typeBonus = 1.5;
					}
					
					double modifier = critical*randomNumber*typeBonus;
					dammage = modifier * dammage;
					if(critical == 1.5) {
						ta.appendText(", coup critique");
					}
					victime.blessure((int)(dammage));
					ta.appendText("\n" + attaquant.getNom() + " a infligé " + (int)(dammage) + " dégâts ");
					this.baisserNombreRepetitions();
				}
			}
		}
	}
	
	@Override
	public AttaqueSpeciale genererMemeAttaque(boolean generer) {
		if(!generer) {
			return null;
		}
		else {
			return new AttaqueSpeciale(super.getNom(), super.getType(), super.getPuissance(), super.getPrecision(), super.getNombreRepetition());
		}
	}
}
