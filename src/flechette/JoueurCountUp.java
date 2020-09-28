/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public class JoueurCountUp extends Joueur{
	
	public JoueurCountUp(String nom) {
		super(nom);
	}
	
	/**
	 * ajoute des points aux points du joueur
	 * @param points les points a ajouter
	 */
	public void ajouterPoints(int points){
		points = points + super.getPoints();
		super.setPoints(points);
	}

	@Override
	public boolean estMieuxClasse(Joueur autreJoueur) {
		return (super.getPoints() >= autreJoueur.getPoints());
	}
	
}
