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
       try(PrintWriter printW  = new PrintWriter (new BufferWriter(new FileWriter(file))))
       {
        Map<Pirate,Tresor> tresors =e.getAffectationTresor();
        Pirate pirate;
        Tresor tresor;
        for (Map.Entry<Pirate,Tresor> mapentry : tresors.entrySet())
        {
            pirate=mapentry.getKey();
            tresor=mapentry.getValue();
            printW.println(pirate.getNomPirate()+" : "+tresor.getNom()+"\n");
        }

       }catch (IOException err) {
        System.err.println(err.getMessage());
        System.exit(1);
    }
   }
}