package core;

/**
 * Interface pour les observateurs dans le patron de conception Observateur.
 */
public interface Observateur {

    /**
     * Méthode appelée lorsqu'un événement est notifié.
     *
     * @param evenement L'événement notifié
     */
    void notifier(Object evenement);
}
