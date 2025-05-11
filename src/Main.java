import core.Vigie;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.*;
import modules.ModuleSuiviMedia;
import modules.ModuleSuiviPersonne;
import util.dataloader;

public class Main {
    public static void main(String[] args) {
        // Logo ASCII pour l'application
        System.out.println("========================================");
        System.out.println("   ____       _      _   _             ");
        System.out.println("  |  _ \\    (_)    | | (_)            ");
        System.out.println("  | |_) |_ __ _  ___| |_ _  ___  _ __  ");
        System.out.println("  |  __/| '__| |/ __| __| |/ _ \\| '_ \\");
        System.out.println("  | |   | |  | | (__| |_| | (_) | | | |");
        System.out.println("  |_|   |_|  |_|\\___|\\__|_|\\___/|_| |_|");
        System.out.println("========================================");
        System.out.println("Bienvenue dans l'application de simulation de la presse française !");

        // Initialisation de la Vigie et des modules spécialisés
        Vigie vigie = new Vigie();
        List<Media> mediasSurveilles = new ArrayList<>();
        List<PersonnePhysique> personnesSurveillees = new ArrayList<>();

        // Remplacement du chemin relatif par un chemin absolu
        String cheminFichierMedias = "data/medias.tsv";
        mediasSurveilles.addAll(dataloader.chargerMedias(cheminFichierMedias));
        //System.out.println("Médias chargés depuis le fichier : " + cheminFichierMedias);

        ModuleSuiviMedia moduleMedia = new ModuleSuiviMedia(mediasSurveilles, vigie);
        ModuleSuiviPersonne modulePersonne = new ModuleSuiviPersonne(personnesSurveillees, vigie);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuer = true;

            while (continuer) {
                System.out.println("\n========================================");
                System.out.println("===============  MENU  ================");
                System.out.println("========================================");
                System.out.println("1. Ajouter un média surveillé");
                System.out.println("2. Ajouter une personne surveillée");
                System.out.println("3. Simuler un événement");
                System.out.println("4. Afficher l'historique des alertes");
                System.out.println("5. Quitter");
                System.out.println("========================================");
                System.out.print("Choisissez une option : ");

                try {
                    int choix = Integer.parseInt(scanner.nextLine());

                    switch (choix) {
                        case 1 -> {
                            System.out.println("\n--- Ajouter un Média ---");
                            System.out.print("Nom du média : ");
                            String nomMedia = scanner.nextLine();
                            mediasSurveilles.add(new Media(mediasSurveilles.size() + 1, nomMedia, null));
                            System.out.println("Média ajouté avec succès !");
                        }
                        case 2 -> {
                            System.out.println("\n--- Ajouter une Personne ---");
                            System.out.print("Nom de la personne : ");
                            String nomPersonne = scanner.nextLine();
                            personnesSurveillees.add(new PersonnePhysique(personnesSurveillees.size() + 1, nomPersonne));
                            System.out.println("Personne ajoutée avec succès !");
                        }
                        case 3 -> {
                            System.out.println("\n--- Simuler un Événement ---");
                            System.out.print("Type d'événement (rachat/publication) : ");
                            String type = scanner.nextLine();
                            System.out.print("Cible (nom du média ou de la personne) : ");
                            String cible = scanner.nextLine();
                            System.out.print("Description de l'événement : ");
                            String description = scanner.nextLine();

                            if (type.equalsIgnoreCase("rachat")) {
                                for (Media media : mediasSurveilles) {
                                    if (media.getNom().equalsIgnoreCase(cible)) {
                                        moduleMedia.notifier(new Evenement(type, LocalDate.now(), media, description));
                                        System.out.println("Événement de rachat notifié avec succès !");
                                        break;
                                    }
                                }
                            } else if (type.equalsIgnoreCase("publication")) {
                                for (PersonnePhysique personne : personnesSurveillees) {
                                    if (personne.getNom().equalsIgnoreCase(cible)) {
                                        modulePersonne.notifier(new Evenement(type, LocalDate.now(), personne, description));
                                        System.out.println("Événement de publication notifié avec succès !");
                                        break;
                                    }
                                }
                            } else {
                                System.out.println("Type d'événement inconnu.");
                            }
                        }
                        case 4 -> {
                            System.out.println("\n--- Historique des Alertes ---");
                            vigie.afficherHistorique();
                        }
                        case 5 -> {
                            continuer = false;
                            System.out.println("\nMerci d'avoir utilisé l'application. Au revoir !");
                        }
                        default -> System.out.println("Option invalide. Veuillez réessayer.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Veuillez entrer un nombre valide.");
                } catch (Exception e) {
                    System.out.println("Une erreur inattendue s'est produite : " + e.getMessage());
                }
            }
        }
    }
}
