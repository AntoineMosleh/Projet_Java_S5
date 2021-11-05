/**
 * Classe permettant de representer un Pirate
 * 
 *
 */
public class Pirate    
{
    //private String nomPirate; //  represente le nom du pirate
    private char nomPirate; //  represente le nom du pirate
    private int[] listePref; // represente la liste des objets préferés de chaque pirate.

    /**
     * Constructeur permettant d'initialiser le tableau des objets de preferance pour chaque pirates
     * @param nm le nom du pirate
     * @param nbPirate le nombre de pirates qui sera egale au nombres d'objets pour chaque pirates
     */

    public Pirate(char np,int nbPirate)
    {
        setNomPirate(np);
        listePref = new int[nbPirate];

    }

    /**
     * Methode permettant de recuperer le nom du pirate
     * @return le nom du pirate
     */

    public char  getNomPirate()
    {
        return this.nomPirate;
    }

    /**
     * Permet de changer le nom du pirate
     * @param nomPirate le nom du pirate a ajouter
     */

    public void setNomPirate(char nomPirate)
    {
        this.nomPirate=nomPirate;
    }

    /**
     * permet de recuperer la liste de preferance des objets d'un pirate
     * @return la liste de reference
     */

    public int[] getListePref()
    {
        return this.listePref;
    }
    
    /**
     * Methode permettant de definir la liste des objets de preference pour un pirate
     * @param preferences est le tableau des objets de preferance du pirate 
     */
    public void ajoutPreference(int[] preferences)
    {
        for(int i=0;i<listePref.length;i++)
            listePref[i]=preferences[i];
    }

    /**
     * Methode permettant de savoir si la liste de preference d'un pirate est vide ou non
     * @return retourne true si la liste est vide et false si elle n'est pas vide
     */
    public Boolean listIsVide()
    {
        Boolean estVide=true;
        for(int i=0;i<listePref.length;i++)
        {
            if(listePref[i]!=0)
            {
                estVide=false;
            }
        }
        return estVide;
    }

    @Override
    public String toString()
    {
        int             i;
        StringBuffer    s = new StringBuffer(nomPirate);

        s.append(" : ");
        for (i = 0; i < listePref.length - 1; i++)
            s.append(listePref[i]).append(" ");
        s.append(listePref[i]);
        return (s.toString());
    }
}
