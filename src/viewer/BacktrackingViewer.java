package viewer;
/*
 * BacktrackingViewer
 *
 * displays backtracking data nicely in a GUI window
 * with a slider bar to traverse the list and help
 * find logic errors that are hard to find in a
 * console overflowing with debug output.
 *
 * @author dwg7486 (David Grzebinski)
 */

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * BacktrackingViewer javaFX Application class
 */
public class BacktrackingViewer extends Application {
    /** List of BacktrackingData to display */
    private static List<BacktrackingData> dataList = new ArrayList<>();

    /** Number (in terms of order added to the list) of the current Configuration being displayed */
    private int currentConfigNum;

    /** Default number of rows in the TextArea display */
    private final int DEFAULT_ROWS = 10;
    /** Default number of columns in the TextArea display */
    private final int DEFAULT_COLS = 50;

    /** Label String for displaying the total number of Configurations in the list */
    private final String TOTAL_CONFIG_LABEL = "  Total configs: %9d ";

    /** Label String for displaying the number of the current Configuration being displayed */
    private final String CURRENT_CONFIG_LABEL = "  Displaying config: %9d ";

    /** Label String for displaying if the current Configuration is valid */
    private final String IS_VALID_LABEL = "  isValid(): %5s ";

    /** Label String for displaying if the current Configuration is the goal Configuration. */
    private final String IS_GOAL_LABEL = "  isGoal(): %5s ";

    /** Font used in the BacktrackingViewer */
    private final Font font = Font.font("monospaced", 14);

    /**
     * Initialize the elements of the BacktrackingViewer GUI and show the stage.
     */
    @Override
    public void start(Stage stage) {
        // Show an error if nothing is in the list
        if (dataList.size() == 0) {
            Label errorLabel = new Label(
                    "The list of BacktrackingData was empty.  " +
                            "Make sure you're adding to the list in Backtracker" +
                            "using BacktrackingViewer.addBacktrackingData(...).");
            errorLabel.setTextFill(Color.RED);
            errorLabel.setFont(Font.font("monospaced bold", 16));
            errorLabel.setPadding(new Insets(20));
            Scene scene = new Scene(errorLabel);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            return;
        }

        // Set up the viewer GUI components
        stage.setTitle("Backtracking Viewer");
        Label titleLabel = new Label("- Backtracking Viewer -");
        titleLabel.setFont(Font.font("monospaced", FontWeight.BOLD, 16));
        titleLabel.setTooltip(new Tooltip("Created by David Grzebinski (dwg7486@rit.edu)"));

        BorderPane mainPane = new BorderPane();
        TextArea configDisplay = buildConfigDisplay();
        Slider configSlider = buildConfigSlider();
        Label totalConfigLabel = buildTotalConfigLabel();
        Label currentConfigLabel = buildCurrentConfigLabel();
        Label isValidLabel = buildIsValidLabel();
        Label isGoalLabel = buildIsGoalLabel();

        // update the display when the slider is changed
        configSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentConfigNum = newValue.intValue();
            BacktrackingData current = dataList.get(currentConfigNum);

            // update text and labels
            configDisplay.setText(current.getConfig().toString());
            currentConfigLabel.setText(String.format(CURRENT_CONFIG_LABEL, currentConfigNum));
            isValidLabel.setText(String.format(IS_VALID_LABEL, (current.isValid() ? "True" : "False")));
            isGoalLabel.setText(String.format(IS_GOAL_LABEL, (current.isGoal() ? "True" : "False")));

            // set some colors for visual aid
            if (current.isValid()) {
                configDisplay.setStyle("-fx-text-fill: black;");
                isValidLabel.setStyle("-fx-text-fill: black;");
            } else {
                configDisplay.setStyle("-fx-text-fill: red;");
                isValidLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Build the information pane
        TilePane pane = new TilePane();
        pane.setOrientation(Orientation.VERTICAL);
        pane.setTileAlignment(Pos.CENTER_RIGHT);
        pane.setPadding(new Insets(10));
        pane.getChildren().addAll(
                titleLabel, totalConfigLabel,
                currentConfigLabel, isValidLabel,
                isGoalLabel);

        BorderPane infoPane = new BorderPane();
        infoPane.setCenter(pane);

        // Combine everything together
        mainPane.setCenter(configDisplay);
        mainPane.setBottom(configSlider);
        mainPane.setRight(infoPane);

        // Show the GUI
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * Build the TextArea used to display the current Configuration
     */
    private TextArea buildConfigDisplay() {
        TextArea configDisplay = new TextArea();
        configDisplay.setFont(Font.font("monospaced", 14));
        configDisplay.setEditable(false);
        configDisplay.setPrefColumnCount(DEFAULT_COLS);
        configDisplay.setPrefRowCount(DEFAULT_ROWS);
        configDisplay.setPrefColumnCount(Math.max(DEFAULT_COLS, computeMaxWidth()));
        configDisplay.setPrefRowCount(Math.max(DEFAULT_ROWS, computeMaxHeight()));
        configDisplay.setText(dataList.get(0).getConfig().toString());
        return configDisplay;
    }

    /**
     * Build the slider used to scroll through the Configurations.
     */
    private Slider buildConfigSlider() {
        Slider configSlider = new Slider();
        configSlider.setMin(0);
        configSlider.setMax(dataList.size()-1);
        configSlider.setBlockIncrement(1);
        return configSlider;
    }

    /**
     * Build the label that shows the total number of Configurations
     */
    private Label buildTotalConfigLabel() {
        Label totalConfigLabel = new Label();
        totalConfigLabel.setFont(font);
        totalConfigLabel.setText(String.format(TOTAL_CONFIG_LABEL, dataList.size() - 1));
        return totalConfigLabel;
    }

    /**
     * Build the Label that shows the current Configuration number
     */
    private Label buildCurrentConfigLabel() {
        Label currentConfigLabel = new Label();
        currentConfigLabel.setFont(font);
        currentConfigLabel.setText(String.format(CURRENT_CONFIG_LABEL, currentConfigNum));
        return currentConfigLabel;
    }

    /**
     * Build the Label that shows if the current Configuration is valid
     */
    private Label buildIsValidLabel() {
        Label isValidLabel = new Label();
        isValidLabel.setFont(font);
        isValidLabel.setText(String.format(IS_VALID_LABEL, (dataList.get(0).isValid() ? "True" : "False")));
        return isValidLabel;
    }

    /**
     * Build the Label that shows if the current Configuration is the goal
     */
    private Label buildIsGoalLabel() {
        Label isGoalLabel = new Label();
        isGoalLabel.setFont(font);
        isGoalLabel.setText(String.format(IS_GOAL_LABEL, (dataList.get(0).isGoal() ? "True" : "False")));
        return isGoalLabel;
    }

    /**
     * Compute the width of the widest config string in chars
     * @return max width
     */
    private int computeMaxWidth() {
        int maxWidth = 0;
        for (BacktrackingData data : dataList) {
            String configString = data.getConfig().toString();
            int count = 0;
            char c;
            for (int i = 0; i < configString.length(); i++) {
                c = configString.charAt(i);
                if (c == '\n') {
                    if (count > maxWidth) maxWidth = count;
                    count = 0;
                } else count++;
            }
        }
        return maxWidth;
    }

    /**
     * Compute the height of the tallest config string in chars
     * @return max height
     */
    private int computeMaxHeight() {
        int maxHeight = 0;
        for (BacktrackingData data : dataList) {
            int height = 0;
            String configString = data.getConfig().toString();
            char c;
            for (int i = 0; i < configString.length(); i++) {
                c = configString.charAt(i);
                if (c == '\n') height++;
            }
            if (height > maxHeight) maxHeight = height;
        }
        return maxHeight;
    }

    /**
     * Add BacktrackingData to the dataList
     * @param data data to add
     */
    public static void addBacktrackingData(BacktrackingData data) {
        dataList.add(data);
    }

    /**
     * Launch the viewer application
     */
    public static void launchViewer() {
        Application.launch();
    }
}
