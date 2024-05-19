package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatManager {

    public void ecrireBlocFichier(String nomFichier, String numeroPartie, int nombreJoueurs, String botEtDifficulte, String nomJoueurEtScore, String gagnant, int nombrePartie) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier, true))) {
            writer.write("Numéro de partie : " + numeroPartie + "\n");
            writer.write("Nombre de joueurs : " + nombreJoueurs + "\n");
            writer.write("Bot et difficulté : " + botEtDifficulte + "\n");
            writer.write("Nom du joueur et score : " + nomJoueurEtScore + "\n");
            writer.write("Gagnant : " + gagnant + "\n");
            writer.write("Nombre de partie : " + nombrePartie + "\n");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GameStats> lireStats(String nomFichier) {
        List<GameStats> stats = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String line;
            GameStats gameStats = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Numéro de partie")) {
                    if (gameStats != null) {
                        stats.add(gameStats);
                    }
                    gameStats = new GameStats();
                    gameStats.setNumeroPartie(line.split(": ")[1]);
                } else if (line.startsWith("Nombre de joueurs")) {
                    gameStats.setNombreJoueurs(Integer.parseInt(line.split(": ")[1]));
                } else if (line.startsWith("Bot et difficulté")) {
                    gameStats.setBotEtDifficulte(line.split(": ")[1]);
                } else if (line.startsWith("Nom du joueur et score")) {
                    gameStats.setNomJoueurEtScore(line.split(": ")[1]);
                } else if (line.startsWith("Gagnant")) {
                    gameStats.setGagnant(line.split(": ")[1]);
                } else if (line.startsWith("Nombre de partie")) {
                    gameStats.setNombrePartie(Integer.parseInt(line.split(": ")[1]));
                }
            }
            if (gameStats != null) {
                stats.add(gameStats);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }
}
