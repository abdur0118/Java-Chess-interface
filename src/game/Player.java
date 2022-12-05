package game;
import Move.Board;

public class Player {
    //This contains human player details
    public enum playerType{
        WHITE,BLACK;
    }
    Board b;
    public String name="";//user name of the player
    public playerType id;//type of player black or white
    Player(playerType id,Board b,String name){
        this.id = id;
        this.b = b;
        this.name = name;
        Main.board.initialise(this.b,this.id);
    }

}

