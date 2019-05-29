import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
//* Python class
//python is a list of nodes
public class Python {
    //store python images and positions
    private ObservableList<Node> python;

    
    public Python(Group Python) {
        this.python = Python.getChildren(); // Initialize nodes
    }

    
    public ObservableList<Node> getPython() {
        return python;
    }
}