/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

import java.util.Scanner;

public class PanneauTextuel implements Panneau{
	private static Scanner scanner = new Scanner(System.in);
	String nomDuJeu;
	String auteurs; 
	
	public PanneauTextuel(String nomDuJeu, String auteurs){
		this.nomDuJeu = nomDuJeu;
		this.auteurs = auteurs;
	}
	
	@Override
	public Flechette viserEtLancerFlechette() {
		int secteur, zone;
		System.out.println("Dans quel secteur voulez-vous mettre la flechette ?");
		do{
			secteur = UtilitairesJeux.lireEntierComprisEntre(0, 25, "Vous n'avez pas rentré un bon numéro de secteur.");
			if((secteur <= 0 && secteur < 21) || secteur == 25) {
				System.out.println("Vous n'avez pas rentré un bon numéro de secteur.");
			}
		}while((secteur < 0 && secteur < 21) || secteur == 25);
		if(secteur == 25) {
			System.out.println("1 : Zone simple / 2 : Zone double");
			zone = UtilitairesJeux.lireEntierComprisEntre(1,2,"Vous n'avez pas rentré un bon numéro de zone.");
		}
		else if(secteur == 0) {
			zone = 0;
		}
		else {
			System.out.println("1 : Zone simple / 2 : Zone double / 3 : Zone triple");
			zone = UtilitairesJeux.lireEntierComprisEntre(1,3,"Vous n'avez pas rentré un bon numéro de zone.");
		}
		
		Flechette flechette = new Flechette(secteur, zone);
		
		return flechette;
	}
	
	@Override
	public void afficherMessageDebutJeu() {
		System.out.println("*****************************************************************");
		System.out.println("    Bienvenue au jeu de flechette - "+nomDuJeu);
		System.out.println("     programme par "+auteurs);
		System.out.println("*****************************************************************");
	}

	@Override
	public void afficherMessageFinJeu() {
		System.out.println("Le jeu est fini.");
	}

	@Override
	public void afficherJoueurDebutTour(Joueur joueur, int numeroTour) {
		System.out.println("C'est le début du tour " + numeroTour + " pour le joueur " + joueur.getNom());
	}
	
	@Override
	public void afficherJoueurFinTour(Joueur joueur, int numeroTour) {
		System.out.println("C'est la fin du tour " + numeroTour + " pour le joueur " + joueur.getNom());
	}
	
	@Override
	public void afficherGagnant(Joueur gagnant) {
		System.out.println("Le joueur gagnant est : " + gagnant.getNom());
	}
	
	@Override
	public void afficherJoueurs(Joueur[] joueurs) {
		for(Joueur joueur : joueurs) {
			System.out.println(joueur.getNom() + " : " + joueur.getPoints() + " / " + joueur.infoSupplementaires());
		}
	}

	@Override
	public void afficherDebutTour(int numeroTour) {
		System.out.println("C'est le début du tour : " + numeroTour);
	}

	@Override
	public void afficherFinTour(int numeroTour) {
		System.out.println("C'est la fin du tour : " + numeroTour);	
	}

	@Override
	public void afficherJoueurDebutFlechette(Joueur joueur, int numeroFlechette) {
		System.out.println("Flechette n°" + numeroFlechette + " pour " + joueur.getNom());	
	}

	@Override
	public void afficherJoueurFinFlechette(Joueur joueur, int numeroFlechette) {
		System.out.println("Flechette n°" + numeroFlechette + " lancée par " + joueur.getNom());
	}

	@Override
	public void afficherFlechette(Flechette flechette) {
		System.out.println("La flechette a été tirée en " + flechette.toString());		
	}

	@Override
	public void afficherMessageInfo(String message) {
		System.out.println(message);		
	}

	@Override
	public void afficherMessageWarning(String message) {
		System.out.println(message);				
	}

	@Override
	public void afficherRecommandationPourJoueur(Joueur joueur, Flechette[] flechetteRecommandee) {
		// TODO Auto-generated method stub
		System.out.println("La recommandation pour " + joueur.getNom() + " est de mettre la flechette en " + flechetteRecommandee.toString());
		
	}
	

}
