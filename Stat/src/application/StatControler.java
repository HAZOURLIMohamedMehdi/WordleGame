package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatControler {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis lineChartXAxis;

    @FXML
    private NumberAxis lineChartYAxis;

    @FXML
    private PieChart pieChart;

    private StatManager statManager;

    @FXML
    public void initialize() {
        statManager = new StatManager();
        String fichier = "C:\\Users\\etudiant\\eclipse-workspace\\Stat\\res\\Statistiques";
        try {
            List<GameStats> stats = statManager.lireStats(fichier);
            populateBarChart(stats);
            populateLineChart(stats);
            populatePieChart(stats);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateBarChart(List<GameStats> stats) {
        for (GameStats stat : stats) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Partie " + stat.getNumeroPartie());
            String[] joueurScores = stat.getNomJoueurEtScore().split(", ");
            for (String joueurScore : joueurScores) {
                String[] parts = joueurScore.split(" - ");
                String joueur = parts[0];
                int score = Integer.parseInt(parts[1]);
                series.getData().add(new XYChart.Data<>(joueur, score));
            }
            barChart.getData().add(series);
        }
    }

    private void populateLineChart(List<GameStats> stats) {
        Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();
        for (GameStats stat : stats) {
            String[] joueurScores = stat.getNomJoueurEtScore().split(", ");
            for (String joueurScore : joueurScores) {
                String[] parts = joueurScore.split(" - ");
                String joueur = parts[0];
                int score = Integer.parseInt(parts[1]);
                seriesMap.putIfAbsent(joueur, new XYChart.Series<>());
                seriesMap.get(joueur).setName(joueur);
                seriesMap.get(joueur).getData().add(new XYChart.Data<>(stat.getNumeroPartie(), score));
            }
        }
        lineChart.getData().addAll(seriesMap.values());
    }

    private void populatePieChart(List<GameStats> stats) {
        Map<String, Integer> winCount = new HashMap<>();
        for (GameStats stat : stats) {
            winCount.put(stat.getGagnant(), winCount.getOrDefault(stat.getGagnant(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : winCount.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }
}
