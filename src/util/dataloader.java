package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Media;
import model.Participation;
import model.PersonnePhysique;

public class dataloader {

    /**
     * Charge les données des personnes à partir d'un fichier TSV.
     *
     * @param filePath Chemin du fichier TSV.
     * @return Liste des personnes physiques.
     */
    public static List<PersonnePhysique> chargerPersonnes(String filePath) {
        List<PersonnePhysique> personnes = new ArrayList<>();
        int idCounter = 1; // Générateur d'identifiants uniques
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Ignorer la première ligne (en-têtes)
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");
                if (columns.length > 0 && !columns[0].isEmpty()) {
                    String nom = columns[0];
                    // Créer une instance de PersonnePhysique avec un ID unique
                    PersonnePhysique personne = new PersonnePhysique(idCounter++, nom);
                    personnes.add(personne);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement des données depuis le fichier : " + filePath, e);
        }
        return personnes;
    }

    /**
     * Charge les relations entre personnes et médias à partir d'un fichier TSV.
     *
     * @param filePath Chemin du fichier TSV.
     * @return Liste des participations entre personnes et médias.
     */
    public static List<Participation> chargerPersonneMedia(String filePath) {
        List<Participation> participations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Ignorer la première ligne (en-têtes)
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");
                if (columns.length >= 5) {
                    String origine = columns[1];
                    String cible = columns[4];
                    double pourcentage = Double.parseDouble(columns[3].replace("%", ""));

                    // Créer des objets fictifs pour PersonnePhysique et Media (à remplacer par des objets réels)
                    PersonnePhysique personne = new PersonnePhysique(0, origine);
                    Media media = new Media(0, cible, null); // Replace 'null' with a valid Entite object if available

                    // Ajouter une participation
                    participations.add(new Participation(personne, media, pourcentage));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement des données depuis le fichier : " + filePath, e);
        }
        return participations;
    }

    /**
     * Charge les données des médias à partir d'un fichier TSV.
     *
     * @param filePath Chemin du fichier TSV.
     * @return Liste des médias.
     */
    public static List<Media> chargerMedias(String filePath) {
        List<Media> medias = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Ignorer la première ligne (en-têtes)
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");
                if (columns.length >= 5) {
                    String nom = columns[0];
                    String type = columns[1];
                    String periodicite = columns[2];
                    String echelle = columns[3];
                    double prix;
                    try {
                        prix = Double.parseDouble(columns[4]);
                    } catch (NumberFormatException e) {
                        prix = 0.0; // Valeur par défaut pour les prix non numériques
                    }

                    // Créer une instance de Media
                    Media media = new Media(medias.size() + 1, nom, null); // Remplacer 'null' par une Entite valide si nécessaire
                    media.setType(type);
                    media.setPeriodicite(periodicite);
                    media.setEchelle(echelle);
                    media.setPrix(prix);

                    medias.add(media);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement des données depuis le fichier : " + filePath, e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Erreur de format dans le fichier : " + filePath, e);
        }
        return medias;
    }
}
