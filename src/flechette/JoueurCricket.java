/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public class JoueurCricket extends Joueur{
	
	private int[] secteurFerme = new int [21];

	public JoueurCricket(String nom) {
		super(nom);
	}

	public boolean estMieuxClasse(Joueur autreJoueur) {
		return (super.getPoints() >= autreJoueur.getPoints());
	}
	
	public void ajouterPoints(int points) {
		points = super.getPoints() + points;
		super.setPoints(points);
	}
	
	public void	SecteurAtteint(Flechette flechette) {
		int indice;
		if(flechette.getSecteur() == 25)
			indice = 20;
		else
			indice = flechette.getSecteur() - 1;
		if(flechette.getZone() > 0)
			secteurFerme[indice] += flechette.getZone() ;
		else
			secteurFerme[indice] += 1;
	}
	
	public boolean secteurFermeON(int secteur) {
		if(secteur == 25)
			secteur = 20;
		else
			secteur = secteur-1;
		if(secteurFerme[secteur] >= 3)
			return true;
		return false;
	}
	
	public boolean tousSecteurFerme() {
		for(int i = 0; i<secteurFerme.length; i++) {
			if(secteurFerme[i] < 3)
				return false;
		}
		return true;
	}
	
	public String infoSupplementaires() {
		String infos="Voici les zones : ";
		for(int i = 0; i<secteurFerme.length; i++) {
			if(i == 20) {
				infos += "\n le secteur 25 est : ";
				if(secteurFermeON(25))
					infos += "ouvert";
				else
					infos += "fermé";
			}
			else {
				infos += "\n le secteur" + i+1 + "est : ";
				if(secteurFermeON(i+1))
					infos += "ouvert";
				else
					infos += "fermé";
			}
		}
		return infos;
	}
}