package cz.cuni.mff.java.hw.fxwords;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        VBox vbox = new VBox();

        Button button = new Button("Select file");

        button.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                var map = countWordsFromFile(selectedFile);

                ObservableList<PieChart.Data> pieList = FXCollections.observableArrayList();
                for(Map.Entry<String, Integer> entry : map.entrySet()) {
                    pieList.add(new PieChart.Data(
                            String.format("%s (%d)", entry.getKey(),
                            entry.getValue()), entry.getValue())
                    );
                }

                PieChart pieChart = new PieChart(pieList);
                // If the count of children is 2 than there is already, in this case, shown chart
                if (vbox.getChildren().stream().count() == 2) {
                    vbox.getChildren().remove(1);
                }
                vbox.getChildren().add(pieChart);
            }
        });


        button.setMaxWidth(Double.MAX_VALUE);

        vbox.getChildren().add(button);

        stage.setScene(new Scene(vbox, 550, 400));
        stage.setTitle("Words");
        stage.show();
    }

    private static Map<String, Integer> countWordsFromFile(File file) {
        Map<String, Integer> map = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into words using regex expression
                String[] words = line.split("\\W+");
                for(String word : words) {
                    Integer wCount = 1;
                    if (map.containsKey(word)) {
                        wCount = map.get(word);
                        wCount = wCount + 1;
                    }
                    map.put(word, wCount);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, "WARNING: Cannot open selected file");
        }

        return map;
    }
}
