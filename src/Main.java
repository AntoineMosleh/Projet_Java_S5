import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.LimitExceededException;

public class Main
{
	public static void main(String[] args)
	{
		Scanner		sc = new Scanner(System.in);
		int			nbPirates;
		Equipage 	equipage;
		nbPirates = askNbPirates(sc);
		equipage = new Equipage(nbPirates);
		// nbPirates = 4;
		menuPirate(nbPirates, equipage,sc);
		sc.close();
	}

	public static void relationPirate(Equipage equipage,Scanner sc){
		//Scanner		sc = new Scanner(System.in);
		char 		pirateJaloux1;
		int 		nombrePirate; 
		char 		pirateJaloux2;
		boolean 	verif;
		
		do 
		{
			System.out.println("Designez le pirate jaloux : ");
			pirateJaloux1 = sc.next().charAt(0);
			verif = equipage.containList(pirateJaloux1);
			if(verif == false)
				System.out.println("Le pirate " + pirateJaloux1 + " n'existe pas");
		} 
		while (verif == false);
		do
		{
			System.out.println("Indiquez le nombre de pirates dont il est jaloux : ");
			nombrePirate = sc.nextInt();
			if(nombrePirate<equipage.getListePirate().size()-1)
				verif = true ;
			if(verif == false)
				System.out.println("Le pirate ne peut posseder plus de mauvaises relations que de pirates ! ");
		} 
		while(verif == false);
		for (int i=0; i<nombrePirate; i++)
		{	
			do
			{	
				System.out.println("Designez le pirate jaloux numero " + (i+1) + " : ");
				pirateJaloux2 = sc.next().charAt(0);
				if(pirateJaloux1!=pirateJaloux2)
				{
					verif = equipage.relationExiste(pirateJaloux1, pirateJaloux2);
					if(!verif)
					{
						equipage.ajouterRelation(pirateJaloux1, pirateJaloux2);
					}
					else 
						System.out.println("La relation existe deja ! ");
				}
				else
					System.out.println("Le pirate ne peut être jaloux de lui meme ! ");
			} 
			while(verif == true);
		}	
		//sc.close();
	}


	public static void preferencePirate(Equipage equipage, Scanner sc,int nombreDePirates)
	{		
		//Scanner 		sc = new Scanner(System.in).useDelimiter("\s+");
		//sc.useDelimiter("\s+");
		char 					nom;
		int[]					pref = new int[nombreDePirates]; // Considerons qu'il n'y ait pas autant d'objets que de pirates
		int 					nombre;

		System.out.println("Veuillez entrer le nom du pirate");
		nom = sc.next().charAt(0);
		System.out.println("Veuillez entrer la liste de preference contenant " + nombreDePirates + " separe par des espaces : ");
		for (int i=0; i<nombreDePirates; i++)
		{
			nombre = sc.nextInt();
			pref[i] = nombre;
		}

		equipage.ajoutPreferencePirate(nom, pref);

		/*char nomlist;
		List<Pirate> list = new ArrayList<>();
		int[] listpref = new int[nombreDePirates];
		list = equipage.getListePirate();
		for(int k=0; k<list.size(); k++)
		{
			nomlist = list.get(k).getNomPirate();
			if (nom == nomlist)
			{	
				listpref = list.get(k).getListePref();
				for (int m=0; m<listpref.length; m++)
				{
					System.out.print(listpref[m]+" ");
				}
			}
		}*/
	}


	public static void pirateEchange(Equipage equipage, Scanner sc)
	{	
		//Scanner sc = new Scanner(System.in);
		char pirate1;
		char pirate2;
		System.out.println("Veuillez indiquer le premier pirate a proceder a l'echange");
		pirate1 = sc.next().charAt(0);
		System.out.println("Veuillez indiquer le deuxieme pirate a proceder a l'echange");
		pirate2 = sc.next().charAt(0);
		equipage.echangeObjet(pirate1, pirate2);
	}
	
	public static int askNbPirates(Scanner sc)
	{
		//Scanner	sc = new Scanner(System.in);
		int		nb;
		do
		{	
			System.out.print("Entrer le nombre de pirates : ");
			nb = sc.nextInt();
			if (nb < 0 || nb >26)
				System.out.println("Le nombre de pirate composant l'equipage ne peut etre negatif ou exceder 26 ");
		}
		while (nb < 0 || nb >26);
		System.out.println("L'equipage est compose de " + nb + " pirates.");
		//sc.close();
		return (nb);
	}

	public static void menuPirate(int nbPirates, Equipage e,Scanner sc) {
        //Scanner		sc = new Scanner(System.in);
		int			choix;
		// e.ajouterRelation('A','B');
		// e.ajouterRelation('B','C');
		// e.ajouterRelation('B','D');
		// int[] pref = new int[nbPirates];
		// pref[0] = 1;
		// pref[1] = 2;
		// pref[2] = 3;
		// pref[3] = 4;
		// e.ajoutPreferencePirate('A', pref);
		// pref[0] = 1;
		// pref[1] = 3;
		// pref[2] = 2;
		// pref[3] = 4;
		// e.ajoutPreferencePirate('B', pref);
		// pref[0] = 3;
		// pref[1] = 2;
		// pref[2] = 1;
		// pref[3] = 4;
		// e.ajoutPreferencePirate('C', pref);
		// pref[0] = 1;
		// pref[1] = 4;
		// pref[2] = 2;
		// pref[3] = 3;
		// e.ajoutPreferencePirate('D', pref);
		// e.solutionNaive();
		// e.afficherSolution();
		do
		{
			System.out.println("1 ajouter une relation");
			System.out.println("2 ajouter des preferences");
			System.out.println("3 Fin");
			choix = sc.nextInt();
			switch(choix)
			{
				case 1:
					relationPirate(e,sc);
					e.afficherRelations();
					break;
				case 2:
				    preferencePirate(e, sc, nbPirates);					
					break;
				case 3:
					System.out.println("Au revoir !");
					break;
				default:
					System.out.println("Le choix " + choix + " n'existe pas.");
					break;
			}
		}
		while (choix != 3);
		//sc.close();
	}
	public static void menuSolution(Equipage e, Scanner sc)
	{
		//Scanner		sc = new Scanner(System.in);
		int			choix;
		do
		{
			System.out.println("1 echanger objet");
			System.out.println("2 afficher");
			System.out.println("3 Fin");
			choix = sc.nextInt();
			switch(choix)
			{
				case 1:
					pirateEchange(e, sc);
					break;
				case 2:

					break;
				case 3:

					break;
				default:
					System.out.println("Le choix " + choix + " n'existe pas.");
					break;
			}
		}
		while (choix != 3 );
		//sc.close();
	}
	
}
