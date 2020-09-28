/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

public class Flechette {
	
	private int secteur; // 0 : hors cible  25 : bulle
	private int zone; 	// -1: simple interne 1 simple externe , 2 double, 3 triple

	
	public Flechette(int secteur, int zone) {
		this.secteur = secteur;
		this.zone = zone;
	}

	public int getSecteur() {
		return secteur;
	}
	
	public int getZone() {
		return zone;
	}

	public int donnerPoints(){
		if (zone==-1)//Zone interne.
			return secteur;
		return secteur*zone;
	}
	
	@Override
	public String toString() {
		if(secteur==0)
			return "hors cible";
		if(secteur==25){
			if(zone==1)
				return "bull ext";
			return"bull";
		}
		return"[secteur,zone]="+"["+secteur+","+zone+"]";
	}
	
	
}
