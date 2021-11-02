import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		int			nbPirates;
		Equipage 	equipage;
		nbPirates = askNbPirates();
		equipage = new Equipage(nbPirates);
		// nbPirates = 4;
		menuPirate(nbPirates, equipage);
	}

	public static void relationPirate(Equipage equipage){
		Scanner		sc = new Scanner(System.in);
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
				verif = equipage.relationExiste(pirateJaloux1, pirateJaloux2);
				if(!verif)
				{
					equipage.ajouterRelation(pirateJaloux1, pirateJaloux2);
				}
				else 
					System.out.println("La relation existe deja ! ");
			} 
			while(verif == true);
		}	
		sc.close();
	}
	
	public static int askNbPirates()
	{
		Scanner	sc = new Scanner(System.in);
		int		nb = 0;

		do
		{
			System.out.print("Entrer le nombre de pirates : ");
			nb = sc.nextInt();
		}
		while (nb < 1 && nb > 26);
		System.out.println("L'equipage est compose de " + nb + " pirates.");
		sc.close();
		return (nb);
	}

	public static void menuPirate(int nbPirates, Equipage e) {
        Scanner		sc = new Scanner(System.in);
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
					relationPirate(e);
					e.afficherRelations();
					break;
				case 2:

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
		sc.close();
	}
	
}
