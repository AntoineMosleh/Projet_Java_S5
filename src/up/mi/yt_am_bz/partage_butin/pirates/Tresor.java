package up.mi.yt_am_bz.partage_butin.pirates;

import javax.print.Doc;

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

	/**
	 * Verifie l'egalite d'un tresor par rapport a un autre
	 * @param t le tresor avec lequel comparer
	 * @return true si le tresor est egal a cette instance, false sinon
	 */
	public boolean equals(Tresor t)
	{
		return (this.nom.equals(t.getNom()));
	}
}
