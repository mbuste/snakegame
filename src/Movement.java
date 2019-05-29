import javafx.scene.Node;
// Movements class
//Tracks Movement
//Update Noddes
public class Movement {
//stores current movement
    public enum Movements {
        UP, DOWN, LEFT, RIGHT
    }
    private Movements movement = Movements.RIGHT;

    //moves node to head
    public static void movement(Movements movement, Python python, Node cPart) {
        switch (movement) {
            case UP:
                cPart.setTranslateX(python.getPython().get(0).getTranslateX());
                cPart.setTranslateY(python.getPython().get(0).getTranslateY() - Main.square); // Move up
                break;
            case DOWN:
                cPart.setTranslateX(python.getPython().get(0).getTranslateX());
                cPart.setTranslateY(python.getPython().get(0).getTranslateY() + Main.square); // Move down
                break;
            case LEFT:
                cPart.setTranslateX(python.getPython().get(0).getTranslateX() - Main.square); // Move left
                cPart.setTranslateY(python.getPython().get(0).getTranslateY());
                break;
            case RIGHT:
                cPart.setTranslateX(python.getPython().get(0).getTranslateX() + Main.square); // Move Right
                cPart.setTranslateY(python.getPython().get(0).getTranslateY());
                break;
        }
    }

    //getter method
    public Movements getDirection() {
        return movement;
    }

    //setter method
    public void setDirection(Movements movement) {
        this.movement = movement;
    }

}