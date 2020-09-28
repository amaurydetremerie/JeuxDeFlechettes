package flechette;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ClassementPanel;
import ui.DashboardPanel;
import ui.FlechetteGUI;
import ui.InfoWarnPanel;
//Sound effects
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PanneauGraphique extends JFrame implements Panneau {
	private FlechetteGUI flechetteGUI;
	String nomDuJeu;
	String about = "Les prof. d'Algo de l'IPL ";
	private DashboardPanel dashboardPanel;
	private ClassementPanel classementPanel;
	private InfoWarnPanel infoWarnPanel;
	private int nombreJoueur;

	private PanneauGraphique(String nomDuJeu, String about, int nombreJoueur, FlechetteGUI flechetteGUI){
		this.flechetteGUI = flechetteGUI;
		this.nomDuJeu = nomDuJeu;
		this.about = about;
		this.nombreJoueur = nombreJoueur;
		this.classementPanel = new ClassementPanel(nombreJoueur);
		this.infoWarnPanel = new InfoWarnPanel();
		this.dashboardPanel = new DashboardPanel(nomDuJeu, about);
        addComponentsToPane(getContentPane());

	}
	@Override
	public void afficherMessageDebutJeu() {
		dashboardPanel.afficherMessageDebutJeu();
	}

	@Override
	public void afficherMessageFinJeu() {
		dashboardPanel.afficherMessageFinJeu();
	}

	@Override
	public void afficherJoueurDebutTour(Joueur joueur, int tours) {
		classementPanel.afficherJoueurDebutTour(joueur, tours);
	}
	
	@Override
	public void afficherJoueurFinTour(Joueur joueur, int tours) {
		classementPanel.afficherJoueurFinTour(joueur, tours);
		
		
	}
	@Override
	public void afficherGagnant(Joueur gagnant) {
		classementPanel.afficherGagnant(gagnant);
		if (gagnant!=null)
			playWinner();
		else
			playSquareGame();
	}

	@Override
	public void afficherJoueurs(Joueur[] joueurs) {
		classementPanel.afficherJoueurs(joueurs);

	}

	@Override
	public void afficherDebutTour(int numeroTour) {
		classementPanel.afficherDebutTour(numeroTour);
	}

	@Override
	public void afficherFinTour(int numeroTour) {
		classementPanel.afficherFinTour(numeroTour);
		playNewRound();
	}

	@Override
	public void afficherJoueurDebutFlechette(Joueur joueur, int essai) {
		classementPanel.afficherJoueurDebutFlechette(joueur, essai);
	}

	@Override
	public void afficherJoueurFinFlechette(Joueur joueur, int essai) {
		classementPanel.afficherJoueurFinFlechette(joueur, essai);
		afficherMessageInfo("");
		afficherMessageWarning("");
	}

	@Override
	public void afficherFlechette(Flechette flechette) {
		flechetteGUI.afficherFlechette(flechette);
		playFlechetteHit();
		
	}

	@Override
	public Flechette viserEtLancerFlechette() {
		Flechette f = flechetteGUI.nextFlechette();
		classementPanel.setLastFlechette(f);
		return f;
	}

	@Override
	public void afficherMessageInfo(String message) {
		infoWarnPanel.afficherMessageInfo(message);
	}

	@Override
	public void afficherMessageWarning(String message) {
		infoWarnPanel.afficherMessageWarning(message);
	}

	@Override
	public void afficherRecommandationPourJoueur(Joueur joueur, Flechette[] flechetteRecommandee) {
		if (flechetteRecommandee==null)
			return;
		playRecommandation();
		flechetteGUI.afficherRecommandationPourJoueur(joueur, flechetteRecommandee);
		String message = joueur.getNom()+": pour gangner, visez";
		if (flechetteRecommandee.length==1)
			message +=" le secteur";
		else
			message+= " un des secteurs: ";
		int nbNullFlechette = 0;
		for (Flechette f: flechetteRecommandee) {
			if (f==null) {
				nbNullFlechette++;
				continue;
			}
			message += f.toString().replace("[secteur,zone]=", "");
			message += /*"["+f.getSecteur()+","+f.getSecteur()+"]"+*/",";
		}
		if (nbNullFlechette==flechetteRecommandee.length)
			return;
		message = message.substring(0, message.length()-1)+".";
		afficherMessageInfo(message);
	}
	
	 private void addComponentsToPane(Container pane) {
	        
	        if (!(pane.getLayout() instanceof BorderLayout)) {
	            pane.add(new JLabel("Container doesn't use BorderLayout!"));
	            return;
	        }
	                
	        pane.add(infoWarnPanel, BorderLayout.PAGE_END);
	        pane.add(dashboardPanel, BorderLayout.PAGE_START);
	        pane.add(flechetteGUI, BorderLayout.CENTER);
	        pane.add(classementPanel, BorderLayout.LINE_END);
	        
	    }

	public static PanneauGraphique createPanneauGraphique (String nomDuJeu, String about, int nombreJoueur) {
		int size = 800;
		FlechetteGUI cible = new FlechetteGUI(size,size, Thread.currentThread());
    	final PanneauGraphique frame = new PanneauGraphique(nomDuJeu, about, nombreJoueur, cible);
    	cible.setPanneauGraphique(frame);
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
		return frame;
	}
	
	//Sound effects
	private void playFlechetteHit() {
		playClip(soundClips[0]);
	}
	private void playNewRound() {
		playClip(soundClips[1]);
	}
	private void playWinner() {
		playClip(soundClips[2]);
	}
	private void playRecommandation() {
		playClip(soundClips[3]);
	}
	private void playSquareGame() {
		playClip(soundClips[4]);
	}
	private void playClip( Clip clip )
	{
		if (clip==null)
			return;
	    if( clip.isRunning() ) clip.stop();
	            clip.setFramePosition( 0 );
	    clip.start();
	}
	private static Clip[] soundClips= new Clip[10];
	static {
		soundClips[0] = loadClip("/flechetteHit.wav");
		soundClips[1] = loadClip("/newRound.wav");
		soundClips[2] = loadClip("/winner.wav");
		soundClips[3] = loadClip("/recommandation.wav");
		soundClips[4] = loadClip("/squareGame.wav");
	}
	private static Clip loadClip( String filename )
	{
	    Clip in = null;

	    try
	    {
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream( PanneauGraphique.class.getResource( filename ) );
	        in = AudioSystem.getClip();
	        in.open( audioIn );
	    }
	    catch( Exception e )
	    {
	    	System.out.println("Erreur: "+e.getMessage());
	    }

	    return in;
	}


}
