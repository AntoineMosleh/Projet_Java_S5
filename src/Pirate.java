/**
 * Classe permettant de representer un Pirate
 * 
 *
 */
public class Pirate    
{
    private String nomPirate; //  represente le nom du pirate
    private int[] listePref; // represente la liste des objets préferés de chaque pirate.

    /**
     * Constructeur permettant d'initialiser le tableau des objets de preferance pour chaque pirates
     * @param nm le nom du pirate
     * @param nbPirate le nombre de pirates qui sera egale au nombres d'objets pour chaque pirates
     */

    public Pirate(String np,int nbPirate)
    {
        setNomPirate(np);
        listePref = new int[nbPirate];

    }

    /**
     * Methode permettant de recuperer le nom du pirate
     * @return
     */

    public String  getNomPirate()
    {
        return this.nomPirate;
    }

    /**
     * Permet de changer le nom du pirate
     * @param nomPirate le nom du pirate a ajouter
     */

    public void setNomPirate(String nomPirate)
    {
        this.nomPirate=nomPirate;
    }

    /**
     * permet de recuperer la liste de preferance des objets d'un pirate
     * @return
     */

    public int[] getListePref()
    {
        return this.listePref;
    }
    
    /**
     * Methodes permettant de definir la liste des objets de preference pour un pirate
     * @param preferences est le tableau des objets de preferance du pirate 
     */


    public void ajoutPreferance(int[] preferences)
    {
        for(int i=0;i<preferences.length;i++)
        {
            listePref[i]=preferences[i];
        }
        

    }

    

}