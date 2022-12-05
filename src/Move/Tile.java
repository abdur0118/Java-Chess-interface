package Move;
import game.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Tile extends StackPane {
    //This class represents a tile in the board
    int row, col;//position of a tile
    public String p;//the piece present on this tile null if no piece eg. pawn or bishop
    public Player.playerType pieceType;//piece type of the piece present on the tile black or white
    Tile(int col, int row) {
        //setting the positions
        this.col = col;
        this.row = row;
        //adding background colour based on the position
        BackgroundFill b = new BackgroundFill(((row + col) % 2) == 0 ? Color.BLACK : Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background back = new Background(b);
        this.setPrefSize(75,75);
        this.setBackground(back);
        //adding action listener to the stackpane
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(move.sourceTile==null){
                    if(Board.tiles[col][row].p!=null&&Board.tiles[col][row].pieceType.ordinal()==Board.turn) {
                        move.setSourceTile(Board.tiles[col][row]);
                    }
                    else{
                        System.out.println("Illegal");
                    }
                }
                else{
                    try {
                        new move().moveTo(Board.tiles[col][row]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalMove illegalMove) {
                        System.out.println(illegalMove);
                    }

                }

            }
        });

    }

    void setTile(Player.playerType id,String s) {
        //updates a tile
        Image img = null;
        try {
            img = new Image(new FileInputStream(move.def_path + id.toString().substring(0, 1) + s + ".gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView piece = new ImageView(img);
        this.getChildren().add(piece);
        this.setAlignment(piece, Pos.CENTER);
        pieceType=id;
        p = s;

        }
}
