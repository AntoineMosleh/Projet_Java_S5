package up.mi.yt_am_bz.partage_butin.programme;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import up.mi.yt_am_bz.partage_butin.pirates.Equipage;
import up.mi.yt_am_bz.partage_butin.pirates.Pirate;
import up.mi.yt_am_bz.partage_butin.pirates.Tresor;
public class SauvegardeFichier
{
   public static void sauvegarder(Equipage e,String file)
   {
       try(PrintWriter printW  = new PrintWriter (new BufferedWriter(new FileWriter(file))))
       {
        Map<Pirate,Tresor> tresors = (HashMap<Pirate,Tresor>) e.getAffectationTresor();
        Pirate pirate;
        Tresor tresor;
        // for (Map.Entry<Pirate,Tresor> mapentry : tresors.entrySet())
        for (int i = 0; i < e.getNbPirates(); i++)
        {
            // pirate = mapentry.getKey();
            // tresor = mapentry.getValue();
            // printW.println(pirate.getNomPirate() + " : " + tresor.getNom());
            pirate = e.getPirate(i);
			tresor = tresors.get(pirate);
            printW.println(pirate.getNomPirate() + ":" + tresor.getNom());
        }
		System.out.println("Le fichier \"" + file + "\" a bien ete enregistre. (appuyez sur entree)");
       }catch (IOException err) {
        System.err.println(err.getMessage());
        System.exit(1);
    }
   }
}
