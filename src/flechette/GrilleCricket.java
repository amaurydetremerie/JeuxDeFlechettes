/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public class GrilleCricket extends Grille{
	
	public GrilleCricket(int nombreJoueurs){
		super(nombreJoueurs);
	}

	public JoueurCricket donnerJoueur(int numero){
		return (JoueurCricket)super.donnerJoueur(numero);
	}
	
	public boolean finJeu() {
		Joueur[] classement;
		classement = super.classement();
		JoueurCricket joueur = (JoueurCricket) classement[0];
		if(joueur.tousSecteurFerme())
			return true;
		return false;
	}
	
	public Joueur donnerGagnant() {
		if(finJeu()) {
			Joueur[] classement;
			classement = super.classement();
			return classement[0];
		}
		return null;
	}
	
	public void flechetteFermee (Flechette flechette) {
		Joueur[] classement;
		classement = super.classement();
		for(int i = 0; i<classement.length; i++) {
			JoueurCricket joueur;
			joueur =(JoueurCricket) classement[i];
			if(!joueur.secteurFermeON(flechette.getSecteur())) {
				joueur.ajouterPoints(flechette.getSecteur());
				//classement[i].ajouterPoints(flechette.donnerPoints());
			}
		}
	}	
}