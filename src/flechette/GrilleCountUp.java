/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public class GrilleCountUp extends Grille{
	
	public GrilleCountUp(int nombreJoueurs){
		super(nombreJoueurs);
	}

	@Override
	public JoueurCountUp donnerJoueur(int numero){
		return (JoueurCountUp)super.donnerJoueur(numero);
	}
	
}
