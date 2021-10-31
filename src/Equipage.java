import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
		affectationTresors = new HashMap<Character,Integer>();
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
}
