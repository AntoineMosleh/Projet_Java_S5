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
		this.initMatrice();
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
	private void initMatrice()
	{
		int i;
		int j;

		for (i = 0; i < nbPirates; i++)
			for (j = 0; j < nbPirates; j++)
				matriceAdjacence[i][j] = false;
	}

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

	/* Methode de recherche de pirate, au cas où*/
	private Pirate findPirate(char nom)
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
	 * Methode de resolution naive du problème */
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
	/**
	 * Methode permettant de dire si deux pirates peuvent etre jaloux entre eux
	 * @param pirate1 le premier pirate
	 * @param pirate2 le deuxieme pirate
	 * @return true si les deux pirates peuvent etre jaloux et false sinon
	 */
	public boolean rechercherJaloux(char pirate1,char pirate2)
	{
		int i = (int)pirate1 - 'A';
		int j = (int)pirate2 - 'A';

		if (matriceAdjacence[i][j]== true && matriceAdjacence[j][i]==true)
		{
			return true;

		}
		else
		{
			return false;

		}
	}
	
    /**
    * methodes permettant de rechercher l'indice de l'objet obtenu par le pirate dans sa liste d'objet de preference
    * @param ratpi le pirate
    * @return l'indice de l'objet obtenu dans sa liste de preference si il est present, retourne -1 sinon
    */
	public int rechercheIndiceObjet(char ratpi)
	{
		Integer objet = affectationTresors.get(ratpi);
		int indice=-1;
		int [] tab;
		for (int i=0;i<listePirates.size();i++)
		{
			if (listePirates.get(i).getNomPirate() == ratpi)
			{
				tab = listePirates.get(i).getListePref();
				for(int j=0;j<tab.length;j++)
				{
					if (tab[j]==objet)
					{
						indice=j;
					}
				}

			}
		}
		return indice;
	}

/**
 * Methode permettant de retourner l'indice d'un objet donné dans la liste de preference d'un pirate donné
 * @param nomPirate nom du pirate
 * @param objet objet dont l'indice est a trouvé
 * @return l'indice de l'objet dans la liste de preference du pirate
 */
	public int rechercheIndiceParapportAObjet(char nomPirate,Integer objet)
	{
		Pirate pirate=null;
		int indice_objet=-1;
		int tab[];
		for(int i=0;i<listePirates.size();i++)
		{
			if (listePirates.get(i).getNomPirate()==nomPirate)
			{
				pirate=listePirates.get(i);
			}
		}
		tab=pirate.getListePref();
		for(int j=0;j<tab.length;j++)
		{
			if(tab[j]==objet)
			{
				indice_objet=j;
			}
		}
		return indice_objet;
	}


    /**
	 * Methode permettant de calculer le cout 
	 * @return le cout
	 */
	public int afficherCout()
	{
		int cout=0;
		for(int i=0;i<listePirates.size();i++)
		{
			char pirate1 =listePirates.get(i).getNomPirate();
			Integer objet_pirate1=affectationTresors.get(pirate1);
			for(int j=0;j<listePirates.size();j++)
			{
				char pirate2 =listePirates.get(j).getNomPirate();
				Integer objet_pirate2=affectationTresors.get(pirate2);
				if(rechercherJaloux(pirate1, pirate2)) 
				{
					int indice_objet_pirate1=rechercheIndiceObjet(pirate1);
					int indice_objet_pirate2=rechercheIndiceObjet(pirate2);
					int indice_objetDePirate2_dansPirate1=rechercheIndiceParapportAObjet(pirate1,objet_pirate2 );
					int indice_objetDePirate1_dansPirate2=rechercheIndiceParapportAObjet(pirate2,objet_pirate1 );
					if ( indice_objetDePirate1_dansPirate2<indice_objet_pirate2 || indice_objetDePirate2_dansPirate1<indice_objet_pirate1)
					{
						cout=cout+1;
					}
				}

			}
		}
		return cout;
	}

	// private boolean is_contained(int[] tab, int n)
	// {
	// 	for (int x : tab)
	// 		if (x == n)
	// 			return (true);
	// 	return (false);
	// }
	//

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

	// private int	calculCout()
	// {
	// 	for (Pirate p : listePirates)
	// 	{

	// 	}
	// }

	// public void afficherCout()
	// {

	// }
	
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

	public List<Pirate> getListePirate()
	{
		return listePirates;
	}

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

	public boolean[][] getMatriceAdjacence()
	{
		return matriceAdjacence;
	}

	public boolean relationExiste(char pirate1, char pirate2)
	{
		int i = pirate1 - 'A';
		int j = pirate2 - 'A';
		return matriceAdjacence[i][j];
	}
}
