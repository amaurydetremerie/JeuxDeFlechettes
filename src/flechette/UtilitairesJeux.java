/*
 * 
 * @author : Leblanc Macha, De Tremerie Amaury
 * 
 */
package flechette;

import java.util.Scanner;

import ui.FlechetteGUI;

public class UtilitairesJeux {
	
	private static Scanner scanner = new Scanner(System.in);
	
	
		
	/**
	 * lit au clavier un entier strictement positif 
	 * @param message le message a afficher si l'entier lu n'est pas strictement positif
	 * @return un entier strictement positif
	 */
	public static int lireEntierPositif(String message){
		int entier = scanner.nextInt();
		while(entier<=0){
			System.out.println(message);
			entier = scanner.nextInt();
		}
		return entier;
	}
	
	/**
	 * lit au clavier un entier strictement positif 
	 * @param message le message a afficher si l'entier lu n'est pas strictement positif
	 * @return un entier strictement positif
	 */
	public static int lireEntierComprisEntre(int min, int max, String message){
		int entier = scanner.nextInt();
		while(entier<min||entier>max){
			System.out.println(message);
			entier = scanner.nextInt();
		}
		return entier;
	}
	
	/**
	 * lit au clavier le caractere O ou N
	 * @param message le message a afficher si le caractere n'est pas O ou N
	 * @return O ou N
	 */
	public static char lireOouN(String message) {
		char c = scanner.next().charAt(0);
		while (c !='O' && c !='N'){
			System.out.println(message);
			c = scanner.next().charAt(0);
		}
		return c;	
	}
	
	/**
	 * lit au clavier une chaine de caracteres non vide
	 * @param message le message a afficher si la chaine de caractere lue 
	 * @return une chaine de caracteres non vide
	 */
	public static String lireStringNonVide(String message) {
		String ch = scanner.next();
		while(ch.equals("")){
			System.out.println(message);
			ch = scanner.next();
		}
		return ch;
	}
	

	
	
	
}
