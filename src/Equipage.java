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
	private Map<String,Integer>	affectationTresors; //Tableau contenant l'affectation
													//du trésor par pirate

	/**
	 * Constructeur
	 * @param nbPirates le nombre de pirates*/
	public Equipage(int nbPirates)
	{
		this.nbPirates = nbPirates;
		this.listePirates = new ArrayList<Pirate>(nbPirates);
		matriceAdjacence = new boolean[nbPirates][nbPirates];
		affectationTresors = new HashMap<String,Integer>();
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
}
