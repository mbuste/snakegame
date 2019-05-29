// Top Scores class

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TopScores {

    // public display method
   //show top 5 points
    public static void display(String[] names, int[] scores) {
        Stage display = new Stage();
        display.initModality(Modality.APPLICATION_MODAL);
        display.setTitle("TOP SCORES");
        display.setMinWidth(250);
        display.setResizable(false);


        // Initialize GridPane layout 
        GridPane grid = new GridPane();
        grid.setHgap(50);
        grid.setVgap(10);

        // Set Labels
        //Arrange labels on grid
        // First row
        Label First = new Label();
        First.setText("1.");
        GridPane.setConstraints(First, 0, 0);
        Label FirstName = new Label();
        FirstName.setText(names[0]);
        GridPane.setConstraints(FirstName, 1, 0);
        Label First_lblScore = new Label();
        First_lblScore.setText("" + scores[0]);
        GridPane.setConstraints(First_lblScore, 2, 0);
        // Second row
        Label Second = new Label();
        Second.setText("2.");
        GridPane.setConstraints(Second, 0, 1);
        Label Second_lblName = new Label();
        Second_lblName.setText(names[1]);
        GridPane.setConstraints(Second_lblName, 1, 1);
        Label Second_lblScore = new Label();
        Second_lblScore.setText("" + scores[1]);
        GridPane.setConstraints(Second_lblScore, 2, 1);
        // Third row
        Label Third = new Label();
        Third.setText("3.");
        GridPane.setConstraints(Third, 0, 2);
        Label Third_lblName = new Label();
        Third_lblName.setText(names[2]);
        GridPane.setConstraints(Third_lblName, 1, 2);
        Label Third_lblScore = new Label();
        Third_lblScore.setText("" + scores[2]);
        GridPane.setConstraints(Third_lblScore, 2, 2);
        // Fourth row
        Label Fourth = new Label();
        Fourth.setText("4.");
        GridPane.setConstraints(Fourth, 0, 3);
        Label Fourth_lblName = new Label();
        Fourth_lblName.setText(names[3]);
        GridPane.setConstraints(Fourth_lblName, 1, 3);
        Label Fourth_lblScore = new Label();
        Fourth_lblScore.setText("" + scores[3]);
        GridPane.setConstraints(Fourth_lblScore, 2, 3);
        // Fifth row
        Label Fifth = new Label();
        Fifth.setText("5.");
        GridPane.setConstraints(Fifth, 0, 4);
        Label Fifth_lblName = new Label();
        Fifth_lblName.setText(names[4]);
        GridPane.setConstraints(Fifth_lblName, 1, 4);
        Label Fifth_lblScore = new Label();
        Fifth_lblScore.setText("" + scores[4]);
        GridPane.setConstraints(Fifth_lblScore, 2, 4);

        // Add the elements to the GridPane
        grid.getChildren().addAll(Fifth,Fifth_lblName,Fifth_lblScore,Fourth,Fourth_lblName,Fourth_lblScore,
                Third,Third_lblName,Third_lblScore,First,FirstName,First_lblScore,Second,Second_lblName,Second_lblScore);
        grid.setAlignment(Pos.CENTER);
        Button btClose = new Button("Close");
        // Communicate status with the Main class
        btClose.setOnAction(e -> {
            display.close();

        });

        // Combine the GridPane and a Close button in a VBox layout
        VBox layout = new VBox(35);
        layout.getChildren().addAll(grid, btClose);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30, 20, 40, 20));

        Scene scene = new Scene(layout);
        display.setScene(scene);
        display.show();
    }
}