package model;

/**
 * Représente un média détenu par une entité (personne physique ou morale).
 *
 * @see Entite
 */
public class Media extends Entite {
    private final Entite proprietaire;
    private String type;
    private String periodicite;
    private String echelle;
    private double prix;

    /**
     * Construit un média avec son identifiant, nom et propriétaire.
     *
     * @param id          identifiant unique du média
     * @param nom         nom du média
     * @param proprietaire entité propriétaire
     */
    public Media(int id, String nom, Entite proprietaire) {
        super(id, nom);
        this.proprietaire = proprietaire;
    }

    /**
     * @return le propriétaire du média
     */
    public Entite getProprietaire() {
        return proprietaire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public String getEchelle() {
        return echelle;
    }

    public void setEchelle(String echelle) {
        this.echelle = echelle;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Media{id=" + getId() + ", nom='" + getNom() + "', proprietaire=" + (proprietaire != null ? proprietaire.getNom() : "null") + "}";
    }
}
