/**
 * Entries class
 * Displays a window the asks the player for their initials, updates
 * and displays the top scores
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Entries {
    private String username = "ABC";
    private Pair<String[], int[]> data;

    /**
     * Public display method
 shows a display with a TextField and 
 prompts the player to enter their initials
 updates and displays the scores
     * */
    public String display(String title, String message, int score) {
        Stage display = new Stage();
        display.initModality(Modality.APPLICATION_MODAL);
        display.setTitle(title);
        display.setMinWidth(250);

        Label Message = new Label();
        Message.setText(message);

        TextField Initials = new TextField("ABC");

        // Only 3 characters input
        Initials.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Initials.getText().length() > 3) {
                    Initials.setText(Initials.getText().substring(0, 3));
                }
            }
        });

        // Input only Uppercase
        Initials.setTextFormatter(new TextFormatter<Object>(e -> {
            e.setText(e.getText().toUpperCase());
            return e;
        }));

        // Exit and show high scores
        Button OK = new Button("Ok");
        OK.setOnAction(e -> {
            if (proofEntry(Initials, Initials.getText())) {
                username = Initials.getText();
                display.close();
                DatabaseControl.insert(Initials.getText(), score);
                data = DatabaseControl.getPoints();
                new TopScores().display(data.getKey(), data.getValue());
            }
        });

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(Message, Initials, OK);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene(layout);
        display.setScene(scene);
        display.show();
        return username;
    }

    /** Private proofEntry
 returns true if the TextField contains only letters and exactly three initials
 otherwise resets the TextField, shows an error message in a display and returns
 false*/
    private boolean proofEntry(TextField initials, String text) {
        String regex="[a-zA-Z]+";
        if (text.matches(regex) && text.length() == 3) return true;
        else {
            new Notifier().display("Error", "Please Enter Three Letters Only");
            initials.setText("ABC");
            initials.requestFocus();
            return false;
        }
    }
}

