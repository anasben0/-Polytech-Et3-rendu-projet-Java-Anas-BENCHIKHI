package model;

/**
 * Représente une personne physique, héritant de la classe Entite.
 * Exemple : un individu comme Vincent Bolloré.
 * @see Entite
 */
public class PersonnePhysique extends Entite {

    /**
     * Constructeur de la personne physique.
     *
     * @param id  Identifiant unique
     * @param nom Nom complet de la personne
     */
    public PersonnePhysique(int id, String nom) {
        super(id, nom);
    }
}
