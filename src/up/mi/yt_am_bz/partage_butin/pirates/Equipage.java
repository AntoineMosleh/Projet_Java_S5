package up.mi.yt_am_bz.partage_butin.pirates;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe de gestion d'un equipage de pirates
 */
public class Equipage
{
	/*Nombre de pirates*/
	private int					nbPirates;
	/*Numero du prochain pirate a ajouter*/
	private int					nextPirate;
	/*Liste des pirates de l'equipage*/
	private List<Pirate>		listePirates;
	/*Liste d'adjacence pour represente les relations entre les pirates*/
	private boolean[][]			matriceAdjacence;
	/*Liste des objets existants pour l'equipage*/
	private Tresor[]			tresorsDispos;
	/*Tableau contenant l'affectation du tresor par pirate*/
	private Map<Pirate,Tresor>	affectationTresors;

	/**
	 * Constructeur
	 * @param nbPirates le nombre de pirates
	 */
	public Equipage(int nbPirates)
	{
		this.nbPirates = nbPirates;
		nextPirate = 0;
		this.listePirates = new ArrayList<Pirate>(nbPirates);
		matriceAdjacence = new boolean[nbPirates][nbPirates];
		tresorsDispos = new Tresor[nbPirates];
		affectationTresors = new HashMap<Pirate, Tresor>(nbPirates);
	}

	/**
	 * Ajout d'un pirate dans l'equipage
	 * @param nom le nom du pirate
	 */
	public void ajoutPirate(String nom)
	{
		listePirates.add(new Pirate(nom, nbPirates, nextPirate++));
	}

	/**
	 * Ajout d'un pirate dans l'equipage
	 * @param pirate le pirate
	 */
	public void ajoutPirate(Pirate pirate)
	{
		listePirates.add(pirate);
	}

	/**
	 * Ajout d'une relation "est jaloux" entre deux pirates
	 * @param p1 le nom du premier pirate
	 * @param p2 le nom du deuxieme pirate
	 */
	public void ajouterRelation(String p1, String p2)
	{
		int i = getPirate(p1).getNumPirate();
		int j = getPirate(p2).getNumPirate();
		matriceAdjacence[i][j] = true;
		matriceAdjacence[j][i] = true;
	}

	/**
	 * Ajout d'une liste de preferences pour un pirate donne
	 * @param nomP le nom du pirate concerne
	 * @param pref liste de preferences
	 */
	public void ajoutPreferencePirate(String nomP, Tresor[] pref)
	{
		/*Verification que la liste des objets existants est remplie*/
		if (tresorsDispos[0] == null)
			System.out.println("La liste des objets existants n'est pas remplie.");
		for (Tresor t : pref)
		/*Verification que chaque preference correspond a un objet existant*/
			if (!tresorExiste(t))
			{
				System.out.println("La liste de preferences entree est incorrecte.");
				return;
			}
		getPirate(nomP).ajoutPreference(pref);
	}

	/**
	 * Verifie si un tresor est present dans la liste de tresors
	 * existants
	 * @param tresor le tresor a verifier
	 * @return true si le tresor existe, false sinon
	 */
	private boolean tresorExiste(Tresor tresor)
	{
		for (Tresor t : tresorsDispos)
			if (t.equals(tresor))
				return (true);
		return (false);
	}

	/**
	 * Echange d'un objet entre 2 pirates
	 * @param pirate1 nom du premier pirate
	 * @param pirate2 nom du deuxieme pirate
	 */
	public void echangeObjet(String pirate1, String pirate2)
	{
		if (affectationTresors.containsKey(getPirate(pirate1)) && affectationTresors.containsKey(getPirate(pirate2)))
		{
			Tresor objet_ephemere = affectationTresors.get(getPirate(pirate1));
			affectationTresors.replace(getPirate(pirate1), affectationTresors.get(getPirate(pirate2)));
			affectationTresors.replace(getPirate(pirate2), objet_ephemere);
		}
	}

	/**
	 * Echange d'un objet entre 2 pirates
	 * @param pirate1 nom du premier pirate
	 * @param pirate2 nom du deuxieme pirate
	 */
	public void echangeObjet(Pirate pirate1, Pirate pirate2)
	{
		if (affectationTresors.containsKey(pirate1) && affectationTresors.containsKey(pirate2))
		{
			Tresor objet_ephemere = affectationTresors.get(pirate1);
			affectationTresors.replace(pirate1, affectationTresors.get(pirate2));
			affectationTresors.replace(pirate2, objet_ephemere);
		}
	}

	/**
	 * Methode de resolution naive du probleme
	 *(Chaque pirate obtient sa premiere preference non deja prise) */
	public void	solutionNaive()
	{
		int	i;

		for (Pirate p : listePirates)
		{
			i = 0;
			while (i < nbPirates && affectationTresors.containsValue(p.getListePref()[i]))
				i++;
			affectationTresors.put(p,p.getListePref()[i]);
		}
	}

	/**
	 * Methode pour afficher la solution actuelle de l'equipage
	 */
	public void afficherSolution()
	{
		if (affectationTresors.isEmpty())
			System.out.println("Pas encore de solution pour l'equipage.");
		else
		{
			System.out.println("Solution actuelle : ");
			for (Pirate p : listePirates)
				System.out.println(p.getNomPirate() + " : " + affectationTresors.get(p).getNom());
		}
	}
	
	private int calculCout()
	{
		/*Cout total*/
		int			cout = 0;
		/*Tableau pour ne compter qu'une fois un pirate jaloux*/
		boolean[]	already_counted = new boolean[nbPirates];
		int			i;
		int			j;

		Pirate		p2;
		Tresor		tp1;
		Tresor		tp2;

		int			tp1p1;
		int			tp2p1;

		/*parcourir tous les pirates*/
		for (Pirate p1 : listePirates)
		{
			tp1 = affectationTresors.get(p1);
			tp1p1 = p1.getIndexPref(tp1);
			/*Si le pirate n'a pas son objet prefere*/
			if (tp1p1 > 0)
			{
				/*Parcours de la matrice pour chercher une jalousie*/
				// for (i = 0; i < matriceAdjacence.length; i++)
				i = p1.getNumPirate();
				for (j = 0; j < matriceAdjacence.length; j++)
				{
					if (matriceAdjacence[i][j])// && !already_counted[p1.getNumPirate()])
					{
						p2 = getPirate(j);
						tp2 = affectationTresors.get(p2);
						tp2p1 = p1.getIndexPref(tp2);
						if (tp1p1 > tp2p1)
						{
							already_counted[p1.getNumPirate()] = true;
							cout++;
							System.out.println("\n" + p1.getNomPirate() + " est jaloux de " + p2.getNomPirate());
						}
					}
				}
			}
		}
		return (cout);
	}

	/**
	 * Methode permettant d'afficher le coût de la solution actuelle
	 */
	public void afficherCout()
	{
		int	cout = calculCout();
		System.out.println("\nLa solution actuelle a un cout de " + cout + "\n");
	}
	
	/**
	 * Affichage de la matrice d'adjacence (principalement pour les tests)
	 */
	public void afficherRelations()
	{
		int	i;
		int	j;
		int	n;

		for (i = 0; i < nbPirates; i++)
		{
			for (j = 0; j < nbPirates; j++)
			{
				n = matriceAdjacence[i][j] == true ? 1 : 0;
				System.out.print(n + " ");
			}
			System.out.println();
		}
	}

	/** Methode de verification qu'un pirate existe dans l'equipage
	 *@param nom le nom recherche
	 *@return true s'il existe, false sinon*/
	public boolean containList(String nom)
	{
		/* Si la methode getPirate renvoie bien un pirate,
		alors un pirate possede bien ce nom*/
		if (getPirate(nom) != null)
			return (true);
		return (false);
		// ArrayList<Character> listNom;
		// listNom = new ArrayList<Character>();
		// for (int i=0; i<listePirates.size(); i++)
		// {
		// 	listNom.add(listePirates.get(i).getNomPirate());
		// }
		// if(listNom.contains(nom))
		// 	return true;
		// return false;
	}

	/**
	 * Methode permettant de savoir si toutes les listes de preferences
	 * des pirates ont ete entrees
	 *@return true si l'equipage est complet, false sinon */
	public boolean equipageComplet()
	{
		for (Pirate p : listePirates)
			if (p.listIsVide())
				return (false);
		return (true);
		// boolean verificationFinal=true;
		// boolean verification;
		// for(int i=0;i<listePirates.size();i++)
		// {
		// 	verification = listePirates.get(i).listIsVide();
		// 	if(verification==true)
		// 	{
		// 		verificationFinal=false;
		// 	}
		// }
		// return verificationFinal;
	}

	/** Methode de verification qu'une relation de jalousie existe deja entre
	 *deux pirates
	 *@param pirate1 le premier pirate du couple a tester
	 *@param pirate2 le seconde pirate du couple a tester
	 *@return true si la relation existe, false sinon*/
	public boolean relationExiste(String pirate1, String pirate2)
	{
		int i = getPirate(pirate1).getNumPirate();
		int j = getPirate(pirate2).getNumPirate();
		return matriceAdjacence[i][j];
	}

	/**
	 * Ajout d'un nouveau tresor dans la liste des tresors existants
	 * @param t le tresor a ajouter
	 */
	public void ajoutTresorDispo(Tresor t)
	{
		int	i = 0;
		
		while (tresorsDispos[i] != null && i < tresorsDispos.length)
			i++;
		tresorsDispos[i] = t;
	}

	/** Getter pour la liste des pirates
	 *@return
	 */
	public List<Pirate> getListePirate()
	{
		return listePirates;
	}

	/** Getter pour la matrice d'adjacence
	 *@return */
	public boolean[][] getMatriceAdjacence()
	{
		return matriceAdjacence;
	}

	/**
	 * Getter pour un pirate en fonction de son nom
	 * @param nom le nom du pirate recherche
	 * @return le pirate s'il existe, null sinon
	 */
	public Pirate getPirate(String nom)
	{
		for (Pirate p : listePirates)
			if (p.equals(nom))
				return (p);
		return (null);
	}

	/**
	 * Getter pour un pirate en fonction de son numero
	 * @param num le numero du pirate recherche
	 * @return le pirate s'il existe, null sinon
	 */
	public Pirate getPirate(int num)
	{
		for (Pirate p : listePirates)
			if (p.getNumPirate() == num)
				return (p);
		return (null);
	}
	
    /**
	 * Methode permettant d'afficher les noms des membres de l'equipage
	 * @return 
	 */
	public String compoEquipage()
	{
		StringBuffer st = new StringBuffer("Les membres de l'equipage sont : \n");
		for(Pirate p : listePirates)
		{
			st.append(p.getNomPirate());
			st.append(" ");
		}
		return st.toString();
	}

	@Override
	public String toString()
	{
		StringBuffer	s = new StringBuffer("Composition de l'equipage et leurs preferences respectives : \n");

		for (Pirate p : listePirates)
			s.append(p.toString()).append("\n");
		return (s.toString());
	}
}
