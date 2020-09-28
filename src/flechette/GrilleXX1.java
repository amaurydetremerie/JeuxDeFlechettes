/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public class GrilleXX1 extends Grille{

	public GrilleXX1(int nombreJoueurs) {
		super(nombreJoueurs);
	}
	
	public JoueurXX1 donnerJoueur(int numero){
		return (JoueurXX1)super.donnerJoueur(numero);
	}
	
	public boolean tousA1() {
		int nbJoueur = super.nombreJoueurs();
		do {
		Joueur joueur = donnerJoueur(nbJoueur);
		if(joueur.getPoints() != 1)
			return false;
		nbJoueur--;
		}while(nbJoueur > 0);
		return true;
	}
	
	public Joueur donnerGagnant() {
		Joueur classement [] = super.classement();
		if(classement[0].getPoints() != 0)
			return null;
		return classement[0];
	}
	public boolean finJeu() {
		if(tousA1())
			return true;
		if(donnerGagnant() != null)
			return true;
		return false;
	}
}