package ui;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import flechette.Joueur;

public class DashboardPanel extends JPanel{
	private JLabel messageLable = new JLabel();
	String nomDuJeu;
	String about = "Les prof. d'algo de l'IPL ";
	
	public DashboardPanel(String nomDuJeu, String about) {
		this.nomDuJeu = nomDuJeu;
		this.about = about;
		this.add(messageLable);
	}
	public void afficherMessageDebutJeu() {
		StringBuilder sb = new StringBuilder();
		sb.append("***********************");
		
		sb.append(" Bienvenue au jeu de flechette - "+nomDuJeu);
		sb.append(" programme par "+about);
		sb.append("***********************");
		messageLable.setText(sb.toString());
	}

	public void afficherMessageFinJeu() {
		StringBuilder sb = new StringBuilder();
		sb.append("***********************");
		sb.append(" Le jeu de flechettes - "+nomDuJeu+" est termine");
		sb.append(" A bientot");
		sb.append(" "+about);
		sb.append("***********************");
		messageLable.setText(sb.toString());
	}
}
