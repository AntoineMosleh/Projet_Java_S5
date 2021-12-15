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
	 * Execution et enregistrement dans l'equipage de la solution naive
	 */
	public void executeSolutionNaive()
	{
		this.affectationTresors = solutionNaive();
	}

	/**
	 * Retourne la solution naive correspondant a l'equipage
	 * @return la hashMap de la solution naive
	 */
	private HashMap<Pirate, Tresor> solutionNaive()
	{
		int	i;
		HashMap<Pirate, Tresor> solution = new HashMap<>(nbPirates);

		for (Pirate p : listePirates)
		{
			i = 0;
			while (i < nbPirates && solution.containsValue(p.getListePref()[i]))
				i++;
			solution.put(p,p.getListePref()[i]);
		}
		return (solution);
	}

	/**
	 * Methode pour afficher la solution actuelle de l'equipage
	 */
	public void afficherSolution()
	{
		if (affectationTresors.isEmpty())
			System.out.println("\nPas encore de solution pour l'equipage.");
		else
		{
			System.out.println("\nSolution actuelle : ");
			for (Pirate p : listePirates)
				System.out.println("Pirate " + p.getNomPirate() + " : " + affectationTresors.get(p));
			System.out.println();
			afficherCout();
		}
	}
	
	/**
	 * Execution et enregistrement dans l'equipage de la solution approximative
	 */
	public void executeApproximationSolution()
	{
		final long nbEssais = 100;

		affectationTresors = approximationSolution(nbEssais);
	}

	/**
	 * Calcul de la factorielle d'un nombre
	 * @param n le nombre
	 * @return le resultat de la factorielle de n
	 *
	 */
	private long fact(int n)
	{
		if (n <= 2)
			return (n);
		return (n * fact(n - 1));
	}

	/**
	 * Algorithme d'approximation (naif)
	 * @param nbEssais entier correspondant au nombre d'essais a faire
	 * @return la Map correspondant a la trouvee
	 */
	private HashMap<Pirate, Tresor> approximationSolution(long nbEssais)
	{
		/*Compteurs pour le nombre d'essais*/
		long								i;
		long								j;
		/*Pirates pour lesquels les tresors sont echanges*/
		Pirate								p1;
		Pirate								p2;
		/*Numeros aleatoires pour le choix des pirates a inverser*/
		int									randomNum;
		int									randomNum2;
		/*Le nombre de possibilites pour l'equipage*/
		long								nbPossibilites = -1;
		/*Liste contenant toutes les solutions essayees*/
		ArrayList<HashMap<Pirate,Tresor>>	listSolutions = new ArrayList<>();
		/*Meilleure solution trouvee*/
		HashMap<Pirate, Tresor>				bestSolution = solutionNaive();
		/*Solution comparee a la meilleure solutin trouvee*/
		HashMap<Pirate, Tresor>				solutionTest;
		/*Au dessus de 20, le resultat de la factorielle ne rentre plus dans un long*/
		if (nbPirates <= 20)
			nbPossibilites = fact(nbPirates);
		/*Verification que le nombre d'essais ne depasse pas le nombre de possibilites.
		 *Si c'est le cas alors on le reduit au nombre de possibilites (sinon il y aura une boucle infinie)*/
		if (nbEssais > nbPossibilites && nbPossibilites != -1)
			nbEssais = nbPossibilites;
		/*Boucle testant nbEssais possibilites*/
		for (i = 0; i < nbEssais; i++)
		{
			/*Compteur pour eviter une boucle infinie dans le cas ou les nombres aleatoires ne permettent
			 *pas dans un temps raisonnable de trouver une nouvelle solution */
			j = 0;
			/*Creation d'une solution pas encore essayee*/
			do
			{
				/*Choix d'un pirate selon son numero choisi aleatoirement*/
				randomNum = (int) ((1 + Math.random() * listePirates.size()) - 1);
				p1 = getPirate(randomNum);
				/*Choix du second pirate (different du premier)*/
				do
				{
					randomNum2 = (int) ((1 + Math.random() * listePirates.size()) - 1);
				}
				while (randomNum == randomNum2);
				p2 = getPirate(randomNum2);
				/*Echange des tresors des deux pirates choisis*/
				solutionTest = (HashMap<Pirate,Tresor>)copyMap(bestSolution);
				swapValueHashMap(solutionTest, p1, p2);
			}
			while (listSolutions.contains(solutionTest) && j++ < nbEssais);
			/*Ajout de la nouvelle solution dans la liste des solutions testees*/
			listSolutions.add(solutionTest);
			/*Si la nouvelle solution a un cout plus faible que la meilleure solution,
			 *alors elle devient la meilleure solution */
			if (calculCout(bestSolution,false) > calculCout(solutionTest,false))
				bestSolution = solutionTest;
		}
		return (bestSolution);
	}

	/**
	 * Echange les valeurs de deux cles dans une Map
	 * @param <T> le type des cles
	 * @param <U> le type des valeurs
	 * @param map la Map ou effectuer l'echange
	 * @param key1 la premiere cle
	 * @param key2 la deuxieme cle
	 */
	private <T extends Object, U extends Object> void swapValueHashMap(HashMap<T,U> map, T key1, T key2)
	{
		U tmp = map.get(key1);

		map.replace(key1, map.get(key1), map.get(key2));
		map.replace(key2, map.get(key2), tmp);
	}

	/**
	 * Effectue et retourne la copie d'une Map
	 * @param <T> le type des cles
	 * @param <U> le type des valeurs
	 * @param map la Map a copier
	 * @return la copie de la Map
	 */
	private <T extends Object, U extends Object> Map<T,U> copyMap(Map<T,U> map)
	{
		Map<T,U> newMap = new HashMap<>(map.size());

		for (T t : map.keySet())
			newMap.put(t, map.get(t));
		return (newMap);
	}

	/**
	 * Calcule le cout d'une solution passee en argument
	 * @param solution proposee
	 * @return
	 */
	private int calculCout(Map<Pirate, Tresor> solution, boolean afficherJalousies)
	{
		/*Cout total*/
		int			cout = 0;
		/*Compteurs pour le parcours de la matrice*/
		int			i;
		int			j;
		/*Le pirate compare au premier*/
		Pirate		p2;
		/*Tresor attribue au pirate 1*/
		Tresor		tp1;
		/*Tresor attribue au pirate 2*/
		Tresor		tp2;
		/*Indice du tresor attribue a p1 dans la liste de preference de p1*/
		int			tp1p1;
		/*Indice du tresor attribue a p2 dans la liste de preference de p1*/
		int			tp2p1;

		/*parcours de tous les pirates*/
		for (Pirate p1 : listePirates)
		{
			tp1 = solution.get(p1);
			tp1p1 = p1.getIndexPref(tp1);
			/*Si le pirate n'a pas son objet prefere*/
			if (tp1p1 > 0)
			{
				/*Parcours de la matrice pour chercher une jalousie (i = pirate actuel)*/
				i = p1.getNumPirate();
				for (j = 0; j < matriceAdjacence.length; j++)
				{
					/*Si une relation de jalousie existe*/
					if (matriceAdjacence[i][j])
					{
						p2 = getPirate(j);
						tp2 = solution.get(p2);
						tp2p1 = p1.getIndexPref(tp2);
						/*Si le pirate 2 a eu un objet que le pirate 1 aurait prefere*/
						if (tp1p1 > tp2p1)
						{
							cout++;
							if (afficherJalousies)
								System.out.println(p1.getNomPirate() + " est jaloux de " + p2.getNomPirate());
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
		int	cout = calculCout(affectationTresors,true);
		System.out.println("\nLa solution actuelle a un cout de " + cout + "\n");
	}
	
	/**
	 * Affichage de la matrice d'adjacence (principalement pour les tests)
	 */
	public void afficherMatrice()
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

	public Map<Pirate,Tresor> getAffectationTresor()
	{
		return this.affectationTresors;
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
	
	public boolean solutionExiste()
	{
		return (!affectationTresors.isEmpty());
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
		StringBuffer	s = new StringBuffer("\nComposition de l'equipage et leurs preferences respectives : \n");

		for (Pirate p : listePirates)
			s.append(p.toString()).append("\n");
		s.append("\nRelations : \n");
		for (int i = 0; i < matriceAdjacence.length; i++)
			for (int j = i + 1; j < matriceAdjacence.length; j++)
				if (relationExiste(getPirate(i).getNomPirate(), getPirate(j).getNomPirate()))
				{
					s.append("Pirate ").append(getPirate(i).getNomPirate())
					.append(" et Pirate ").append(getPirate(j).getNomPirate())
					.append(" se detestent.\n");
				}
		return (s.toString());
	}
}
