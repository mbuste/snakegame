/** Notifier class
 * Displays a window with a message and a close button
 */
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Notifier {
    // public display
     
    public static void display(String title, String message){
        Stage display = new Stage();
        display.initModality(Modality.APPLICATION_MODAL);
        display.setTitle(title);
        display.setMinWidth(250);

        Label Message = new Label();
        Message.setText(message);

        Button Close = new Button("Ok");
        Close.setOnAction(e->display.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(Message,Close);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20,20,20,20));

        Scene scene = new Scene(layout);
        display.setScene(scene);
        display.show();
    }
}
