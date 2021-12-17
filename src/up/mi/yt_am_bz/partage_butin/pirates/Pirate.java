package up.mi.yt_am_bz.partage_butin.pirates;

/**
 * Classe de représentation d'un Pirate
 */
public class Pirate    
{
	/*represente le nom du pirate*/
	private String		nomPirate;
	/*numero du pirate dans l'equipage*/
	private int			numPirate;
	/* represente la liste des objets préferés de chaque pirate.*/
	private Tresor[]	listePref;

	/**
	 * Constructeur permettant d'initialiser le tableau des préférences pour chaque pirates
	 * @param np le nom du pirate
	 * @param nbPirate le nombre de pirates qui sera egale au nombres d'objets pour chaque pirates
	 * @param numPirate l'ordre d'enregistrement du pirate dans l'equipage
	 */
	public Pirate(String np, int nbPirate, int numPirate)
	{
		setNomPirate(np);
		this.numPirate = numPirate;
		listePref = new Tresor[nbPirate];
	}

	/**
	 * Methode permettant de recuperer le nom du pirate
	 * @return le nom du pirate
	 */
	public String  getNomPirate()
	{
		return this.nomPirate;
	}

	/**
	 * Getter pour le numero du pirate
	 * @return le numero du pirate
	 */
	public int getNumPirate()
	{
		return (numPirate);
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
	 * permet de récupérer la liste de préférences des objets d'un pirate
	 * @return la liste de reference
	 */
	public Tresor[] getListePref()
	{
		return this.listePref;
	}

	/**
	 * Méthode permettant de définir la liste des préférences d'objets pour un pirate
	 * @param preferences est le tableau des objets de preferance du pirate
	 */
	public void ajoutPreference(Tresor[] preferences)
	{
		for(int i=0;i<listePref.length;i++)
			listePref[i] = preferences[i];
	}

	/**
	 * Methode permettant de savoir si la liste de préférences d'un pirate est vide ou non
	 * @return true si la liste est vide et false si elle n'est pas vide
	 */
	public Boolean listIsVide()
	{
		Boolean estVide=true;
		for(int i=0;i<listePref.length;i++)
		{
			if(listePref[i]!=null)
			{
				estVide=false;
			}
		}
		return estVide;
	}

	/**
	 * Verification d'egalite d'un pirate par rapport a son nom
	 * @param nom le nom du pirate
	 * @return true si le nom correspond au pirate, false sinon
	 */
	public boolean equals(String nom)
	{
		return (nom.equals(this.nomPirate));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return (true);
		if (obj == null)
			return (false);

		if (getClass() != obj.getClass())
			return (false);
		Pirate p = (Pirate)obj;
		if (!this.nomPirate.equals(p.getNomPirate()))
			return (false);
		return (true);
	}

	@Override
	public int hashCode()
	{
		final int	seed = 23;
		int			result = 1;

		result = seed * result + nomPirate.hashCode();
		return (result);
	}

	/**
	 * Retourne l'indice d'un objet dans la liste de preferences
	 * du pirate
	 * @param t le tresor recherche
	 * @return l'indice de l'objet dans les preferences du pirate ou -1 si
	 * l'objet n'est pas dans le tableau
	 */
	public int getIndexPref(Tresor t)
	{
		for (int i = 0; i < listePref.length; i++)
			if (listePref[i].equals(t))
				return (i);
		return (-1);
	}

	@Override
	public String toString()
	{
		int             i = 0;
		StringBuffer    s = new StringBuffer("Pirate ").append(nomPirate);

		s.append(" : ");
		if (listePref[0] != null)
		{
			for (i = 0; i < listePref.length - 1; i++)
				s.append(listePref[i].getNom()).append(" ");
			s.append(listePref[i].getNom());
		}
		return (s.toString());
	}
}
