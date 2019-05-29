import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//* Mouse class


public class Mouse{
    //mouse image
    private final Image mouseImage =  new Image("resources/Mouse.png");
    private final ImageView mouseI;
    //mouse position
    private int position3, position_3;

   //initialize position
    public Mouse(Python python) {
        this.mouseI = new ImageView(mouseImage);
        move(python);
    }

    /** move method
 Finds a position for mouse
 * checks if it is on python*/
    public void move(Python python) {
        this.position3 = ((int)(Math.random()*(Main.width-Main.square))/Main.square * Main.square);
        this.position_3 = ((int)(Math.random()*(Main.height-Main.square))/Main.square * Main.square);
        if (this.position_3 == 0) this.position_3 = position_3 +40;
        getFImage().setFitHeight(40);
        getFImage().setPreserveRatio(true);
        getFImage().setTranslateX(position3);
        getFImage().setTranslateY(position_3);
        if (this.position_3 == 0) this.position_3 = position_3 +40;
        getFImage().setFitHeight(40);
        getFImage().setPreserveRatio(true);
        getFImage().setTranslateX(position3);
        getFImage().setTranslateY(position_3);
        if (againstPython(python)) {
            move(python);
        }
    }

    /** againstPython method
 check mouse against python */
    public boolean againstPython(Python python) {
        for (Node part : python.getPython()) {
            if (mouseI.getTranslateX() == part.getTranslateX()
                    && mouseI.getTranslateY() == part.getTranslateY()) {
                return true;
            }
        }
        return false;
    }

    /** GETTER */
    public ImageView getFImage() {
        return mouseI;
    }
}