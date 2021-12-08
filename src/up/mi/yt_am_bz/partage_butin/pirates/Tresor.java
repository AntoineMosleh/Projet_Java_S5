package up.mi.yt_am_bz.partage_butin.pirates;

/**
 * Classe representant un objet (tresor)
 */
public class Tresor
{
	/*Nom de l'objet*/
	private String nom;

	/**
	 * Constructeur
	 * @param nom le nom de l'objet
	 */
	public Tresor(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Getter pour le nom de l'objet
	 */
	public String getNom()
	{
		return (nom);
	}

	/**
	 * Setter pour le nom de l'objet
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
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
		Tresor t = (Tresor)obj;
		if (!this.nom.equals(t.getNom()))
			return (false);
		return (true);
	}

	@Override
	public int hashCode()
	{
		final int	seed = 23;
		int			result = 1;
		
		result = seed * result + nom.hashCode();
		return (result);
	}

	@Override
	public String toString()
	{
		return (nom);
	}
}
