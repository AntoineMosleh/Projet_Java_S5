import java.lang.module.FindException;
import java.util.Scanner;
public class Main 
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int nbPirates=creationEquipage(sc);
        Equipage equipage = new Equipage(nbPirates);
        //ajoutDesRelations(sc, equipage,nbPirates);
        //ajoutDesPreference(sc, equipage, nbPirates);
        menu(sc, equipage, nbPirates);
        sc.close();
    }
        
        /**
         * Methode demandant à l'utilisateur le nombres de pirates,permettant la creation de l'equipage
         * 
         */
        public static int creationEquipage(Scanner sc)
        {
            int nombrePirates;
            do
            {
                System.out.print("Veuillez entrer le nombres de pirates :) \n");
                 nombrePirates=sc.nextInt();
                if(nombrePirates<0 || nombrePirates>26)
                {
                    System.out.println("Le nombres de pirates ne peut etre negative ou exceder 26");
                }
            }while(nombrePirates<0 || nombrePirates>26);
            System.out.println("L'equipage est composé de "+nombrePirates+" pirates !");
            return nombrePirates;
        }


        /**
         * Methode permettant de rajouter une realtion "ne s'aime pas" entre les pirates
         * @param sc Scanner permettant la saisie
         * @param e represente l'equipage
         * @param nbPirates represente le nombres de pirates
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
            System.out.println("Veuillez saisir les differentes relations entre les pirates :");
            
            do //demande quel pirate est jaloux
            {
                System.out.println("Saisissez quel pirate est jaloux :");
                str = sc.nextLine();
                pirateJaloux1 = sc.next().charAt(0);
                verif1 = e.containList(pirateJaloux1);
                if (verif1==false)
                {
                    System.out.println("le pirate : "+pirateJaloux1+" n'existe pas ");
                }
                System.out.println("Vous avez choisis le pirate: "+ pirateJaloux1);

            }while(verif1==false);

            do //demande avec combien de pirates est il jaloux
            {
                System.out.println("Avec combien de pirates est-il jaloux ? ");
                nombrePiratesJaloux =sc.nextInt();
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

            for (int i=0;i<nombrePiratesJaloux;i++)//demande les pirates dont le piratJaloux1 est jaloux
            {
                do
                {
                    System.out.println("Designez le pirate jaloux numero "+ (i+1) + " : ");
                    str2 = sc.nextLine();
                    pirateJaloux2= sc.next().charAt(0);
                    if (pirateJaloux2 != pirateJaloux1 )
                    {
                        verif3= e.relationExiste(pirateJaloux1, pirateJaloux2);
                        if(verif3==false)
                        {
                            e.ajouterRelation(pirateJaloux1, pirateJaloux2);

                        }
                        else
                        {
                            System.out.println("La relation existe deja ! ");
                            i--; 
                        }
                    }
                    else
                    {
                        System.out.println("Le pirate ne peut être jaloux de lui meme ! ");
                        i--;

                    }

                }while(verif3==true);
            }

        }
        /**
         * Methode permettant de saisir la liste de preference d'un pirate
         * @param sc Scanner permettant la saisie
         * @param e represente l'equipage
         * @param nbPirates represente le nombres d'objet a saisir dans la liste de preference
         */
        public static void ajoutDesPreference(Scanner sc,Equipage e,int nbPirates)
        {
            int[] listeDePreference = new int[nbPirates];
            String str;
            char nomPirate;
            int objet;
            Boolean verif=false;
            Boolean verif2=false;
            Pirate pirate;
            System.out.println("Nous allons maintenant proceder a l'ajout des preference des pirates :)");
            do
            {
                System.out.println("Veuillez entrer le nom du pirate :");
                str =sc.nextLine();
                nomPirate = sc.next().charAt(0);
                verif=e.containList(nomPirate);
                if (verif==false)
                {
                    System.out.println("le pirate : "+nomPirate+" n'existe pas ");
                }
            }while(verif==false);
            do
            {
                pirate=e.findPirate(nomPirate);
                verif2=pirate.listIsVide();
                if(verif2==true)
                {
                    System.out.println("Veuillez maintenant saisir la liste de preference de "+ nomPirate +" contenant "+nbPirates+" objets separe par des espaces");
                    for (int i=0; i<nbPirates; i++)
                    {
                        objet = sc.nextInt();
                        listeDePreference[i] = objet;
                    }
                    e.ajoutPreferencePirate(nomPirate, listeDePreference);
                }
                else
                {
                    System.out.println("Le pirate a deja une liste de preference");
                }
            }while(verif2==false);
            System.out.print("Le pirate choisi,avec sa liste de preference: ");
            int[] tabTest;
            tabTest=pirate.getListePref();
            System.out.print(nomPirate+ " ");
            for(int j=0;j<tabTest.length;j++)
            {
                System.out.print(tabTest[j] + " ");
            }

        }

        public static boolean finPremierMenu(Equipage e)
        {
            boolean verification=false;
            


        }



        public static void menu(Scanner sc,Equipage e,int nombresDePirates)
        {
            int choix;
            boolean verification=false;
            do
            {
                System.out.println("1 ajouter une relation");
			    System.out.println("2 ajouter des preferences");
			    System.out.println("3 Fin");

                choix =sc.nextInt();
                switch(choix)
                {
                    case 1:
                        ajoutDesRelations(sc, e,nombresDePirates);
                        e.afficherRelations();
                        break;
                    case 2:
                        ajoutDesPreference(sc, e, nombresDePirates);
                        break;
                    case 3:
                        finPremierMenu();
                        break;
                    default:
                        System.out.println("Le choix " + choix + " n'existe pas.");
                        break;
                }


            }while (verification==false);
        }





        
    }
