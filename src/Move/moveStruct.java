package Move;
import java.io.IOException;
import java.util.ArrayList;

public interface moveStruct {
    //This is the interface of the move class
    String def_path = "C:\\Users\\CSC\\IdeaProjects\\Chess\\chess\\"; //location of the images of the pieces
    void moveTo(Tile destination) throws IOException, IllegalMove;
    //checks if a particular position is a valid position to move
    static boolean check(ArrayList<ArrayList<Boolean>> a) {
        for (ArrayList<Boolean> i : a) {
            for (Boolean j : i) {
                if (j) {
                    return true;
                }
            }
        }
        return false;

    }
}
