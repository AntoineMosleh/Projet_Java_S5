import java.io.File;

import java.io.*;
public class LectureFichier
{

    public static Equipage lecture(String file)
    {
        BufferedReader bf =null;
        Pirate pirate = null;
        int objet =null;
        //List<Pirate> pirates =new ArrayList<Pirate>();
        List<Integer> objets=new ArrayList<Integer>();
        int nbPirates =comptagePirates(file);
        Equipage equipage = new Equipage(nbPirates);
        try
        {
            bf = new BufferedReader(file);
            String line;
            while((line=bf.readLine())!=null)
            {
                if (line.startsWith("pirate"))
                {
                    pirate=parserPirate(line, nbPirates);
                    //pirates.add(pirate);
                    equipage.getListePirate().add(pirate);
                }
                if (line.startsWith("objet"))
                {
                    objet=parserObjet(line);
                    objets.add(objet);
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
        bf.close();
    }


    private static int comptagePirates(String file)
    {
        int nombresPirates=0;
        BufferedReader bf=null;
        try
        {
            bf = new BufferedReader(file);
            String line;
            while((line=bf.readLine())!=null)
            {
                if (line.startsWith("pirate"))
                {
                    nombresPirates++;
                }
            }
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
        bf.close();
        return nombresPirates;
    }


    private static Pirate parserPirate(String line,int nbPirates)
    {
        String infos = line.substring("pirate(".length(), line.length() - 1);
		String[] infosTab = infos.split(",");
        char nomP =infosTab[0].charAt(0);
		return new Pirate(nomP,nbPirates);
    }

    private static Integer parserObjet(String line)
    {
        String infos = line.substring("objet(".length(), line.length() - 1);
        infos=infos.replace("o", "");
		return Integer.parseInt(infos);

    }

    private static void parserDeteste(String line,Equipage e)
    {
        String infos = line.substring("deteste(".length(), line.length() - 1);
		String[] infosTab = infos.split(",");
        char nomP1 =infosTab[0].charAt(0);
        char nomP2 =infosTab[1].charAt(0);
        e.ajouterRelation2(nomp1, nomp2);
    }

    private static void parserPreferences(String line,int nbPirates,Equipage e)
    {
        String infos = line.substring("preferences(".length(), line.length() - 1);
        infos=infos.replace("o", "");
		String[] infosTab = infos.split(",");
        char nomP =infosTab[0].charAt(0);
        int[] objets = new int[nbPirates];
        for(int i=1;i<infosTab.length;i++)
        {
            objets[i-1]=Integer.parseInt(infosTab[i]);
        }
        e.ajoutPreferencePirate(nomP,objets);
    }
 
}