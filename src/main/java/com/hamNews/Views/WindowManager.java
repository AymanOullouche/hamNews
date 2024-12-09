package com.hamNews.Views;

import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class WindowManager {
    private static List<Stage> openStages = new ArrayList<>();

    public static void addWindow(Stage stage) {
        openStages.add(stage);
    }

    // Fermer toutes les fenêtres ouvertes
    public static void closeAllWindows() {
        for (Stage stage : openStages) {
            if (stage != null) {
                stage.close();  // Fermer chaque fenêtre
            }
        }
        openStages.clear();  // Vider la liste après fermeture des fenêtres
    }
}
