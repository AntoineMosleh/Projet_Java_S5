package up.mi.yt_am_bz.partage_butin.programme;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import up.mi.yt_am_bz.partage_butin.pirates.*;

public class Main 
{
    public static void main(String[] args)
    {
        String		fichier;
        Equipage	equipage;
        Scanner sc = new Scanner(System.in);

		if (args.length > 0)
			fichier = args[0];
		else
			fichier = choixFichier(sc);
		equipage = LectureFichier.lecture(fichier);
        // menu1(sc, equipage, nbPirates);
		System.out.println("L'equipage a bien ete forme !");
        menu(sc, equipage);
        sc.close();
    }

	private static String choixFichier(Scanner sc)
	{
		File	fichier;

		do
		{
			System.out.print("Veuillez entrer le nom du fichier : ");
			fichier = new File(sc.nextLine());
		}
		while (!fichier.exists());
		return fichier.getPath();
	}

	private static void menu(Scanner sc, Equipage e)
	{
		int		choix = -1;
		String	ligne;
		do
		{
			System.out.println("\n****** EQUIPAGE ******");
			System.out.println(e.toString());
			System.out.println("\n****** MENU ******");
			System.out.println("1 Resolution automatique");
			System.out.println("2 Resolution manuelle");
			System.out.println("3 Sauvegarder");
			System.out.println("0 Fin\n");
			System.out.println("Que voulez-vous faire ?");
			ligne = sc.nextLine();
			try
			{
				choix = Integer.parseInt(ligne);
				switch(choix)
				{
					case 0:
						System.out.println("Au revoir !");
						break;
					case 1:
						break;
					case 2:
						menuResolutionManuelle(sc, e);
						break;
					case 3:
						break;
					default:
						System.out.println("Le choix " + choix + " est incorrect. (appuyez sur entree)");
						sc.nextLine();
						break;
				}
			}
			catch(NumberFormatException exception)
			{
				System.out.println("\"" + ligne + "\" est un choix invalide.");
			}
		}
		while (choix != 0);

	}

    /**
     * Methode affichant et gérant le menu de resolution manuelle,
     *@param e l'equipage deja parametre
     *@param sc le scanner du menu principal
     */
    private static void menuResolutionManuelle(Scanner sc, Equipage e)
	{
		int			choix;

        // e.solutionNaive();
		do
		{
            // e.afficherSolution();
            System.out.println("\n****** Resolution Manuelle ******");
			System.out.println("1 echanger objet");
			System.out.println("2 afficher le cout");
			System.out.println("3 Fin");
            System.out.println("\nQue voulez-vous faire ?");
			choix = sc.nextInt();
            sc.nextLine();
			switch(choix)
			{
				case 1:
                    echangeObjets(e, sc);
					break;
				case 2:
                    e.afficherCout();
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
	}

    /**
     * Méthode intermediare du menu2 permettant l'échange des objets de deux pirates
     *@param équipage l'equipage dans lequel effectuer l'échange
     *@param sc le scanner du menu principal
     */
	private static void echangeObjets(Equipage equipage, Scanner sc)
	{
		Pirate    pirate1 = null;
		Pirate    pirate2 = null;
        // boolean verif;

        // pirate1 = ' ';
        // pirate2 = ' ';
        do
        {
            System.out.println("Echange des objets entre deux pirates.");
            System.out.print("Premier pirate pour l'echange : ");
            pirate1 = equipage.getPirate(sc.next());
            if (pirate1 == null)
                System.out.println("Le pirate " + pirate1 + " n'existe pas.");
            else
            {
                System.out.print("Second pirate pour l'echange : ");
                pirate2 = equipage.getPirate(sc.next());
            }
        }
        while (pirate1 == null || pirate2 == null);
        equipage.echangeObjet(pirate1, pirate2);
        //     pirate1 = sc.next();
        //     if (!equipage.containList(pirate1))
        //         System.out.println("Le pirate " + pirate1 + " n'existe pas.");
        //     else
        //     {
        //         System.out.print("Second pirate pour l'echange : ");
        //         pirate2 = sc.next().charAt(0);
        //     }
        //     sc.nextLine();
        //     verif = equipage.containList(pirate1) && equipage.containList(pirate2);
        // }
        // while (!verif);
        // equipage.echangeObjet(pirate1, pirate2);
	}
}
