import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 * Classe de gestion d'un équipage de pirates
 */
public class Equipage
{
	private int					nbPirates; //Nombre de pirates
	private List<Pirate>		listePirates; //Liste des pirates de l'equipage
	private boolean[][]			matriceAdjacence; //Liste d'adjacence pour representer
												  //les relations entre les pirates
	private Map<Character,Integer>	affectationTresors; //Tableau contenant l'affectation
													//du trésor par pirate

	/**
	 * Constructeur
	 * @param nbPirates le nombre de pirates*/
	public Equipage(int nbPirates)
	{
		this.nbPirates = nbPirates;
		this.listePirates = new ArrayList<Pirate>(nbPirates);
		matriceAdjacence = new boolean[nbPirates][nbPirates];
		affectationTresors = new HashMap<Character,Integer>(nbPirates);
		this.initPirates();
		// this.initMatrice();
	}

	/**
	 * Initialisation de la liste de pirates */
	private void initPirates()
	{
		char nom = 'A';

		for (int i = 0; i < nbPirates; i++)
			listePirates.add(new Pirate(nom++, nbPirates));

	}

	/**
	 * Initialisation de la matrice d'adjacence */
	// private void initMatrice()
	// {
	// 	int i;
	// 	int j;

	// 	for (i = 0; i < nbPirates; i++)
	// 		for (j = 0; j < nbPirates; j++)
	// 			matriceAdjacence[i][j] = false;
	// }

	/**
	 * Ajout d'une relation "est jaloux" entre deux pirates
	 *@param p1 le nom du premier pirate
	 *@param p2 le nom du deuxième pirate
	 */
	public void ajouterRelation(char p1, char p2)
	{
		int i = (int)p1 - 'A';
		int j = (int)p2 - 'A';
		matriceAdjacence[i][j] = true;
		matriceAdjacence[j][i] = true;
	}

	/**
	 * Ajout d'une liste d'objet de preference pour un pirate donné
	 * @param nomP le nom du pirate concerné
	 * @param pref liste d'objet de preferences
	 */
	public void ajoutPreferencePirate(char nomP,int[] pref)
	{
		for(int i=0;i<listePirates.size();i++)
		{
			if (listePirates.get(i).getNomPirate()==nomP)
			{
				listePirates.get(i).ajoutPreference(pref);
			}
		}
	}

	/** Methode de recherche d'un pirate
	 *@param nom le nom en char du pirate
	 *@return Le pirate trouve ou null sinon*/
	public Pirate findPirate(char nom) // je l'ai remise en public
	{
		for (Pirate p : listePirates)
			if (p.getNomPirate() == nom)
				return (p);
		return (null);
	}

	/**
	 * Echange d'objets entre 2 pirates donné en parametre
	 * @param pirate1 nom du premier pirate
	 * @param pirate2 nom du deuxieme pirate
	 */
	public void echangeObjet(char pirate1,char pirate2)
	{
		if (affectationTresors.containsKey(pirate1) && affectationTresors.containsKey(pirate2))
		{
			Integer objet_ephemere =affectationTresors.get(pirate1);
			affectationTresors.replace(pirate1, affectationTresors.get(pirate2));
			affectationTresors.replace(pirate2, objet_ephemere);			
		}

	}

	/**
	 * Methode de resolution naive du problème
	 *(Chaque pirate obtient sa premiere preference non deja prise) */
	public void	solutionNaive()
	{
		int[]	tresorsDonnes = new int[nbPirates];
		int		i;
		int		j;

		for (i = 0; i < nbPirates; i++)
			tresorsDonnes[i] = 0;

		for (Pirate p : listePirates)
		{
			i = 0;
			while (i < nbPirates && affectationTresors.containsValue(p.getListePref()[i]))
				i++;
			affectationTresors.put(p.getNomPirate(),p.getListePref()[i]);
		}
	}

	private boolean is_contained(int[] tab, int n)
	{
		for (int x : tab)
			if (x == n)
				return (true);
		return (false);
	}


	/**
	 * Methode pour afficher la solution actuelle de l'équipage */
	public void afficherSolution()
	{
		char	nom = 'A';

		if (affectationTresors.isEmpty())
			return;
		System.out.println("Solution actuelle : ");
		for (nom = 'A'; nom - 'A' < nbPirates; nom++)
			System.out.println(nom + " : " + affectationTresors.get(nom));
	}

	/**
	 * Methode de calcul du cout total de la solution actuelle
	 *@return */
	private int	calculCout()
	{
		int	count; //le cout total
		int	i;
		int	j;
		boolean[] already_counted = new boolean[nbPirates]; //pour ne compter qu'une fois chaque pirate
		Pirate	p1; //pirate compare 1
		Pirate	p2; //pirate compare 2
		int		t1; //tresor du pirate 1
		int		t2; //tresor du pirate 2
		int		indiceTP1P1; //indice du tresor de p1 dans les preferences de p1
		int		indiceTP1P2; //indice du tresor de p1 dans les preferences de p2
		int		indiceTP2P1; //indice du tresor de p2 dans les preferences de p1
		int		indiceTP2P2; //indice du tresor de p2 dans les preferences de p2

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
					t1 = affectationTresors.get(p1.getNomPirate());
					t2 = affectationTresors.get(p2.getNomPirate());
					indiceTP1P1 = find_index(p1.getListePref(),t1);
					indiceTP1P2 = find_index(p2.getListePref(),t1);
					indiceTP2P1 = find_index(p1.getListePref(),t2);
					indiceTP2P2 = find_index(p2.getListePref(),t2);
					/* Si p1 et p2 ont un tresor correspondant a une preference differente
					 * Et que p1 n'a pas encore ete compte */
					if (indiceTP1P1 != indiceTP2P2 && !already_counted[p1.getNomPirate() - 'A'])
						/* Si p2 a un tresor que p1 aurait prefere */
						if (indiceTP1P1 >= indiceTP2P1)
						{
							System.out.println("jalousie entre " + p1.getNomPirate() + " et " + p2.getNomPirate());
							already_counted[p1.getNomPirate() - 'A'] = true;
							count++;
						}
				}
			}
		}
		return (count);
	}

	/**
	 * Methode privee permettant de trouver le premier index d'un nombre donne dans un tableau
	 *@param tab le tableau dans lequel rechercher
	 *@param nb le nombre a chercher
	 *@return l'index du nombre dans le tableau ou -1 s'il n'est pas dedans*/
	private int find_index(int[] tab, int nb)
	{
		for (int i = 0; i < tab.length; i++)
			if (tab[i] == nb)
				return (i);
		return (-1);
	}

	/**
	 * Methode permettant d'afficher le cout de la solution actuelle */
	public void afficherCout()
	{
		System.out.println("La solution actuelle a un cout de " + calculCout());
	}
	
	/**
	 * Affichage de la matrice d'adjacence (principalement pour les tests) */
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
	public boolean containList(char nom)
	{
		ArrayList<Character> listNom;
		listNom = new ArrayList<Character>();
		for (int i=0; i<listePirates.size(); i++)
		{
			listNom.add(listePirates.get(i).getNomPirate());
		}
		if(listNom.contains(nom))
			return true;
		return false;
	}

	/** Methode de verification qu'une relation de jalousie existe deja entre
	 *deux pirates
	 *@param pirate1 le premier pirate du couple a tester
	 *@param pirate2 le seconde pirate du couple a tester
	 *@return true si la relation existe, false sinon*/
	public boolean relationExiste(char pirate1, char pirate2)
	{
		int i = pirate1 - 'A';
		int j = pirate2 - 'A';
		return matriceAdjacence[i][j];
	}


	/** Getter pour la liste des pirates
	 *@return */
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

	@Override
	public String toString()
	{
		StringBuffer	s = new StringBuffer("Composition de l'equipage et leurs preferences respectives : ");

		for (Pirate p : listePirates)
			s.append(p.toString()).append("\n");
		return (s.toString());
	}
}
