package up.mi.yt_am_bz.partage_butin.programme;

import java.io.File;
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
		while (!fichier.exists() || fichier.isDirectory());
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
			System.out.println("3 Afficher la solution actuelle");
			System.out.println("4 Sauvegarder");
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
						e.executeApproximationSolution();
						e.afficherSolution();
						System.out.println("(appuyez sur entree)");
						sc.nextLine();
						break;
					case 2:
						menuResolutionManuelle(sc, e);
						break;
					case 3:
						e.afficherSolution();
						System.out.println("(appuyez sur entree)");
						sc.nextLine();
						break;
					case 4:
						if (e.solutionExiste())
							menuSauvegarde(e,sc);
						else
							System.out.println("Il n'y a pas encore de solution.");
						break;
					default:
						System.out.println("Le choix " + choix + " est incorrect. (appuyez sur entree)");
						sc.nextLine();
						break;
				}
			}
			catch(NumberFormatException exception)
			{
				System.out.println("\"" + ligne + "\" est un choix invalide. (appuyez sur entree)");
				sc.nextLine();
			}
		}
		while (choix != 0);

	}

	private static void menuSauvegarde(Equipage e, Scanner sc)
	{
		String	fileName;

		System.out.println("\n****** Menu de sauvegarde ******");
		System.out.println("Dans quel fichier enregistrer la solution ?");
		fileName = sc.nextLine();
		SauvegardeFichier.sauvegarder(e, fileName);
		sc.nextLine();
	}

    /**
     * Methode affichant et gérant le menu de resolution manuelle,
     *@param e l'equipage deja parametre
     *@param sc le scanner du menu principal
     */
    private static void menuResolutionManuelle(Scanner sc, Equipage e)
	{
		int		choix = -1;
		String	ligne;

		if (!e.solutionExiste())
        	e.executeSolutionNaive();
		do
		{
			System.out.println(e);
            e.afficherSolution();
			System.out.println("(appuyez sur entree)");
			sc.nextLine();
            System.out.println("\n****** Resolution Manuelle ******");
			System.out.println("1 echanger objet");
			System.out.println("2 afficher le cout");
			System.out.println("3 Fin");
            System.out.println("\nQue voulez-vous faire ?");
            ligne = sc.nextLine();
			try
			{
				choix = Integer.parseInt(ligne);
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
						sc.nextLine();
						break;
				}
			}
			catch(NumberFormatException exception)
			{
				System.out.println("\"" + ligne + "\" est un choix invalide. (appuyez sur entree)");
				sc.nextLine();
			}
		}
		while (choix != 3);
	}

    /**
     * Methode intermediare du menuResolutionManuelle permettant l'echange des objets de deux pirates
     *@param équipage l'equipage dans lequel effectuer l'echange
     *@param sc le scanner du menu principal
     */
	private static void echangeObjets(Equipage equipage, Scanner sc)
	{
		Pirate		pirate1 = null;
		Pirate		pirate2 = null;
		String		nom;

		do
        {
            System.out.println("Echange des objets entre deux pirates.");
            System.out.print("Premier pirate pour l'echange : ");
			nom = sc.nextLine();
            pirate1 = equipage.getPirate(nom);
            if (pirate1 == null)
                System.out.println("Le pirate " + nom + " n'existe pas.");
            else
            {
                System.out.print("Second pirate pour l'echange : ");
				nom = sc.nextLine();
                pirate2 = equipage.getPirate(nom);
				if (pirate2 == null)
                	System.out.println("Le pirate " + nom + " n'existe pas.");
            }
        }
        while (pirate1 == null || pirate2 == null);
        equipage.echangeObjet(pirate1, pirate2);
	}

	public static void sauvegarder(Equipage e,Scanner sc)
	{
		sc.nextLine();
		System.out.print("Quel est le chemin vers le fichier de sauvegarde ? ");
		String file = sc.nextLine();
		SauvegardeFichier.sauvegarder(e, file);
	}
}
