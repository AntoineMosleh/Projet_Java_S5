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

    public static Equipage lecture(String file)
    {
        BufferedReader bf = null;
        Pirate pirate = null;
        Tresor objet = null;
        // int objet = 0;
        //List<Pirate> pirates =new ArrayList<Pirate>();
        // List<Integer> objets=new ArrayList<Integer>();
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


    private static Pirate parserPirate(String line,int nbPirates, int ordrePirate)
    {
        String infos = line.substring("pirate(".length(), line.length() - 2);
		// String[] infosTab = infos.split(",");
        // char nomP =infosTab[0].charAt(0);
        // String nomP = infos;
		return new Pirate(infos, nbPirates, ordrePirate);
    }

    private static Tresor parserObjet(String line)
    {
        String infos = line.substring("objet(".length(), line.length() - 2);
        // infos=infos.replace("o", "");
		// return Integer.parseInt(infos);
        return (new Tresor(infos));
    }

    private static void parserDeteste(String line,Equipage e)
    {
        String infos = line.substring("deteste(".length(), line.length() - 2);
		String[] infosTab = infos.split(",");
        String nomP1 =infosTab[0];
        String nomP2 =infosTab[1];
        e.ajouterRelation(nomP1, nomP2);
    }

    private static void parserPreferences(String line,int nbPirates,Equipage e)
    {
        String infos = line.substring("preferences(".length(), line.length() - 2);
        // infos=infos.replace("o", "");
		String[] infosTab = infos.split(",");
        String nomP = infosTab[0];
        Tresor[] objets = new Tresor[nbPirates];

        /*Verification que la ligne contient le bon nombre d'objets*/
        if (infosTab.length <= nbPirates)
        {
            System.out.println("Erreur du nombre d'objets dans le fichier.");
            return;
        }
        // String[] objets = Arrays.copyOfRange(infosTab, 1, infosTab.length);
        for (int i = 0; i + 1 < infosTab.length; i++)
            objets[i] = new Tresor(infosTab[i + 1]);
        e.ajoutPreferencePirate(nomP, objets);
        // String[] objets = new String[nbPirates];
        // for(int i = 1; i < infosTab.length; i++)
        // {
        //     objets[i-1] = infosTab[i];
        // }
        // e.ajoutPreferencePirate(nomP,objets);
    }
}
