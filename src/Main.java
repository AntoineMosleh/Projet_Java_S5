import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		int	nbPirates;

		nbPirates = askNbPirates();
		// nbPirates = 4;
		menuPirate(nbPirates);
	}

	public static int askNbPirates()
	{
		Scanner	in = new Scanner(System.in);
		int		nb = 0;

		do
		{
			System.out.print("Entrer le nombre de pirates : ");
			nb = in.nextInt();
		}
		while (nb < 1 && nb > 26);
		System.out.println("L'équipage est composé de " + nb + " pirates.");
		return (nb);
	}

	public static void menuPirate(int nbPirates) {
        Scanner		sc = new Scanner(System.in);
		int			choix;
		Equipage	e = new Equipage(nbPirates);
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
			System.out.println("2 ajouter des préférences");
			System.out.println("3 Fin");
			choix = sc.nextInt();
			switch(choix)
			{
				case 1:

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
