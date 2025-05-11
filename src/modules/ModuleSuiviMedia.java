package modules;

import core.ModuleSpecialise;
import core.Observateur;
import core.Vigie;
import java.util.List;
import model.Evenement;
import model.Media;

/**
 * Module spécialisé chargé de surveiller les événements de type "rachat"
 * concernant un ou plusieurs médias spécifiques. Si l’un des médias surveillés
 * est concerné par un rachat, une alerte est transmise à la Vigie.
 *
 * @see Media
 * @see Evenement
 */
public class ModuleSuiviMedia implements ModuleSpecialise, Observateur {
    private final List<Media> mediasSurveilles;
    private final Vigie vigie;

    /**
     * Construit un module de suivi de rachats pour des médias donnés.
     *
     * @param mediasSurveilles liste des médias à surveiller
     * @param vigie            instance de la vigie à alerter
     */
    public ModuleSuiviMedia(List<Media> mediasSurveilles, Vigie vigie) {
        this.mediasSurveilles = mediasSurveilles;
        this.vigie = vigie;
    }

    /**
     * Traite un événement. Si c’est un rachat visant un média surveillé,
     * une alerte est envoyée à la vigie.
     *
     * @param evenement l’événement à analyser
     */
    @Override
    public void traiter(Evenement evenement) {
        if (!evenement.getType().equalsIgnoreCase("rachat")) {
            return;
        }

        for (Media m : mediasSurveilles) {
            if (evenement.getCible() instanceof Media cible && cible.equals(m)) {
                String alerte = "ALERTE : Le média \"" + m.getNom()
                        + "\" a été la cible d’un rachat le " + evenement.getDate()
                        + " : " + evenement.getContenu();
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
