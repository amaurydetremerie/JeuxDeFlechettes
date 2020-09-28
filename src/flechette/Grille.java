/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public abstract class Grille {
	
	// La table contient les joueurs selon l'ordre du jeu
	private Joueur[] tableJoueurs;

	/**
	 * construit une table vide pouvant contenir nombreJoueurs joueurs
	 * @param nombreJoueurs le nombre de joueurs participants
	 * @throws IllegalArgumentException : nombreJoueurs invalide : minimum 1 joueur!
	 */
	public Grille(int nombreJoueurs) {
		if(nombreJoueurs<1)
			throw new IllegalArgumentException("Attention, minimum 1 joueur!");
		tableJoueurs = new Joueur[nombreJoueurs];
	}
	
	public int nombreJoueurs() {
		return tableJoueurs.length;
	}

	/**
	 * ajoute un joueur dans la table
	 * @param joueur le joueur a ajouter
	 * @param numero le numero d'ordre du joueur dans le jeu (numerotation commence a 1)
	 * @throws IllegalArgumentException : joueur ou numero invalide(s)
	 */
	public void ajouterJoueur(Joueur joueur, int numero){
		if(numero < 1 || numero > tableJoueurs.length)
			throw new IllegalArgumentException("Numéro non valide");
		if(joueur == null)
			throw new IllegalArgumentException("Objet joueur vide");
		
		tableJoueurs[numero-1] = joueur;
	}
	
	/**
	 * renvoie le joueur dont le numero est passe en parametre
	 * @param numero le numero d'ordre du joueur dans le jeu
	 * @return le joueur
	 * @throws IllegalArgumentException : numero invalide 
	 */
	public Joueur donnerJoueur(int numero){
		if(numero < 1 || numero > tableJoueurs.length)
			throw new IllegalArgumentException("Numéro non valide");
		return tableJoueurs[numero-1];
	}
	
	public String toString(){
		String ch ="Voici les points :";
		for (int i = 0; i < tableJoueurs.length; i++) {
			ch += "\n"+tableJoueurs[i];
		}
		return ch;
	}
	
	/**
	 * classement des joueurs (du meilleur au moins bon
	 * @return une table de joueurs
	 */
	public Joueur[] classement(){			
		Joueur[] classement = new Joueur[nombreJoueurs()];
		for(int i = 0; i < tableJoueurs.length; i++) {
			classement[i] = tableJoueurs[i];
		}
		for(int j = 0; j<classement.length; j++) {	
			for(int i = 0; i<(classement.length-1) ;i++) {
				Joueur joueur1, joueur2;
				joueur1 = classement[i];
				joueur2 = classement[i+1];
				if(joueur1.estMieuxClasse(joueur2)) {
					classement[i] = joueur2;
					classement[i+1] = joueur1;
				}
			}
		}
		return classement;
	}	
	
}
