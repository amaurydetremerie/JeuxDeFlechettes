/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

import java.util.Scanner;

public class JeuCricket {

	private static Scanner scanner = new Scanner(System.in);
	private static GrilleCricket grille;
	private static Panneau panneau;
	private static String auteurs = " Leblanc Macha et De Tremerie Amaury "; // mettez vos noms ici!

	public static void main(String[] args) {

		System.out.println("Bienvenue a un Cricket");
		System.out.print("Entrez le nombre de joueurs : ");
		int nbreJoueurs = UtilitairesJeux.lireEntierPositif("Le nombre de joueurs est de minimum 1");
		grille = new GrilleCricket(nbreJoueurs);
		
		System.out.println("Entrez les noms des joueurs selon l'ordre du jeu : ");
		for (int numJoueur = 1; numJoueur <= nbreJoueurs; numJoueur++) {
			System.out.print("Entrez le nom du joueur " + numJoueur + " : ");
			String nomJoueur = UtilitairesJeux.lireStringNonVide("Le nom doit contenir au moins une lettre");
			JoueurCricket joueur = new JoueurCricket(nomJoueur);
			grille.ajouterJoueur(joueur, numJoueur);
				}

		System.out.print("Entrez le nombre de tours : ");
		int nbreTours = UtilitairesJeux.lireEntierPositif("Le nombre de tours est de minimum 1");

		System.out.print("Voulez-vous utiliser une interface graphique?(O/N) : ");
		char utiliserGui = UtilitairesJeux.lireOouN("Tapez O ou N : ");

		if (utiliserGui == 'N')
			panneau = new PanneauTextuel("Cricket", auteurs);
		else
			panneau = PanneauGraphique.createPanneauGraphique("Cricket", auteurs, nbreJoueurs);

		// DEBUT JEU
		
		panneau.afficherMessageDebutJeu();
		panneau.afficherJoueurs(grille.classement());
		int j = 1;
		while(j <= nbreTours+1 && !grille.finJeu()) {
			faireTour(j);
			j++;
		}
		panneau.afficherGagnant(grille.donnerGagnant());
		panneau.afficherJoueurs(grille.classement());
		panneau.afficherMessageFinJeu();
	}

	private static void faireTour(int numeroTour) {
		JoueurCricket joueur;
		panneau.afficherDebutTour(numeroTour);
		int l = 1;
		while(!grille.finJeu() && l <= grille.nombreJoueurs()) {
			joueur = grille.donnerJoueur(l);
			panneau.afficherJoueurDebutTour(joueur, numeroTour);
			faireVolee(joueur);
			panneau.afficherJoueurFinTour(joueur, numeroTour);
			l++;
		}
		panneau.afficherFinTour(numeroTour);
	}

	private static void faireVolee(JoueurCricket joueur) {
		int i = 1;
		while(!grille.finJeu() && i<=3) {
			panneau.afficherJoueurDebutFlechette(joueur, i);
			Flechette flechette = panneau.viserEtLancerFlechette();
			panneau.afficherFlechette(flechette);
			if(joueur.secteurFermeON(flechette.getSecteur())) {
				grille.flechetteFermee(flechette);
			}
			else {
				joueur.SecteurAtteint(flechette);
			}
			panneau.afficherJoueurFinFlechette(joueur, i);
			i++;
		}
	}
}