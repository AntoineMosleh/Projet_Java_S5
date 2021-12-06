package up.mi.yt_am_bz.partage_butin.pirates;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

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

	// /**
	//  * Constructeur vide
	//  */
	// public Equipage()
	// {
	// 	nbPirates = 0;
	// 	nextPirate = 0;
	// 	listePirates = new ArrayList<Pirate>();
	// 	matriceAdjacence = new boolean[1][1];
	// 	tresorsDispos = new Tresor[1];
	// 	affectationTresors = new HashMap<String, String>();
	// }

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
		// this.initPirates();
	}

	/**
	 * Initialisation de la liste de pirates
	 */
	// private void initPirates()
	// {
	// 	char nom = 'A';

	// 	for (int i = 0; i < nbPirates; i++)
	// 		listePirates.add(new Pirate(nom++, nbPirates));
	// }

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

    // /**
	//  * Ajout d'une relation "est jaloux" entre deux pirates quand leur nom de pirate ne sont pas des lettres
	//  * @param p1 le nom du premier pirate
	//  * @param p2 le nom du deuxieme pirate
	//  */
	// public void ajouterRelation2(char p1, char p2)
	// {
	// 	int i =Character.getNumericValue(p1);
	// 	int j =Character.getNumericValue(p2);
	// 	matriceAdjacence[i][j] = true;
	// 	matriceAdjacence[j][i] = true;
	// }

	/**
	 * Ajout d'une liste de preferences pour un pirate donne
	 * @param nomP le nom du pirate concerne
	 * @param pref liste de preferences
	 */
	public void ajoutPreferencePirate(String nomP, Tresor[] pref)
	{
		getPirate(nomP).ajoutPreference(pref);
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
	 * Methode de resolution naive du probleme
	 *(Chaque pirate obtient sa premiere preference non deja prise) */
	public void	solutionNaive()
	{
		// Tresor[]	tresorsDonnes = new Tresor[nbPirates];
		int			i;
		// int			j;

		// for (i = 0; i < nbPirates; i++)
		// 	tresorsDonnes[i] = ;

		for (Pirate p : listePirates)
		{
			i = 0;
			while (i < nbPirates && affectationTresors.containsValue(p.getListePref()[i]))
				i++;
			affectationTresors.put(p,p.getListePref()[i]);
		}
	}

	// /**
	//  * Methode privee permettant de savoir si un nombre est contenu dans un tableau
	//  *@param tab le tableau dans lequel verifier
	//  *@param n le nombre a chercher*/
	// private boolean is_contained(int[] tab, int n)
	// {
	// 	for (int x : tab)
	// 		if (x == n)
	// 			return (true);
	// 	return (false);
	// }


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

	/**
	 * Methode de calcul du cout total de la solution actuelle
	 *@return */
	private int	calculCout()
	{
		/*le cout total*/
		int	count;
		/*compteurs pour parcours de la matrice*/
		int	i;
		int	j;
		/*pour ne compter qu'une fois chaque pirate*/
		boolean[] already_counted = new boolean[nbPirates];
		/*pirate compare 1*/
		Pirate	p1;
		/*pirate compare 2*/
		Pirate	p2;
		/*tresor du pirate 1*/
		Tresor	t1;
		/*tresor du pirate 2*/
		Tresor	t2;
		/*indice du tresor de p1 dans les preferences de p1*/
		int		indiceTP1P1;
		/*indice du tresor de p1 dans les preferences de p2*/
		int		indiceTP1P2;
		/*indice du tresor de p2 dans les preferences de p1*/
		int		indiceTP2P1;
		/*indice du tresor de p2 dans les preferences de p2*/
		int		indiceTP2P2;

		count = 0;
		for (i = 0; i < matriceAdjacence.length; i++)
		{
			for (j = 0; j < matriceAdjacence.length; j++)
			{
				/* Si les deux pirates ont une relation de jalousie */
				if (i != j && matriceAdjacence[i][j])
				{
					p1 = listePirates.get(i);
					p2 = listePirates.get(j);
					t1 = affectationTresors.get(p1);
					t2 = affectationTresors.get(p2);
					indiceTP1P1 = p1.getIndexPref(t1);
					indiceTP1P2 = p2.getIndexPref(t1);
					indiceTP2P1 = p1.getIndexPref(t2);
					indiceTP2P2 = p2.getIndexPref(t2);
					// indiceTP1P1 = find_index(p1.getListePref(),t1);
					// indiceTP1P2 = find_index(p2.getListePref(),t1);
					// indiceTP2P1 = find_index(p1.getListePref(),t2);
					// indiceTP2P2 = find_index(p2.getListePref(),t2);
					/* Si p1 et p2 ont un tresor correspondant a une preference differente
					 * Et que p1 n'a pas encore ete compte */
					if (indiceTP1P1 != indiceTP2P2 && !already_counted[p1.getNumPirate()])
						/* Si p2 a un tresor que p1 aurait prefere */
						if (indiceTP1P1 >= indiceTP2P1) //peut etre mettre >
						{
							System.out.println("\n" + p1.getNomPirate() + " est jaloux de " + p2.getNomPirate());
							already_counted[p1.getNumPirate()] = true;
							count++;
						}
				}
			}
		}
		return (count);
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
