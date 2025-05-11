package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe jouant le rôle de générateur d'événements dans le patron Observateur.
 */
public class GenerateurEvenements {

    private final List<Observateur> observateurs;

    /**
     * Constructeur initialisant la liste des observateurs.
     */
    public GenerateurEvenements() {
        this.observateurs = new ArrayList<>();
    }

    /**
     * Ajoute un observateur à la liste.
     *
     * @param observateur L'observateur à ajouter
     */
    public void ajouterObservateur(Observateur observateur) {
        observateurs.add(observateur);
    }

    /**
     * Supprime un observateur de la liste.
     *
     * @param observateur L'observateur à supprimer
     */
    public void supprimerObservateur(Observateur observateur) {
        observateurs.remove(observateur);
    }

    /**
     * Notifie tous les observateurs d'un événement.
     *
     * @param evenement L'événement à notifier
     */
    public void notifierObservateurs(Object evenement) {
        for (Observateur observateur : observateurs) {
            observateur.notifier(evenement);
        }
    }
}
