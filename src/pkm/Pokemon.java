package pkm;
import java.util.List;

import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class Pokemon {	
	private int numeroPokedex;
	private String nom;
	private String type1;
	private String type2;
	private int niveau;
	private String nomDonne;
	private Joueur monJoueur;
	private int appetit;
	private int loyaute;
	private int attaque;
	private int defense;
	private int attaqueSpeciale;
	private int defenseSpeciale;
	private int vitesse;
	private int hp;
	private int hpRestant;
	private List<Attaque> attaques = new ArrayList<>();
	
	// Constructeur
	public Pokemon(int numeroPokedex, String nom, String type1, String type2, int niveau, String nomDonne, 
			Joueur monJoueur, int hp, int attaque, int defense, int attaqueSpeciale, int defenseSpeciale, int vitesse,
			Attaque[] attaques) {
		this.numeroPokedex = numeroPokedex;
		this.nom = nom;
		this.type1 = type1;
		this.type2 = type2;
		this.niveau = niveau;
		this.nomDonne = nomDonne;
		this.monJoueur = monJoueur;
		this.appetit = 50;
		this.loyaute = 0;
		this.attaque = attaque;
		this.defense = defense;
		this.attaqueSpeciale = attaqueSpeciale;
		this.defenseSpeciale = defenseSpeciale;
		this.vitesse = vitesse;
		this.hp = hp;
		this.hpRestant = hp;
		for(int i = 0; i < attaques.length; i++) {	
			this.ajouterAttaque(attaques[i]);
		}
	}

	public Pokemon(int numeroPokedex, String nom, String type1, int niveau, int hp, int attaque, int defense, int attaqueSpeciale, int defenseSpeciale, int vitesse, Attaque[] attaques) {
		this(numeroPokedex, nom, type1, null, niveau, null, null, hp, attaque, defense, attaqueSpeciale, defenseSpeciale, vitesse, attaques);
	}
	
	// Getters
	public int getNumeroPokedex() {
		return this.numeroPokedex;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getType1() {
		return this.type1;
	}
	
	public String getType2() {
		return this.type2;
	}
	
	public String getNomDonne() {
		return this.nomDonne;
	}
	
	public Joueur getMonJoueur() {
		return this.monJoueur;
	}
	
	public int getAppetit() {
		return this.appetit;
	}
	
	public int getLoyaute() {
		return this.loyaute;
	}
	
	public int getNiveau() {
		return this.niveau;
	}
	
	public int getAttaque() {
		return this.attaque;
	}
	
	public int getDefense() {
		return this.defense;
	}
	
	public int getAttaqueSpeciale() {
		return this.attaqueSpeciale;
	}
	
	public int getDefenseSpeciale() {
		return this.defenseSpeciale;
	}
	
	public int getVitesse() {
		return this.vitesse;
	}
	
	public int getHP() {
		return this.hp;
	}
	
	public int getHPRestant(){
		return this.hpRestant;
	}
	
	public List<Attaque> getAttaques() {
		return this.attaques;
	}
	
	// Setters
	public void setNomDonne(String nomDonne) {
		this.nomDonne = nomDonne;
	}
	
	public void setMonJoueur(Joueur joueur) {
		this.monJoueur = joueur;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public void baisserAppetit(int difference) {
		this.appetit -= difference;
		if(this.appetit < 0) {
			this.appetit = 0;
		}
	}
	
	public void monterAppetir(int difference) {
		this.appetit += difference;
		if(this.appetit > 100) {
			this.appetit = 100;
		}
	}
	
	public void baisserLoyaute(int difference) {
		this.loyaute -= difference;
		if(this.loyaute < 0) {
			this.loyaute = 0;
		}
	}

	public void monterLoyaute(int difference) {
		this.loyaute += difference;
		if(this.loyaute > 100) {
			this.loyaute = 100;
		}
	}
	/*
	public void utiliser(Utilisable item) {
		if(null != item) {
			if(null != this.getMonJoueur()) {
				int indexPokemon = this.getMonJoueur().trouverPokemon(this);
				if(indexPokemon != -1) {
					item.utiliser(this.getMonJoueur(), indexPokemon);
				}
				else {
					System.out.println(this.getNom() + " n'appartient pas à " + this.getMonJoueur().getNom());
				}
			}
			else {
				System.out.println(this.getNom() + " n'a pas de maitre.");
			}
		}
		else {
			System.out.println("Item passé en paramètre est null");
		}
	}
	*/
	public void miseANiveau() {
		this.niveau += 1;
	}
	
	private int trouverAttaque(Attaque attaque) {
		
		return this.attaques.indexOf(attaque);
	}
	
	public void ajouterAttaque(Attaque attaque) {
		if(null != attaque) {
			
			if(! attaque.isCompatible(this)) {
				System.out.println(attaque.getNom() + " n'est pas compatible avec " + this.getNom());
			}
			else {
				if(attaques.size() <= 4) {
					attaques.add(attaque);
				}
				
			}
		}
	}
	
	public void ajouterAttaque(Attaque attaque, int i) {
		if((null == attaque) || (i < 0) || (i >= 4)) {
			System.out.println("Erreur paramètre");
		}
		else {
			if(! attaque.isCompatible(this)) {
				System.out.println(attaque.getNom() + " n'est pas compatible avec " + this.getNom());
			}
			else {
				int position = trouverAttaque(attaque);
				if(position != 1) {
					System.out.println(this.getNom() + " a déjà appris " + attaque.getNom());
				}
				else {
					System.out.println(this.getNom() + " a oublié " + this.attaques.get(position));
					this.attaques.set(position, attaque);
				}
			}
		}
	}
	
	public void rechargerAttaques() {
		for(int i = 0; i < attaques.size(); i++) {
			if(null != this.attaques.get(i)) {
				this.attaques.get(i).resetNombreRepetitions();
			}
		}
	}
	
	public void blessure(int dommage) {
		this.hpRestant -= dommage;
		if(this.hpRestant < 0) {
			this.hpRestant = 0;
		}
	}
	
	public boolean isKO() {
		boolean KO = false;
		if(this.hpRestant == 0) {
			KO = true;
		}
		return KO;
	}
	
	public void soignerPokemon() {
		this.hpRestant = hp;
	}
	
	public int getIndexAttaque(String nomAttaque) {
		int indexAttaque = -1;
		int index = 0;
		for(Attaque attaque : this.attaques) {
			if(attaque.getNom().equals(nomAttaque)) {
				indexAttaque = index;
			}
			index++;
		}
		return indexAttaque;
	}
	
	public void utiliserAttaque(int index, Pokemon victime, TextArea ta) {
		if((victime.isKO()) || (index < 0) || (index > 4)) {
			System.out.println("Erreur paramètre");
		}
		else {
			if(null == this.attaques.get(index)) {
				System.out.println(this.getNom() + " n'a pas d'attaque à l'index " + index);
			}
			else {
				ta.setText(this.nom + " utilise " + this.attaques.get(index).getNom());
				this.attaques.get(index).utiliserAttaque(this, victime, ta);
			}
		}
	}
	
	public void afficherEtatAttaques() {
		System.out.println("---------------------------");
		for(int i = 0; i < attaques.size(); i++) {
			if(null != this.attaques.get(i)) {
				System.out.println("index " + i + ": " + attaques.get(i).getNom() + ", " + attaques.get(i).getRepetitionRestantes() + "/" + attaques.get(i).getNombreRepetition());
			}
		}
		System.out.println("---------------------------");
	}
	
	public Pokemon genererMemePokemon(boolean generer) {
		if(!generer) {
			return null;
		}
		else {
			Attaque[] sesAttaques = new Attaque[this.attaques.size()];
			sesAttaques = this.attaques.toArray(sesAttaques);
			return new Pokemon(this.numeroPokedex, this.nom, this.type1, this.niveau, this.hp, this.attaque, this.defense, this.attaqueSpeciale, this.defenseSpeciale, this.vitesse, sesAttaques);
		}
	}
	
	private String attaquesDePokemon() {
		String attaquesDePokemon = "";
		for(int i = 0; i < attaques.size(); i++) {
			if(null != this.attaques.get(i)) {
				attaquesDePokemon += attaques.get(i) + "\n";
			}
		}
		return attaquesDePokemon;
	}
	
	// Méthode toString
	/******
	 * si on change toString() par sePresente() avant d'avoir creer la methode sePresent()
	 * alors lorsqu'on veut utiliser System.out.println(p1)
	 * il sera affiche l'adresse memoire du pokemon p1, car java cherche automatiquement si toString() est present ou pas
	 * si toString() n'existe pas, alors il affiche l'adress memoire a la place
	******/
	public String toString() {
		return("No." +this.numeroPokedex + "; Nom : " + this.nom + "; Type : " + this.type1 + "; Niveau : " + this.niveau + ";" + " Nom donne : " + this.nomDonne + "; Joueur : " + this.monJoueur + 
				"; Appetit : " + this.appetit + "; Loyaute : " + this.loyaute + "; HP : " + this.hpRestant + "/" +  this.hp + "; Attaque : " + this.attaque +
				"; Defense : " + this.defense + "; Attaque Speciale : " + this.attaqueSpeciale + "; Defense Speciale : " + 
				this.defenseSpeciale + "; Attaques : " + attaquesDePokemon() + "\n");
	}

}
