package Move;

public class IllegalMove extends Exception {
    @Override
    public String toString() {
        move.clear();
        return "Illegal Move";
    }
}
