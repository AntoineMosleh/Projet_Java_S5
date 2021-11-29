import java.util.List;
import java.util.Scanner;

public class Main 
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int nbPirates=creationEquipage(sc);
        Equipage equipage = new Equipage(nbPirates);

        menu1(sc, equipage, nbPirates);
        menu2(sc, equipage);
        sc.close();
    }
        
    /**
     * Méthode demandant à l'utilisateur le nombre de pirates de l'équipage
     */
    public static int creationEquipage(Scanner sc)
    {
        int nombrePirates;
        do
        {
            System.out.print("Veuillez entrer le nombres de pirates :) \n");
            nombrePirates=sc.nextInt();
            if(nombrePirates<=0 || nombrePirates>26)
            {
                System.out.println("Le nombres de pirates ne peut etre negative,nul ou exceder 26");
            }
        }while(nombrePirates<=0 || nombrePirates>26);
        System.out.println("L'equipage est compose de "+nombrePirates+" pirates !");
        sc.nextLine();
        return nombrePirates;
    }


    /**
     * Méthode permettant de rajouter une realtion "ne s'aime pas" entre les pirates
     * @param sc Scanner permettant la saisie
     * @param e represente l'equipage
     * @param nbPirates représente le nombres de pirates
     */
    public static void ajoutDesRelations(Scanner sc,Equipage e,int nbPirates)
    {
        String str;
        String str2;
        char pirateJaloux1;
        char pirateJaloux2;
        boolean verif1=false;
        boolean verif2=false;
        boolean verif3=false;
        int nombrePiratesJaloux;
        int i=0;
        System.out.println("Nous allons proceder a la saisie des differentes relations entre les pirates :");
        System.out.println(e.compoEquipage());
        do //demande quel pirate est jaloux
        {
            System.out.println("Saisissez quel pirate est jaloux :");
            str = sc.nextLine();
            pirateJaloux1 = str.charAt(0);
            verif1 = e.containList(pirateJaloux1);
            if (verif1==false)
            {
                System.out.println("le pirate : "+pirateJaloux1+" n'existe pas ");
            }
            else
            {
                System.out.println("Vous avez choisi le pirate: "+ pirateJaloux1);
            }

        }while(verif1==false);

        do //demande avec combien de pirates est il jaloux
        {
            System.out.println("De combien de pirates est-il jaloux ? ");
            nombrePiratesJaloux =sc.nextInt();
            sc.nextLine();
            if (nombrePiratesJaloux<= nbPirates-1)
            {
                verif2 = true;
            }
            if(verif2==false)
            {
                System.out.println("Le pirate ne peut posseder plus de mauvaises relations que de pirates ! ");
            }

        }while(verif2==false);
        System.out.println("Veuillez saisir les pirates que "+pirateJaloux1+" n'aime pas :");

        while(i<nombrePiratesJaloux)//demande les pirates dont le piratJaloux1 est jaloux
        {
            do
            {
                System.out.println("Designez le pirate jaloux numero "+ (i+1) + " : ");
                str2 = sc.nextLine();
                pirateJaloux2= str2.charAt(0);
                if (pirateJaloux2 != pirateJaloux1 )
                {
                    verif3= e.relationExiste(pirateJaloux1, pirateJaloux2);
                    if(verif3==false)
                    {
                        e.ajouterRelation(pirateJaloux1, pirateJaloux2);
                        i++;
                    }
                    else
                    {
                        System.out.println("La relation existe deja ! ");
                    }
                }
                else
                {
                    System.out.println("Le pirate ne peut etre jaloux de lui meme ! ");
                }

            }while(verif3==true);
        }
    }

    /**
     * Méthode permettant de saisir la liste de préférence d'un pirate
     * @param sc Scanner permettant la saisie
     * @param e represente l'equipage
     * @param nbPirates représente le nombre d'objets à saisir dans la liste de préférences
     */
    public static void ajoutDesPreference(Scanner sc,Equipage e,int nbPirates)
    {
        int[] listeDePreference = new int[nbPirates];
        String str;
        char nomPirate;
        int objet;
        boolean verif=false;
        boolean verif2=false;
        boolean listePrefIncorrecte;
        Pirate pirate = null;

        System.out.println("Nous allons maintenant proceder a l'ajout des preferences pour les pirates :)");
        System.out.println(e.compoEquipage());
        do
        {
            System.out.println("Veuillez entrer le nom du pirate :");
            str =sc.nextLine();
            nomPirate = str.charAt(0);
            verif = e.containList(nomPirate);
            if (verif==false)
            {
                System.out.println("le pirate "+nomPirate+" n'existe pas ");
            }
            else
            {
                pirate = e.findPirate(nomPirate);
                verif2 = pirate.listIsVide();
            }
            if (verif && verif2 == false)
                System.out.println("Le pirate a deja une liste de preference");
        }
        while(verif==false || verif2 == false);
        do
        {
            System.out.println("Veuillez maintenant saisir la liste de preference de "+ nomPirate +" contenant "+nbPirates+" objets separe par des espaces");
            //System.out.println(line);
            listePrefIncorrecte = false;
            for (int i=0; i<nbPirates; i++)
            {
                objet = sc.nextInt(); //line.charAt(i*2)- '0';
                if ( objet<= nbPirates && objet>0)
                {
                    listeDePreference[i] = objet;
                }
                else
                {
                    System.out.println("L'objet "+objet+" est incorrect");
                    i = nbPirates; //Sortir de la boucle prematurement
                    listePrefIncorrecte = true;
                }
            }
            sc.nextLine();
        }
        while (listePrefIncorrecte);
        e.ajoutPreferencePirate(nomPirate, listeDePreference);

        System.out.print("Le pirate choisi,avec sa liste de preference: ");
        int[] tabTest;
        tabTest=pirate.getListePref();
        System.out.print(nomPirate+ " ");
        for(int j=0;j<tabTest.length;j++)
        {
            System.out.print(tabTest[j] + " ");
        }
        System.out.println("\n");

    }

    /**
     * Méthode permettant de mettre fin au premier menu si la liste de préférences de tous les membres de l'equipage est remplie
     * @param e represante l'equipage
     * @return true si le premier menu peut prendre fin,false si non
     */
    public static boolean finPremierMenu(Equipage e)
    {
        return (e.equipageComplet());
    }

    /**
     * Méthode permettant d'afficher et de gérer le premier menu
     *@param sc le scanner du menu principal
     *@param e l'équipage a paramétrer
     *@param nombreDePirates le nombre de pirates*/
    public static void menu1(Scanner sc,Equipage e,int nombreDePirates)
    {
        int choix;
        boolean verification=false;
        do
        {
            System.out.println("1 ajouter une relation");
            System.out.println("2 ajouter des preferences");
            System.out.println("3 Fin \n");
            System.out.println("Que souhaitez vous faire ?");

            choix =sc.nextInt();
            sc.nextLine();
            switch(choix)
            {
                case 1:
                    ajoutDesRelations(sc, e,nombreDePirates);
                    //e.afficherRelations();
                    break;
                case 2:
                    if (!e.equipageComplet())
                        ajoutDesPreference(sc, e, nombreDePirates);
                    else
                        System.out.println("Toutes les preferences ont deja ete entrees.");
                    break;
                case 3:
                    if (!(verification = finPremierMenu(e)))
                        System.out.println(" \n Les pirates ne possedent pas tous une liste de preference \n");
                    break;
                default:
                    System.out.println("Le choix " + choix + " n'existe pas.");
                    break;
            }

        }while (verification==false);
    }

    /**
     * Méthode affichant et gérant le deuxieme menu, après que l'equipage
     * ait ete paramétré
     *@param e l'equipage déjà paramétré
     *@param sc le scanner du menu principal
     */
    private static void menu2(Scanner sc, Equipage e)
	{
		int			choix;

        e.solutionNaive();
		do
		{
            e.afficherSolution();
            System.out.println();
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
		char    pirate1;
		char    pirate2;
        boolean verif;

        pirate1 = ' ';
        pirate2 = ' ';
        do
        {
            System.out.println("Echange des objets entre deux pirates.");
            System.out.print("Premier pirate pour l'echange : ");
            pirate1 = sc.next().charAt(0);
            if (!equipage.containList(pirate1))
                System.out.println("Le pirate " + pirate1 + " n'existe pas.");
            else
            {
                System.out.print("Second pirate pour l'echange : ");
                pirate2 = sc.next().charAt(0);
            }
            sc.nextLine();
            verif = equipage.containList(pirate1) && equipage.containList(pirate2);
        }
        while (!verif);
        equipage.echangeObjet(pirate1, pirate2);
	}
}
