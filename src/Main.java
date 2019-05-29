import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.AudioClip;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.control.*;
public class Main extends Application{
	public static final int square = 40;
    public static final int width = 20 * square;
    public static final int height = 15 * square;
    // Background image
    public final static Image bcgImage = new Image("resources/fbc.png");
    
    private int Level = 1;
    private int points = 0;
    
    private boolean played = false;
    private int newPart = 0;
    
    private int stop = 0;
    private boolean sound = true;
    private boolean endGame = false;
    private boolean active = false;
    private double position1;
    private double position_1;
    private double position2;
    private double position_2;
    
    private Movement direction = new Movement();
    private char movement = 'R';
    private Node cPart;
    
    private Python python;
    private Mouse mouse;
    
    private Text message;
    private ImageView sHead, sNeck, sPart;
    
    private Timeline time = new Timeline();
    // Database management
    private Pair<String[],int[]> fetched;

 
    public static void main(String [] args){
		launch(args);
	}
    @Override
	public void start(Stage primaryStage){
    	Scene play = new Scene(getGame());
    	Scene home = new Scene(new Home().getRoot());
    	// Enter play
        home.setOnMouseClicked(event -> {
            primaryStage.setScene(play);
            primaryStage.show();
            active = true;
            time.play();
           
        });
        
     // Handle play events
        play.setOnKeyPressed(event -> {
            if (!played && stop == 0)
                return;

            switch (event.getCode()) {
                case UP:
                    if (direction.getDirection() != Movement.Movements.DOWN && stop != 1) {
                        direction.setDirection(Movement.Movements.UP);
                        soundOn('U');
                        movement = 'U';
                        sHead = Part.changeMovement(movement);
                        sHead.setTranslateX(position1);
                        sHead.setTranslateY(position_1);
                        python.getPython().set(0, sHead);
                    }
                    break;
                case DOWN:
                    if (direction.getDirection() != Movement.Movements.UP && stop != 1) {
                        direction.setDirection(Movement.Movements.DOWN);
                        soundOn('D');
                        movement = 'D';
                        sHead = Part.changeMovement(movement);
                        sHead.setTranslateX(position1);
                        sHead.setTranslateY(position_1);
                        python.getPython().set(0, sHead);
                    }
                    break;
                case LEFT:
                    if (direction.getDirection() != Movement.Movements.RIGHT && stop != 1) {
                        direction.setDirection(Movement.Movements.LEFT);
                        soundOn('L');
                        movement = 'L';
                        sHead = Part.changeMovement(movement);
                        sHead.setTranslateX(position1);
                        sHead.setTranslateY(position_1);
                        python.getPython().set(0, sHead);
                    }
                    break;
                case RIGHT:
                    if (direction.getDirection() != Movement.Movements.LEFT && stop != 1) {
                        direction.setDirection(Movement.Movements.RIGHT);
                        soundOn('R');
                        movement = 'R';
                        sHead = Part.changeMovement(movement);
                        sHead.setTranslateX(position1);
                        sHead.setTranslateY(position_1);
                        python.getPython().set(0, sHead);
                    }
                    break;
                case SPACE:
                    if (stop == 0) {
                        pause(python);
                    }
                    else {
                        resume(python);
                    }
                    break;
                default:
                    break;
            }

            played = false;
        });

    	
    	primaryStage.setTitle("PYTHON GAME");
        primaryStage.setResizable(false);
        primaryStage.setMaxWidth(width+6);
        primaryStage.setMaxHeight(height+36);

        primaryStage.setScene(home);
        primaryStage.show();
        startGame(python);

        
        //primary stage management
        //track changes
        //manage properties
        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

                if (endGame == true) {
                    active = false;
                    primaryStage.setScene(home);
                    primaryStage.show();
                    //display points while game runs
                    message.setText("Level: " + Level + "   Points: " + points);
                    mouse.move(python);
                    endGame = false;
                }
            }
        });


	}
    private Pane getGame() {
        // root Pane
        Pane root = new Pane();
        root.setPrefSize(width, height);
        // Background
        final ImageView bcgImage = new ImageView(Main.bcgImage);
        bcgImage.setLayoutY(40);
     // output points
        //output game level
        message = new Text("Level: " + Level + "   Score: " + points);
        message.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        message.setFill(Color.BLUE);
        message.setLayoutX(30);
        message.setLayoutY(30);

        DropShadow dropshadow = new DropShadow();
        dropshadow.setOffsetY(3.0f);
        dropshadow.setColor(Color.color(0.5f, 0.5f, 0.5f));

        message.setEffect(dropshadow);
        
     //sound image management
        Image Sound =  new Image("resources/sound_on.png");
        Image Mute =  new Image("resources/sound_off.png");
        ImageView sImage = new ImageView(Sound);
        sImage.setFitHeight(30);
        sImage.setPreserveRatio(true);
        sImage.setLayoutX(760);
        sImage.setLayoutY(5);
        sImage.setOnMouseClicked(event -> {
            if(sound == true) {
                sound = false;
                sImage.setImage(Mute);
            }else {
                sound = true;
                sImage.setImage(Sound);
            }
        });
        
        // Initialize game 
        Group snakeBody = new Group();
        python = new Python(snakeBody);
        mouse = new Mouse(python);
        
     // Keyframes
        KeyFrame frame = new KeyFrame(Duration.seconds(0.3), event -> {
            if(!active)
                return;

            if (python.getPython().size() <= 1) {
                cPart = python.getPython().get(0);
            }

            position2 = cPart.getTranslateX();
            position_2 = cPart.getTranslateY();
            // Position the new part in front of the current head
            Movement.movement(direction.getDirection(), python, cPart);

            position1 = cPart.getTranslateX();
            position_1 = cPart.getTranslateY();

            played = true;
            if (python.getPython().size() == 1) {
                if (newPart == 0) {
                    soundOn('M');
                    python.getPython().get(0).setTranslateX(position1);
                    python.getPython().get(0).setTranslateY(position_1);
                } else {
                    soundOn('M');
                    sHead = Part.changeMovement(movement);
                    sHead.setTranslateX(position1);
                    sHead.setTranslateY(position_1);
                    python.getPython().add(0, sHead);

                    sNeck = Part.changeMovement('S');
                    sNeck.setTranslateX(position2);
                    sNeck.setTranslateY(position_2);
                    python.getPython().set(1, sNeck);
                    newPart --;
                }
            } else if (python.getPython().size() > 1) {
                if (newPart == 0) {
                    soundOn('M');
                    sHead = Part.changeMovement(movement);
                    sHead.setTranslateX(position1);
                    sHead.setTranslateY(position_1);

                    sNeck = Part.changeMovement('S');
                    sNeck.setTranslateX(position2);
                    sNeck.setTranslateY(position_2);
                    python.getPython().add(0, sHead);
                    python.getPython().set(1, sNeck);
                    python.getPython().remove(python.getPython().size()-1);
                } else {
                    soundOn('M');
                    ImageView hImage = Part.changeMovement(movement);
                    hImage.setTranslateX(position1);
                    hImage.setTranslateY(position_1);
                    sNeck = Part.changeMovement('S');
                    sNeck.setTranslateX(position2);
                    sNeck.setTranslateY(position_2);
                    python.getPython().add(0, hImage);
                    python.getPython().set(1, sNeck);
                    newPart --;
                }
                }
         // Update the points
            message.setText("Level: " + Level + "   Points: " + points);

            
            // end game If python bites itself
            for (Node part : python.getPython()) {
                if (part != python.getPython().get(0) && position1 == part.getTranslateX()
                        && position_1 == part.getTranslateY()) {
                    soundOn('C');
                    soundOn('E');
                    stop(python);
                    break;
                }
            }
         // end game if python hits wall
            
            if (position1 < 0 || position1 >= width || position_1 < 40 || position_1 >= height) {
                soundOn('C');
                soundOn('E');
                stop(python);
            }
         

            // python feeds
            if (mouse.getFImage().getTranslateX() == position1 && mouse.getFImage().getTranslateY() == position_1) {
                mouse.move(python);
                points ++;
                newPart += Level;
                soundOn('B');
            }
         // Level based on points
             if (points <= 50 && Level != 3) {
                Level = 3;
            } if (points <= 25 && Level != 2) {
                Level = 2;
            } if (points <= 10 && Level != 1) {
                Level = 1;
            }
        });
     

        // Manage Timelines
        time.getKeyFrames().add(frame);
        time.setCycleCount(Timeline.INDEFINITE);
        // Scores 
        Pane p = new Pane();
        p.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        p.getChildren().addAll(bcgImage,message,sImage);

        root.getChildren().addAll(p,mouse.getFImage(),snakeBody);

        return root;
    }
    //start game
    //relaunch game
    private void startGame(Python python) {
        direction.setDirection(Movement.Movements.RIGHT);
        movement = 'R';
        sPart = Part.changeMovement(movement);
        sPart.setTranslateX(0);
        sPart.setTranslateY(280);
        python.getPython().add(sPart);
        Level = 1;
        newPart = 0;
        points = 0;
        time.play();
    }

    //stop game
 //check points
    private void stop(Python snake) {
        endGame = true;
        snake.getPython().clear();
        time.stop();
        message.setText("GAME OVER");
        points(points);
        startGame(snake);
        active = false;
        time.pause();
        stop = 0;
        position1 += 40; 
    }
    private void pause(Python python) {
        stop = 1;
        active = false;
        message.setText(" PAUSED");
        time.pause();
    }

    //resume from pause
    private void resume(Python python) {
        stop = 0;
        active = true;
        message.setText("Level: " + Level + "   Score: " + points);
        time.play();

    }

   //checks database points
    //if new points get into top five
    //insert the points
    private void points(int points){
        boolean top5 = false;
        fetched = DatabaseControl.getPoints();
        for(int i = 0; i< 5; i++)
            if (points > fetched.getValue()[i])
                top5 = true;
        if (top5){
            new Entries().display("Enter Name", "Enter Your Initials", points);
        }
        else
            new TopScores().display(fetched.getKey(),fetched.getValue());
    }
    //manage sounds
    private void soundOn(char direction) {
        if (sound) {
            switch (direction) {
                case 'U':
                    AudioClip u = new AudioClip(this.getClass().getResource("resources/up.wav").toString());
                    u.play();
                    break;
                case 'D':
                    AudioClip d = new AudioClip(this.getClass().getResource("resources/down.wav").toString());
                    d.play();
                    break;
                case 'L':
                    AudioClip l = new AudioClip(this.getClass().getResource("resources/left.wav").toString());
                    l.play();
                    break;
                case 'R':
                    AudioClip r = new AudioClip(this.getClass().getResource("resources/right.wav").toString());
                    r.play();
                    break;
                case 'B':
                    AudioClip b = new AudioClip(this.getClass().getResource("resources/bite.wav").toString());
                    b.play();
                    break;
                case 'E':
                    AudioClip e = new AudioClip(this.getClass().getResource("resources/ending.wav").toString());
                    e.play();
                    break;
                case 'C':
                    AudioClip c = new AudioClip(this.getClass().getResource("resources/crash.wav").toString());
                    c.play();
                    break;
                case 'M':
                    AudioClip m = new AudioClip(this.getClass().getResource("resources/move.wav").toString());
                    m.play();
                    break;
                case 'W':
                    AudioClip w = new AudioClip(this.getClass().getResource("resources/win.wav").toString());
                    w.play();
                    break;
                default:
                    break;
            }
        }
    }
   
   }

