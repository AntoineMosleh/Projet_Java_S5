package up.mi.yt_am_bz.partage_butin.programme;

// import java.io.File;
import java.io.*;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Arrays;

import up.mi.yt_am_bz.partage_butin.pirates.*;

/**
 * Gestion du parsing des fichiers
 */
public class LectureFichier
{

    /**
     * Methode permettant la lecture du fichier en parametre 
     * @param file le fichier
     * @return l'equipage apres le parsing du fichier
     */
    public static Equipage lecture(String file)
    {
        BufferedReader bf = null;
        Pirate pirate = null;
        Tresor objet = null;
        int nbPirates = comptagePirates(file);
        /*Compteur permettant de connaitre l'ordre du pirate a ajouter*/
        int ordrePirate = 0;
        Equipage equipage = new Equipage(nbPirates);
        try
        {
            bf = new BufferedReader(new FileReader(file));
            String line;
            while((line=bf.readLine())!=null)
            {
                if (line.startsWith("pirate"))
                {
                    pirate = parserPirate(line, nbPirates, ordrePirate++);
                    //pirates.add(pirate);
                    //ameliorer avec nouvelle methode equipage.ajoutPirate()
                    equipage.ajoutPirate(pirate);
                }
                if (line.startsWith("objet"))
                {
                    objet = parserObjet(line);
                    equipage.ajoutTresorDispo(objet);
                    // objets.add(objet);
                }
                if (line.startsWith("deteste"))
                {
                    parserDeteste(line, equipage);
                }
                if (line.startsWith("preferences"))
                {
                    parserPreferences(line, nbPirates, equipage);
                }
            }
            bf.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getMessage());
			System.exit(1);
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
			System.exit(1);
        }
        return equipage;
    }

    /**
     * Methode permettant de compter le nombre de pirates de l'equipage a partir du fichier donné en parametre
     * @param file le fichier
     * @return
     */
    private static int comptagePirates(String file)
    {
        int nombresPirates=0;
        BufferedReader bf=null;
        try
        {
            bf = new BufferedReader(new FileReader(file));
            String line;
            while((line=bf.readLine())!=null)
            {
                if (line.startsWith("pirate"))
                {
                    nombresPirates++;
                }
            }
            bf.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getMessage());
			System.exit(1);
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
			System.exit(1);
        }
        return nombresPirates;
    }

    /**
     * Methode permettant d'instancier un objet de la classe Pirate a partir d'une chaine de caractere 
     * @param line chaine de caractere de la forme pirate(nom_pirate)
     * @param nbPirates le nombre de pirates
     * @param ordrePirate l'ordre du pirate
     * @return
     */
    private static Pirate parserPirate(String line,int nbPirates, int ordrePirate)
    {
        String infos = line.substring("pirate(".length(), line.length() - 2);
		return new Pirate(infos, nbPirates, ordrePirate);
    }

    /**
     * Methode permettant d'instancier un objet de la classe Tresor a partir d'une chaine de caractere
     * @param line chaine de caractere de la forme "objet(nom_objet)"
     * @return
     */
    private static Tresor parserObjet(String line)
    {
        String infos = line.substring("objet(".length(), line.length() - 2);
        return (new Tresor(infos));
    }
    
    /**
     * Methode permettant de creer une relation de jalousie entre 2 pirates a partir d'une chaine de caractere
     * @param line chaine de caractere de la forme "deteste(nom_pirate1,nom_pirate2)"
     * @param e equipage
     */
    private static void parserDeteste(String line,Equipage e)
    {
        String infos = line.substring("deteste(".length(), line.length() - 2);
		String[] infosTab = infos.split(",");
        String nomP1 =infosTab[0];
        String nomP2 =infosTab[1];
        e.ajouterRelation(nomP1, nomP2);
    }

    /**
     * Methode permettant de creer une liste de preferences pour un pirate a partir d'une chaine de caractere
     * @param line chaine de caractere de la forme "preferences(nom_pirate_1,nom_objet_1,nom_objet_2,nom_objet_3)"
     * @param nbPirates nombre de pirate 
     * @param e equipage
     */
    private static void parserPreferences(String line,int nbPirates,Equipage e)
    {
        String infos = line.substring("preferences(".length(), line.length() - 2);
		String[] infosTab = infos.split(",");
        String nomP = infosTab[0];
        Tresor[] objets = new Tresor[nbPirates];

        /*Verification que la ligne contient le bon nombre d'objets*/
        if (infosTab.length <= nbPirates)
        {
            System.out.println("Erreur du nombre d'objets dans le fichier.");
            return;
        }

        for (int i = 0; i + 1 < infosTab.length; i++)
            objets[i] = new Tresor(infosTab[i + 1]);
        e.ajoutPreferencePirate(nomP, objets);
    }
}
