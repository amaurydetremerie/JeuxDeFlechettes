/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

import java.util.Scanner;

public class JeuXX1 {

	private static Scanner scanner = new Scanner(System.in);
	private static GrilleXX1 grille;
	private static Panneau panneau;
	private static String auteurs = " Leblanc Macha et De Tremerie Amaury ";
	private static boolean gagnant = false;

	public static void main(String[] args) {

		System.out.println("Bienvenue a un XX1");
		
		System.out.print("Entrez le nombre de joueurs : ");
		int nbreJoueurs = UtilitairesJeux.lireEntierPositif("Le nombre de joueurs est de minimum 1");
		grille = new GrilleXX1(nbreJoueurs);

		System.out.println("Voulez vous jouer une partie à 501 points ?(O/N)");
		char utiliser501point = UtilitairesJeux.lireOouN("Tapez O ou N : ");
		
		if(utiliser501point == 'O') {
			System.out.println("Entrez les noms des joueurs selon l'ordre du jeu : ");
			for (int numJoueur = 1; numJoueur <= nbreJoueurs; numJoueur++) {
				System.out.print("Entrez le nom du joueur " + numJoueur + " : ");
				String nomJoueur = UtilitairesJeux.lireStringNonVide("Le nom doit contenir au moins une lettre");
				JoueurXX1 joueur = new JoueurXX1(nomJoueur);
				grille.ajouterJoueur(joueur, numJoueur);
			}
		}
		else {
			System.out.println("Avec combien de points voulez-vous jouer ?");
			int pointPartie;
			do{
				pointPartie = UtilitairesJeux.lireEntierPositif("Le nombre doit être supérieur à 1.");
			}while(pointPartie <= 1);
			System.out.println("Entrez les noms des joueurs selon l'ordre du jeu : ");
			for (int numJoueur = 1; numJoueur <= nbreJoueurs; numJoueur++) {
				System.out.print("Entrez le nom du joueur " + numJoueur + " : ");
				String nomJoueur = UtilitairesJeux.lireStringNonVide("Le nom doit contenir au moins une lettre");
				JoueurXX1 joueur = new JoueurXX1(nomJoueur, pointPartie);
				grille.ajouterJoueur(joueur, numJoueur);
			}
		}

		System.out.print("Entrez le nombre de tours : ");
		int nbreTours = UtilitairesJeux.lireEntierPositif("Le nombre de tours est de minimum 1");

		System.out.print("Voulez-vous utiliser une interface graphique?(O/N) : ");
		char utiliserGui = UtilitairesJeux.lireOouN("Tapez O ou N : ");

		if (utiliserGui == 'N')
			panneau = new PanneauTextuel("XX1", auteurs);
		else
			panneau = PanneauGraphique.createPanneauGraphique("XX1", auteurs, nbreJoueurs);
		
		// DEBUT JEU
		
		panneau.afficherMessageDebutJeu();
		panneau.afficherJoueurs(grille.classement());
		int j = 1;
		while(!gagnant && j <= nbreTours && !grille.finJeu()) {
			faireTour(j);
			panneau.afficherJoueurs(grille.classement());
			j++;
		}
		panneau.afficherGagnant(grille.donnerGagnant());
		panneau.afficherJoueurs(grille.classement());
		panneau.afficherMessageFinJeu();
	}

	private static void faireTour(int numeroTour) {
		JoueurXX1 joueur;
		panneau.afficherDebutTour(numeroTour);
		int l = 1;
		while(!gagnant && l <= grille.nombreJoueurs()) {
			joueur = grille.donnerJoueur(l);
			panneau.afficherJoueurDebutTour(joueur, numeroTour);
			faireVolee(joueur);
			panneau.afficherJoueurFinTour(joueur, numeroTour);
			l++;
		}
		panneau.afficherFinTour(numeroTour);
	}

	private static void faireVolee(JoueurXX1 joueur) {
		int i = 1;
		int pointsSauvegarde = joueur.getPoints();
		while(!gagnant && i<=3) {
			if(joueur.getPoints() == 1 && i == 1) {
				return;
			}
			int points = joueur.getPoints();
			if(joueur.flechetteRecommandee() != null) {
				panneau.afficherRecommandationPourJoueur(joueur, joueur.flechetteRecommandee());
			}
			panneau.afficherJoueurDebutFlechette(joueur, i);
			Flechette flechette = panneau.viserEtLancerFlechette();
			panneau.afficherFlechette(flechette);
			if(points < flechette.donnerPoints()) {
				panneau.afficherJoueurFinFlechette(joueur, i);
				joueur.setPoints(pointsSauvegarde);
				return;
			}
			if(points > flechette.donnerPoints()) {
				joueur.retirerPoints(flechette.donnerPoints());
			}
			if(points == flechette.donnerPoints()) {
				if(flechette.getZone() == 2) {
					joueur.retirerPoints(flechette.donnerPoints());
					gagnant = true;
				}
				else {
					panneau.afficherJoueurFinFlechette(joueur, i);
					joueur.setPoints(pointsSauvegarde);
					return;
				}
			}
			panneau.afficherJoueurFinFlechette(joueur, i);
			i++;
		}
	}
}