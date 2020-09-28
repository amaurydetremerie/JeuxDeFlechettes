/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public abstract class Joueur {
	
	private String nom;
	private int points;
	
	public Joueur(String nom) {
		if(nom==null||nom.equals(""))
			throw new IllegalArgumentException("nom invalide");
		this.nom = nom;
		this.points = 0;
	}
	
	public Joueur(String nom, int points) {
		this(nom);
		this.points = points;
	}

	public String getNom() {
		return nom;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * Verifie si le joueur courant est mieux classe que le joueur passe en parametre
	 * @param autreJoueur le joueur compare
	 * @return true si le joueur courant est mieux classe que l'autreJoueur, false sinon
	 */
	public abstract boolean estMieuxClasse(Joueur autreJoueur);
	
		@Override
	public String toString() {
		return nom + " " + points;
	}
	
	/**
	 * renvoie les informations autres que le nom et les points
	 * par exemple le tableau des compteurs pour un joueur de cricket
	 * @return une chaîne de caracteres avec les informations autres que le nom et les points
	 */
	public String infoSupplementaires() {
		return "";
	}
}
