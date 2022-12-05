package game;
import Move.Board;
import Move.Tile;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
    //This class starts and ends the game
    static Board board;
    static String Wname=" ",Bname=" ";
    public static Boolean Whum=true,Bhum=false;
    public final static GridPane root = new GridPane(); // this is the root node
    public static Player wPlayer; // white player
    public static Player bPlayer; // black player
    final static Scene scene = new Scene(root,600,600);
    public static Stage primaryStage;
    void startGame(){
        //this method creates a form to collect the details of the players
        VBox container = new VBox(); //it is used to contain the form that is added to the root node
        //white player details
        Label Label1 = new Label("White player  ");
        RadioButton whitePlayerComputer = new RadioButton("Computer");
        RadioButton whitePlayerHuman = new RadioButton("Human");
        whitePlayerHuman.setSelected(true);
        ToggleGroup whiteTogglegroup = new ToggleGroup();
        whitePlayerHuman.setToggleGroup(whiteTogglegroup);
        whitePlayerComputer.setToggleGroup(whiteTogglegroup);
        TextField whiteTextField = new TextField();
        Label temp1 = new Label("Name ");
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(temp1,whiteTextField);
        hbox1.setSpacing(10);
        whiteTogglegroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton temp = (RadioButton) whiteTogglegroup.getSelectedToggle();
                whiteTextField.clear();
                if(temp == whitePlayerComputer){
                    Whum = false;
                    whiteTextField.setText("Random1");
                }
                else{
                    Whum = true;
                }
            }
        });

       //black player details
        Label Label2 = new Label("Black player  ");
        RadioButton blackPlayerComputer = new RadioButton("Computer");
        blackPlayerComputer.setSelected(true);
        RadioButton blackPlayerHuman = new RadioButton("Human");
        ToggleGroup blackTogglegroup = new ToggleGroup();
        blackPlayerHuman.setToggleGroup(blackTogglegroup);
        blackPlayerComputer.setToggleGroup(blackTogglegroup);
        TextField blackTextField = new TextField("Random2");
        Label temp2 = new Label("Name ");
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(temp2, blackTextField);
        hbox2.setSpacing(10);
        blackTogglegroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton temp = (RadioButton) blackTogglegroup.getSelectedToggle();
                blackTextField.clear();
                if (temp == blackPlayerComputer) {
                    Bhum = false;
                    blackTextField.setText("Random1");
                }
                else {
                    Bhum = true;
                }
            }
        });

        //submit button
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Wname = whiteTextField.getText();
                Bname = blackTextField.getText();
                if (!Wname.trim().isEmpty() && !Bname.trim().isEmpty()&& (Whum || Bhum)) {
                    root.getChildren().removeAll(container);// changes the content of scene
                    Game();
                }
                else if(!(Whum||Bhum)){
                    System.out.println("Both cannot be computer");
                }
                else {
                    System.out.println("Enter name");
                }
            }
        });

        //loading the form
        container.getChildren().addAll(Label1, whitePlayerComputer, whitePlayerHuman, hbox1
                , Label2, blackPlayerComputer, blackPlayerHuman, hbox2, submit);
        container.setLayoutX(100);
        container.setSpacing(20);
        root.getChildren().add(container);
        root.setAlignment(Pos.TOP_CENTER);
    }

    static void Game(){
        root.setAlignment(Pos.TOP_LEFT);
        board = new Board(root);//creates chess board and loads tiles with colors
        wPlayer = new Player(Player.playerType.WHITE,board,Wname);//creates players and loads the pieces of white side
        bPlayer = new Player(Player.playerType.BLACK,board,Bname);//creates players and loads the pieces of black side
        if(!Whum){
            Comp.moveTo();//initiates the move if computer is white
        }
    }

    @Override
    public void start(Stage Stage) throws Exception{
        primaryStage = Stage;
        startGame();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess Game");
        primaryStage.show();

    }
    public static void exit(Tile destination){
        //declares the winner if the piece to be removed is a king
        //Player.playerType Winner = destination.pieceType==Player.playerType.BLACK? Player.playerType.WHITE: Player.playerType.BLACK;
        String Winner = destination.pieceType==Player.playerType.BLACK?Main.wPlayer.name:Main.bPlayer.name;
        String Loser = destination.pieceType==Player.playerType.WHITE?Main.wPlayer.name:Main.bPlayer.name;
        Main.root.getChildren().clear();//removes the board
        //concluding form is loaded
        //analyze button
        Button analyze = new Button("Analyze winner ");
        //winner label
        Label declare = new Label("Winner is "+Winner);
        declare.setFont(new Font("Algerian",20));
        //exit button
        Button exit = new Button("Exit");
        VBox container = new VBox();
        container.getChildren().addAll(declare,analyze,exit);
        container.setSpacing(20);
        Main.root.add(container,0,0);
        Main.root.setAlignment(Pos.CENTER);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.primaryStage.close();
            }
        });
        File file = new File("C:\\Users\\CSC\\OneDrive\\Desktop\\lab\\oops java\\part B\\result.txt");
        FileWriter bw = null;
        try {
            bw = new FileWriter(file,true);
            bw.write(Winner+" won against "+Loser+"\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        analyze.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    BufferedReader b = new BufferedReader(new FileReader(file));
                    Pattern pat = Pattern.compile(Winner+".*won.*");
                    String s;
                    int count=0;
                    while((s= b.readLine())!=null){
                        Matcher mat = pat.matcher(s);
                        if(mat.matches()) {
                            System.out.println(mat.group());
                            count++;
                        }
                    }
                    System.out.println(Winner+" won "+count+" times");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
