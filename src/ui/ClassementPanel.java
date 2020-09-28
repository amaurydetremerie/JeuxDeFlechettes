package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import flechette.Flechette;
import flechette.Joueur;

public class ClassementPanel extends JPanel {
	private int ligne = 0;
	private int column = 6;
	private JLabel[][] labelListe;
	private InfoWarnPanel[] labelPourJoueurListe;
	private JPanel result = new JPanel();
	private Joueur[] listeJoueur;
	private JLabel toursLabel;

	private GridBagConstraints gbc = new GridBagConstraints();

	private void constructUI() {
		GridBagLayout gridbag = new GridBagLayout();

		result.setLayout(gridbag);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0 / 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		for (int j = 0; j < labelListe[0].length; j++) {
			gbc.gridx = j;
			gridbag.setConstraints(labelListe[0][j], gbc);
			result.add(labelListe[0][j]);
		}
		
		for (int i = 1; i < labelListe.length ; i ++) {
			gbc.gridy = 2*i-1;
			gbc.gridwidth = 1;
			for (int j = 0; j < labelListe[0].length; j++) {
				gbc.gridx = j;
				gridbag.setConstraints(labelListe[i][j], gbc);
				result.add(labelListe[i][j]);
			}
			//if (i / 2 < labelPourJoueurListe.length) {
				gbc.gridx = 1;
				gbc.gridy = 2*i;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.gridwidth = 5;
				gridbag.setConstraints(labelPourJoueurListe[i-1], gbc);
				result.add(labelPourJoueurListe[i-1]);
			//}
		}
	}

	private JLabel createHeader(String title, int w, int h) {
		JLabel lbl = new JLabel(title);
		lbl.setPreferredSize(new Dimension(w, h));
		return lbl;
	}

	public ClassementPanel(int ligne) {
		this.ligne = ligne;
		this.setLayout(new BorderLayout());

		labelListe = new JLabel[ligne + 1][column];
		labelListe[0][0] = createHeader("Classement", 100, 25);
		labelListe[0][1] = createHeader("Joueur", 100, 25);
		labelListe[0][2] = createHeader("Points", 100, 25);
		labelListe[0][3] = createHeader("Essai 1", 100, 25);
		labelListe[0][4] = createHeader("Essai 2", 100, 25);
		labelListe[0][5] = createHeader("Essai 3", 100, 25);
		for (int i = 1; i < labelListe.length; i++) {
			for (int j = 0; j < labelListe[0].length; j++) {
				labelListe[i][j] = new JLabel();
				labelListe[i][j].setOpaque(true);
				labelListe[i][j].setBackground(Color.white);
				labelListe[i][j].setPreferredSize(new Dimension(100, 25));
			}
		}
		labelPourJoueurListe = new InfoWarnPanel[ligne];
		for (int i = 0; i < labelPourJoueurListe.length; i++) {
			labelPourJoueurListe[i] = new InfoWarnPanel(14);
			labelPourJoueurListe[i].setPreferredSize(new Dimension(500, 25));
			//labelPourJoueurListe[i].afficherMessageInfo(i+" 20:3, 19:3, 18:3, 17:3, 16:3, 15:3, bull: 3");
		}
		constructUI();
		this.add(result, BorderLayout.PAGE_START);

		this.toursLabel = new JLabel();
		toursLabel.setFont(new Font(toursLabel.getFont().getName(), Font.BOLD, 36));
		toursLabel.setOpaque(true);
		toursLabel.setForeground(Color.DARK_GRAY);

		this.add(toursLabel, BorderLayout.CENTER);

	}

	public void afficherJoueurs(Joueur[] joueurs) {
		listeJoueur = joueurs;
		for (int i = 1; i < labelListe.length; i++) {
			labelListe[i][0].setText(i + "");
			labelListe[i][1].setText(joueurs[i - 1].getNom());
			labelListe[i][2].setText(joueurs[i - 1].getPoints() + "");
			labelPourJoueurListe[i-1].afficherMessageInfo(""+joueurs[i-1].infoSupplementaires());
		}
	}

	public void afficherGagnant(Joueur gagnant) {
		if (gagnant == null) {
			toursLabel.setText("Pas de gagnant. Essayez une autre fois");
			toursLabel.setForeground(Color.ORANGE);

			for (int i = 1; i < labelListe.length; i++) {
				for (int j = 0; j < labelListe[0].length; j++) {
					labelListe[i][j].setBackground(Color.ORANGE);
				}
			}
			return;
		}
		for (int i = 0; i < column; i++)
			labelListe[1][i].setBackground(Color.decode("#25B233"));
		toursLabel.setText("Félicitation " + gagnant.getNom() + "!");
		toursLabel.setForeground(Color.decode("#25B233"));
		for (int i = 2; i < labelListe.length; i++) {
			for (int j = 0; j < labelListe[0].length; j++) {
				labelListe[i][j].setBackground(Color.RED);
			}
		}
	}

	public void afficherJoueurFinTour(Joueur joueur, int tours) {
		int indiceJoueur = trouverIndice(joueur);
		if (indiceJoueur == -1)
			return;
		labelListe[indiceJoueur + 1][2].setText(joueur.getPoints() + "");
	}

	public void afficherJoueurDebutFlechette(Joueur joueur, int noFlechette) {
		int indiceJoueur = trouverIndice(joueur);
		if (indiceJoueur == -1)
			return;
		labelListe[indiceJoueur + 1][2 + noFlechette].setBackground(Color.ORANGE);
	}

	public void afficherJoueurFinFlechette(Joueur joueur, int noFlechette) {
		int indiceJoueur = trouverIndice(joueur);
		if (indiceJoueur == -1)
			return;
		labelListe[indiceJoueur + 1][2 + noFlechette].setBackground(Color.decode("#25B233"));
		/*int essayPoints = joueur.getPoints() - Integer.parseInt(labelListe[indiceJoueur + 1][2].getText());
		for (int i = 1; i < noFlechette; i++) {
			essayPoints = essayPoints - Integer.parseInt(labelListe[indiceJoueur + 1][2 + i].getText());
		}
		labelListe[indiceJoueur + 1][2 + noFlechette].setText(essayPoints + "");
		*/
		labelListe[indiceJoueur + 1][2 + noFlechette].setText(lastFlechette.donnerPoints() + "");
		majPointsJoueur();
	}
	private void majPointsJoueur() {
		for (int i=0; i<listeJoueur.length; i++) {
			Joueur j = listeJoueur[i];
			int oldPoints = Integer.parseInt(labelListe[i + 1][2].getText());
			int newPoints = j.getPoints();
			labelListe[ i + 1][2].setText(""+newPoints);
			if (oldPoints!=newPoints)
				labelListe[ i + 1][2].setBackground(Color.decode("#dd602e"));
			else
				labelListe[ i + 1][2].setBackground(Color.white);
			labelPourJoueurListe[i].afficherMessageInfo(""+j.infoSupplementaires());

		}
	}

	public void afficherJoueurDebutTour(Joueur joueur, int tours) {
		int indiceJoueur = trouverIndice(joueur);
		if (indiceJoueur == -1)
			return;

	}

	private int trouverIndice(Joueur joueur) {
		for (int i = 0; i < listeJoueur.length; i++) {
			if (listeJoueur[i] == joueur)
				return i;
		}
		return -1;
	}

	public void afficherDebutTour(int numeroTour) {
		toursLabel.setText("Tour " + numeroTour);
		for (int i = 1; i < labelListe.length; i++) {
			for (int j = 3; j < column; j++) {
				labelListe[i][j].setText("");
				labelListe[i][j].setBackground(Color.WHITE);

			}
		}
	}

	public void afficherFinTour(int numeroTour) {
		try {
			Thread.sleep(1500l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 1; i < labelListe.length; i++) {
			labelListe[i][2].setBackground(Color.WHITE);
			labelListe[i][3].setText("");
			labelListe[i][3].setBackground(Color.WHITE);
			labelListe[i][4].setText("");
			labelListe[i][4].setBackground(Color.WHITE);
			labelListe[i][5].setText("");
			labelListe[i][5].setBackground(Color.WHITE);
		}

	}

	Flechette lastFlechette;
	public void setLastFlechette(Flechette f) {
		lastFlechette = f;
		
	};

}
