package modules;

import core.ModuleSpecialise;
import core.Observateur;
import core.Vigie;
import java.util.List;
import model.Evenement;
import model.PersonnePhysique;

/**
 * Module spécialisé chargé de surveiller les publications concernant
 * une ou plusieurs personnes physiques. Il déclenche une alerte si
 * une publication mentionne une personne surveillée ou la cible directement.
 *
 * @see PersonnePhysique
 * @see Evenement
 */
public class ModuleSuiviPersonne implements ModuleSpecialise, Observateur {
    private List<PersonnePhysique> personnesSurveillees;
    private Vigie vigie; // Référence vers la vigie pour lui envoyer les alertes

    /**
     * Construit un module de suivi de publications pour des personnes physiques.
     *
     * @param personnesSurveillees liste des personnes à surveiller
     * @param vigie                instance de la vigie à alerter
     */
    public ModuleSuiviPersonne(List<PersonnePhysique> personnesSurveillees, Vigie vigie) {
        this.personnesSurveillees = personnesSurveillees;
        this.vigie = vigie;
    }

    /**
     * Traite un événement. Si c'est une publication qui concerne une personne surveillée,
     * une alerte est envoyée à la vigie.
     *
     * @param evenement l'événement à analyser
     */
    @Override
    public void traiter(Evenement evenement) {
        if (!evenement.getType().equalsIgnoreCase("publication")) {
            return;
        }

        for (PersonnePhysique p : personnesSurveillees) {
            boolean mentionDansContenu = evenement.getContenu().toLowerCase().contains(p.getNom().toLowerCase());
            boolean cibleEstPersonne = evenement.getCible() instanceof PersonnePhysique
                    && ((PersonnePhysique) evenement.getCible()).equals(p);

            if (mentionDansContenu || cibleEstPersonne) {
                String alerte = "ALERTE : " + p.getNom() + " a été mentionné dans une publication le "
                        + evenement.getDate() + " : " + evenement.getContenu();
                System.out.println("[DEBUG] Envoi de l'alerte à la Vigie : " + alerte);
                vigie.recevoirAlerte(alerte);
                break;
            }
        }
    }

    /**
     * Méthode appelée lorsqu'un événement est notifié.
     *
     * @param evenement L'événement notifié
     */
    @Override
    public void notifier(Object evenement) {
        if (evenement instanceof Evenement e) {
            traiter(e);
        }
    }
}
