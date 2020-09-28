package flechette;

public interface Panneau {
	void afficherMessageDebutJeu();
	void afficherMessageFinJeu();
	void afficherJoueurDebutTour(Joueur joueur, int numeroTour);
	void afficherJoueurFinTour(Joueur joueur, int numeroTour);
//	void afficherGagniantEtPerdants(Joueur gagnant, ArrayList<Joueur> listPerdants);
	void afficherGagnant(Joueur gagnant);
	void afficherJoueurs(Joueur[] joueurs);
	void afficherDebutTour(int numeroTour);
	void afficherFinTour(int numeroTour);
	void afficherJoueurDebutFlechette(Joueur joueur, int numeroFlechette);
	void afficherJoueurFinFlechette(Joueur joueur, int numeroFlechette);
	void afficherFlechette(Flechette flechette);
	Flechette viserEtLancerFlechette();
	void afficherMessageInfo(String message);
	void afficherMessageWarning(String message);
	void afficherRecommandationPourJoueur(Joueur joueur, Flechette[] flechetteRecommandee);
}
