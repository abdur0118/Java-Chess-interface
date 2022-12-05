package Move;
import javafx.scene.layout.GridPane;
import game.*;

public class Board {
    //This class represents a board
    final static String[][] initialPieceArrangement={{"P","P","P","P","P","P","P","P"},
                                                    {"R","N","B","Q","K","B","N","R"}};
    public static int turn;
    static GridPane root;
    public static Tile[][] tiles = new Tile[8][8];
    public Board(GridPane root){
        turn =Player.playerType.WHITE.ordinal();
        this.root = root;
        //creating tiles
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                tiles[j][i]=new Tile(j,i);
                this.root.add(tiles[j][i],j,i);
            }
        }

    }

    public void initialise(Board b,Player.playerType id)  {
        //loading the images
        switch (id){
            case BLACK:
                for(int i=1,stringIndex=0;i>=0;i--,stringIndex++){
                    for(int j=0;j<8;j++){
                        tiles[j][i].setTile(id,initialPieceArrangement[stringIndex][j]);
                    }
                }
                break;
            case WHITE:
                for(int i=6,stringIndex=0;i<8;i++,stringIndex++){
                    for(int j=0;j<8;j++){
                        tiles[j][i].setTile(id,initialPieceArrangement[stringIndex][j]);
                    }
                }

        }

    }
}
