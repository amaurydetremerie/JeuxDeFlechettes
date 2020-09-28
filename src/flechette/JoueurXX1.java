/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public class JoueurXX1 extends Joueur{

	public JoueurXX1(String nom) {
		super(nom, 501);
	}
	
	public JoueurXX1(String nom, int point) {
		super(nom, point);
	}
	
	public boolean estMieuxClasse(Joueur autreJoueur) {
		return (super.getPoints() >= autreJoueur.getPoints());
	}
	
	public void retirerPoints(int points) {
		points = super.getPoints() - points;
		super.setPoints(points);
	}
	
	public Flechette[] flechetteRecommandee() {
		Flechette flechettes[];
		int points = super.getPoints();
		if(!(points > 1 && points < 51)) {
			if(points == 100) {
				flechettes = new Flechette[2];
				Flechette flechette1 = new Flechette(25, 2);
				flechettes[0] = flechette1;
				flechettes[1] = flechette1;
				return flechettes;
			}
			if(points == 150) {
				flechettes = new Flechette[3];
				Flechette flechette1 = new Flechette(25, 2);
				flechettes[0] = flechette1;
				flechettes[1] = flechette1;
				flechettes[2] = flechette1;
				return flechettes;
			}
			return null;
		}
		if(points > 40 && points < 50) {
			flechettes = new Flechette[2];
			Flechette flechette1 = new Flechette((points-40), 1);
			Flechette flechette2 = new Flechette(20, 2);
			flechettes[0] = flechette1;
			flechettes[1] = flechette2;
			return flechettes;
		}
		if(points%2 != 0) {
			flechettes = new Flechette[2];
			Flechette flechette1 = new Flechette(1, 1);
			Flechette flechette2 = new Flechette(((points-1)/2), 2);
			flechettes[0] = flechette1;
			flechettes[1] = flechette2;
			return flechettes;
		}
		flechettes = new Flechette[1];
		Flechette flechette = new Flechette((points/2), 2);
		flechettes[0] = flechette;
		return flechettes;
	}
}